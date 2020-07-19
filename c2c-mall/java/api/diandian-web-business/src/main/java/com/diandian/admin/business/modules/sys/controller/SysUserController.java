package com.diandian.admin.business.modules.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.business.modules.sys.service.SysRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserService;
import com.diandian.admin.business.modules.sys.vo.PasswordForm;
import com.diandian.admin.business.modules.sys.vo.SysMerchant;
import com.diandian.admin.business.modules.sys.vo.SysUserForm;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 系统用户
 *
 * @author x
 * @date 2018/11/08
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private ShopInfoService shopInfoService;


    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (!SysConstant.USER_SUPPER_ADMIN.equals(getUserId())) {
            if(!SysConstant.ROOT_ORG.equals(getUser().getOrgTypeId())){
                params.put("orgId", getUser().getOrgId());
            } else {
                params.put("orgIsRoot", SysConstant.IS_YES);
            }
        }
        Page<SysUserModel> page = new PageWrapper<SysUserModel>(params).getPage();
        PageResult pageResult = sysUserService.listPage(page, params);
        return RespData.ok(pageResult);
    }

    /**
     * 店铺用户列表
     */
    @GetMapping("/listPageMerchant")
    public RespData listPageMerchant(@RequestParam Map<String, Object> params) {
        return RespData.ok(sysUserService.listPageMerchant(params));
    }

    /**
     * 店铺添加用户
     */
    @GetMapping("/merchantAdd")
    public RespData merchantAdd(@RequestParam Map<String, Object> params) {
        return RespData.ok(sysUserService.listPageMerchant(params));
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/merchantInfo")
    public RespData merchantInfo() {
        Map map=new HashMap();
        ShopInfoModel shopInfoModel=shopInfoService.getByUserId(getUserId());
        map.put("shop",shopInfoModel);
        map.put("userInfo",getUser());
        return RespData.ok(map);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public RespData info() {
        return RespData.ok(getUser());
    }

    /**
     * 修改登录用户密码
     */
    @PostMapping("/password")
    public RespData password(@RequestBody @Valid PasswordForm form) {
        AssertUtil.notBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return RespData.fail("原密码不正确");
        }
        return RespData.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:list")
    public RespData info(@PathVariable("userId") Long userId) {
        SysUserForm userForm = sysUserService.getUserFormById(userId);
        return RespData.ok(userForm);
    }

    /**
     * 保存用户
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:user:add")
    public RespData add(@RequestBody @Valid SysUserForm user) {
        AssertUtil.notBlank(user.getUsername(), "用户名不能为空");
        AssertUtil.notBlank(user.getPassword(), "密码不能为空");

        //校验用户名是否存在
        SysUserModel exists = sysUserService.getByUsername(user.getUsername());
        AssertUtil.isTrue(null == exists || SysConstant.IS_YES.equals(exists.getDelFlag()), "用户名已存在");
        this.setUserOrgInfo(user, true);
        if(!SysConstant.USER_SUPPER_ADMIN.equals(getUserId())){
            AssertUtil.isTrue(null != user.getOrgId()&& user.getOrgId() > 0, "参数错误");
        }
        SysUserModel userModel = new SysUserModel();
        BeanUtil.copyProperties(user, userModel);
        userModel.setCreateBy(getUserId());
        if(null != exists){
            userModel.setId(exists.getId());
            userModel.setOrgId(null == user.getOrgId() ? exists.getOrgId() : user.getOrgId());
            userModel.setPassword(user.getNewPassword());
            userModel.setDelFlag(SysConstant.IS_NO);
            sysUserService.update(userModel);
        }else{
            sysUserService.saveSysUser(userModel);
        }
        return RespData.ok();
    }

    /**
     * 保存用户
     */
    @PostMapping("/addMerchantUser")
    public RespData addMerchantUser(@RequestBody @Valid SysMerchant user) {
        AssertUtil.notBlank(user.getUsername(), "用户名不能为空");
        AssertUtil.notBlank(user.getPassword(), "密码不能为空");
        //校验用户名是否存在
        SysUserModel exists = sysUserService.getByUsername(user.getUsername());
        AssertUtil.isTrue(null == exists || SysConstant.IS_YES.equals(exists.getDelFlag()), "用户名已存在");
        SysUserModel sysUserModel=new SysUserModel();
        sysUserModel.setUsername(user.getUsername());
        sysUserModel.setPhone(user.getUsername());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUserModel.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        sysUserModel.setSalt(salt);
        sysUserModel.setOrgId(Long.valueOf(1));
        sysUserModel.setOrgTypeId(Long.valueOf(1));
        return RespData.ok();
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public RespData update(@RequestBody @Valid SysUserForm user) {
        AssertUtil.notBlank(user.getUsername(), "用户名不能为空");
        //校验用户名是否存在
        SysUserModel exists = sysUserService.getByUsername(user.getUsername());
        if (null != exists && !exists.getId().equals(user.getId())) {
            throw new BusinessException("用户名已存在");
        }
        this.setUserOrgInfo(user, false);
        sysUserService.updateSysUser(user);
        return RespData.ok();
    }

    @PostMapping("/resetPwd")
    @RequiresPermissions("sys:user:update")
    public RespData resetPwd(Long id) {
        AssertUtil.notNull(id, "id不能为空");
        if (!SysConstant.USER_SUPPER_ADMIN.equals(getUserId())) {
            throw new BusinessException("只有超级管理员才能重置密码");
        }
        sysUserService.resetPwd(id);
        return RespData.ok();

    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public RespData delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, SysConstant.USER_SUPPER_ADMIN)) {
            return RespData.fail("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return RespData.fail("当前用户不能删除");
        }
        sysUserService.deleteBatch(userIds);

        return RespData.ok();
    }

    private void setUserOrgInfo(SysUserForm user, boolean isRegister){
        if(!isRegister){
            SysUserModel oldUser = sysUserService.getById(user.getId());
            AssertUtil.notNull(oldUser, "用户信息不存在");
            if(SysConstant.UserType.SUPER.getValue().equals(oldUser.getType()) && SysConstant.UserType.GENERAL.getValue().equals(user.getType())){
                throw new BusinessException("无法将超级管理员的身份改成普通管理员");
            }
            user.setOrgId(oldUser.getOrgId());
            user.setOrgTypeId(oldUser.getOrgTypeId());
        }
        if(null != user.getRoleIdList() && user.getRoleIdList().size() > 0){
            Set<Long> orgIdList = roleService.getOrgIdListByRoleIdList(user.getRoleIdList());
            AssertUtil.isFalse(orgIdList.size() > 1, "请选择同一机构下的角色");
            if(orgIdList.size() > 0){
                Long orgId = orgIdList.iterator().next();
                SysOrgModel org = sysOrgService.getById(orgId);
                AssertUtil.notNull(org, "参数错误");
                if(SysConstant.UserType.SUPER.getValue().equals(user.getType())){
                    QueryWrapper<SysUserModel> userWrapper = new QueryWrapper<>();
                    userWrapper.eq("org_id", orgId);
                    userWrapper.eq("type", user.getType());
                    AssertUtil.isTrue(sysUserService.count(userWrapper) == 0, "同个机构只能有一个超级管理员");
                }
                user.setOrgId(orgId);
                user.setOrgTypeId(org.getOrgTypeId());
            }
        } else if(null != user.getOrgId() && user.getOrgId() > 0){
            SysOrgModel org = sysOrgService.getById(user.getOrgId());
            AssertUtil.notNull(org, "参数错误");
            user.setOrgId(user.getOrgId());
            user.setOrgTypeId(org.getOrgTypeId());
        } else {
            if(SysConstant.UserType.SUPER.getValue().equals(user.getType())){
                QueryWrapper<SysUserModel> userWrapper = new QueryWrapper<>();
                userWrapper.isNull("org_id");
                userWrapper.eq("type", user.getType());
                AssertUtil.isTrue(sysUserService.count(userWrapper) == 0, "系统超级管理员只能有一个");
            }
            user.setOrgId(null);
        }
    }
}
