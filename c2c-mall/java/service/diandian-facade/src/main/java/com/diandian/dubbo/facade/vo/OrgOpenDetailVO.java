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
 * @date 2019/4/4 14:30
 */
@Getter
@Setter
@ToString
public class OrgOpenDetailVO implements Serializable {

    private static final long serialVersionUID = 5418283100955784248L;
    private String tradeNo;
    private String orgName;
    private Integer busType;
    private Integer tradeType;
    private Integer tradeNum;
    private Integer tradeStart;
    private Integer tradeEnd;
    private String orgTypeName;
    private String softwareTypeName;
    private String fromOrgName;
    private String fromMchName;
    private Date createTime;
}
