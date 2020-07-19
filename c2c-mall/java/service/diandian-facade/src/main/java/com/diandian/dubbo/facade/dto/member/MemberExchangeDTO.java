package com.diandian.dubbo.facade.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author x
 * @date 2018/09/15
 */
@Getter
@Setter
@ToString
public class MemberExchangeDTO implements Serializable {

    /**
     * 商户ID
     */
    private Long merchantId;
    private String merchantName;
    private String mechantAcc;

    /**
     * 会员帐号
     */
    private String memberAccount;

    /**
     * 充值金额
     */
    private BigDecimal amount;

    /**
     * 是否兑换兑换券
     */
    private Integer isExchange;

    /**
     * 是否兑换购物券
     */
    private Integer isShopping;

    /**
     * 兑换兑换券标志（0，兑换， 1 不兑换）
     */
    private Integer isExchangeSelFlag;

    /**
     *  购物券标志（0，兑换， 1 不兑换）
     */
    private Integer isShoppingSelFlag;

}
