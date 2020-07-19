package com.diandian.admin.business.modules.sys.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.business.modules.sys.vo.OrgApplyUserForm;
import com.diandian.admin.business.modules.sys.vo.SysUserForm;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.dto.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 用户接口
 * @author x
 */
public interface SysUserService extends IService<SysUserModel> {


    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    SysUserModel getByUsername(String username);

    /**
     * 分页查询用户列表
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);

    /**
     * 保存用户
     */
    void saveSysUser(SysUserModel user);

    /**
     *
     * 功能描述: 更新用户信息
     *
     * @param user
     * @return
     * @author cjunyuan
     * @date 2019/3/2 11:42
     */
    void updateSysUser(SysUserForm user);


    /**
     * 修改用户
     */
    void update(SysUserModel user);

    /**
     * 重置密码
     * @param id
     */
    void resetPwd(Long id);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

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
     * @date 2019/2/18 16:56
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
     * @date 2019/2/19 10:35
     */
    PageResult listPage(Page<SysUserModel> page, Map<String, Object> params);

    /**
     *
     * 功能描述: 添加申请机构的系统用户信息
     *
     * @param orgApplyUserForm
     * @return
     * @author cjunyuan
     * @date 2019/2/27 20:00
     */
    void saveOrUpdateUserForOrgApply(OrgApplyUserForm orgApplyUserForm);

    /**
     *
     * 功能描述:
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/4/1 18:16
     */
    void undoApply(Long orgId);

    void delete(Long id);

    PageResult listPageMerchant(Map<String, Object> params);
}
