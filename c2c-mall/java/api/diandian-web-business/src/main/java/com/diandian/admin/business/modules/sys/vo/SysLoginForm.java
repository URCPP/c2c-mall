package com.diandian.admin.business.modules.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author x
 * @date 2018-11-08
 */
@Data
public class SysLoginForm {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
