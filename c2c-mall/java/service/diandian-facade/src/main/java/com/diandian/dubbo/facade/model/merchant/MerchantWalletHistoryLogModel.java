package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户软件变动历史表
 *
 * @author jbh
 * @date 2019/02/21
 */
@Data
@TableName("merchant_wallet_history_log")
public class MerchantWalletHistoryLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 商户名称
     */
    @TableField("merchant_name")
    private String merchantName;

    /**
     * 买家帐号
     */
    @TableField("member_account")
    private String memberAccount;

    /**
     * 类型（0 兑换商品； 1 充值）
     */
    @TableField("type_flag")
    private Integer typeFlag;

    /**
     * 更新前金额
     */
    @TableField("pre_amount")
    private BigDecimal preAmount;

    /**
     * 变动金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 更新后金额
     */
    @TableField("post_amount")
    private BigDecimal postAmount;

    /**
     * 操作记录
     */
    @TableField("opt_record")
    private String optRecord;


}
