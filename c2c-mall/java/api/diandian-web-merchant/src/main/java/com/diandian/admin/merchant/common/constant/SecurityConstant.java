package com.diandian.admin.merchant.common.constant;

/**
 * 登录、权限常量
 * @author x
 */
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer ";

    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = "8dd76acdd2044f1a8975b4399344b626";

    /**
     * token参数头
     */
    String HEADER = "accessToken";

    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    String USERID = "userId";

    /**
     * 用户选择JWT保存时间参数头
     */
    String SAVE_LOGIN = "saveLogin";

    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_USER";

}
