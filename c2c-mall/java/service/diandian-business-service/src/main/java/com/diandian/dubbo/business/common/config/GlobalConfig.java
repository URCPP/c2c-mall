package com.diandian.dubbo.business.common.config;

import com.diandian.dubbo.facade.model.biz.BizConfigModel;
import com.diandian.dubbo.facade.service.biz.BizConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * 全局配置文件
 * @author cjunyuan
 * @date 2019/6/13 16:22
 */
@Component
public class GlobalConfig {

    @Autowired
    private BizConfigService bizConfigService;

    public static BizConfigModel bizConfig;

    public static BigDecimal percent = new BigDecimal(100);

    @PostConstruct
    public void init() {
        bizConfig = bizConfigService.getOne();
    }
}
