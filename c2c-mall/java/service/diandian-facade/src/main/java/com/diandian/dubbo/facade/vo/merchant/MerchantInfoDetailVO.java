package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cjunyuan
 * @date 2019/3/2 18:04
 */
@Data
public class MerchantInfoDetailVO implements Serializable {

    private static final long serialVersionUID = 7638605551366107540L;

    private Long id;
    private String code;
    private Long parentId;
    private String parentName;
    private String name;
    private Long softTypeId;
    private String leader;
    private String phone;
    private String loginName;
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
    private Long oneCategoryId;
    private String oneCategory;
    private Long twoCategoryId;
    private String twoCategory;
    private String diningStartTime;
    private String diningEndTime;
    private String consume;
    private String merchantExpireTime;
    private Integer approveFlag;
    private Integer disabledFlag;
    private Long recommendId;
    private Integer recommendType;
    private String recommendName;
    private Integer infoIsPersonal;
    private String powerAttorneyPic;
    private Integer level;
}
