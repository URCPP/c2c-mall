package com.diandian.dubbo.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author x
 * @date 2018-10-23
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.diandian"})
@MapperScan("com.diandian.dubbo.business.mapper")
@ImportResource({"classpath:dubbo/spring-dubbo.xml"})
@EnableScheduling
public class BusinessServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(BusinessServiceApplication.class)
                .web(WebApplicationType.NONE)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
//        OrderConsumer orderConsumer = context.getBean(OrderConsumer.class);
//        orderConsumer.start();
    }
}
