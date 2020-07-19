package com.diandian.dubbo.facade.common.validation.api;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 10:13
 */
@Documented
@Constraint(validatedBy = {CustomMinValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
public @interface CustomMin {

    String code() default "0";

    String message() default "";

    int value();

    Class<?>[]  groups()  default   {};

    Class<? extends Payload>[]  payload() default {};
}
