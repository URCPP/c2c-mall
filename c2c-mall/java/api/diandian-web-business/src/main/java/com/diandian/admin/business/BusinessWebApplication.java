package com.diandian.admin.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author x
 */
@SpringBootApplication(scanBasePackages = {"com.diandian.admin"})
@MapperScan("com.diandian.admin.business.modules.*.mapper")
@ImportResource("classpath:dubbo/consumer.xml")
public class BusinessWebApplication {


    public static void main(String[] args) {
        SpringApplication.run(BusinessWebApplication.class, args);
    }

    /**
     * 开发时候配置跨域，现在使用vue-cli 自己的代理解决，生成部署，是用Nginx 代理
     *
     * @return
     */
    @Bean
    @Order
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
