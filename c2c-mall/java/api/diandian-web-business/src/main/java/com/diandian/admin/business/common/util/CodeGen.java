package com.diandian.admin.business.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Date;

/**
 * @author x
 * @date 2018-11-16
 */
public class CodeGen {

    public static String genMerchantCode(String prefix) {
        return prefix + DateUtil.format(new Date(), "yyMMdd") + RandomUtil.randomNumbers(4);
    }
}
