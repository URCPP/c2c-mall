package com.diandian.admin.business.modules.sys.service.impl;

import com.diandian.admin.business.modules.sys.service.ShiroService;
import com.diandian.admin.business.modules.sys.mapper.SysMenuMapper;
import com.diandian.admin.business.modules.sys.mapper.SysUserMapper;
import com.diandian.admin.business.modules.sys.mapper.SysUserTokenMapper;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.model.sys.SysMenuModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.admin.model.sys.SysUserTokenModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author x
 * @date 2018-11-08
 */
@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    SysMenuMapper sysMenuMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserTokenMapper sysUserTokenMapper;

    @Override
    public Set<String> listUserPermissions(SysUserModel user) {
        List<String> permsList = null;

        //系统管理员，拥有最高权限
        if(null != user && SysConstant.USER_SUPPER_ADMIN.equals(user.getId())){
            List<SysMenuModel> menuList = sysMenuMapper.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuModel menu : menuList){
                permsList.add(menu.getPerms());
            }
        //机构超级管理员，拥有机构的最高权限
        } else if(null != user && SysConstant.UserType.SUPER.getValue().equals(user.getType())){
            permsList = sysMenuMapper.listAllPermsByOrgId(user.getOrgId());
        } else{
            permsList = sysUserMapper.listAllPermsByUserId(user.getId());
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenModel getUserTokenByToken(String token) {
        return sysUserTokenMapper.getByToken(token);
    }

    @Override
    public SysUserModel getUserById(Long userId) {
        return sysUserMapper.getById(userId);
    }
}
