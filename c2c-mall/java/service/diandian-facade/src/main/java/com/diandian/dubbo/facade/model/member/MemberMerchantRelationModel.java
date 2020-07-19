package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户下的会员帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Data
@TableName("member_merchant_relation")
public class MemberMerchantRelationModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 会员帐号
     */
    @TableField("merchant_login_name")
    private String merchantLoginName;

    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     *
     */
    @TableField("member_login_name")
    private String memberLoginName;

    /**
     * 可用余额
     */
    @TableField("avail_balance")
    private BigDecimal availBalance;

    /**
     * 冻结余额
     */
    @TableField("freeze_balance")
    private BigDecimal freezeBalance;

    /**
     * 可用的兑换券数量
     */
    @TableField("exchange_coupon_num")
    private Integer exchangeCouponNum;

    /**
     * 累积的兑换券数量
     */
    @TableField("exchange_coupon_sum")
    private Integer exchangeCouponSum;

    /**
     * 可用的购物券金额
     */
    @TableField("shopping_coupon_amount")
    private BigDecimal shoppingCouponAmount;

    /**
     * 累积购物券金额
     */
    @TableField("shopping_coupon_sum")
    private BigDecimal shoppingCouponSum;

    /**
     * 累积消费次数
     */
    @TableField("consume_times_sum")
    private Integer consumeTimesSum;


}
