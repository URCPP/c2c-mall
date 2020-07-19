package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户钱包帐户信息表
 *
 * @author jbh
 * @date 2019/02/21
 */
@Data
@TableName("merchant_wallet_info")
public class MerchantWalletInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 可用储备金
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 保证金
     */
    @TableField("margin_amount")
    private BigDecimal marginAmount;

    /**
     * 未兑换的兑换券量
     */
    @TableField("surplus_exchange_number")
    private Integer surplusExchangeNumber;

    /**
     * 己兑换的兑换数量
     */
    @TableField("exchange_number")
    private Integer exchangeNumber;

    /**
     * 商户累计消耗储备金
     */
    @TableField("amount_sum")
    private BigDecimal amountSum;


    /**
     *
     */
    @TableField("remark")
    private String remark;

}
