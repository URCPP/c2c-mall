package com.diandian.dubbo.facade.dto.api.query;

import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.validation.api.CustomNotBlank;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 9:53
 */
@ToString
public class GetTokenQueryDTO implements Serializable {

    private static final long serialVersionUID = 40527553768805254L;

    @CustomNotBlank(code = IntegralStoreConstant.ERROR_40001_CODE, message = IntegralStoreConstant.ERROR_40001_MESSAGE)
    private String appId;

    @CustomNotBlank(code = IntegralStoreConstant.ERROR_40002_CODE, message = IntegralStoreConstant.ERROR_40002_MESSAGE)
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
