package com.diandian.admin.business.modules.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/15 17:21
 */
@Getter
@Setter
@ToString
public class ResponseExpress100DTO implements Serializable {

    private Boolean result;
    private String returnCode;
    private String message;

    public ResponseExpress100DTO(){}

    public ResponseExpress100DTO(Boolean result, String returnCode, String message){
        this.result = result;
        this.returnCode = returnCode;
        this.message = message;
    }

    public static ResponseExpress100DTO fail() {
        return new ResponseExpress100DTO(Boolean.FALSE, "500", "失败");
    }

    public static ResponseExpress100DTO success() {
        return new ResponseExpress100DTO(Boolean.TRUE, "200", "成功");
    }
}
