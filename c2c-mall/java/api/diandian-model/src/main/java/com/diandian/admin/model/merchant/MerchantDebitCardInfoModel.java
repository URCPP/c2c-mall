package com.diandian.admin.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Data;

/**
 * 商户银行卡信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
@Data
@TableName("merchant_debit_card_info")
public class MerchantDebitCardInfoModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 
 	/**
	 * 创建人
	 */
    @TableField("create_by")
	private String createBy;
 
 
 	/**
	 * 类型 0 个人 ；1 公司
	 */
    @TableField("type")
	private Integer type;
 
 	/**
	 *  状态 0 正常；1 禁用
	 */
    @TableField("state")
	private Integer state;
 
 	/**
	 * 商户编码
	 */
    @TableField("merchant_id")
	private Long merchantId;
 
 	/**
	 * 银行名称
	 */
    @TableField("bank_name")
	private String bankName;
 
 	/**
	 * 支行名称
	 */
    @TableField("branch_bank_name")
	private String branchBankName;
 
 	/**
	 * 银行卡号
	 */
    @TableField("card_number")
	private String cardNumber;
 
 	/**
	 * 持卡人姓名
	 */
    @TableField("cardholder_name")
	private String cardholderName;
 
 	/**
	 * 持卡人电话
	 */
    @TableField("cardholder_phone")
	private String cardholderPhone;
 
 	/**
	 * 身份证号码
	 */
    @TableField("id_card")
	private String idCard;
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;

	/**
	 *   0 默认卡；1 非默认卡
	 */
	@TableField("default_flag")
	private Integer defaultFlag;
}
