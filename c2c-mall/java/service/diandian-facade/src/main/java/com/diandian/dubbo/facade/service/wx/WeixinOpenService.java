package com.diandian.dubbo.facade.service.wx;

import com.diandian.dubbo.facade.dto.wx.WeixinPushRequestDTO;

/**
 * 微信开放平台service
 * @author cjunyuan
 * @date 2019/6/26 18:51
 */
public interface WeixinOpenService {
    
    /**
     *
     * 功能描述: 显示预授权跳转链接
     *
     * @param token
     * @return 
     * @author cjunyuan
     * @date 2019/6/27 8:58
     */
    String showGotoPreAuthUrl(String token);
    
    /**
     *
     * 功能描述: 获取预授权URL
     *
     * @param token
     * @return 
     * @author cjunyuan
     * @date 2019/6/26 18:55
     */
    String getPreAuthUrl(String token);

    /**
     *
     * 功能描述: 预授权回调
     *
     * @param authorizationCode
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/6/27 9:00
     */
    void preAuthCallBack(String authorizationCode, Long merchantId);

    /**
     *
     * 功能描述: 接受微信每10分钟推送的component_verify_ticket请求
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/6/27 9:11
     */
    String receiveTicket(WeixinPushRequestDTO dto);

    /**
     *
     * 功能描述: 接收微信公众号的消息推送
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/6/27 9:22
     */
    String receiveWeixinMsgPush(WeixinPushRequestDTO dto);
}
