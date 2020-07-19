package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 机构表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_org_info")
public class BizOrgInfoModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 机构ID
	 */
	@TableField("org_id")
	private Long orgId;
 
 	/**
	 * 名称
	 */
    @TableField("org_name")
	private String orgName;
 
 	/**
	 * 编码
	 */
    @TableField("org_code")
	private String orgCode;
 
 	/**
	 * 机构类型ID
	 */
    @TableField("org_type_id")
	private Long orgTypeId;
 
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
	 * 地址
	 */
    @TableField("address")
	private String address;

	/**
	 * 联系人
	 */
	@TableField("contact_name")
	private String contactName;
 
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
	 * 营业执照编码
	 */
	@TableField("business_license_code")
	private String businessLicenseCode;
 
 	/**
	 * 开户银行
	 */
    @TableField("open_bank")
	private String openBank;
 
 	/**
	 * 开户支行
	 */
    @TableField("open_branch_bank")
	private String openBranchBank;
 
 	/**
	 * 银行卡账号
	 */
    @TableField("bank_card_no")
	private String bankCardNo;

 	/**
	 * 银行卡图片
	 */
    @TableField("bank_card_pic")
	private String bankCardPic;
 
 	/**
	 * 开户人名称
	 */
    @TableField("account_name")
	private String accountName;
 
 	/**
	 * 状态（0禁用，1正常）
	 */
    @TableField("state")
	private Integer state;
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
 	/**
	 * 推荐机构ID
	 */
    @TableField("recommend_id")
	private Long recommendId;
 
 	/**
	 * 上级机构ID
	 */
    @TableField("parent_id")
	private Long parentId;

	/**
	 * 开通类型
	 */
	@TableField("open_type")
	private Integer openType;

	/**
	 * 删除状态（0-未删除，1-已删除）
	 */
	@TableField("del_flag")
	private Integer delFlag;

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
