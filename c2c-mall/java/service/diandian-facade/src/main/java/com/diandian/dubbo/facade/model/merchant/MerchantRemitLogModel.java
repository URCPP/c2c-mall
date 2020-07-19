package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户汇款记录明细表
 *
 * @author jbh
 * @date 2019/03/29
 */
@Data
@TableName("merchant_remit_log")
public class MerchantRemitLogModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 类型（0 储备金充值   1 商城开通充值）
     */
    @TableField("type")
    private Integer type;

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
     * 汇款金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 汇款凭证地址
     */
    @TableField("proof_url")
    private String proofUrl;

    /**
     * 审核状态(0 待审核；  1 审核通过; 2审核失败)
     */
    @TableField("audit_flag")
    private Integer auditFlag;

    @TableField("audit_fail_reason")
    private String auditFailReason;

    @TableField("auditor")
    private String auditor;

    @TableField("audit_time")
    private Date auditTime;


}
