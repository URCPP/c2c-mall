package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 会员储值记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Data
@TableName("member_stored_log")
public class MemberStoredLogModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 会员储值订单号
	 */
    @TableField("bill_no")
	private String billNo;
 
 	/**
	 * 商户ID
	 */
    @TableField("merchant_id")
	private Long merchantId;
 
 	/**
	 * 商户名字
	 */
    @TableField("merchant_name")
	private String merchantName;
 
 	/**
	 * 会员ID
	 */
    @TableField("member_id")
	private Long memberId;
 
 	/**
	 * 会员帐号
	 */
    @TableField("member_account")
	private String memberAccount;
 
 	/**
	 * 会员昵称
	 */
    @TableField("member_nick_name")
	private String memberNickName;
 
 	/**
	 * 充值前，会员余额
	 */
    @TableField("member_balance")
	private BigDecimal memberBalance;
 
 	/**
	 * 储值金额
	 */
    @TableField("stored_amount")
	private BigDecimal storedAmount;
 
 	/**
	 * 实际到帐金额
	 */
    @TableField("real_amount")
	private BigDecimal realAmount;
 
 	/**
	 * 赠送的卡券数量
	 */
    @TableField("exchange_coupon_num")
	private Integer exchangeCouponNum;
 
 	/**
	 * 赠送的购物券金额
	 */
    @TableField("shopping_coupon_amount")
	private BigDecimal shoppingCouponAmount;
 
 	/**
	 * 储值描述
	 */
    @TableField("description")
	private String description;
 
 
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
}
