package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 提现规则表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_withdrawal_rule")
public class BizWithdrawalRuleModel extends BaseModel {

	private static final long serialVersionUID = 1L;


	/**
	 * 规则名称
	 */
	@TableField("name")
	private String name;
 
 	/**
	 * 单笔最小提现金额
	 */
    @TableField("min_amount")
	private BigDecimal minAmount;
 
 	/**
	 * 单笔最高提现金额
	 */
    @TableField("max_amount")
	private BigDecimal maxAmount;
 
 	/**
	 * 当日最高提现次数
	 */
    @TableField("day_max_num")
	private Integer dayMaxNum;
 
 	/**
	 * 提现费率
	 */
    @TableField("fee")
	private BigDecimal fee;
 
 	/**
	 * 0禁用1启用(只能一个启用)
	 */
    @TableField("state")
	private Integer state;
 
 
 
}
