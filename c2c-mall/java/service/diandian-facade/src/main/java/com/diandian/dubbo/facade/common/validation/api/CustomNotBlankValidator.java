package com.diandian.dubbo.facade.common.validation.api;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 10:15
 */
public class CustomNotBlankValidator implements ConstraintValidator<CustomNotBlank, String> {

    private int code;

    @Override
    public void initialize(CustomNotBlank constraintAnnotation) {
        code = constraintAnnotation.code();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isBlank(value)){
            return false;
        }
        return true;
    }
}
