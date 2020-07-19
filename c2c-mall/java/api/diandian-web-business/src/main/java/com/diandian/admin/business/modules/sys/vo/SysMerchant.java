package com.diandian.admin.business.modules.sys.vo;

import com.diandian.dubbo.facade.common.validation.StringNotIncludeBlank;
import lombok.Data;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-11 13:49
 */
@Data
public class SysMerchant {

    private Long id;

    @StringNotIncludeBlank(message = "登录账号不能包含空格")
    private String username;

    @StringNotIncludeBlank(message = "登录密码不能包含空格")
    private String password;

    private Long shopId;
}