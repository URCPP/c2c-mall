package com.diandian.dubbo.facade.common.enums;

/**
 * 支付通道枚举
 *
 * @author x
 * @date 2018-10-24
 */
public enum ChannelEnum {

    WXPAY_PERSON("微信个人"),

    ALIPAY_PERSON("支付宝个人"),

    ALIPAY_BANK_ACCOUNT("支付宝转账到银行卡"),

    UNION_PAY("云闪付");

    private String name;

    private ChannelEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
