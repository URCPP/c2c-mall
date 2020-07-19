package com.diandian.admin.business.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author cjunyuan
 * @date 2019/5/15 16:36
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "express.100")
public class Express100Properties {

    private String key;
    private String callbackUrl;
    private String subscriptionUrl;
}
