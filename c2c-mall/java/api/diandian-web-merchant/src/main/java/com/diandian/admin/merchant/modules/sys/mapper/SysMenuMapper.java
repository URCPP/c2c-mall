package com.diandian.admin.merchant.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.admin.model.sys.SysMenuModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysMenuMapper extends BaseMapper<SysMenuModel> {

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
     * 角色id集合查询菜单
     * @param roleIdList
     * @return
     */
    List<SysMenuModel> listByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

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
