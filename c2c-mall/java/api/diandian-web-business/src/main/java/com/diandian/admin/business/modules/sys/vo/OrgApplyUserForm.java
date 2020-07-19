package com.diandian.admin.business.modules.sys.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/2/27 20:19
 */
@Data
public class OrgApplyUserForm implements Serializable {

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码
     */
    private String newPassword;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱号码
     */
    private String email;

    /**
     * 申请信息ID
     */
    private Long applyId;

    /**
     * 申请信息ID
     */
    private Long createBy;

    /**
     * 状态
     */
    private Integer state;
}
