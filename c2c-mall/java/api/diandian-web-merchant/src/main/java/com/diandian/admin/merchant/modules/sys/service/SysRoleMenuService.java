package com.diandian.admin.merchant.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.sys.SysRoleMenuModel;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysRoleMenuService extends IService<SysRoleMenuModel> {


    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> listMenuIdByRoleId(Long roleId);


    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatchByRoleIds(Long[] roleIds);

    /**
     * 更新角色菜单
     *
     *
     * @param roleId  角色
     * @param menuIds 菜单列表
     * @return
     */
    Boolean insertRoleMenus(Long roleId, String menuIds);

}

