package com.diandian.admin.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * 会员TOKEN信息表
 *
 * @author wbc
 * @date 2019/02/27
 */
@Data
@TableName("member_token")
public class MemberTokenModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     *
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * token
     */
    @TableField("token")
    private String token;

    /**
     * token过期时间
     */
    @TableField("expire_time")
    private Date expireTime;

    /**
     * 最后一次登陆时间`
     */
    @TableField("last_login_time")
    private Date lastLoginTime;


}
