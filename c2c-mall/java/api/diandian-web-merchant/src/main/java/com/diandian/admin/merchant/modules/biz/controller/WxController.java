package com.diandian.admin.merchant.modules.biz.controller;


import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.common.util.WeiXinUtil;
import com.diandian.admin.merchant.common.util.WxUtil;
import com.diandian.dubbo.facade.model.wx.WxModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/wx")
public class WxController {


    /**
     * 验证token的时候，微信会调用这个接口
     */
    @GetMapping("/signature")
    public RespData signature(String url) {
        WxModel wx = WeiXinUtil.getWinXinEntity(url);
        // 将wx的信息到给页面
        Map<String, Object> map = new HashMap<String, Object>();
        String signature = WxUtil.getSignature(wx.getTicket(), wx.getNoncestr(), wx.getTimestamp(), url);
        map.put("signature", signature.trim());//签名
        map.put("timestamp", wx.getTimestamp().trim());//时间戳
        map.put("noncestr",  wx.getNoncestr().trim());//随即串
        map.put("appId","wx6077766c64d080dd");//appID
        return  RespData.ok(map);
    }

}
