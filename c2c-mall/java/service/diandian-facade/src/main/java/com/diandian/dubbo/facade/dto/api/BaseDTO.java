package com.diandian.dubbo.facade.dto.api;

import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.validation.api.CustomNotBlank;
import com.diandian.dubbo.facade.common.validation.api.CustomNotNull;
import lombok.ToString;

import javax.validation.Valid;
import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 18:54
 */
@ToString
public class BaseDTO implements Serializable {

    @CustomNotBlank(code = IntegralStoreConstant.ERROR_40001_CODE, message = IntegralStoreConstant.ERROR_40001_MESSAGE)
    private String appId;

    @CustomNotNull(code = IntegralStoreConstant.ERROR_40003_CODE, message = IntegralStoreConstant.ERROR_40003_MESSAGE)
    private Long timestamp;

    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
