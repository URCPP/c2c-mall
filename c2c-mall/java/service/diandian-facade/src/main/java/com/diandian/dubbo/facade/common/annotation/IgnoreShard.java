package com.diandian.dubbo.facade.common.annotation;

import java.lang.annotation.*;

/**
 * 微信用户登录效验
 * @author x
 * @date 2018/09/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreShard {
}
