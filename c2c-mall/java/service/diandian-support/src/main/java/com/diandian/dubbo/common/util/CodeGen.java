package com.diandian.dubbo.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Date;

/**
 * @author x
 * @date 2018-11-16
 */
public class CodeGen {

    //分享汇 redis中token的key
    public static String REDIS_FXH_TOKEN_KEY = "supply:fxh:access_token";

    public static String genMerchantCode(String prefix) {
        return prefix + DateUtil.format(new Date(), "yyMMdd") + RandomUtil.randomNumbers(4);
    }

}
