package com.diandian.dubbo.facade.vo.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author jbuhuan
 * @date 2019/3/6 19:45
 */
@Data
public class OrderExchangeVO implements Serializable {
    private String orderNo;
    private BigDecimal orderAmount;
    private BigDecimal actualAmount;
    private Long merchantId;
    private Integer orderType;
    private Date createTime;
    private Date updateTime;
    private Long id;
    private String productImageUrl;
    private Long skuId;
    private String skuName;
    private Long repositoryId;
    private String repositoryName;
    private BigDecimal price;
    private BigDecimal addPrice;
    private Integer num;
    private BigDecimal serviceFee;
    private String recvName;
    private String recvPhone;
    private String recvAddress;
    private BigDecimal transportFee;
    private Long transportId;
    private String transportName;
    private Long transportCompanyId;
    private String transportCompanyName;
    private Integer state;
    private Date payTime;
    private Date transmitTime;
    private Date confirmRecvTime;
    private Date completeTime;
    private Long memberId;
    private String memberName;
    private String shopName;
    private List<OrderDetailExpressInfoVO> expressInfoList;
}
