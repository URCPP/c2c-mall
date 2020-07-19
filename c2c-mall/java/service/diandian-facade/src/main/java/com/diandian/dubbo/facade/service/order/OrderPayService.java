package com.diandian.dubbo.facade.service.order;


import com.diandian.dubbo.facade.model.order.OrderPayModel;

/**
 * 订单支付
 *
 * @author zweize
 * @date 2019/03/06
 */
public interface OrderPayService {

    /**
     * 根据交易单号 获取支付订单
     * @param tradeOrderNo
     * @return
     */
    OrderPayModel getByTradeOrderNo(String tradeOrderNo);

    /**
     * 新增
     * @param orderPayModel
     */
    void save(OrderPayModel orderPayModel);
}
