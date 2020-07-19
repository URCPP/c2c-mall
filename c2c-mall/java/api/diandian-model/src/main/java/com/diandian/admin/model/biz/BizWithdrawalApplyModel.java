package com.diandian.admin.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 提现申请表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_withdrawal_apply")
public class BizWithdrawalApplyModel extends BaseModel {

	private static final long serialVersionUID = 1L;

 	/**
	 * 订单编号
	 */
    @TableField("order_no")
	private String orderNo;
 
 	/**
	 * 机构ID
	 */
    @TableField("agent_id")
	private Long agentId;
 
 	/**
	 * 提现金额
	 */
    @TableField("withdrawal_amount")
	private BigDecimal withdrawalAmount;
 
 	/**
	 * 提现前金额
	 */
    @TableField("withdrawal_start")
	private BigDecimal withdrawalStart;
 
 	/**
	 * 提现后金额
	 */
    @TableField("withdrawal_end")
	private BigDecimal withdrawalEnd;
 
 	/**
	 * 提现手续费
	 */
    @TableField("withdrawal_fee")
	private BigDecimal withdrawalFee;
 
 	/**
	 * 实际提现金额
	 */
    @TableField("actual_amount")
	private BigDecimal actualAmount;
 
 	/**
	 * 审核ID
	 */
    @TableField("audit_user_id")
	private Long auditUserId;
 
 	/**
	 * 审核状态（0待审核，1审核通过，2审核失败）
	 */
    @TableField("audit_state")
	private Integer auditState;
 
 	/**
	 * 付款人ID
	 */
    @TableField("payment_user_id")
	private Long paymentUserId;
 
 	/**
	 * 付款状态（0待付款1付款成功2付款失败）
	 */
    @TableField("payment_state")
	private Integer paymentState;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;
 
}
