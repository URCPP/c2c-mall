package com.diandian.dubbo.facade.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;import java.util.Date;

/**
 * 订单支付
 *
 * @author zweize
 * @date 2019/03/06
 */
@Data
@TableName("order_pay")
public class OrderPayModel extends BaseModel {

	private static final long serialVersionUID = 1L;

 	/**
	 * 订单ID
	 */
    @TableField("order_id")
	private Long orderId;
 
 	/**
	 * 订单号
	 */
    @TableField("order_no")
	private String orderNo;
 
 	/**
	 * 交易单号
	 */
    @TableField("trade_order_no")
	private String tradeOrderNo;
 
 	/**
	 * 交易金额
	 */
    @TableField("trade_amount")
	private BigDecimal tradeAmount;
 
 	/**
	 * 交易方式
	 */
    @TableField("trade_way")
	private String tradeWay;
 
 	/**
	 * 交易状态(0交易失败 1交易成功 2退款)
	 */
    @TableField("state")
	private Integer state;
 
 	/**
	 * 交易时间
	 */
    @TableField("trade_time")
	private Date tradeTime;
 
 
 
}
