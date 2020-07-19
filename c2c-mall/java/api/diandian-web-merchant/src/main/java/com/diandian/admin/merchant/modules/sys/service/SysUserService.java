package com.diandian.admin.merchant.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.dto.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 用户接口
 * @author x
 */
public interface SysUserService extends IService<SysUserModel> {


    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    SysUserModel getByUsername(String username);

    /**
     * 分页查询用户列表
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);

    /**
     * 保存用户
     */
    void saveSysUser(SysUserModel user);


    /**
     * 修改用户
     */
    void update(SysUserModel user);

    /**
     * 重置密码
     * @param id
     */
    void resetPwd(Long id);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> listAllMenuIdByUserId(Long userId);

    void deleteById(Long id);


}
