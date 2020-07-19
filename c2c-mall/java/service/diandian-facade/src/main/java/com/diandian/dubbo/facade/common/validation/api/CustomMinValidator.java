package com.diandian.dubbo.facade.common.validation.api;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 10:15
 */
public class CustomMinValidator implements ConstraintValidator<CustomMin, Integer> {

    private int minValue;

    @Override
    public void initialize(CustomMin constraintAnnotation) {
        minValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return null != value && value >= minValue;
    }
}
