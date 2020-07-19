package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商户会员兑换券变动台账表
 *
 * @author wbc
 * @date 2019/03/04
 */
@Data
@TableName("member_exchange_history_log")
public class MemberExchangeHistoryLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 会员卡券兑换单号
     */
    @TableField("bill_no")
    private String billNo;

    /**
     * 类型(0 发放; 1 消费)
     */
    @TableField("type_flag")
    private Integer typeFlag;

    /**
     * 订单类型(0 兑换券充值；1 会员储值赠送； 2 积分兑换)
     */
    @TableField("bill_type_flag")
    private Integer billTypeFlag;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 商户名
     */
    @TableField("merchant_account")
    private String merchantAccount;

    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 会员帐户
     */
    @TableField("member_account")
    private String memberAccount;

    /**
     * 兑换数量
     */
    @TableField("exchange_number")
    private Integer exchangeNumber;

    /**
     * 兑换前兑换券数量
     */
    @TableField("before_number")
    private Integer beforeNumber;

    /**
     * 兑换后数量
     */
    @TableField("after_number")
    private Integer afterNumber;


    /**
     *
     */
    @TableField("remark")
    private String remark;

}
