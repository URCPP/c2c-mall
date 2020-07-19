package com.diandian.admin.business.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.sys.mapper.SysRoleMapper;
import com.diandian.admin.business.modules.sys.mapper.SysUserRoleMapper;
import com.diandian.admin.business.modules.sys.service.SysUserRoleService;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.model.sys.SysUserRoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统用户角色关系服务
 *
 * @author x
 * @date 2018/09/20
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleModel> implements SysUserRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.remove(new QueryWrapper<SysUserRoleModel>().eq("user_id", userId));
        if (roleIdList == null || roleIdList.size() == 0) {
            return;
        }

        //保存用户与角色关系
//        List<SysUserRoleModel> list = new ArrayList<>(roleIdList.size());
        for (Long roleId : roleIdList) {
            SysUserRoleModel sysUserRoleEntity = new SysUserRoleModel();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);
//            list.add(sysUserRoleEntity);
            this.save(sysUserRoleEntity);
        }
//        this.insertBatch(list);
    }

    @Override
    public List<Long> listRoleId(Long userId) {
        List<SysUserRoleModel> list = baseMapper.selectList(new QueryWrapper<SysUserRoleModel>().eq("user_id", userId));
        List<Long> result = new ArrayList<>();
        if (null != list && list.size() > 0) {
            list.forEach(item -> {
                result.add(item.getRoleId());
            });
        }
        return result;
    }

    @Override
    public int deleteBatchByRoleIds(Long[] roleIds){
        return baseMapper.delete(new QueryWrapper<SysUserRoleModel>().in("role_id", Arrays.asList(roleIds)));
    }
}
