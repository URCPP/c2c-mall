package com.diandian.dubbo.facade.vo.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jbuhuan
 * @date 2019/3/6 11:46
 */
@Data
public class OrderNumVO implements Serializable {
    /**
     *待发货数量
     */
    private Integer unSendOutNum;
    /**
     *待收货数量
     */
    private Integer unReceivedNum;
    /**
     *总兑换订单数量
     */
    private Integer exchangeNum;

    /**
     *今日采购订单数
     */
    private Integer curOrderNum;

    /**
     *商品总数
     */
    private Integer productTotal;

    /**
     *今日兑换量
     */
    private Integer curExchangeNum;

    /**
     *总采购订单数量
     */
    private Integer orderNum;


}
