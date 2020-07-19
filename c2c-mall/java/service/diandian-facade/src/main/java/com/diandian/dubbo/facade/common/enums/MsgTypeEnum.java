package com.diandian.dubbo.facade.common.enums;

/**
 * @author x
 * @date 2018-12-19
 */
public enum MsgTypeEnum {

    ONLINE("上线"),
    OFFLINE("离线"),
    PROCEED("收款成功"),
    GEN_QRCODE("生成二维码"),
    CASH_APPLY("提现申请"),
    AUTO_CASH("自动提现"),
    CASH_RECV("提现到账"),
    BILL_QUERY("账单查询"),
    SMS("短信通知");

    private String name;

    MsgTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
