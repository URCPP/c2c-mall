package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cjunyuan
 * @date 2019/2/26 17:18
 */
@Data
public class OrgApplyInfoDetailVO implements Serializable {

    private static final long serialVersionUID = -5712319897807217399L;

    private Long id;

    /**
     * 申请编号
     */
    private String applyNo;

    /**
     * 申请类型，0-付费（申请），1-免费（使用免费名额开通）
     */
    private Integer applyType;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 机构类型名称
     */
    private Long orgTypeId;

    /**
     * 机构类型名称
     */
    private String orgTypeName;

    /**
     * 省ID
     */
    private Integer provinceCode;

    /**
     * 市ID
     */
    private Integer cityCode;

    /**
     * 区县ID
     */
    private Integer areaCode;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 身份证正面图片
     */
    private String idcardPositivePic;

    /**
     * 身份证反面图片
     */
    private String idcardReversePic;

    /**
     * 营业执照图片
     */
    private String businessLicensePic;

    /**
     * 营业执照编码
     */
    private String businessLicenseCode;

    /**
     * 开通银行
     */
    private String openBank;

    /**
     * 开通支行
     */
    private String openBranchBank;

    /**
     * 银行账号
     */
    private String bankCardNo;

    /**
     * 银行卡图片
     */
    private String bankCardPic;

    /**
     * 开户人姓名
     */
    private String accountName;

    /**
     * 审核状态（0 待审核，1审核通过 ，2审核失败）
     */
    private Integer auditState;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核失败原因
     */
    private String auditFailReason;

    /**
     * 机构或商户ID（修改专用）
     */
    private Long originalId;

    /**
     * 申请对象类型，0-机构，1-商户（修改专用）
     */
    private Integer originalType;

    /**
     * 原始机构/商户名称
     */
    private String originalName;

    /**
     * 推荐对象ID
     */
    private Long recommendId;

    /**
     * 推荐对象类型
     */
    private Integer recommendType;

    /**
     * 推荐对象名称
     */
    private String recommendName;

    /**
     * 上级对象ID
     */
    private Long parentId;

    /**
     * 上级对象名称
     */
    private String parentName;

    /**
     * 上级对象名称
     */
    private String paymentPic;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区县名称
     */
    private String areaName;

    /**
     * 信息是否本人（0-否， 1-是）
     */
    private Integer infoIsPersonal;

    /**
     * 委托书图片地址
     */
    private String powerAttorneyPic;
}
