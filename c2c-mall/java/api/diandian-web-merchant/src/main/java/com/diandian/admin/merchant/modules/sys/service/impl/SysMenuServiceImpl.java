package com.diandian.admin.merchant.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.merchant.modules.sys.service.SysMenuService;
import com.diandian.admin.merchant.modules.sys.service.SysRoleMenuService;
import com.diandian.admin.merchant.modules.sys.mapper.SysMenuMapper;
import com.diandian.admin.merchant.modules.sys.service.SysUserService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.model.sys.SysMenuModel;
import com.diandian.admin.model.sys.SysRoleMenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单服务
 *
 * @author x
 * @date 2018/09/20
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuModel> implements SysMenuService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuModel> listUserMenuByUserId(Long userId) {
        //系统管理员，拥有最高权限
        if (SysConstant.USER_SUPPER_ADMIN.equals(userId)) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.listAllMenuIdByUserId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public List<SysMenuModel> listByParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuModel> menuList = listByParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuModel> userMenuList = new ArrayList<>();
        for (SysMenuModel menu : menuList) {
            if (menuIdList.contains(menu.getId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuModel> listByParentId(Long parentId) {
        return baseMapper.listByParentId(parentId);
    }

    @Override
    public List<SysMenuModel> listNotButton() {
        return baseMapper.listNotButton();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenuModel>().eq("menu_id", menuId));
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuModel> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuModel> menuList = listByParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuModel> getMenuTreeList(List<SysMenuModel> menuList, List<Long> menuIdList) {
        List<SysMenuModel> subMenuList = new ArrayList<>();

        for (SysMenuModel entity : menuList) {
            //目录
            if (SysConstant.MenuType.CATALOG.getValue().equals(entity.getType())) {
                entity.setChildren(getMenuTreeList(listByParentId(entity.getId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

    @Override
    public List<SysMenuModel> listByRoleIdList(List<Long> roleIdList) {
        return baseMapper.listByRoleIdList(roleIdList);
    }

    @Override
    public List<SysMenuModel> listAllMenu() {
        return baseMapper.listAllMenu();
    }

    @Override
    public SysMenuModel getById(Long id) {
        return baseMapper.getById(id);
    }

    @Override
    public boolean updateById(SysMenuModel entity) {
        return super.updateById(entity);
    }
}
