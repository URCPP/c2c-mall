package com.diandian.admin.business.modules.sys.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.business.modules.sys.service.SysUserService;
import com.diandian.admin.business.modules.sys.service.SysUserTokenService;
import com.diandian.admin.business.modules.sys.vo.SysLoginForm;
import com.diandian.admin.business.modules.sys.vo.TokenVO;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.model.user.UserShopRoleModel;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.user.UserShopRoleService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 登录相关
 *
 * @author x
 * @date 2018/11/08
 */
@RestController
public class SysLoginController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private UserShopRoleService userShopRoleService;


    /**
     * 登录
     */
    @PostMapping("/sys/login")
    public RespData login(@RequestBody SysLoginForm form) throws IOException {

        //用户信息
        SysUserModel user = sysUserService.getByUsername(form.getUsername());

        //账号锁定
        if (null == user || SysConstant.STATUS_DISABLED.equals(user.getDelFlag())) {
            return RespData.fail("账号不存在");
        }

        if(3==user.getType()){
            return RespData.fail("账号不存在");
        }

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return RespData.fail("账号或密码不正确");
        }

        //账号锁定
        if (SysConstant.STATUS_DISABLED.equals(user.getState())) {
            return RespData.fail("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        TokenVO token = sysUserTokenService.createToken(user.getId());
        return RespData.ok(token.getToken());
    }

    /**
     * 商户后台登入
     */
    @PostMapping("/sys/merchantLogin")
    public RespData merchantLogin(@RequestBody SysLoginForm form) throws IOException {

        //用户信息
        SysUserModel user = sysUserService.getByUsername(form.getUsername());

        //店铺信息
       ShopInfoModel shopInfoModel=shopInfoService.getByUserId(user.getId());
        if(null!=shopInfoModel){
            if(null != user && (shopInfoModel.getPayState()==0)){
                return RespData.fail("请交纳质保金");
            }
            if(null != user && (shopInfoModel.getPayState()==2)){
           TokenVO token = sysUserTokenService.createToken(user.getId());
           return RespData.ok(token.getToken());
       }
        }

       

        //账号锁定
        if (null == user || SysConstant.STATUS_DISABLED.equals(user.getDelFlag())) {
            return RespData.fail("账号不存在");
        }

        if(3!=user.getType()){
            return RespData.fail("账号不存在");
        }

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return RespData.fail("账号或密码不正确");
        }

        UserShopRoleModel userShopRoleModel=userShopRoleService.getByUserId(user.getId());
        if(null==userShopRoleModel){
            return RespData.fail("您还不是商户");
        }

        //账号锁定
        if (SysConstant.STATUS_DISABLED.equals(user.getState())) {
            return RespData.fail("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        TokenVO token = sysUserTokenService.createToken(user.getId());
        return RespData.ok(token.getToken());
    }


    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public RespData logout() {
        sysUserTokenService.logout(getUserId());
        return RespData.ok();
    }

}
