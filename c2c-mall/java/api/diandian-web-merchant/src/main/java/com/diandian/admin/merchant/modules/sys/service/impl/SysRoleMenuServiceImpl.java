package com.diandian.admin.merchant.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.merchant.modules.sys.service.SysRoleMenuService;
import com.diandian.admin.merchant.modules.sys.mapper.SysRoleMenuMapper;
import com.diandian.admin.model.sys.SysRoleMenuModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 系统角色菜单关系服务
 *
 * @author x
 * @date 2018/09/20
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuModel> implements SysRoleMenuService {

    @Override
    public List<Long> listMenuIdByRoleId(Long roleId) {
        return baseMapper.listMenuIdByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        this.deleteBatchByRoleIds(new Long[]{roleId});

        if (null == menuIdList || menuIdList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        for (Long menuId : menuIdList) {
            SysRoleMenuModel sysRoleMenuEntity = new SysRoleMenuModel();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);
            this.save(sysRoleMenuEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertRoleMenus(Long roleId, String menuIds) {
        SysRoleMenuModel condition = new SysRoleMenuModel();
        condition.setRoleId(roleId);
        this.remove(new QueryWrapper<>(condition));

        if (StrUtil.isBlank(menuIds)) {
            return Boolean.TRUE;
        }
        List<String> menuIdList = Arrays.asList(menuIds.split(","));
        for (String menuId : menuIdList) {
            SysRoleMenuModel roleMenu = new SysRoleMenuModel();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(Long.valueOf(menuId));
            this.save(roleMenu);
        }
        return Boolean.TRUE;
    }

    @Override
    public int deleteBatchByRoleIds(Long[] roleIds) {
        return baseMapper.deleteBatchByRoleIds(roleIds);
    }
}
