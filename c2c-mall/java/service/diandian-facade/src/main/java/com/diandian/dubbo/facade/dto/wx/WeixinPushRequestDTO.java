package com.diandian.dubbo.facade.dto.wx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 微信推送请求对象
 * @author cjunyuan
 * @date 2019/6/27 9:34
 */
@Getter
@Setter
@ToString
public class WeixinPushRequestDTO implements Serializable {

    private static final long serialVersionUID = -5578224683682412854L;

    private String requestBody;

    private String timestamp;

    private String nonce;

    private String signature;

    private String encType;

    private String msgSignature;

    private String openId;

    private String appId;
}
