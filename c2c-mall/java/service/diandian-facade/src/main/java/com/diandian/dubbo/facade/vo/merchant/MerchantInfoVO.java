package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wubc
 * @date 2019/2/27 17:10
 */
@Data
public class MerchantInfoVO implements Serializable {

    private Long id;
    /**
     * 帐户名
     */
    private String loginName;

    private Integer level;

    /**
     * 冻结金额
     */
    private BigDecimal freezeCommission;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机
     */
    private String phone;

    /**
     * 软件名
     */
    private String softTypeName;

    private String softTypeId;

    private String newPhone;

    private String validateCode;

    /**
     * 商户过期时间
     */
    private Date merchantExpireTime;

    /**
     * 过期状态  0未过期 ； 1己过期
     */
    private Integer isExpireFlag;

    /**
     * 认证状态  0未认证 ； 1己认证
     */
    private Integer approveFlag;

    /**
     * 商户自定义商城开通状态( 0 未开通 ， 1 开通)
     */
    private Integer mallOpenFlag;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 商户编号
     */
    private String code;

    /**
     * 负责人
     */
    private String leader;


    private String invitationCode;




}
