package com.diandian.dubbo.business.service.impl.wx;

import cn.hutool.core.util.StrUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.wx.WeixinPushRequestDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWeixinMpModel;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantWeixinMpService;
import com.diandian.dubbo.facade.service.wx.WeixinOpenService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author cjunyuan
 * @date 2019/6/26 18:54
 */
@Service("weixinOpenService")
@Slf4j
public class WeixinOpenServiceImpl implements WeixinOpenService {

    @Value("${weixin.open.3rd.preAuthAccessUrl}")
    private String preAuthAccessUrl;

    @Value("${weixin.open.3rd.preAuthCallbackUrl}")
    private String preAuthCallbackUrl;

    @Autowired
    private WxOpenService wxOpenService;

    @Autowired
    private WxOpenMessageRouter wxOpenMessageRouter;

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private MerchantWeixinMpService merchantWeixinMpService;

    @Override
    public String showGotoPreAuthUrl(String token){
        return "<a href='" + preAuthAccessUrl + "?token=" + token + "' target='_parent'>go</a>";
    }

    @Override
    public String getPreAuthUrl(String token){
        try {
            return wxOpenService.getWxOpenComponentService().getPreAuthUrl(preAuthCallbackUrl + "?token=" + token);
        } catch (WxErrorException e) {
            throw new DubboException("获取预授权URL失败");
        }
    }

    @Override
    public void preAuthCallBack(String authorizationCode, Long merchantId){
        try{
            WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
            WxOpenQueryAuthResult queryAuthResult = wxOpenComponentService.getQueryAuth(authorizationCode);
            WxOpenAuthorizerInfoResult authorizerInfo = wxOpenComponentService.getAuthorizerInfo(queryAuthResult.getAuthorizationInfo().getAuthorizerAppid());
            MerchantWeixinMpModel mchWeixinMp = getMchWeixinMpModel(authorizerInfo, merchantId);
            merchantWeixinMpService.bindWeixinMp(mchWeixinMp);
        }catch (Exception e){
            throw new DubboException("预授权回调处理失败");
        }
    }

    @Override
    public String receiveTicket(WeixinPushRequestDTO dto){
        if (!StrUtil.equalsIgnoreCase("aes", dto.getEncType()) ||
                !wxOpenService.getWxOpenComponentService().checkSignature(dto.getTimestamp(), dto.getNonce(), dto.getSignature())) {
            throw new DubboException("非法请求，可能属于伪造的请求！");
        }
        // aes加密的消息
        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(dto.getRequestBody(), wxOpenService.getWxOpenConfigStorage(), dto.getTimestamp(), dto.getNonce(), dto.getMsgSignature());
        log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
        try {
            String out = wxOpenService.getWxOpenComponentService().route(inMessage);
            log.debug("\n组装回复信息：{}", out);
            return out;
        } catch (WxErrorException e) {
            log.error("receive_ticket", e);
            throw new DubboException("组装回复信息失败");
        }
    }

    @Override
    public String receiveWeixinMsgPush(WeixinPushRequestDTO dto){
        if (!StrUtil.equalsIgnoreCase("aes", dto.getEncType())
                || !wxOpenService.getWxOpenComponentService().checkSignature(dto.getTimestamp(), dto.getNonce(), dto.getSignature())) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        String out = "";
        // aes加密的消息
        WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(dto.getRequestBody(),
                wxOpenService.getWxOpenConfigStorage(), dto.getTimestamp(), dto.getNonce(), dto.getMsgSignature());
        log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
        // 全网发布测试用例
        if (StringUtils.equalsAnyIgnoreCase(dto.getAppId(), "wxd101a85aa106f53e", "wx570bc396a51b8ff8")) {
            try {
                if (StringUtils.equals(inMessage.getMsgType(), WxConsts.MassMsgType.TEXT)) {
                    if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
                        out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
                                WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                                        .fromUser(inMessage.getToUser())
                                        .toUser(inMessage.getFromUser())
                                        .build(),
                                wxOpenService.getWxOpenConfigStorage()
                        );
                    } else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {
                        String msg = inMessage.getContent().replace("QUERY_AUTH_CODE:", "") + "_from_api";
                        WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(inMessage.getFromUser()).build();
                        wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(dto.getAppId()).getKefuService().sendKefuMessage(kefuMessage);
                    }
                } else if (StringUtils.equals(inMessage.getMsgType(), WxConsts.XmlMsgType.EVENT)) {
                    WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback").toUser(inMessage.getFromUser()).build();
                    wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(dto.getAppId()).getKefuService().sendKefuMessage(kefuMessage);
                }
            } catch (WxErrorException e) {
                log.error("callback", e);
                throw new DubboException("接收微信公众号消息推送处理失败");
            }
        }else{
            WxMpXmlOutMessage outMessage = wxOpenMessageRouter.route(inMessage, dto.getAppId());
            if(outMessage != null){
                out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outMessage, wxOpenService.getWxOpenConfigStorage());
            }
        }
        return out;
    }


    private MerchantWeixinMpModel getMchWeixinMpModel(WxOpenAuthorizerInfoResult authorizerInfo, Long merchantId){
        MerchantWeixinMpModel weixinMp = new MerchantWeixinMpModel();
        weixinMp.setAppId(authorizerInfo.getAuthorizationInfo().getAuthorizerAppid());
        weixinMp.setAuthorizerRefreshToken(authorizerInfo.getAuthorizationInfo().getAuthorizerRefreshToken());
        weixinMp.setHeadImg(authorizerInfo.getAuthorizerInfo().getHeadImg());
        weixinMp.setNickName(authorizerInfo.getAuthorizerInfo().getNickName());
        weixinMp.setAlias(authorizerInfo.getAuthorizerInfo().getAlias());
        weixinMp.setPrincipalName(authorizerInfo.getAuthorizerInfo().getPrincipalName());
        weixinMp.setQrcodeUrl(authorizerInfo.getAuthorizerInfo().getQrcodeUrl());
        weixinMp.setServiceTypeInfo(authorizerInfo.getAuthorizerInfo().getServiceTypeInfo());
        weixinMp.setUserName(authorizerInfo.getAuthorizerInfo().getUserName());
        weixinMp.setVerifyTypeInfo(authorizerInfo.getAuthorizerInfo().getVerifyTypeInfo());
        weixinMp.setOpenCard(authorizerInfo.getAuthorizerInfo().getBusinessInfo().get("open_card"));
        weixinMp.setOpenPay(authorizerInfo.getAuthorizerInfo().getBusinessInfo().get("open_pay"));
        weixinMp.setOpenScan(authorizerInfo.getAuthorizerInfo().getBusinessInfo().get("open_scan"));
        weixinMp.setOpenShake(authorizerInfo.getAuthorizerInfo().getBusinessInfo().get("open_shake"));
        weixinMp.setOpenStore(authorizerInfo.getAuthorizerInfo().getBusinessInfo().get("open_store"));

        // 查询用户信息
        MerchantInfoModel oldMch = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(oldMch, "商户信息不存在");
        // 账号被锁定
        if (!BizConstant.STATE_NORMAL.equals(oldMch.getDisabledFlag())) {
            throw new DubboException("账号已被锁定,请联系管理员");
        }
        weixinMp.setMerchantId(oldMch.getId());
        return weixinMp;
    }
}
