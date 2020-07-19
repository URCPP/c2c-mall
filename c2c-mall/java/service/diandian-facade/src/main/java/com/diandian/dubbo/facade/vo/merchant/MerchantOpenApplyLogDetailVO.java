package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/2/28 13:41
 */
@Data
public class MerchantOpenApplyLogDetailVO implements Serializable {

    private static final long serialVersionUID = 943465883075350848L;

    private Long id;

    private String billNo;

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
     * 软件类型ID
     */
    private Long softTypeId;

    /**
     * 软件类型名称
     */
    private String softTypeName;

    /**
     * 省ID
     */
    private Integer provinceCode;

    /**
     * 省ID
     */
    private String provinceName;

    /**
     * 市ID
     */
    private Integer cityCode;

    /**
     * 市ID
     */
    private String cityName;

    /**
     * 区县ID
     */
    private Integer areaCode;

    /**
     * 市ID
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
    private String loginName;

    /**
     * 身份证正面图片
     */
    private String idcardPositivePic;

    /**
     * 身份证反面图片
     */
    private String idcardReversePic;

    /**
     * 营业执照
     */
    private String businessLicensePic;

    /**
     * 营业执照编码
     */
    private String businessLicenseCode;

    /**
     * 是否委托
     */
    private Integer infoIsPersonal;

    /**
     * 委托书
     */
    private String powerAttorneyPic;

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
     * 上级对象ID
     */
    private Long parentId;


    /**
     * 上级对象类型
     */
    private Integer parentTypeFlag;

    private String parentName;

    /**
     * 推荐对象ID
     */
    private Long recommendId;

    /**
     * 推荐对象类型
     */
    private Integer recommendTypeFlag;

    private String recommendName;

    /**
     * 打款图片Base64编码
     */
    private String paymentPic;

    private Long oneCategoryId;
    private String oneCategory;
    private Long twoCategoryId;
    private String twoCategory;
    private String diningStartTime;
    private String diningEndTime;
    private String consume;
    private Integer applyStateFlag;
}
