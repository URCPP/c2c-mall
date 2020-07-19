package com.diandian.admin.business.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.admin.business.modules.sys.vo.SysUserForm;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.dto.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:29
 */
public interface SysUserMapper extends BaseMapper<SysUserModel> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     * @return
     */
    List<String> listAllPermsByUserId(Long userId);

    /**
     * 用户名查询
     *
     * @param username
     * @return
     */
    SysUserModel getByUsername(String username);


    /**
     * 通过ID查询用户
     *
     * @param id
     * @return
     */
    SysUserModel getById(Long id);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> listAllMenuIdByUserId(Long userId);

    /**
     *
     * 功能描述: 通过ID获取用户信息，包括机构名称
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/18 16:58
     */
    SysUserForm getUserFormById(Long id);

    /**
     *
     * 功能描述: 联表分页查询
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/19 10:36
     */
    Page<SysUserModel> listPage(Page<SysUserModel> page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 查询机构下的用户ID列表
     *
     * @param orgId
     * @return
     * @author cjunyuan
     * @date 2019/4/1 18:21
     */
    List<Long> getUserIdListByOrgId(@Param(value = "orgId") Long orgId);

    Page<SysUserModel> listPageMerchant(Page<SysUserModel> page,@Param("params")Map<String, Object> params);
}
