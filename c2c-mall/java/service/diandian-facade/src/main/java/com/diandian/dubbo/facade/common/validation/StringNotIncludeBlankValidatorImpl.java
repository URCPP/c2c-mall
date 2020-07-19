package com.diandian.dubbo.facade.common.validation;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义字符串是否包含空字符串判断
 *
 * @author cjunyuan
 * @date 2019/3/25 10:24
 */
public class StringNotIncludeBlankValidatorImpl implements ConstraintValidator<StringNotIncludeBlank, String> {
    @Override
    public void initialize(StringNotIncludeBlank constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //null时不进行校验
        if (StringUtils.isNotBlank(value) && value.contains(" ")) {
            return false;
        }
        return true;
    }
}
