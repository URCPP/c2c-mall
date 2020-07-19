package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 商户待付款积分订单
 * @author cjunyuan
 * @date 2019/5/17 9:53
 */
@Getter
@Setter
@ToString
public class MchIntegralOrderAmountResultDTO {

    private Integer freightBearer;

    private BigDecimal transportFee;

    private BigDecimal price;

    private Integer num;

    private BigDecimal serviceFee;
}
