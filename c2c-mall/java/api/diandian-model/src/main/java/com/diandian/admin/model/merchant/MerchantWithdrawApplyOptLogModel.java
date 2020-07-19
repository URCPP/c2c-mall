package com.diandian.admin.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商家提现申请操用记录表
 *
 * @author jbh
 * @date 2019/02/26
 */
@Data
@TableName("merchant_withdraw_apply_opt_log")
public class MerchantWithdrawApplyOptLogModel extends BaseModel {

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
	 * 提现金额
	 */
    @TableField("amount")
	private BigDecimal amount;
 
 	/**
	 * 提现状态（0：进行中，1：提现成功，2：提现失败）
	 */
    @TableField("apply_state")
	private Integer applyState;
 
 	/**
	 * 操作记录
	 */
    @TableField("opt_record")
	private String optRecord;
 
 	/**
	 * 操作类型(0：软件开通 1：商家提现)
	 */
    @TableField("opt_type")
	private Integer optType;
 
 
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
}
