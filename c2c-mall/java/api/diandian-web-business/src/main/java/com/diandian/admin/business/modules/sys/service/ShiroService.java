package com.diandian.admin.business.modules.sys.service;


import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.admin.model.sys.SysUserTokenModel;

import java.util.Set;

/**
 * @author x
 * @date 2018-11-08
 */
public interface ShiroService {


    /**
     * 获取用户权限列表
     */
    Set<String> listUserPermissions(SysUserModel user);

    /**
     * 根据token获取
     * @param token
     * @return
     */
    SysUserTokenModel getUserTokenByToken(String token);

    /**
     * 根据用户ID获取
     * @param userId
     */
    SysUserModel getUserById(Long userId);
}
