package com.diandian.admin.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商家提现申请记录表
 *
 * @author jbh
 * @date 2019/02/26
 */
@Data
@TableName("merchant_withdraw_apply_log")
public class MerchantWithdrawApplyLogModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 提现单号
	 */
    @TableField("bill_no")
	private String billNo;
 
 	/**
	 * 商家ID
	 */
    @TableField("merchant_id")
	private Long merchantId;
 
 	/**
	 * 商家名称
	 */
    @TableField("merchant_name")
	private String merchantName;
 
 	/**
	 * 商户软件类型ID
	 */
    @TableField("soft_type_id")
	private Long softTypeId;

	/**
	 * 商户软件名称
	 */
	@TableField("soft_type_name")
	private String softTypeName;
 
 	/**
	 * 提现金额
	 */
    @TableField("withdraw_fee")
	private BigDecimal withdrawFee;
 
 	/**
	 * 实付金额
	 */
    @TableField("arrival_money")
	private BigDecimal arrivalMoney;
 
 	/**
	 * 提现费率
	 */
    @TableField("withdraw_rate")
	private BigDecimal withdrawRate;
 
 	/**
	 * 审核状态  0待审核，1审核通过，2审核不通过
	 */
    @TableField("audit_state")
	private Integer auditState;
 
 	/**
	 * 审核人ID
	 */
    @TableField("audit_user_id")
	private Long auditUserId;
 
 	/**
	 * 审核人姓名
	 */
    @TableField("audit_user_name")
	private String auditUserName;
 
 	/**
	 * 审核时间
	 */
    @TableField("audit_time")
	private Date auditTime;
 
 	/**
	 * 付款ID
	 */
    @TableField("payment_user_id")
	private Long paymentUserId;
 
 	/**
	 * 付款人
	 */
    @TableField("payment_user_name")
	private String paymentUserName;
 
 	/**
	 * 付款时间
	 */
    @TableField("payment_time")
	private Date paymentTime;
 
 	/**
	 * 付款银行单号
	 */
    @TableField("payment_bank_no")
	private String paymentBankNo;
 
 	/**
	 * 提现状态（0：进行中，1：提现成功，2：提现失败）
	 */
    @TableField("apply_state")
	private Integer applyState;
 
 	/**
	 * 提现失败原因
	 */
    @TableField("fail_reason")
	private String failReason;
 
 	/**
	 * 银行卡号
	 */
    @TableField("bank_card_no")
	private String bankCardNo;
 
 	/**
	 * 银行户名
	 */
    @TableField("bank_account_name")
	private String bankAccountName;
 
 	/**
	 * 银行名称
	 */
    @TableField("bank_name")
	private String bankName;
 
 	/**
	 * 分行名称
	 */
    @TableField("bank_branch_name")
	private String bankBranchName;
 
 
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;

 
}
