package com.diandian.dubbo.facade.dto.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author cjunyuan
 * @date 2019/5/16 16:16
 */
@Getter
@Setter
@ToString
public class MerchantOrderTradeDTO implements Serializable {

    private String orderNo;

    private String skuName;

    private BigDecimal price;

    private Integer num;

    private Integer shareFlag;

    private Integer state;

    private String createTime;
}
