package com.diandian.dubbo.business.service.impl.wx;

import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.service.wx.WeixinMpService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

/**
 *
 * @author cjunyuan
 * @date 2019/6/28 17:53
 */
@Service("weixinMpService")
@Slf4j
public class WeixinMpServiceImpl implements WeixinMpService {

    @Autowired
    private WxMpService wxMpService;

    @Value("${weixin.mp.authorizeUrl}")
    private String mpAuthorizeUrl;

    @Override
    public String getSilentAuthorizationUrl(String host, String returnUrl) {
        log.info(mpAuthorizeUrl);
        return wxMpService.oauth2buildAuthorizationUrl(mpAuthorizeUrl, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl));
    }

    @Override
    public String getAuthorizationUrl(String host, String returnUrl) {
        return wxMpService.oauth2buildAuthorizationUrl(mpAuthorizeUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
    }

    @Override
    public String getOpenIdByOAuthCode(String code){
        try {
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            return accessToken.getOpenId();
        } catch (WxErrorException e) {
            log.info("【微信授权回调】openId获取失败");
            throw new DubboException("微信授权回调失败");
        }
    }
}
