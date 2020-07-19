package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 机构申请信息列表对象
 * @author cjunyuan
 * @date 2019/2/20 9:25
 */
@Data
public class SoftWareStrategyVO implements Serializable {

    private static final long serialVersionUID = 1627841679587925474L;

    private Long id;

    private String typeName;

    private String softwareTypeName;

    private Integer strategyType;

    private BigDecimal rewardValue;

    private Integer rewardType;

    private String state;

    private String remark;

    private Date createTime;

    private String rewardTypeName;

}
