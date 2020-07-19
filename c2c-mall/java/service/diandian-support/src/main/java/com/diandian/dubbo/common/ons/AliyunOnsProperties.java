package com.diandian.dubbo.common.ons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @date 2019-03-11
 */
@Component
@ConfigurationProperties(prefix = "aliyun.ons")
@Getter
@Setter
public class AliyunOnsProperties {

    private String accessKey;

    private String secretKey;

    private String orderDelayGid;

    private String orderDelayTopic;

    private String orderStateGid;

    private String orderOptGid;

    private String tradeTopic;
}
