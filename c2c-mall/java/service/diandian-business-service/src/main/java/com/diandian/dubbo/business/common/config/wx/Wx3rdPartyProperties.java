package com.diandian.dubbo.business.common.config.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信开放平台-第三方平台配置文件
 * @author cjunyuan
 * @date 2019/4/9 11:08
 */
@Data
@ConfigurationProperties(prefix = "weixin.open.3rd")
public class Wx3rdPartyProperties {
    /**
     * 设置微信三方平台的appid
     */
    private String componentAppId;

    /**
     * 设置微信三方平台的app secret
     */
    private String componentSecret;

    /**
     * 设置微信三方平台的token
     */
    private String componentToken;

    /**
     * 设置微信三方平台的EncodingAESKey
     */
    private String componentAesKey;
}
