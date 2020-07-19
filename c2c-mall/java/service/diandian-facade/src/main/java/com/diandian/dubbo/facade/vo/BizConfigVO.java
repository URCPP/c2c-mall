package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author jbuhuan
 * @Date 2019/3/14 15:15
 */
@Data
public class BizConfigVO implements Serializable {
    /**
     * 客服联系方式
     */
    private String serverContact;

    /**
     * 帮助中心是否最新（0 是，1否）
     */
    private Integer helpFlag;

    /**
     * 官方营业执照图片地址
     */
    private String officialBusinessLicensePic;

    /**
     * APP下载URL
     */
    private String appUrl;

    /**
     * 兑换商城URL
     */
    private String exchangeMallUrl;

    /**
     * 保证金
     */
    private BigDecimal marginAmount;

    /**
     * 自定义商城开通费用
     */
    private BigDecimal mallOpenFee;

    /**
     * 官方电话
     */
    private String officialTel;



    /**
     * 图片Base64
     */
    private String businessLicensePicBase64;

    private  String showBusinessLicensePic;
}
