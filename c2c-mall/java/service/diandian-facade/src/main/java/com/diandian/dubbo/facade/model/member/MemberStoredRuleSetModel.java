package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员储值设置表
 *
 * @author wbc
 * @date 2019/02/13
 */
@Data
@TableName("member_stored_rule_set")
public class MemberStoredRuleSetModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 商户ID
	 */
    @TableField("merchant_id")
	private Long merchantId;
 

 
 
 	/**
	 *  状态 0 正常；1 禁用；
	 */
    @TableField("state_flag")
	private Integer stateFlag;
 
 	/**
	 * 储值金额
	 */
    @TableField("stored_amount")
	private BigDecimal storedAmount;
 
 	/**
	 * 到账金额
	 */
    @TableField("real_amount")
	private BigDecimal realAmount;
 
 	/**
	 * 赠送的兑换券数量
	 */
    @TableField("exchange_coupon_num")
	private Integer exchangeCouponNum;
 
 	/**
	 * 赠送的购物券金额
	 */
    @TableField("shopping_coupon_amount")
	private BigDecimal shoppingCouponAmount;

 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;

}
