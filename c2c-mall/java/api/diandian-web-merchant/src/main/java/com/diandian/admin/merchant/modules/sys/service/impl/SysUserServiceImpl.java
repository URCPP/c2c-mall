package com.diandian.admin.merchant.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.merchant.modules.sys.mapper.SysUserMapper;
import com.diandian.admin.merchant.modules.sys.service.SysRoleService;
import com.diandian.admin.merchant.modules.sys.service.SysUserRoleService;
import com.diandian.admin.merchant.modules.sys.service.SysUserService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统用户服务
 *
 * @author x
 * @date 2018/09/20
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserModel> implements SysUserService {

    private static final int TOKEN_EXPIRE = 1000 * 60 * 60 * 12;

    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private SysRoleService roleService;

    @Override
    public SysUserModel getByUsername(String username) {
        return baseMapper.getByUsername(username);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Long createUserId = (Long) params.get("createBy");

        IPage<SysUserModel> page = this.page(
                new PageWrapper<SysUserModel>(params).getPage(),
                new QueryWrapper<SysUserModel>().select("id","username","email","phone","avatar","state","create_time")
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .eq(createUserId != null, "create_by", createUserId).eq("del_flag", 0)
        );
        return new PageResult(page);
    }


    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserModel userModel = new SysUserModel();
        userModel.setPassword(newPassword);
        return this.update(userModel,
                new QueryWrapper<SysUserModel>().eq("id", userId).eq("password", password));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUser(SysUserModel user) {
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        this.save(user);
        //检查角色是否越权
        checkRole(user,user.getCreateBy());
        //保存用户与角色关系
        userRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserModel user) {
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }

        this.updateById(user);

        //检查角色是否越权
        checkRole(user,user.getUpdateBy());

        //保存用户与角色关系
        userRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    @Override
    public void resetPwd(Long id) {
        SysUserModel user = new SysUserModel();
        user.setId(id);
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash("123456", salt).toHex());
        user.setSalt(salt);
        this.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] userIds) {
        List<SysUserModel> userList = new ArrayList<>();
        for (Long userId : userIds) {
            SysUserModel user = new SysUserModel();
            user.setDelFlag(SysConstant.STATUS_DELETED);
            user.setId(userId);
            userList.add(user);

        }
        this.updateBatchById(userList);
    }

    @Override
    public List<Long> listAllMenuIdByUserId(Long userId) {
        return baseMapper.listAllMenuIdByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        baseMapper.deleteById(id);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserModel user,Long currentUserId) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (SysConstant.USER_SUPPER_ADMIN.equals(currentUserId)) {
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = roleService.listRoleIdListByCreater(user.getCreateBy());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new BusinessException("新增用户所选角色，不是本人创建");
        }
    }
}
