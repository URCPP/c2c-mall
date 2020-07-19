package com.diandian.admin.business.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.admin.model.sys.SysMenuModel;
import com.diandian.admin.model.vo.MenuTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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


    /**
     * 机构类型ID查询菜单
     * @param orgTypeId
     * @return
     */
    List<SysMenuModel> listByOrgTypeId(@Param("orgTypeId") Long orgTypeId);

    /**
     * 查询机构的所有权限
     *
     * @param orgId 机构ID
     * @return
     */
    List<String> listAllPermsByOrgId(@Param("orgId") Long orgId);


    /**
     * 查询机构的所有菜单ID
     *
     * @param orgId 机构ID
     * @return
     */
    List<Long> listAllMenuIdByOrgId(@Param("orgId") Long orgId);

    /**
     *
     * 功能描述: 获取菜单树的列表
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/3/8 15:13
     */
    List<MenuTreeVO> treeList(@Param("params") Map<String, Object> params);

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
