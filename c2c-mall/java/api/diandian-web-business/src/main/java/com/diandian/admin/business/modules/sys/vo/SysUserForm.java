package com.diandian.admin.business.modules.sys.vo;

import com.diandian.dubbo.facade.common.validation.StringNotIncludeBlank;
import lombok.Data;

import java.util.List;

/**
 * @author x
 * @date 2018-11-09
 */
@Data
public class SysUserForm {


    private Long id;

    @StringNotIncludeBlank(message = "登录账号不能包含空格")
    private String username;

    @StringNotIncludeBlank(message = "登录密码不能包含空格")
    private String password;

    @StringNotIncludeBlank(message = "登录密码不能包含空格")
    private String newPassword;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;


    private List<Long> roleIdList;

    private Integer state;

    private Long orgId;

    private Long orgTypeId;

    private String orgName;


}
