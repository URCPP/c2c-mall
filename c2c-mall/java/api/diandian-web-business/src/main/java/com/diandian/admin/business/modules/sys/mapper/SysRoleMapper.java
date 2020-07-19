package com.diandian.admin.business.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.admin.model.sys.SysRoleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:29
 */
public interface SysRoleMapper extends BaseMapper<SysRoleModel> {

    /**
     *
     * 功能描述: 分页查询
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/19 10:17
     */
    Page<SysRoleModel> listPage(Page<SysRoleModel> page, @Param("params") Map<String, Object> params);

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> listRoleIdListByCreater(Long createUserId);

    /**
     * 通过条件查询角色列表
     * @param params
     * @return 角色列表
     */
    List<SysRoleModel> list(@Param("params") Map<String, Object> params);

    SysRoleModel getById(Long roleId);

    /**
     *
     * 功能描述: 查询角色对应的机构ID列表
     *
     * @param roleIds
     * @return
     * @author cjunyuan
     * @date 2019/2/22 17:09
     */
    Set<Long> getOrgIdListByRoleIdList(@Param("roleIds") List<Long> roleIds);

}
