package com.diandian.dubbo.facade.dto.pay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 微信支付自定义数据对象
 * @author cjunyuan
 * @date 2019/6/10 18:28
 */
@Getter
@Setter
@ToString
public class WxPayNotifyAttachDTO implements Serializable {

    private Integer businessType;
    private String payType;
}
