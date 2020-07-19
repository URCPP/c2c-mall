package com.diandian.dubbo.facade.dto.wx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 微信公众号支付预下单结果对象
 * @author cjunyuan
 * @date 2019/6/28 15:53
 */
@Getter
@Setter
@ToString
public class WeixinPayMpOrderResultDTO implements Serializable {

    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packageValue;
    private String signType;
    private String paySign;
}
