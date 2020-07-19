package com.diandian.admin.merchant.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.sys.SysMenuModel;

import java.util.List;

/**
 * 菜单管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysMenuService extends IService<SysMenuModel> {


    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenuModel> listByParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuModel> listByParentId(Long parentId);


    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuModel> listNotButton();



    /**
     * 获取用户菜单列表
     */
    List<SysMenuModel> listUserMenuByUserId(Long userId);

    /**
     * 删除
     */
    void delete(Long menuId);


    /**
     * 角色id集合查询菜单
     * @param roleIdList
     * @return
     */
    List<SysMenuModel> listByRoleIdList(List<Long> roleIdList);

    /**
     * 查询所有菜单 并返回父节点名称
     * @return
     */
    List<SysMenuModel> listAllMenu();

    /**
     * 通过主键ID查询 并返回父节点名称
     * @param id
     * @return
     */
    SysMenuModel getById(Long id);


}

