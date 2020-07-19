package com.diandian.admin.business.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.sys.service.SysMenuService;
import com.diandian.admin.business.modules.sys.service.SysRoleMenuService;
import com.diandian.admin.business.modules.sys.mapper.SysMenuMapper;
import com.diandian.admin.business.modules.sys.service.SysUserRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.model.sys.SysMenuModel;
import com.diandian.admin.model.sys.SysRoleMenuModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.admin.model.vo.MenuTreeVO;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysOrgService sysOrgService;

    @Override
    public List<SysMenuModel> listUserMenuByUser(SysUserModel user) {
        //系统管理员，拥有最高权限
        if (null != user && SysConstant.USER_SUPPER_ADMIN.equals(user.getId())) {
            return getAllMenuList(null);
        }
        if(null != user && SysConstant.UserType.SUPER.getValue().equals(user.getType())){
            List<Long> menuIdList = sysMenuMapper.listAllMenuIdByOrgId(user.getOrgId());
            return getAllMenuList(menuIdList);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.listAllMenuIdByUserId(user.getId());
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
        for (SysMenuModel entity : menuList) {
            List<SysMenuModel> subMenuList = listByParentId(entity.getId(), menuIdList);
            if(subMenuList.size() > 0){
                getMenuTreeList(subMenuList, menuIdList);
            }
            entity.setChildren(subMenuList);
        }
        return menuList;
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

    @Override
    public List<SysMenuModel> listUserMenu(SysUserModel userModel){
        if (SysConstant.USER_SUPPER_ADMIN.equals(userModel.getId())) {
            return baseMapper.selectList(new QueryWrapper<SysMenuModel>().orderByAsc("sort"));
        }
        SysOrgModel orgModel = sysOrgService.getById(userModel.getOrgId());
        AssertUtil.notNull(orgModel, "参数错误");
        if (SysConstant.UserType.SUPER.getValue().equals(userModel.getType())) {
            return baseMapper.listByOrgTypeId(orgModel.getOrgTypeId());
        }
        List<Long> roleIds = sysUserRoleService.listRoleId(userModel.getId());
        return baseMapper.listByRoleIdList(roleIds);
    }

    @Override
    public List<SysMenuModel> listByOrgTypeId(Long orgTypeId){
        return baseMapper.listByOrgTypeId(orgTypeId);
    }

    @Override
    public List<MenuTreeVO> treeList(Map<String, Object> params){
        return baseMapper.treeList(params);
    }

    @Override
    public List<MenuTreeVO> list() {
        return baseMapper.list();
    }
}
