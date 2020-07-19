package com.diandian.dubbo.facade.vo.member;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author jbuhuan
 * @Date 2019/3/13 21:30
 */
@Data
public class MemberExchangeOderWeekVO implements Serializable {
    /**
     * 兑换订单周数量
     */
    private Integer exchangeOrderWeekNum;
    /**
     * 兑换订单周总额
     */
    private BigDecimal exchangeOrderWeekTotal;
}
