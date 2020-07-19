package com.diandian.admin.merchant.common.exception;

import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.common.exception.DubboException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常拦截
 *
 * @author x
 * @date 2018/9/13 17:18
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public RespData handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return RespData.fail(500, e.getMsg());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(DubboException.class)
    public RespData handleDubboException(DubboException e) {
        log.error("业务异常", e);
        return RespData.fail(500, e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespData bindMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (null != fieldError) {
            return RespData.fail(500, fieldError.getDefaultMessage());
        }
        return RespData.fail(500, "参数错误");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public RespData handleUnauthorizedException(UnauthorizedException e) {

        return RespData.fail("没有权限");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespData handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return RespData.fail(String.format("不支持%s请求", e.getMethod()));
    }

    /**
     * 其他未定义异常
     */
    @ExceptionHandler(Exception.class)
    public RespData handleException(Exception e) {
        log.error("网络异常", e);
        return RespData.fail(500, "网络异常");
    }
}
