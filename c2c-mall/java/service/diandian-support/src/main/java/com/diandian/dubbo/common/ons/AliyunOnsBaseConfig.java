package com.diandian.dubbo.common.ons;

import com.aliyun.openservices.ons.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

/**
 * @author zzhihong
 * @date 2019-03-18
 */
public class AliyunOnsBaseConfig {

    @Autowired
    protected AliyunOnsProperties aliyunOnsProperties;

    /**
     * 构建消息生产者
     *
     * @param gid
     * @return
     */
    protected Producer buildProducer(String gid) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, gid);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, aliyunOnsProperties.getAccessKey());
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, aliyunOnsProperties.getSecretKey());
        //设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名，到控制台的实例基本信息中查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR,
                "http://MQ_INST_1191866292725191_BbTcqO7U.cn-hangzhou.mq-internal.aliyuncs.com:8080");
        return ONSFactory.createProducer(properties);
    }


    protected Consumer buildConsumer(String gid, String topic, String tag, MessageListener listener) {
        Properties properties = new Properties();
        // 您在控制台创建的 Group ID
        properties.put(PropertyKeyConst.GROUP_ID, gid);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, aliyunOnsProperties.getAccessKey());
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, aliyunOnsProperties.getSecretKey());
        // 设置 TCP 接入域名，到控制台的实例基本信息中查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR,
                "http://MQ_INST_1191866292725191_BbTcqO7U.cn-hangzhou.mq-internal.aliyuncs.com:8080");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(topic, tag, listener);
        return consumer;
    }
}
