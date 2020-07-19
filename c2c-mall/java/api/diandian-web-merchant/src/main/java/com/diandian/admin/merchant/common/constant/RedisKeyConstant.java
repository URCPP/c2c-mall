package com.diandian.admin.merchant.common.constant;

/**
 * @author zzhihong
 * @date 2018-09-22
 */
public class RedisKeyConstant {

    public final static String SMS_CODE_FOR_APPLY_CREDIT_CARD = "smscode:apply_credit_card:";

    public final static String SMS_LOGIN_CODE= "smscode:login_code:";

    public final static String SMS_OPENSHOP_CODE= "smscode:openshop_code:";

    public final static String SMS_BANK_CARD="smscode:bank_card:";

    public final static String SMS_RESET_PASSWORD="smscode:reset_password";

    /**
     * 短信验证码过期时间
     */
    public final static long SMS_CODE_VERIFICATION_EXPIRE = 5*60L;
}

