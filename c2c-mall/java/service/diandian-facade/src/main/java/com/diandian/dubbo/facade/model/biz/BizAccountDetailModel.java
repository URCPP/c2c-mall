package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 机构账号明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_account_detail")
public class BizAccountDetailModel extends BaseModel {

	private static final long serialVersionUID = 1L;

 	/**
	 * 交易编号
	 */
    @TableField("trade_no")
	private String tradeNo;
 
 	/**
	 * 交易类型（0收入，1支出）
	 */
    @TableField("trade_type")
	private Integer tradeType;
 
 	/**
	 * 业务类型（0.推荐奖励 1续费奖励 2待结算销售佣金转入 3待结算奖金转入 4提现失败退还，5提现支出 ）
	 */
    @TableField("bus_type")
	private Integer busType;
 
 	/**
	 * 交易金额
	 */
    @TableField("trade_amount")
	private BigDecimal tradeAmount;
 
 	/**
	 * 交易前金额
	 */
    @TableField("trade_start")
	private BigDecimal tradeStart;
 
 	/**
	 * 交易后金额
	 */
    @TableField("trade_end")
	private BigDecimal tradeEnd;
 
 	/**
	 * 机构ID
	 */
    @TableField("org_id")
	private Long orgId;
 
 	/**
	 * 从哪个机构获益
	 */
    @TableField("from_org_id")
	private Long fromOrgId;
 
 	/**
	 * 
	 */
    @TableField("remark")
	private String remark;
 
 
 
}
