package com.diandian.admin.merchant.common.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis增强插件配置
 *
 * @author x
 * @date 2018/9/12 17:53
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * SQL执行效率插件【生产环境可以关闭】
     *
     * @return PerformanceInterceptor
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }


    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    /**
     * 公共字段自动填充
     */
    @Bean
    public GlobalConfig globalConfiguration(TimeMetaObjectHandler timeMetaObjectHandler) {
        GlobalConfig gc = new GlobalConfig();
        gc.setMetaObjectHandler(timeMetaObjectHandler);
        return gc;
    }
}
