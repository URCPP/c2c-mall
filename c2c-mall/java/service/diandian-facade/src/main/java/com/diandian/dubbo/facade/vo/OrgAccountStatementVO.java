package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 对账单
 * @author cjunyuan
 * @date 2019/3/19 10:27
 */
@Getter
@Setter
@ToString
public class OrgAccountStatementVO implements Serializable {

    private static final long serialVersionUID = -5978950228831352782L;

    private String date;

    private Integer accountType;

    private Integer incomeCnt;

    private BigDecimal incomeAmount;

    private Integer expenditureCnt;

    private BigDecimal expenditureAmount;
}
