package com.diandian.admin.business.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.sys.SysMenuModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.admin.model.vo.MenuTreeVO;

import java.util.List;
import java.util.Map;

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
    List<SysMenuModel> listUserMenuByUser(SysUserModel user);

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



    /**
     *
     * 功能描述: 获取用户菜单列表
     *
     * @param userModel
     * @return
     * @author cjunyuan
     * @date 2019/2/18 14:31
     */
    List<SysMenuModel> listUserMenu(SysUserModel userModel);

    /**
     *
     * 功能描述: 获取机构类型菜单列表
     *
     * @param orgTypeId
     * @return
     * @author cjunyuan
     * @date 2019/2/18 14:31
     */
    List<SysMenuModel> listByOrgTypeId(Long orgTypeId);

    /**
     *
     * 功能描述: 获取菜单树的列表
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/3/8 15:13
     */
    List<MenuTreeVO> treeList(Map<String, Object> params);

    /**
     *
     * 功能描述: 获取菜单树的列表
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/8 15:13
     */
    List<MenuTreeVO> list();


}

