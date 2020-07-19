package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author cjunyuan
 * @date 2019/3/19 19:34
 */
@Getter
@Setter
@ToString
public class OrgAccountDetailVO implements Serializable {

    private static final long serialVersionUID = -3880476930441705830L;
    private String tradeNo;
    private String orgName;
    private Integer accountType;
    private Integer busType;
    private Integer tradeType;
    private BigDecimal tradeAmount;
    private BigDecimal tradeStart;
    private BigDecimal tradeEnd;
    private String fromOrgName;
    private String fromMchName;
    private Date createTime;
}
