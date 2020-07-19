package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Data
@TableName("merchant_account_info")
public class MerchantAccountInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 可用余额
     */
    @TableField("available_balance")
    private BigDecimal availableBalance;

    /**
     * 冻结余额
     */
    @TableField("freeze_balance")
    private BigDecimal freezeBalance;

    /**
     *
     */
    @TableField("remark")
    private String remark;

}
