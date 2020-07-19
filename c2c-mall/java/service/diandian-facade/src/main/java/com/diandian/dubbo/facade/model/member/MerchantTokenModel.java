package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * 商户TOKEN信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
@Data
@TableName("merchant_token")
public class MerchantTokenModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * merchantId
     */
    @TableField("merchant_id")
    private Long merchantId;

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
