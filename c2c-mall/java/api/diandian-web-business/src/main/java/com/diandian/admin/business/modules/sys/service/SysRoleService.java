package com.diandian.admin.business.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.sys.SysRoleModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.dto.PageResult;

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
public interface SysRoleService extends IService<SysRoleModel> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> listRoleIdListByCreater(Long createUserId);


    PageResult listPage(Map<String, Object> params);


    void saveSysRole(SysRoleModel role, SysUserModel user);


    void update(SysRoleModel role, SysUserModel user);

    void deleteBatchByRoleIds(Long[] roleIds);

    SysRoleModel getById(Long roleId);

    /**
     *
     * 功能描述: 联表分页查询
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/19 10:19
     */
    PageResult listPage(Page<SysRoleModel> page, Map<String, Object> params);

    /**
     *
     * 功能描述: 联表列表查询
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/22 16:55
     */
    List<SysRoleModel> list(Map<String, Object> params);

    /**
     *
     * 功能描述: 查询角色对应的机构ID列表
     *
     * @param roleIds
     * @return
     * @author cjunyuan
     * @date 2019/2/22 17:09
     */
    Set<Long> getOrgIdListByRoleIdList(List<Long> roleIds);
}

