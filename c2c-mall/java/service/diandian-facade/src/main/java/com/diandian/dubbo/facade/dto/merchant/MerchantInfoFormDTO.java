package com.diandian.dubbo.facade.dto.merchant;

import com.diandian.dubbo.facade.common.validation.StringNotIncludeBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/3/2 17:01
 */
@Getter
@Setter
@ToString
public class MerchantInfoFormDTO implements Serializable {

    private static final long serialVersionUID = 3792461800575621057L;

    private Long id;
    private String name;
    private Long softTypeId;
    @StringNotIncludeBlank(message = "登录账号不能包含空格")
    private String loginName;
    @StringNotIncludeBlank(message = "登录密码不能包含空格")
    private String loginPassword;
    @StringNotIncludeBlank(message = "登录密码不能包含空格")
    private String newPassword;
    private String phone;
    private String leader;
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
    private Integer recommendTypeFlag;
    private String recommendName;
    private Long parentId;
    private Integer parentTypeFlag;
    private String parentName;
    private Long oneCategoryId;
    private String oneCategory;
    private Long twoCategoryId;
    private String twoCategory;
    private String diningStartTime;
    private String diningEndTime;
    private String consume;
    private Integer approveFlag;
    private Integer disabledFlag;
    private String merchantExpireTime;
    private Long createBy;
    private Integer openType;
    private Integer infoIsPersonal;
    private String powerAttorneyPicBase64;
}
