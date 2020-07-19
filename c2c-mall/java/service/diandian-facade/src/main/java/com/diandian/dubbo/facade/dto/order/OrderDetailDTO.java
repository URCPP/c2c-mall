package com.diandian.dubbo.facade.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细对象
 * @author cjunyuan
 * @date 2019/6/5 15:08
 */
@Getter
@Setter
@ToString
public class OrderDetailDTO implements Serializable {

    private static final long serialVersionUID = 6108123000388093307L;

    private Long id;

    private String shopName;

    private String merchantName;

    private String orderNo;

    private Integer orderType;

    private Long productId;

    private Integer afterSaleFlag;

    private String productImageUrl;

    private String skuName;

    private String specInfo;

    private String repositoryName;

    private BigDecimal price;

    private BigDecimal addPrice;

    private Integer num;

    private BigDecimal serviceFee;

    private String transportName;

    private BigDecimal transportFee;

    private String recvName;

    private String recvPhone;

    private String recvAddress;

    private Integer state;

    private Date createTime;
}
