package com.diandian.admin.business.modules.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 快递100订阅请求参数
 * @author cjunyuan
 * @date 2019/5/15 20:39
 */
@Getter
@Setter
@ToString
public class SubscriptionRequestParamDTO implements Serializable {

    private String company;
    private String number;
    private String from;
    private String to;
    private String key;
    private SubscriptionRequestParam parameters;

    @Getter
    @Setter
    @ToString
    public class SubscriptionRequestParam implements Serializable{
        private String callbackurl;
        private String salt;
        private String resultv2;
        private String autoCom;
        private String interCom;
        private String departureCountry;
        private String departureCom;
        private String destinationCountry;
        private String destinationCom;
        private String phone;
    }
}
