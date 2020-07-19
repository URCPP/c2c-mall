package com.diandian.dubbo.product.common.config;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Producer;
import com.diandian.dubbo.common.ons.AliyunOnsBaseConfig;
import com.diandian.dubbo.product.common.OrderExpireListener;
import com.diandian.dubbo.product.common.OrderStateListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzhihong
 * @date 2019-03-11
 */
@Configuration
public class AliyunOnsConfig extends AliyunOnsBaseConfig {

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "orderDelayProducer")
    public Producer orderDelayProducer() {
        return super.buildProducer(aliyunOnsProperties.getOrderDelayGid());
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "orderDelayConsumer")
    public Consumer orderDelayConsumer(OrderExpireListener orderDelayListener) {
        return super.buildConsumer(aliyunOnsProperties.getOrderDelayGid(), aliyunOnsProperties.getOrderDelayTopic(), "*", orderDelayListener);
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "tradeProducer")
    public Producer tradeProducer() {
        return super.buildProducer(aliyunOnsProperties.getOrderOptGid());
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "tradeConsumer")
    public Consumer tradeConsumer(OrderStateListener orderStateListener) {
        return super.buildConsumer(aliyunOnsProperties.getOrderStateGid(), aliyunOnsProperties.getTradeTopic(), "orderState", orderStateListener);
    }

}
