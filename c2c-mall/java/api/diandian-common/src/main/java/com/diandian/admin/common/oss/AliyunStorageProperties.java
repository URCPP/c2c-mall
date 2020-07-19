package com.diandian.admin.common.oss;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @author zengzh
 * @date create at 2018/4/16 17:27
 */

@Data
@Conditional(AliyunStorageCondition.class)
@ConfigurationProperties(prefix = "aliyun.oss")
@Component
public class AliyunStorageProperties {


    /**
     * 阿里云绑定的域名
     */

    private String domain;


    /**
     * 阿里云路径前缀
     */

    private String prefix;


    /**
     * 阿里云EndPoint
     */

    private String endPoint;


    /**
     * 阿里云AccessKeyId
     */

    @Value("${aliyun.accessKey}")
    private String accessKeyId;


    /*
     * 阿里云AccessKeySecret
     */

    @Value("${aliyun.secretKey}")
    private String accessKeySecret;

   /**
     * 阿里云BucketName
     */

    private String bucketName;

   /**
     * 阿里云EndPoint
     */

    private String fileEndPoint;
}

