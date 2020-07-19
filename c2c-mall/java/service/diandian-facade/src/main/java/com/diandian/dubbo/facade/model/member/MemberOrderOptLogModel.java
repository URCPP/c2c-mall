package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import lombok.Data;

import java.util.List;

/**
 * 会员订单操作记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Data
@TableName("member_order_opt_log")
public class MemberOrderOptLogModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 订单单号
	 */
    @TableField("order_no")
	private String orderNo;
 
 	/**
	 * 订单类型(0 会员储值；1 兑换券充值)
	 */
    @TableField("order_type_flag")
	private Integer orderTypeFlag;
 
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
	 * 操作记录
	 */
    @TableField("opt_record")
	private String optRecord;
 
 	/**
	 * 操作状态（0：进行中，1：操作成功，2：操作失败）
	 */
    @TableField("opt_state_flag")
	private Integer optStateFlag;

	/**
	 * 兑换券数量
	 */
	@TableField("exchange_coupon_num")
	private Integer exchangeCouponNum;

	/**
	 * 会员承担运费（0否，1是）
	 */
	@TableField("assume_freight_flag")
	private Integer assumeFreightFlag;

	/**
	 * 订单状态（0已创建，1已付运费，2已扣会员积分，3已扣商户储备金）
	 */
	@TableField("order_state")
	private Integer orderState;
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;

    @TableField(exist = false)
    private List<OrderDetailModel> orderDetailList;
 
}
