package com.diandian.dubbo.facade.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义参数校验注解 校验 List 集合中是否有null 元素
 */

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DecimalScaleValidatorImpl.class) //// 此处指定了注解的实现类为ListNotHasNullValidatorImpl
public @interface DecimalScale {

	/**
	 * 小数位数量,默认为两位
	 */
	int value() default 2;

	String message() default "最多只能有两位小数";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 定义List，为了让Bean的一个属性上可以添加多套规则
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		DecimalScale[] value();
	}
}
