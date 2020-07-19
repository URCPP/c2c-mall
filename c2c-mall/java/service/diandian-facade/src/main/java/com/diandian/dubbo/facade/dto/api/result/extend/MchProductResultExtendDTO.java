package com.diandian.dubbo.facade.dto.api.result.extend;

import com.diandian.dubbo.facade.dto.api.result.MchProductResultDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 待处理的商户商品数据对象
 * @author cjunyuan
 * @date 2019/5/14 11:43
 */
@Getter
@Setter
@ToString
public class MchProductResultExtendDTO extends MchProductResultDTO {

    private static final long serialVersionUID = 5956677135699956737L;

    private Long skuId;

    private Integer transportType;

    private String thumbUrlStr;
}
