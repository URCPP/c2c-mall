package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MerchantInfoTotalVO {

    /**
     * 总收入
     */
    private BigDecimal totalIncome;

    /**
     * 今日营业额
     */
    private BigDecimal todayTurnover;
    /**
     * 购买总人数
     */
    private Integer totalMember;

    /**
     * 今日购买人数
     */
    private Integer todayMember;

    /**
     * 订单总数
     */
    private Integer totalOrder;

    /**
     * 今日订单数
     */
    private Integer todayOrder;

    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 门店头像
     */
    private String shopAvatar;


}
