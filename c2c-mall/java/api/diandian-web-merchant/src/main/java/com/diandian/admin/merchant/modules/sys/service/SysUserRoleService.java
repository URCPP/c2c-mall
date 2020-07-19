package com.diandian.admin.merchant.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.sys.SysUserRoleModel;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:29
 */
public interface SysUserRoleService extends IService<SysUserRoleModel> {


    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> listRoleId(Long userId);


    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatchByRoleIds(Long[] roleIds);

}

