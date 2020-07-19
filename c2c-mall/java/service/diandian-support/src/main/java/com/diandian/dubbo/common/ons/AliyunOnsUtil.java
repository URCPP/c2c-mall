package com.diandian.dubbo.common.ons;

import com.aliyun.openservices.ons.api.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author x
 * @date 2019-03-11
 */
@Component
@Slf4j
public class AliyunOnsUtil {

    @Autowired
    private AliyunOnsProperties properties;

    @Autowired(required = false)
    private Producer orderDelayProducer;
    @Autowired(required = false)
    private Producer tradeProducer;

    public void doSendOrderDelayAsync(String key, String msg, long delay) {
        log.info("发送MQ topic={},key={},msg={}", properties.getOrderDelayTopic(), key, msg);
        Message message = new Message(properties.getOrderDelayTopic(), "orderExpire", key, msg.getBytes());
        long delayTime = System.currentTimeMillis() + delay + new Random().nextInt(300000);
        message.setStartDeliverTime(delayTime);
        orderDelayProducer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("订单延迟消息发送成功,msg={}", msg);
            }

            @Override
            public void onException(OnExceptionContext onExceptionContext) {
                log.error("订单延迟消息发送异常,msg={}", msg, onExceptionContext);
            }
        });
    }

    public void doSendTradeAsync(String key, String msg, String tag) {
        Message message = new Message(properties.getTradeTopic(), tag, key, msg.getBytes());
        tradeProducer.sendAsync(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("订单消息发送成功,msg={}", msg);
            }

            @Override
            public void onException(OnExceptionContext onExceptionContext) {
                log.error("订单消息发送异常,msg={}", msg, onExceptionContext);
            }
        });
    }

}
