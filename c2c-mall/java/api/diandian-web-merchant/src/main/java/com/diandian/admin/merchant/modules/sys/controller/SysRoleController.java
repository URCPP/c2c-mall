package com.diandian.admin.merchant.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.sys.service.SysRoleMenuService;
import com.diandian.admin.merchant.modules.sys.service.SysRoleService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysRoleModel;
import com.diandian.dubbo.facade.dto.PageResult;
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

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (!SysConstant.USER_SUPPER_ADMIN.equals(getUserId())) {
            params.put("createUserId", getUserId());
        }

        PageResult page = sysRoleService.listPage(params);

        return RespData.ok(page);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:list")
    public RespData list() {
        QueryWrapper<SysRoleModel> wrapper = new QueryWrapper<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (!SysConstant.USER_SUPPER_ADMIN.equals(getUserId())) {
            wrapper.eq("create_by", getUserId());
        }
        List<SysRoleModel> list = sysRoleService.list(wrapper);
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
        sysRoleService.save(role);
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
        sysRoleService.update(role);
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
