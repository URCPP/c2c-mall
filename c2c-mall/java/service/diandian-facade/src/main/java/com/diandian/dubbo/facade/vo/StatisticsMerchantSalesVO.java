package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户商品销售概况对象
 * @author cjunyuan
 * @date 2019/4/12 15:49
 */
@Getter
@Setter
@ToString
public class StatisticsMerchantSalesVO implements Serializable {

    private static final long serialVersionUID = -5373565353193860762L;

    private Long merchantId;
    private Long parentId;
    private String code;
    private String name;
    private Integer tradeNum;
    private BigDecimal tradeAmount;
    private BigDecimal refundAmount;
    private BigDecimal receivedAmount;
    private Date createTime;
}
