package com.diandian.admin.merchant.modules.biz.vo.member;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wubc
 * @date 2019/2/14 23:25
 */
@Data
public class MemberExchangeRuleVO {

    /**
     * 兑换基准金额
     */
    private BigDecimal amountBase;

    /**
     * 兑换积分
     */
    private Integer exchangeRate;

    /**
     * 购物券
     */
    private BigDecimal shoppingRate;

    /**
     * 商户累计兑换积分数
     */
    private Integer merchantExchangeSum;

    /**
     * 商户累计兑换购物数
     */
    private BigDecimal merchantShoppingSum;

}
