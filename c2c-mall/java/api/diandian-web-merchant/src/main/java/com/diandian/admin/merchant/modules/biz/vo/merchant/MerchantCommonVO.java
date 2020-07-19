package com.diandian.admin.merchant.modules.biz.vo.merchant;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wubc
 * @date 2019/2/26 9:56
 */
@Data
public class MerchantCommonVO {

    /**
     * 官方电话
     */
    private String companyTel;

    /**
     * 商城URL
     */
    private String merchantMallUrl;

    /**
     * APP URL
     */
    private String merchantAppUrl;

    /**
     * 保证金
     */
    private BigDecimal marginAmount;

    /**
     * 商城开通金额
     */
    private BigDecimal openAmount;
    private String nationalHotline;
    private String serverTel;
    private String serverQq;
    private String companyAddress;
    private String companyIntroduce;
    private String disclaimer;
    private String privacy;
    private String businessLicense;
    private String rightsProtection;
    private String informTel;
    private String netRecord;
    private String copyrightInfo;
    private String voucherProtocol;
    private String registerProtocol;
    private String renewProtocol;
    private String openProtocol;
    private String lawProtocol;
    private String netPoliceRemind;
    private String informCenter;
    private String informApp;
    private String honestLogo;
    private String credibleLogo;
    private String shuiDeLogo;
    private String netPoliceLogo;
    private String informCenterLogo;
    private String informAppLogo;

    private String honestUrl;
    private String credibleUrl;
    private String serverUrl;
    private String shuiDe;

    private String openPic;
    private String mOpenPic;
    private String appQrcode;
    private String mallQrcode;
    private String serverWx;
    private String offlinePayInfo;
    private String merchantHomeUrl;
    private String merchantRegisterUrl;


}
