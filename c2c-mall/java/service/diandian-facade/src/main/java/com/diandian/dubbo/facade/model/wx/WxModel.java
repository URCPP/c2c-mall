package com.diandian.dubbo.facade.model.wx;

import lombok.Data;

@Data
public class WxModel {

    private String access_token;
    private String ticket;
    private String noncestr;
    private String timestamp;
    private String str;
    private String signature;

}
