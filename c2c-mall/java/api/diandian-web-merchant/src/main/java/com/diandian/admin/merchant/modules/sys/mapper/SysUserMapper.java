package com.diandian.admin.merchant.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.admin.model.sys.SysUserModel;

import java.util.List;

/**
 * 系统用户
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:29
 */
public interface SysUserMapper extends BaseMapper<SysUserModel> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     * @return
     */
    List<String> listAllPermsByUserId(Long userId);

    /**
     * 用户名查询
     *
     * @param username
     * @return
     */
    SysUserModel getByUsername(String username);


    /**
     * 通过ID查询用户
     *
     * @param id
     * @return
     */
    SysUserModel getById(Long id);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> listAllMenuIdByUserId(Long userId);

}
