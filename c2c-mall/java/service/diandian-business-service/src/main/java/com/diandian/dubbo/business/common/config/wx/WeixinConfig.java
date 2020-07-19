package com.diandian.dubbo.business.common.config.wx;

import com.diandian.dubbo.business.common.handle.wx.*;
import com.diandian.dubbo.facade.service.pay.WeixinPayService;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * 微信支付配置
 * @author cjunyuan
 * @date 2019/6/6 10:32
 */
@Configuration
@ConditionalOnClass(WeixinPayService.class)
@EnableConfigurationProperties({WeixinProperties.class, Wx3rdPartyProperties.class})
public class WeixinConfig {

    @Autowired
    private ClickHandler clickHandler;

    @Autowired
    private ViewHandler viewHandler;

    @Autowired
    private TextHandler textHandler;

    @Autowired
    private SubscribeHandler subscribeHandler;

    @Autowired
    private UnSubscribeHandler unSubscribeHandler;

    @Autowired
    private WeixinProperties weixinProperties;

    @Autowired
    private Wx3rdPartyProperties properties;

    @Bean
    public WxOpenService wxOpenService(WxOpenInRedisConfigStorage configStorage){
        WxOpenService wxOpenService=new WxOpenServiceImpl();
        wxOpenService.setWxOpenConfigStorage(configStorage);
        return wxOpenService;
    }

    @Bean
    public WxOpenInRedisConfigStorage wxOpenInRedisConfigStorage(JedisPool jedisPool){
        WxOpenInRedisConfigStorage inRedisConfigStorage=new WxOpenInRedisConfigStorage(jedisPool);
        inRedisConfigStorage.setComponentAppId(properties.getComponentAppId());
        inRedisConfigStorage.setComponentAppSecret(properties.getComponentSecret());
        inRedisConfigStorage.setComponentToken(properties.getComponentToken());
        inRedisConfigStorage.setComponentAesKey(properties.getComponentAesKey());
        return inRedisConfigStorage;
    }

    @Bean
    public WxOpenMessageRouter wxOpenMessageRouter(WxOpenService wxMpService){
        WxOpenMessageRouter wxOpenMessageRouter = new WxOpenMessageRouter(wxMpService);
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE).handler(unSubscribeHandler).end();
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler).end();
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.VIEW).handler(viewHandler).end();
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.CLICK).handler(clickHandler).end();
        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).handler(textHandler).end();
        return wxOpenMessageRouter;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(this.weixinProperties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(this.weixinProperties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(this.weixinProperties.getMchKey()));
        payConfig.setKeyPath(StringUtils.trimToNull(this.weixinProperties.getKeyPath()));

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    @Bean
    public WxMpService wxMpService(WxMpConfigStorage configStorage){
        WxMpService wxMpService=new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(JedisPool jedisPool){
        WxMpInRedisConfigStorage wxMpConfigStorage=new WxMpInRedisConfigStorage(jedisPool);
        wxMpConfigStorage.setAppId(weixinProperties.getAppId());
        wxMpConfigStorage.setSecret(weixinProperties.getAppSecret());
        return wxMpConfigStorage;
    }
}
