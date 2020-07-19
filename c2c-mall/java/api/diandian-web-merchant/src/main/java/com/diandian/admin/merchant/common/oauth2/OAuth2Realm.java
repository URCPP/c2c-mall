package com.diandian.admin.merchant.common.oauth2;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.merchant.common.util.CodeGen;
import com.diandian.admin.merchant.modules.sys.service.ShiroService;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.member.MerchantTokenModel;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 认证Realm实现
 *
 * @author x
 * @date 2018/11/08
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Lazy
    @Autowired
    ShiroService shiroService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(接口保护，验证接口调用权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        MerchantInfoModel MerchantInfoModel = (MerchantInfoModel) principals.getPrimaryPrincipal();
        // 用户权限列表，根据用户拥有的权限标识与如 @permission标注的接口对比，决定是否可以调用接口
//        Set<String> permsSet = shiroService.listUserPermissions(user.getId());

        Set<String> permsSet = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        // 根据accessToken，查询用户token信息
        String phone = stringRedisTemplate.opsForValue().get(CodeGen.genTokenRedisKey(token));
        if (phone == null) {
            // token已经失效
            throw new IncorrectCredentialsException("登录失效，请重新登录");
        }
        // 查询用户信息
        MerchantInfoModel merchant = shiroService.getMerchantByPhone(phone);
        // 账号被锁定
        if (!SysConstant.STATUS_NORMAL.equals(merchant.getDisabledFlag())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(merchant, token, getName());
        return info;
    }
}
