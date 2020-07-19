package com.diandian.admin.merchant.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.admin.model.sys.SysRoleModel;

import java.util.List;

/**
 * 角色
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:29
 */
public interface SysRoleMapper extends BaseMapper<SysRoleModel> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> listRoleIdListByCreater(Long createUserId);

    /**
     * 通过机构ID查询角色列表
     * @param orgId 机构ID
     * @return 角色列表
     */
    List<SysRoleModel> listByOrgId(Long orgId);

    SysRoleModel getById(Long roleId);


}
