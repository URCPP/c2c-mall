package com.diandian.dubbo.facade.common.exception;

import com.diandian.dubbo.facade.common.constant.RetEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zzh
 * @date 2018/9/12 17:23
 */
@Getter
@Setter
public class DubboException extends RuntimeException {

    private String msg;
    private String code;

    public DubboException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public DubboException(RetEnum retEnum) {
        super(retEnum.getMessage());
        this.code = retEnum.getCode();
        this.msg = retEnum.getMessage();
    }

    public DubboException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public DubboException(String code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public DubboException(String code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
