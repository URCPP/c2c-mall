package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员卡券兑换记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Data
@TableName("member_exchange_log")
public class MemberExchangeLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 会员卡券兑换单号
     */
    @TableField("bill_no")
    private String billNo;

    /**
     * 兑换券订单类型(0 商家兑换;)
     */
    @TableField("type_flag")
    private Integer typeFlag;

    /**
     * 兑换状态(0 兑换成功;)
     */
    @TableField("state_flag")
    private Integer stateFlag;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 商户名
     */
    @TableField("merchant_name")
    private String merchantName;

    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 会员帐号
     */
    @TableField("member_account")
    private String memberAccount;

    /**
     * 会员昵称
     */
    @TableField("member_nick_name")
    private String memberNickName;

    /**
     * 充值前，会员余额
     */
    @TableField("member_balance")
    private BigDecimal memberBalance;

    /**
     * 兑换金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 对应的兑换券数量
     */
    @TableField("exchang_coupon_quota")
    private Integer exchangCouponQuota;

    /**
     * 兑换金额对应的购物券金额
     */
    @TableField("shopping_coupon_quota")
    private BigDecimal shoppingCouponQuota;

    /**
     * 兑换前兑换券数量
     */
    @TableField("before_exchange")
    private Integer beforeExchange;

    /**
     * 兑换前的购物券金额
     */
    @TableField("before_shopping")
    private BigDecimal beforeShopping;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
