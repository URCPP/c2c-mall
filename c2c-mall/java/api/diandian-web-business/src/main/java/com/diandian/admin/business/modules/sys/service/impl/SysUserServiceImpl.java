package com.diandian.admin.business.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.sys.mapper.SysUserMapper;
import com.diandian.admin.business.modules.sys.mapper.SysUserTokenMapper;
import com.diandian.admin.business.modules.sys.service.SysRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserService;
import com.diandian.admin.business.modules.sys.vo.OrgApplyUserForm;
import com.diandian.admin.business.modules.sys.vo.SysUserForm;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.model.sys.SysRoleModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.sys.OrgQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOrgApplyModel;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.service.biz.BizOrgApplyService;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
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
    @Autowired
    private BizOrgApplyService bizOrgApplyService;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    @Override
    public SysUserModel getByUsername(String username) {
        return baseMapper.getByUsername(username);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Long orgId = (Long) params.get("orgId");

        IPage<SysUserModel> page = this.page(
                new PageWrapper<SysUserModel>(params).getPage(),
                new QueryWrapper<SysUserModel>().select("id","username","email","phone","avatar","state","create_time")
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .eq(orgId != null, "org_id", orgId).eq("del_flag", 0)
        );
        return new PageResult(page);
    }


    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserModel userModel = new SysUserModel();
        userModel.setPassword(newPassword);
        SysUserModel oldUser = this.getById(userId);
        AssertUtil.notNull(oldUser, "用户信息不存在");
        userModel.setOrgId(oldUser.getOrgId());
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
        //checkRole(user,user.getCreateBy());
        //保存用户与角色关系
        userRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysUser(SysUserForm user) {
        SysUserModel oldUser = baseMapper.getById(user.getId());
        AssertUtil.notNull(oldUser, "用户信息不存在");
        SysUserModel update = new SysUserModel();
        update.setId(oldUser.getId());
        update.setUsername(user.getUsername());
        update.setPhone(user.getPhone());
        update.setEmail(user.getEmail());
        update.setState(user.getState());
        update.setOrgId(user.getOrgId());
        update.setState(user.getState());
        update.setType(user.getType());
        if (StringUtils.isNotBlank(user.getNewPassword())){
            AssertUtil.isTrue(oldUser.getPassword().equals(new Sha256Hash(user.getPassword(), oldUser.getSalt()).toHex()), "原始密码不正确");
            update.setPassword(new Sha256Hash(user.getNewPassword(), oldUser.getSalt()).toHex());
        }
        baseMapper.updateById(update);
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
        //checkRole(user,user.getUpdateBy());

        //保存用户与角色关系
        userRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    @Override
    public void resetPwd(Long id) {
        SysUserModel user = new SysUserModel();
        SysUserModel oldUser = this.getById(id);
        AssertUtil.notNull(oldUser, "用户信息不存在");
        user.setOrgId(oldUser.getOrgId());
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
            SysUserModel oldUser = sysUserMapper.selectById(userId);
            AssertUtil.notNull(oldUser, "用户信息不存在");
            AssertUtil.isTrue(!SysConstant.UserType.SUPER.getValue().equals(oldUser.getType()), "超级管理员无法被删除");
            SysUserModel user = new SysUserModel();
            user.setDelFlag(SysConstant.STATUS_DELETED);
            user.setId(userId);
            user.setOrgId(oldUser.getOrgId());
            userList.add(user);

        }
        this.updateBatchById(userList);
    }

    @Override
    public List<Long> listAllMenuIdByUserId(Long userId) {
        return baseMapper.listAllMenuIdByUserId(userId);
    }

    @Override
    public SysUserForm getUserFormById(Long id){
        return baseMapper.getUserFormById(id);
    }

    @Override
    public PageResult listPage(Page<SysUserModel> page, Map<String, Object> params){
        Page<SysUserModel> resultPage = baseMapper.listPage(page, params);
        return new PageResult(resultPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateUserForOrgApply(OrgApplyUserForm userForm){
        AssertUtil.isTrue(null != userForm.getApplyId() && userForm.getApplyId() > 0, "请选择要添加账号的申请信息");
        BizOrgApplyModel orgApply = bizOrgApplyService.getById(userForm.getApplyId());
        AssertUtil.notNull(orgApply, "申请信息不存在");
        AssertUtil.notBlank(userForm.getUsername(), "用户名不能为空");
        AssertUtil.notBlank(userForm.getPassword(), "密码不能为空");
        QueryWrapper<SysUserModel> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username", userForm.getUsername());
        AssertUtil.isTrue(baseMapper.selectCount(userWrapper) == 0, "用户名已存在");
        OrgQueryDTO orgQuery = new OrgQueryDTO();
        orgQuery.setApplyId(userForm.getApplyId());
        SysOrgModel org = sysOrgService.getOne(orgQuery);
        AssertUtil.notNull(org, "机构申请未通过审核");

        if(null != userForm.getId() && userForm.getId() > 0){
            SysUserModel oldUser = baseMapper.selectById(userForm.getId());
            AssertUtil.notNull(oldUser, "参数错误");
            SysUserModel update = new SysUserModel();
            update.setId(oldUser.getId());
            if(StringUtils.isNotBlank(userForm.getNewPassword())){
                AssertUtil.isTrue(oldUser.getPassword().equals(new Sha256Hash(userForm.getPassword(), oldUser.getSalt()).toHex()), "原始密码不正确");
                update.setPassword(new Sha256Hash(userForm.getNewPassword(), oldUser.getSalt()).toHex());
            }
            update.setPhone(userForm.getPhone());
            update.setEmail(userForm.getEmail());
            update.setState(userForm.getState());
            baseMapper.updateById(update);
        } else {
            SysUserModel user = new SysUserModel();
            user.setUsername(userForm.getUsername());
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setPassword(new Sha256Hash(userForm.getPassword(), salt).toHex());
            user.setSalt(salt);
            user.setOrgId(org.getId());
            user.setType(0);
            user.setPhone(userForm.getPhone());
            user.setEmail(userForm.getEmail());
            user.setCreateBy(userForm.getCreateBy());
            user.setState(userForm.getState());
            baseMapper.insert(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void undoApply(Long orgId){
        List<Long> ids = sysUserMapper.getUserIdListByOrgId(orgId);
        if(ids.size() > 0){
            sysUserTokenMapper.deleteBatchIds(ids);
        }
        UpdateWrapper<SysUserModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("org_id", orgId);
        SysUserModel user = new SysUserModel();
        user.setDelFlag(BizConstant.STATE_DISNORMAL);
        sysUserMapper.update(user, wrapper);
    }

    @Override
    public void delete(Long id) {
        sysUserMapper.deleteById(id);
    }

    @Override
    public PageResult listPageMerchant(Map<String, Object> params) {
        IPage<SysUserModel> page=sysUserMapper.listPageMerchant(new PageWrapper<SysUserModel>(params).getPage(),params);
        return new PageResult(page);
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
