package com.diandian.dubbo.facade.model.merchant;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商户积分商城开放平台信息
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-10
 */
@Data
@TableName("merchant_open_platform")
public class MerchantOpenPlatformModel extends BaseModel {

    private static final long serialVersionUID = -7943367916449585893L;
    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 商户appId
     */
    @TableField("app_id")
    private String appId;

    /**
     * 商户密钥
     */
    @TableField("app_secret")
    @JSONField(serialize = false)
    private String appSecret;

    /**
     * 积分商城开放平台请求凭证
     */
    @TableField("access_token")
    private String accessToken;

    /**
     * token的过期时间
     */
    @TableField("token_expire_time")
    private Date tokenExpireTime;

    /**
     * ip白名单
     */
    @TableField("white_ip")
    private String whiteIp;


}
