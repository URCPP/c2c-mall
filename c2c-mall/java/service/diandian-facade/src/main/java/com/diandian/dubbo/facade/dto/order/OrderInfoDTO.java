package com.diandian.dubbo.facade.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 下单实体
 *
 * @author zzhihong
 */
@Data
public class OrderInfoDTO implements Serializable {

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 分享Id
     */
    private Long shareId;

    /**
     * 仓库ID
     */
    private Long repositoryId;

    /**
     * 运输方式ID
     */
    private Long transportId;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 会员消耗兑换券数量
     */
    private Integer exchangeCouponNum;

    /**
     * 规格值
     */
    private String skuValue;

    private Long shopId;
}
