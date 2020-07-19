package com.diandian.dubbo.facade.service.order;

/**
 * @author x
 * @date 2019-03-11
 */
public interface OrderTransactionService {

    /**
     * 关闭订单并恢复库存
     *
     * @param orderNo
     */
    void closeOrderAndRecoverStock(String orderNo);
}
