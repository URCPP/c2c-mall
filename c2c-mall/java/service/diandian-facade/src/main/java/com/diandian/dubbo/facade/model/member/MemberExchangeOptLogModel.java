package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 会员兑换券充值操作记录表
 *
 * @author wbc
 * @date 2019/02/13
 */
@Data
@TableName("member_exchange_opt_log")
public class MemberExchangeOptLogModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 提现单号
	 */
    @TableField("bill_no")
	private String billNo;
 
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
	 * 操作状态（0：进行中，1：操作成功，2：操作失败）
	 */
    @TableField("opt_state")
	private Integer optState;
 
 	/**
	 * 操作记录
	 */
    @TableField("opt_record")
	private String optRecord;
 
 
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
}
