package com.diandian.admin.merchant.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Date;

/**
 * @author x
 * @date 2018-11-16
 */
public class CodeGen {

    //登录token redis key
    public static final String LOGIN_TOKEN_NAME = "member:login:token:%s";

    public static String genMerchantCode(String prefix) {
        return prefix + DateUtil.format(new Date(), "yyMMdd") + RandomUtil.randomNumbers(4);
    }

    public static String genTokenRedisKey(String token){
        return String.format(LOGIN_TOKEN_NAME, token);
    }
}
