package com.diandian.admin.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author x
 * @date 2018/9/12 17:23
 */
@Getter
@Setter
@ToString
public class BusinessException extends RuntimeException {

    private String msg;
    private int code = 500;

    public BusinessException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BusinessException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public BusinessException(int code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public BusinessException(int code, String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

}
