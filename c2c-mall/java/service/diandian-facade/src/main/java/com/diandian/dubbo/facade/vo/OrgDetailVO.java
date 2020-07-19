package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/3/1 17:59
 */
@Data
public class OrgDetailVO implements Serializable {

    private static final long serialVersionUID = 1799579642531382331L;

    private Long orgId;
    private String orgCode;
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
    private Integer cityCode;
    private Integer areaCode;
    private String address;
    private String idcardPositivePic;
    private String idcardReversePic;
    private String bankCardPic;
    private String businessLicensePic;
    private String businessLicenseCode;
    private Integer sort;
    private Integer state;
    private Integer approveFlag;
    private Long recommendId;
    private Integer recommendType;
    private String recommendName;
    private Integer infoIsPersonal;
    private String powerAttorneyPic;
}
