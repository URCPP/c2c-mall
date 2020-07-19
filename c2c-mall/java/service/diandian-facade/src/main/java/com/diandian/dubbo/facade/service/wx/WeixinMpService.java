package com.diandian.dubbo.facade.service.wx;

/**
 * 微信公众平台service
 * @author cjunyuan
 * @date 2019/6/26 18:51
 */
public interface WeixinMpService {

    /**
     *
     * 功能描述: 获取静默授权的跳转链接
     *
     * @param host
     * @param returnUrl
     * @return
     * @author cjunyuan
     * @date 2019/6/28 17:51
     */
    String getSilentAuthorizationUrl(String host, String returnUrl);

    /**
     *
     * 功能描述: 获取微信授权的跳转链接
     *
     * @param host
     * @param returnUrl
     * @return
     * @author cjunyuan
     * @date 2019/6/28 17:51
     */
    String getAuthorizationUrl(String host, String returnUrl);

    /**
     *
     * 功能描述: 根据授权回调code获取openId
     *
     * @param code
     * @return
     * @author cjunyuan
     * @date 2019/6/28 18:18
     */
    String getOpenIdByOAuthCode(String code);
}
