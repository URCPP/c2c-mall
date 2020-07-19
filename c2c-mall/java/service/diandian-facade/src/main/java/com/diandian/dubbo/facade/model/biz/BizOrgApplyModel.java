package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * 机构申请表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_org_apply")
public class BizOrgApplyModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 申请编号
	 */
    @TableField("apply_no")
	private String applyNo;

	/**
	 * 申请类型，0-付费（申请），1-免费（使用免费名额开通）
	 */
	@TableField("apply_type")
	private Integer applyType;
 
 	/**
	 * 机构名称
	 */
    @TableField("org_name")
	private String orgName;

	/**
	 * 原始对象ID（修改专用）
	 */
    @TableField("original_id")
    private Long originalId;

	/**
	 * 原始对象类型，0-机构，1-商户（修改专用）
	 */
	@TableField("original_type")
	private Integer originalType;

 	/**
	 * 机构类型ID
	 */
    @TableField("org_type_id")
	private Long orgTypeId;

	/**
	 * 机构类型名称
	 */
	@TableField("org_type_name")
	private String orgTypeName;

 	/**
	 * 省ID
	 */
    @TableField("province_code")
	private Integer provinceCode;
 
 	/**
	 * 省名称
	 */
    @TableField("province_name")
	private String provinceName;
 
 	/**
	 * 市ID
	 */
    @TableField("city_code")
	private Integer cityCode;
 
 	/**
	 * 市名称
	 */
    @TableField("city_name")
	private String cityName;
 
 	/**
	 * 区县ID
	 */
    @TableField("area_code")
	private Integer areaCode;
 
 	/**
	 * 区县名称
	 */
    @TableField("area_name")
	private String areaName;

	/**
	 * 联系人姓名
	 */
	@TableField("contact_name")
	private String contactName;
 
 	/**
	 * 地址
	 */
    @TableField("address")
	private String address;
 
 	/**
	 * 手机号码
	 */
    @TableField("phone")
	private String phone;
 
 	/**
	 * 邮箱
	 */
    @TableField("email")
	private String email;
 
 	/**
	 * 身份证号码
	 */
    @TableField("IDCard")
	private String idcard;
 
 	/**
	 * 身份证正面图片
	 */
    @TableField("IDCard_positive_pic")
	private String idcardPositivePic;
 
 	/**
	 * 身份证反面图片
	 */
    @TableField("IDCard_reverse_Pic")
	private String idcardReversePic;

	/**
	 * 营业执照图片
	 */
	@TableField("business_license_pic")
	private String businessLicensePic;

	/**
	 * 营业执照图片
	 */
	@TableField("business_license_code")
	private String businessLicenseCode;
 
 	/**
	 * 开通银行
	 */
    @TableField("open_bank")
	private String openBank;
 
 	/**
	 * 开通支行
	 */
    @TableField("open_branch_bank")
	private String openBranchBank;
 
 	/**
	 * 银行账号
	 */
    @TableField("bank_card_no")
	private String bankCardNo;
 
 	/**
	 * 银行卡图片
	 */
    @TableField("bank_card_pic")
	private String bankCardPic;
 
 	/**
	 * 开户人姓名
	 */
    @TableField("account_name")
	private String accountName;
 
 	/**
	 * 审核状态（0 待审核，1审核通过 ，2审核失败）
	 */
    @TableField("audit_state")
	private Integer auditState;
 
 	/**
	 * 审核人ID
	 */
    @TableField("audit_user_id")
	private Long auditUserId;
 
 	/**
	 * 审核时间
	 */
    @TableField("audit_time")
	private Date auditTime;
 
 	/**
	 * 审核失败原因
	 */
    @TableField("audit_fail_reason")
	private String auditFailReason;
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
 	/**
	 * 推荐对象ID
	 */
    @TableField("recommend_id")
	private Long recommendId;

	/**
	 * 推荐对象类型
	 */
	@TableField("recommend_type")
	private Integer recommendType;

 	/**
	 * 上级机构ID
	 */
    @TableField("parent_id")
	private Long parentId;

	/**
	 * 组织树串
	 */
	@TableField("tree_str")
	private String treeStr;

	/**
	 * 信息是否本人（0-否， 1-是）
	 */
	@TableField("info_is_personal")
	private Integer infoIsPersonal;

	/**
	 * 委托书图片地址
	 */
	@TableField("power_attorney_pic")
	private String powerAttorneyPic;


}
