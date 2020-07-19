package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 机构类型开通费用统计对象
 * @author cjunyuan
 * @date 2019/3/28 10:14
 */
@Getter
@Setter
@ToString
public class StatisticsTypeOpenFeeVO implements Serializable {

    private Long typeId;

    private Integer type;

    private Integer level;

    private String typeName;

    private Integer openCnt;

    private BigDecimal openingCost;
}
