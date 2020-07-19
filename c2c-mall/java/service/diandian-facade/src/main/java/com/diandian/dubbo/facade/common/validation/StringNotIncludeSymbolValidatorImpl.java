package com.diandian.dubbo.facade.common.validation;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 功能描述: 自定义字符串是否包含特殊字符判断
 *
 * @param
 * @return
 * @author cjunyuan
 * @date 2019/6/5 14:04
 */
public class StringNotIncludeSymbolValidatorImpl implements ConstraintValidator<StringNotIncludeSymbol, String> {

    private static final Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");

    @Override
    public void initialize(StringNotIncludeSymbol constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(value)) {
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return true;
    }
}
