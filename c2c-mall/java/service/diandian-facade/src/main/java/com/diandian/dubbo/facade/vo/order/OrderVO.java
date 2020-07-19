package com.diandian.dubbo.facade.vo.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yqingyuan
 * @Date: 2019/2/28 14:33
 * @Version 1.0
 */
@Data
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal totalPrice;

    private String productImageUrl;

    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * SKUID
     */
    private Long skuId;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 规格信息 逗号隔开
     */
    private String specInfo;

    /**
     * 仓库ID
     */
    private Long repositoryId;

    /**
     * 仓库名称
     */
    private String repositoryName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer num;

    private BigDecimal serviceFee;
    private String recvName;
    private String recvPhone;
    private String recvAddress;
    private String recvPostCode;
    private String transportNo;
    private BigDecimal transportFee;
    private String transportFeeIntroduce;
    private Long transportId;
    private String transportName;
    private Long transportCompanyId;
    private String transportCompanyName;
    private Integer afterSaleFlag;
    private Integer shareFlag;
    private Integer state;
    private String remark;
    private Date createTime;
    private Date updateTime;

    private BigDecimal orderAmount;
    private BigDecimal actualAmount;
    private BigDecimal refundAmount;
    private Long shopId;
    private String shopName;
    private Long merchantId;
    private String merchantName;
    private Long merchantSoftwareTypeId;
    private String merchantSoftwareTypeName;
    private Integer orderType;




}
