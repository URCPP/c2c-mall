package com.diandian.dubbo.facade.dto.merchant;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ExchangeDTO implements Serializable {
    /**
     * 商户商品ID
     */
    private Long id;
    /**
     * 商户ID
     */
    private Long merchantId;
    private String merchantName;

    private Long skuId;

    /**
     * 商品单价
     */
    private BigDecimal amount;

    /**
     * 兑换商品总价
     */
    private BigDecimal totalAmount;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 兑换价
     */
    private Integer exchangePrice;

    /**
     * 兑换数量
     */
    private Integer number;

    /**
     * 兑换总数量
     */
    private Integer totalNum;


    /**
     * 会员帐号
     */
    private String memberAccount;

    private Long memberAccountId;

    /**
     * 会员ID
     */
    private Long memberId;

}
