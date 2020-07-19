package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/4/25 16:04
 */
@Getter
@Setter
@ToString
public class TransportFeeRuleVO implements Serializable {


    private static final long serialVersionUID = -4118990265494222371L;

    private Long transportRuleId;

    /**
     * 首值
     */
    private BigDecimal firstValue;

    /**
     * 运费
     */
    private BigDecimal firstFee;

    /**
     * 续值
     */
    private BigDecimal extValue;

    /**
     * 续费
     */
    private BigDecimal extFee;

    /**
     * 保费（运输保险费）
     */
    private BigDecimal insurance;
}
