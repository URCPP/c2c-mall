package com.diandian.dubbo.facade.dto.merchant;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUpdatePwdDTO implements Serializable {

    /**
     * 验证码
     */
    private String validateCode;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String confirmPassword;


    /**
     * 提现密码
     */
    private String cashPassword;

    /**
     * 确认提现密码
     */
    private String confirmCashPassword;




}
