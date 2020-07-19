package com.diandian.admin.business.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.business.modules.sys.service.SysRoleMenuService;
import com.diandian.admin.business.modules.sys.service.SysRoleService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysRoleModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author x
 * @date 2018/11/08
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己机构下的角色列表
        if (!SysConstant.USER_SUPPER_ADMIN.equals(getUserId())) {
            if(!SysConstant.ROOT_ORG.equals(getUser().getOrgTypeId())){
                params.put("orgId", getUser().getOrgId());
            } else {
                params.put("orgIsRoot", SysConstant.IS_YES);
            }
        }
        Page<SysRoleModel> page = new PageWrapper<SysRoleModel>(params).getPage();
        PageResult pageResult = sysRoleService.listPage(page, params);
        return RespData.ok(pageResult);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:list")
    public RespData select(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (!SysConstant.USER_SUPPER_ADMIN.equals(getUserId())) {
            if(!SysConstant.ROOT_ORG.equals(getUser().getOrgTypeId())){
                params.put("orgId", getUser().getOrgId());
            } else {
                params.put("orgIsRoot", SysConstant.IS_YES);
            }
        }
        List<SysRoleModel> list = sysRoleService.list(params);
        return RespData.ok(list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:list")
    public RespData info(@PathVariable("roleId") Long roleId) {
        /*SysRoleModel role = sysRoleService.selectById(roleId);
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.listMenuIdByRoleId(roleId);
        role.setMenuIdList(menuIdList);*/
        SysRoleModel role = sysRoleService.getById(roleId);
        if(role.getOrgId() != null && role.getOrgId() != 0L){
            SysOrgModel orgModel = sysOrgService.getById(role.getOrgId());
            role.setOrgName(orgModel.getOrgName());
        }
        return RespData.ok(role);
    }

    /**
     * 保存角色
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:role:add")
    public RespData add(@RequestBody SysRoleModel role) {
        AssertUtil.notBlank(role.getRoleName(), "角色名称不能为空");
        role.setCreateBy(getUserId());
        sysRoleService.saveSysRole(role, getUser());
        return RespData.ok();
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public RespData update(@RequestBody SysRoleModel role) {
        AssertUtil.notBlank(role.getRoleName(), "角色名称不能为空");
        role.setCreateBy(getUserId());
        sysRoleService.update(role, getUser());
        return RespData.ok();
    }

    /**
     * 删除角色
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public RespData delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatchByRoleIds(roleIds);
        return RespData.ok();
    }
}
