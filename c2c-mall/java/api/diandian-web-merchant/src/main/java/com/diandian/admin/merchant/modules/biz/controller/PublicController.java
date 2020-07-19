package com.diandian.admin.merchant.modules.biz.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.common.constant.RedisKeyConstant;
import com.diandian.admin.common.oss.AliyunStorageUtil;
import com.diandian.admin.merchant.common.util.CookieUtils;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.common.util.SMSUtil;
import com.diandian.dubbo.facade.service.wx.WeixinMpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 公共接口
 *
 * @author wubc
 * @date 2018/11/2 8:49
 */

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private WeixinMpService weixinMpService;

    @Value("${project.hostUrl}")
    private String hostUrl;

    @Value("${project.cookie.domain}")
    private String cookieDomain;


    @PostMapping("/sendSms")
    public RespData sendSmsMessage(HttpServletRequest request, @RequestParam(value = "phone") String phone) {
        String code = redisTemplate.opsForValue().get(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + phone);
        AssertUtil.isNull(code, "请勿频繁发送验证码");
        code = RandomUtil.randomNumbers(6);
        while ("0".equals(code.subSequence(0, 1))){
            code = RandomUtil.randomNumbers(6);
        }
        boolean sendResult = SMSUtil.sendMsgValidateCode(code, phone);
        AssertUtil.isFalse(!sendResult, "验证码发送失败");
        redisTemplate.opsForValue().set(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + phone, code, RedisKeyConstant.SMS_CODE_VERIFICATION_EXPIRE, TimeUnit.SECONDS);
        return RespData.ok();
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public RespData uploadAvatar(@RequestParam("file") MultipartFile file) {
        String avatarPath = null;
        try {
            String fileExt = FileUtil.extName(file.getOriginalFilename());
            avatarPath = AliyunStorageUtil.uploadFile("merchant", file.getInputStream(), "." + fileExt);
        } catch (IOException e) {
            log.error("上传头像异常", e);
            RespData.fail(500, "上传失败");
        }
        return RespData.ok(avatarPath);
    }

    @GetMapping("/wxMp/goOauth")
    public RespData goOauth(HttpServletResponse response, @RequestParam("returnUrl") String returnUrl) throws IOException {
        log.info("进入.............................................");
        String redirectUrl = weixinMpService.getSilentAuthorizationUrl(hostUrl, returnUrl);
        log.info("【微信网页授权】获取code,redirectUrl={}", redirectUrl);

        return RespData.ok( redirectUrl);


    }

    @GetMapping("/wxMp/authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam("code") String code, @RequestParam("state") String returnUrl) throws IOException{
        log.info("进入authorize.............................................");
        String openId = weixinMpService.getOpenIdByOAuthCode(code);
        CookieUtils.setCookie(response, cookieDomain, "openId", openId);
        log.info("【微信网页授权】获取openid,returnUrl={}", returnUrl);
        response.sendRedirect(returnUrl);
    }



}
