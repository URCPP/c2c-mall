package com.diandian.dubbo.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zzh
 * @date 2018/9/12 17:23
 */
@Getter
@Setter
public class StorageException extends RuntimeException {

    private String msg;
    private String code;

    public StorageException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public StorageException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public StorageException(String code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public StorageException(String code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
