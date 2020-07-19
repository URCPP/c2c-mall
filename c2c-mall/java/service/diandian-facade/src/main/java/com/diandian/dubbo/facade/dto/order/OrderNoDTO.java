package com.diandian.dubbo.facade.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderNoDTO implements Serializable {
    private String orderNo;
    private BigDecimal orderAmount;
    private BigDecimal freight;
    private Long createTime;
}
