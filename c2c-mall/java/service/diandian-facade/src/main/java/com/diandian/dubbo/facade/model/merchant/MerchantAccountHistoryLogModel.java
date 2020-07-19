package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户资金账户变动明细表
 *
 * @author jbh
 * @date 2019/02/26
 */
@Data
@TableName("merchant_account_history_log")
public class MerchantAccountHistoryLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 备注
     */
    @TableField("remark")
    private Long remark;

    /**
     * 删除状态，0-未删除，1-已删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 业务类型（1-订单收入，2-销售收入，3-角色升级，4-直推奖励，5-直属消费返佣，6-直属销售返佣，7-下级收益，8-直属升级，9-冻结金额领取，10-购物消费，11-提现，12-冻结金额收入）
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 交易方式（1-余额，2-冻结余额）
     */
    @TableField("transaction_mode")
    private Integer transactionMode;

    /**
     * 交易号
     */
    @TableField("transaction_number")
    private String transactionNumber;

    /**
     * 交易金额
     */
    @TableField("transaction_amount")
    private BigDecimal transactionAmount;

    /**
     * 交易前金额
     */
    @TableField("amount_before_transaction")
    private BigDecimal amountBeforeTransaction;

    /**
     * 交易后金额
     */
    @TableField("amount_after_transaction")
    private BigDecimal amountAfterTransaction;

    /**
     * 交易类型（1-收入，2-支出）
     */
    @TableField("transaction_type")
    private Integer transactionType;

    /**
     * 来源（用户id或订单id）
     */
    @TableField("source")
    private Long source;

}
