package com.diandian.dubbo.facade.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 扣减库存DTO
 *
 * @author zzhihong
 * @date 2019-03-07
 */
@Data
public class OrderSubStockDTO implements Serializable {

    private static final long serialVersionUID = 312518898207965029L;

    /**
     * SKU ID
     */
    private Long skuId;

    private Long id;

    /**
     * 仓库ID
     */
    private Long repositoryId;

    /**
     * 当前库存
     */
    private Integer currentStock;

    /**
     * 扣减库存
     */
    private Integer subNum;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单详情ID
     */
    private Long orderDetailId;
}
