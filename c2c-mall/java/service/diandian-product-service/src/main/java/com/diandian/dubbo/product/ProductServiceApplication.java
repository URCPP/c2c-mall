package com.diandian.dubbo.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author x
 * @date 2018-10-23
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.diandian.dubbo"})
@MapperScan("com.diandian.dubbo.product.mapper")
@ImportResource({"classpath:dubbo/spring-dubbo.xml"})
public class ProductServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(ProductServiceApplication.class)
                .web(WebApplicationType.NONE)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
