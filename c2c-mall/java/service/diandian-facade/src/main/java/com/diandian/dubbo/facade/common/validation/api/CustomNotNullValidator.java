package com.diandian.dubbo.facade.common.validation.api;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 10:15
 */
public class CustomNotNullValidator implements ConstraintValidator<CustomNotNull, Object> {

    @Override
    public void initialize(CustomNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(null == value){
            return false;
        }
        return true;
    }
}
