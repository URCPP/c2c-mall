package com.diandian.dubbo.facade.dto.merchant;

import com.diandian.dubbo.facade.common.validation.StringNotIncludeBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/2/28 10:14
 */
@Getter
@Setter
@ToString
public class MerchantOpenApplyDTO implements Serializable {

    private static final long serialVersionUID = 8079630655594287302L;

    private Long id;

    /**
     * 申请类型，0-付费（申请），1-免费（使用免费名额开通）
     */
    private Integer applyType;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 商户类型ID
     */
    private Long softTypeId;

    /**
     * 省ID
     */
    private Long provinceCode;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市ID
     */
    private Long cityCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区县ID
     */
    private Long areaCode;

    /**
     * 区县名称
     */
    private String areaName;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 联系人姓名
     */
    private String leader;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 登录账号
     */
    @StringNotIncludeBlank(message = "登录账号不能包含空格")
    private String loginName;

    /**
     * 登录密码
     */
    @StringNotIncludeBlank(message = "登录密码不能包含空格")
    private String loginPassword;

    /**
     * 登录密码
     */
    @StringNotIncludeBlank(message = "登录密码不能包含空格")
    private String newPassword;

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

    /**
     * 营业执照编码
     */
    private String businessLicenseCode;

    /**
     * 是否委托（0-否，1-是）
     */
    private Integer infoIsPersonal;

    /**
     * 营业执照编码
     */
    private String powerAttorneyPicBase64;

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
     * 上级对象ID
     */
    private Long parentId;


    /**
     * 上级对象类型
     */
    private Integer parentTypeFlag;

    /**
     * 推荐对象ID
     */
    private Long recommendId;

    /**
     * 推荐对象类型
     */
    private Integer recommendTypeFlag;

    /**
     * 打款图片Base64编码
     */
    private String paymentPicBase64;

    private Long oneCategoryId;
    private String oneCategory;
    private Long twoCategoryId;
    private String twoCategory;
    private String diningStartTime;
    private String diningEndTime;
    private String consume;
}
