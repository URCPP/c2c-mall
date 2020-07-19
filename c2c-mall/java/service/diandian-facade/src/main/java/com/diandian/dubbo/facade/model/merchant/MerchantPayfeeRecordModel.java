package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;import java.util.Date;

/**
 * 商户支付表
 *
 * @author zweize
 * @date 2019/03/07
 */
@Data
@TableName("merchant_payfee_record")
public class MerchantPayfeeRecordModel extends BaseModel {

	private static final long serialVersionUID = 1L;


	/**
	 * 商户ID
	 */
	@TableField("merchant_id")
	private Long merchantId;

 	/**
	 * 支付订单号
	 */
    @TableField("pay_no")
	private String payNo;
 
 	/**
	 * 商户支付类型 0充值 1续费
	 */
    @TableField("pay_type")
	private Integer payType;
 
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
	 * 交易状态(0待支付 1交易成功 2交易失败)
	 */
    @TableField("state")
	private Integer state;
 
 	/**
	 * 交易时间
	 */
    @TableField("trade_time")
	private Date tradeTime;
 
 
 
}
