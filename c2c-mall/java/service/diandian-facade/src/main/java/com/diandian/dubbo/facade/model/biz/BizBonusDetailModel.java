package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 奖金明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_bonus_detail")
public class BizBonusDetailModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 交易编号
	 */
    @TableField("trade_no")
	private String tradeNo;
 
 	/**
	 * 交易类型（0收入1支出）
	 */
    @TableField("trade_type")
	private Integer tradeType;
 
 	/**
	 * 业务类型（0推荐奖励1续费奖励2奖金发放）
	 */
    @TableField("bus_type")
	private Integer busType;
 
 	/**
	 * 交易金额
	 */
    @TableField("trade_amount")
	private BigDecimal tradeAmount;
 
 	/**
	 * 交易前，待发放奖金金额
	 */
    @TableField("trade_start")
	private BigDecimal tradeStart;
 
 	/**
	 * 交易后，待发放奖金金额
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
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
 	/**
	 * 发放人员ID
	 */
    @TableField("issue_user_id")
	private Long issueUserId;
 
 	/**
	 * 发放时间
	 */
    @TableField("issue_time")
	private Date issueTime;
 
 
 
}
