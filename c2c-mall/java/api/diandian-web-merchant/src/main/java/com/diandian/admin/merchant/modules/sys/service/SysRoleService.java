package com.diandian.admin.merchant.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.sys.SysRoleModel;
import com.diandian.dubbo.facade.dto.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:29
 */
public interface SysRoleService extends IService<SysRoleModel> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> listRoleIdListByCreater(Long createUserId);


    PageResult listPage(Map<String, Object> params);


    void saveSysRole(SysRoleModel role);


    void update(SysRoleModel role);

    void deleteBatchByRoleIds(Long[] roleIds);


    SysRoleModel getById(Long roleId);




}

