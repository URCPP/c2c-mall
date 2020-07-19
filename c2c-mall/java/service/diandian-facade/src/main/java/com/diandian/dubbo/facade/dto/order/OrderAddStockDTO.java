package com.diandian.dubbo.facade.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 增加库存DTO
 *
 * @author zzhihong
 * @date 2019-03-07
 */
@Data
public class OrderAddStockDTO implements Serializable {

    private static final long serialVersionUID = 312518898207965029L;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 仓库ID
     */
    private Long repositoryId;

    /**
     * 当前库存
     */
    private Integer currentStock;

    /**
     * 增加库存
     */
    private Integer addNum;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单详情ID
     */
    private Long orderDetailId;
}
