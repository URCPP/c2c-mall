package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 按天统计费用
 * @author cjunyuan
 * @date 2019/3/7 21:37
 */
@Getter
@Setter
@ToString
public class StatisticsByDayVO implements Serializable {

    private static final long serialVersionUID = 8345517466633146832L;

    private String date;

    private BigDecimal amount;
}
