package com.diandian.dubbo.facade.dto.api;

import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.validation.api.CustomNotNull;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 18:54
 */
@ToString
public class GenericRequestDTO implements Serializable {

    @CustomNotNull(code = IntegralStoreConstant.ERROR_40003_CODE, message = IntegralStoreConstant.ERROR_40003_MESSAGE)
    private Long timestamp;

    private String sign;

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
