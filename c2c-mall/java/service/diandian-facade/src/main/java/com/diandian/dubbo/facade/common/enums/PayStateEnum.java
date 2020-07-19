package com.diandian.dubbo.facade.common.enums;

/**
 * 订单状态枚举
 *
 * @author x
 * @date 2018-10-27
 */
public enum PayStateEnum {

    //订单创建  //等待付款
    ORDER_CREATE,

    //下单成功
    ORDER_DOING,

    //下单失败
    ORDER_FAIL,

    //交易处理中
    TRADE_DOING,

    //交易成功
    TRADE_SUCCESS,

    //交易失败
    TRADE_FAIL,

    //问题单
    ISSUE,

    //过期单
    EXPIRED,


    //退款中
    REFUND_DING,

    //退款成功
    REFUND_SUCCESS,

    //回调处理失败
    UNKNOWN;

}
