package com.diandian.dubbo.facade.service.support;

/**
 * @author x
 * @date 2019-02-18
 */
public interface NoGenerator {

    /**
     * 获取申请单号
     *
     * @return
     */
    String getApplyNo();

    /**
     * 获取充值单号
     *
     * @return
     */
    String getRechargeNo();

    /**
     * 获取兑换单号
     *
     * @return
     */
    String getExchangeNo();

    /**
     * 获取商城订单单号
     *
     * @return
     */
    String getShopOrderNo();


    String getPublicOrderNo();

    /**
     * 获取奖励流水编号
     *
     * @return
     */
    String getRewardTradeNo();

    /**
     * 获取交易明细编号
     * @return
     */
    String getTradeNo();

    /**
     * 获取机构账户编号
     *
     * @return
     */
    String getOrgAccountNo();

    /**
     * 获取机构编号
     *
     * @return
     */
    String getOrgNo();

    /**
     * 获取商户编号
     *
     * @return
     */
    String getMerchantNo();

    /**
     * 获取会员编号
     *
     * @return
     */
    String getMemberNo();

    /**
     * 获取提现订单编号
     */
    String getWithdrawalNo();

    /**
     * 获取商户订单支付编号
     * @return
     */
    String getMerchantPayNo();

    /**
     * 获取商户线下支付编号
     * @return
     */
    String getMerchantOfflinePayNo();
}
