package com.diandian.dubbo.facade.model.user;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserConfiguration implements Serializable {
    private Integer id;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 平台logo
     */
    private String platformLogo;

    /**
     * 客服热线
     */
    private String customerPhone;

    /**
     * 在线客服QQ
     */
    private String customerQqOnline;

    /**
     * 最低提现金额
     */
    private BigDecimal minWithdrawal;

    /**
     * 最低提现倍数
     */
    private BigDecimal minWithdrawalMultiple;

    /**
     * 手续费低于多少钱
     */
    private BigDecimal commissionChargeMin;

    /**
     * 手续费高于多少钱
     */
    private BigDecimal commissionChargeMax;

    /**
     * 手续费低于多少钱收取价格
     */
    private BigDecimal commissionMinMoney;

    /**
     * 手续费高于多少钱收取价格
     */
    private BigDecimal commissionMaxMoney;

    /**
     * 冻结返佣（%）
     */
    private BigDecimal freezeCommission;

    /**
     * 直属消费（%）
     */
    private BigDecimal immediateConsumption;

    /**
     * 直属销售（%）
     */
    private BigDecimal immediateSales;

    /**
     * 合伙人下级收益(%)
     */
    private BigDecimal juniorPartnerIncome;

    /**
     * 直推会员奖励
     */
    private BigDecimal directMember;

    /**
     * 直推会员升级奖励（%）
     */
    private BigDecimal directMemberUpgrade;

    /**
     * 升级商户金额
     */
    private BigDecimal upgradeMerchant;

    /**
     * 升级合伙人
     */
    private BigDecimal upgradePartner;

    /**
     * 销售服务费（%）
     */
    private BigDecimal salesServiceMoney;


}