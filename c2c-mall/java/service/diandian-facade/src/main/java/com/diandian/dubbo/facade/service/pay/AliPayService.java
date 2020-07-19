package com.diandian.dubbo.facade.service.pay;


import java.math.BigDecimal;

public interface AliPayService {

    /**
     * 商品下单支付
     * @param orderNo
     * @return
     */
    String placeOrder(String orderNo,Long minutes);

    /**
     * 手机商品下单支付
     * @param orderNo
     * @return
     */
    String phoneOrder(String orderNo,Long minutes);

    /**
     * 积分商城-兑换商品-支付运输费用
     * @param orderNo
     * @return
     */
    String payTransportFee(String orderNo);

    /**
     * 商户（充值，续费） 支付
     * @param merchantId
     * @param amount
     * @param payType
     * @param tradeWay
     * @return
     */
    String merchantPayFee(Long merchantId, BigDecimal amount, Integer payType, String tradeWay);

    /**
     * 手机端 - 商户（充值，续费） 支付
     * @param merchantId
     * @param amount
     * @param payType
     * @param tradeWay
     * @return
     */
    String phoneMerchantPayFee(Long merchantId, BigDecimal amount, Integer payType, String tradeWay);

    /**
     * 手机端 - 商户缴纳保证金 支付
     * @param merchantId
     * @param amount
     * @param payType
     * @param tradeWay
     * @return
     */

    String phonePaySecurityDeposit(Long merchantId, BigDecimal amount, Integer payType, String tradeWay);

    /**
     * 手机端 - 商户缴纳保证金 支付
     * @param merchantId
     * @param amount
     * @param payType
     * @param tradeWay
     * @return
     */

    String phonePayMargin(Long merchantId, BigDecimal amount, Integer payType, String tradeWay);
}
