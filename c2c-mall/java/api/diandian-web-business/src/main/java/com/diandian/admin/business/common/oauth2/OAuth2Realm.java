package com.diandian.admin.business.common.oauth2;

import com.diandian.admin.business.modules.sys.service.ShiroService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.admin.model.sys.SysUserTokenModel;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(接口保护，验证接口调用权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserModel user = (SysUserModel) principals.getPrimaryPrincipal();
        // 用户权限列表，根据用户拥有的权限标识与如 @permission标注的接口对比，决定是否可以调用接口
        Set<String> permsSet = shiroService.listUserPermissions(user);
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
        SysUserTokenModel sysUserToken = shiroService.getUserTokenByToken(token);
        if (sysUserToken == null || sysUserToken.getExpireTime().getTime() < System.currentTimeMillis()) {
            // token已经失效
            throw new IncorrectCredentialsException("登录失效，请重新登录");
        }
        // 查询用户信息
        SysUserModel user = shiroService.getUserById(sysUserToken.getUserId());
        if(null == user || SysConstant.STATUS_DISABLED.equals(user.getDelFlag())){
            throw new IncorrectCredentialsException("账号不存在，请重新登录");
        }
        // 账号被锁定
        if (!SysConstant.STATUS_NORMAL.equals(user.getState())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, getName());
        return info;
    }
}
