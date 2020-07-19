package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 销售结算明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_commission_detail")
public class BizCommissionDetailModel extends BaseModel {

	private static final long serialVersionUID = 1L;


	/**
	 * 订单详情ID
	 */
	@TableField("order_detail_id")
	private Long orderDetailId;

 	/**
	 * 订单编号
	 */
    @TableField("order_no")
	private String orderNo;
 
 	/**
	 * 
	 */
    @TableField("trade_no")
	private String tradeNo;
 
 	/**
	 * 交易类型（0收入 1支出）
	 */
    @TableField("trade_type")
	private Integer tradeType;
 
 	/**
	 * 业务类型（0个人销售业绩 1团队销售业绩 2订单退款）
	 */
    @TableField("bus_type")
	private Integer busType;
 
 	/**
	 * 交易金额
	 */
    @TableField("trade_amount")
	private BigDecimal tradeAmount;
 
 	/**
	 * 交易前，待结算销售佣金金额
	 */
    @TableField("trade_start")
	private BigDecimal tradeStart;
 
 	/**
	 * 交易后，待结算销售佣金金额
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
