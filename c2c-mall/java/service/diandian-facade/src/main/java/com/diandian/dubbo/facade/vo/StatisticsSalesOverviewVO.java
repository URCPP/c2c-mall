package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售统计对象
 * @author cjunyuan
 * @date 2019/4/17 11:37
 */
@Getter
@Setter
@ToString
public class StatisticsSalesOverviewVO implements Serializable {

    private static final long serialVersionUID = -4723857505043218801L;

    private Integer tradeNum;

    private BigDecimal tradeAmount;

    private BigDecimal directTradeAmount;

    private BigDecimal serviceFee;

    private BigDecimal transportFee;

    private BigDecimal directTeamAmount;

    private BigDecimal otherTeamAmount;

    private BigDecimal settledAmount;

    private BigDecimal refundAmount;
}
