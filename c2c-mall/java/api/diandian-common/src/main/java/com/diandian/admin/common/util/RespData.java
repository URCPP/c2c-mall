package com.diandian.admin.common.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @author x
 * @date 2018/9/14 9:27
 */
@Data
public class RespData<T> implements Serializable {

    private static final long serialVersionUID = 5027039959339142736L;

    /**
     * 成功响应码
     */
    private static final int RESP_CODE_SUCCESS = 0;

    /**
     * 失败响应码
     */
    private static final int RESP_CODE_FAIL = 500;

    /**
     * 成功响应文本
     */
    private static final String RESP_STRING_SUCCESS = "成功";

    private Integer code;

    private String message;

    private Boolean success;

    private Long timestamp;

    private T data;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public RespData(Boolean success, Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public static RespData ok() {
        return RespData.ok(null);
    }

    public static RespData ok(Object data) {
        return new RespData<>(Boolean.TRUE, RespData.RESP_CODE_SUCCESS, RespData.RESP_STRING_SUCCESS, data);
    }

    public static RespData fail(String msg) {
        return RespData.fail(RespData.RESP_CODE_FAIL, msg);
    }

    public static RespData fail(int code, String msg) {
        return new RespData<>(Boolean.FALSE, code, msg, null);
    }

}
