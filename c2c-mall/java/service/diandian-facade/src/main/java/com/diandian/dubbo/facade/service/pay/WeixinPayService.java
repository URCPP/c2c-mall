package com.diandian.dubbo.facade.service.pay;

import com.diandian.dubbo.facade.dto.wx.WeixinPayMpOrderResultDTO;
import com.diandian.dubbo.facade.tuple.BinaryTuple;

import java.math.BigDecimal;

/**
 * 微信支付接口
 * @author cjunyuan
 * @date 2019/6/5 17:34
 */
public interface WeixinPayService {

    /**
     *
     * 功能描述: PC商品下单支付
     *
     * @param orderNo
     * @param ipAddress
     * @param host
     * @return
     * @author cjunyuan
     * @date 2019/6/10 10:26
     */
    String pcPlaceOrder(String orderNo, String host, String ipAddress);

    /**
     *
     * 功能描述: 手机商品下单支付
     *
     * @param orderNo
     * @param ipAddress
     * @param host
     * @return
     * @author cjunyuan
     * @date 2019/6/5 17:32
     */
    String h5PlaceOrder(String orderNo, String host, String ipAddress);

    /**
     *
     * 功能描述: 积分商城-兑换商品-支付运输费用
     *
     * @param orderNo
     * @param host
     * @param ipAddress
     * @return
     * @author cjunyuan
     * @date 2019/6/5 17:32
     */
    String h5PayTransportFee(String orderNo, String host, String ipAddress);

    /**
     *
     * 功能描述: 积分商城-兑换商品-支付运输费用
     *
     * @param orderNo
     * @param host
     * @param ipAddress
     * @param openId
     * @return
     * @author cjunyuan
     * @date 2019/6/5 17:32
     */
    WeixinPayMpOrderResultDTO mpPayTransportFee(String orderNo, String openId, String host, String ipAddress);

    /**
     *
     * 功能描述: 商户（充值，续费） 支付
     *
     * @param merchantId
     * @param amount
     * @param payType
     * @param ipAddress
     * @param host
     * @return
     * @author cjunyuan
     * @date 2019/6/5 17:33
     */
    BinaryTuple<String, String> pcMchPayFee(Long merchantId, BigDecimal amount, Integer payType, String host, String ipAddress);

    /**
     *
     * 功能描述: 手机端 - 商户（充值，续费） 支付
     *
     * @param merchantId
     * @param amount
     * @param payType
     * @param ipAddress
     * @param host
     * @return
     * @author cjunyuan
     * @date 2019/6/5 17:33
     */
    BinaryTuple<String, String> h5MchPayFee(Long merchantId, BigDecimal amount, Integer payType, String host, String ipAddress);

    /**
     *
     * 功能描述: 微信支付回调处理逻辑
     *
     * @param xmlData
     * @return
     * @author cjunyuan
     * @date 2019/6/11 18:09
     */
    //String wxPayNotify(String xmlData);

    /**
     *
     * 功能描述: PC端商户支付开通费用
     *
     * @param merchantId
     * @param ipAddress
     * @param host
     * @return
     * @author cjunyuan
     * @date 2019/6/12 10:39
     */
    BinaryTuple<String, String> pcMchPayOpeningCost(Long merchantId, String host, String ipAddress);

    /**
     *
     * 功能描述: 手机端 - 商户支付开通费用
     *
     * @param merchantId
     * @param ipAddress
     * @param host
     * @return
     * @author cjunyuan
     * @date 2019/6/5 17:33
     */
    BinaryTuple<String, String> h5MchPayOpeningCost(Long merchantId, String host, String ipAddress);


    /**
     *
     * 功能描述: 手机端下单mp（公众号）支付
     *
     * @param orderNo
     * @param notifyUrl
     * @param ipAddress
     * @param openId
     * @return
     * @author urcpp
     * @date 2019/9/23
     */
    WeixinPayMpOrderResultDTO mpOrderPay(String orderNo, String openId, String notifyUrl, String ipAddress);


    /**
     *
     * 功能描述: 手机端下单h5支付
     *
     * @param orderNo
     * @param ipAddress
     * @param notifyUrl
     * @return
     * @author urcpp
     * @date 2019/9/23
     */
    String h5OrderPay(String orderNo, String notifyUrl, String ipAddress);
}
