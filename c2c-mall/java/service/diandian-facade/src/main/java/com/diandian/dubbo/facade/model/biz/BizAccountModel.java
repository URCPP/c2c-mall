package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 机构账号表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_account")
public class BizAccountModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 机构ID
	 */
    @TableField("org_id")
	private Long orgId;
 
 	/**
	 * 账号编号
	 */
    @TableField("account_no")
	private String accountNo;
 
 	/**
	 * 可用余额
	 */
    @TableField("available_balance")
	private BigDecimal availableBalance;
 
 	/**
	 * 冻结金额
	 */
    @TableField("frozen_balance")
	private BigDecimal frozenBalance;
 
 	/**
	 * 未发放奖金
	 */
    @TableField("bonus")
	private BigDecimal bonus;
 
 	/**
	 * 累计奖金
	 */
    @TableField("total_bonus")
	private BigDecimal totalBonus;
 
 	/**
	 * 待结算销售佣金
	 */
    @TableField("commission")
	private BigDecimal commission;
 
 	/**
	 * 累计销售佣金
	 */
    @TableField("total_commission")
	private BigDecimal totalCommission;

	/**
	 * 机构名称
	 */
	@TableField(exist = false)
    private String orgName;
}
