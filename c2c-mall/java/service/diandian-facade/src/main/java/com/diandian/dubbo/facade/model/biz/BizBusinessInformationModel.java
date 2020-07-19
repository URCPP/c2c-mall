package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:商户信息
 * @author: wsk
 * @create: 2019-09-06 15:45
 */
@Data
@TableName("biz_business_information")
public class BizBusinessInformationModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 商户Id
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标识
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 店铺id
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;


    /**
     * 企业名称
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 企业类型
     */
    @TableField("business_type")
    private String businessType;

    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 主营一级分类Id
     */
    @TableField("classify_id_1")
    private String classifyId1;

    /**
     * 主营二级分类Id
     */
    @TableField("classify_id_2")
    private String classifyId2;

    /**
     * 单品质保金
     */
    @TableField("single_quality_deposit")
    private BigDecimal singleQualityDeposit;

    /**
     * 推荐码
     */
    @TableField("referral_code")
    private String referralCode;

    /**
     * 注册地区（1：大陆地区，2：港澳台地区，3：境外企业）
     */
    @TableField("registered_area")
    private Integer registeredArea;

    /**
     * 注册地址
     */
    @TableField("registration_address")
    private String registrationAddress;

    /**
     * 经营地址
     */
    @TableField("business_address")
    private String businessAddress;

    /**
     * 经营范围
     */
    @TableField("business_scope")
    private String businessScope;

    /**
     * 注册资本
     */
    @TableField("registered_capital")
    private BigDecimal registeredCapital;

    /**
     * 成立日期
     */
    @TableField("found_date")
    private Date foundDate;

    /**
     * 币种
     */
    @TableField("currency")
    private String currency;

    /**
     * 营业期限开始时间
     */
    @TableField("business_term_start")
    private Date businessTermStart;

    /**
     * 营业期限结束时间
     */
    @TableField("business_term_end")
    private Date businessTermEnd;

    /**
     * 统一社会信用代码
     */
    @TableField("unified_social_credit_code")
    private String unifiedSocialCreditCode;

    /**
     * 营业执照
     */
    @TableField("business_license")
    private String businessLicense;

    /**
     * 开户许可证
     */
    @TableField("account_licence")
    private String accountLicence;

    /**
     * 开票证明
     */
    @TableField("invoice_proof")
    private String invoiceProof;

    /**
     * 一般纳税人资质
     */
    @TableField("general_taxpayer_qualification")
    private String generalTaxpayerQualification;

    /**
     * 法人姓名
     */
    @TableField("legal_person_name")
    private String legalPersonName;

    /**
     * 法人身份证号码
     */
    @TableField("legal_person_id_number")
    private String legalPersonIdNumber;

    /**
     * 身份证正反面
     */
    @TableField("legal_person_identity_card_photos")
    private String legalPersonIdentityCardPhotos;

    /**
     * 身份证有效期开始
     */
    @TableField("identity_card_start_date")
    private Date identityCardStartDate;

    /**
     * 身份证有效期结束
     */
    @TableField("identity_card_end_date")
    private Date identityCardEndDate;

    /**
     * 联系人姓名
     */
    @TableField("contacts_name")
    private String contactsName;

    /**
     * 联系人手机号
     */
    @TableField("contacts_phone")
    private String contactsPhone;

    /**
     * 联系邮箱
     */
    @TableField("contact_email")
    private String contactEmail;

    /**
     * 联系QQ
     */
    @TableField("contact_qq")
    private String contactQQ;

    /**
     * 联系人身份证正反面
     */
    @TableField("contacts_identity_card_photos")
    private String contactsIdentityCardPhotos;

    /**
     * 1管理员开通，2自行开通
     */
    @TableField(exist = false)
    private Integer type;

    /**
     * 店铺类型ID
     */
    @TableField(exist = false)
    private Long shopTypeId;

    /**
     * 店铺类型名称
     */
    @TableField(exist = false)
    private String shopTypeName;

    /**
     * 密码
     */
    @TableField(exist = false)
    private String password;

    /**
     * 店铺
     */
    @TableField(exist = false)
    private ShopInfoModel shop;
    /**
     * 平台抽点
     */
    @TableField(exist = false)
    private BigDecimal platformProfit;

    @TableField(exist = false)
    private BigDecimal  floorPriceProportion;

    @TableField(exist = false)
    private BigDecimal agentProfit;

}