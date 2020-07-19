package com.diandian.dubbo.business.common.config;

import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.Consumer;
import com.diandian.dubbo.business.common.OrderOptListener;
import com.diandian.dubbo.common.ons.AliyunOnsBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzhihong
 * @date 2019-03-11
 */
@Configuration
public class AliyunOnsConfig extends AliyunOnsBaseConfig {

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "tradeProducer")
    public Producer tradeProducer() {
        return super.buildProducer(aliyunOnsProperties.getOrderStateGid());
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown", name = "tradeConsumer")
    public Consumer tradeConsumer(OrderOptListener orderOptListener) {
        return super.buildConsumer(aliyunOnsProperties.getOrderOptGid(), aliyunOnsProperties.getTradeTopic(), "orderOpt", orderOptListener);
    }

}
