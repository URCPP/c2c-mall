package com.diandian.dubbo.facade.dto.sys;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/3/1 16:41
 */
@Getter
@Setter
@ToString
public class OrgFormDTO implements Serializable {

    private static final long serialVersionUID = -1722370121453657963L;
    private Long orgId;
    private Long parentId;
    private String parentName;
    private String orgName;
    private Long orgTypeId;
    private String contactName;
    private String phone;
    private String email;
    private String idcard;
    private String openBank;
    private String openBranchBank;
    private String bankCardNo;
    private String accountName;
    private Integer provinceCode;
    private String provinceName;
    private Integer cityCode;
    private String cityName;
    private Integer areaCode;
    private String areaName;
    private String address;
    private String idcardPositivePicBase64;
    private String idcardReversePicBase64;
    private String bankCardPicBase64;
    private String businessLicensePicBase64;
    private String businessLicenseCode;
    private Long recommendId;
    private String recommendName;
    private Integer state;
    private Integer sort;
    private Integer approveFlag;
    private Integer openType;
    private Integer infoIsPersonal;
    private String powerAttorneyPicBase64;
}
