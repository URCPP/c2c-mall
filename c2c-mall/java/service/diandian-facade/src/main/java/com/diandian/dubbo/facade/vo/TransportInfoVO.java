package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品运输方式对象
 * @author cjunyuan
 * @date 2019/4/22 20:01
 */
@Getter
@Setter
@ToString
public class TransportInfoVO implements Serializable {

    private static final long serialVersionUID = -4618365761009275123L;

    private Long repositoryId;

    private String repositoryName;

    private Long transportId;

    private Integer transportType;

    private String transportName;

    private BigDecimal feeType;

    private String tips;

    List<TransportFeeRuleVO> rules;
}
