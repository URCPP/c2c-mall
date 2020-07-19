package com.diandian.dubbo.facade.model.user;

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
@TableName("user_account_history_log")
public class UserAccountHistoryLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 商户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 资金变动类型(1商品收入，2提现)
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


}
