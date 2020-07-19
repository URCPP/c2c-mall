package com.diandian.dubbo.facade.dto.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 机构申请DTO对象
 * @author cjunyuan
 * @date 2019/2/25 22:01
 */
@Getter
@Setter
@ToString
public class OrgApplyInfoDTO implements Serializable {

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
     * 机构或商户ID（修改专用）
     */
    private Long originalId;

    /**
     * 申请对象类型，0-机构，1-商户（修改专用）
     */
    private Integer originalType;

    /**
     * 原始对象名称
     */
    private String originalName;

    /**
     * 机构类型ID
     */
    private Long orgTypeId;

    /**
     * 省ID
     */
    private Integer provinceCode;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市ID
     */
    private Integer cityCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区县ID
     */
    private Integer areaCode;

    /**
     * 区县名称
     */
    private String areaName;

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
     * 身份证正面图片Base64编码
     */
    private String idcardPositivePicBase64;

    /**
     * 身份证反面图片Base64编码
     */
    private String idcardReversePicBase64;

    /**
     * 营业执照图片Base64编码
     */
    private String businessLicensePicBase64;

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
    private String bankCardPicBase64;

    /**
     * 开户人姓名
     */
    private String accountName;

    /**
     * 上级机构ID
     */
    private Long parentId;

    /**
     * 上级机构ID
     */
    private Integer parentType;

    /**
     * 推荐机构ID
     */
    private Long recommendId;

    /**
     * 推荐机构ID
     */
    private Integer recommendType;

    /**
     * 推荐机构ID
     */
    private String recommendName;

    /**
     * 打款图片Base64编码
     */
    private String paymentPicBase64;

    /**
     * 机构树串
     */
    private String treeStr;

    /**
     * 信息是否本人（0-否， 1-是）
     */
    private Integer infoIsPersonal;

    /**
     * 委托书图片地址
     */
    private String powerAttorneyPicBase64;
}
