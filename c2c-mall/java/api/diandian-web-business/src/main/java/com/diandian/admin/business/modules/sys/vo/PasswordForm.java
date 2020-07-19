package com.diandian.admin.business.modules.sys.vo;

import com.diandian.dubbo.facade.common.validation.StringNotIncludeBlank;
import lombok.Data;

/**
 * @author x
 * @date 2018-11-08
 */
@Data
public class PasswordForm {

    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    @StringNotIncludeBlank(message = "登录账号不能包含空格")
    private String newPassword;

}
