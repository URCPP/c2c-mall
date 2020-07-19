package com.diandian.admin.merchant.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.admin.model.sys.SysRoleMenuModel;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuModel> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> listMenuIdByRoleId(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatchByRoleIds(Long[] roleIds);

}
