package com.diandian.dubbo.facade.dto.member;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberInfoDTO implements Serializable {

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 会员帐号
     */
    private String memberAcc;

    /**
     * 会员密码
     */
    private String memberPwd;

    /**
     * 会员类型
     */
    private Integer type;


    /**
     * 会员电话
     */
    private String phone;

    /**
     * 验证码
     */
    private String validateCode;

    private String nickName;
    private String avatar;

}
