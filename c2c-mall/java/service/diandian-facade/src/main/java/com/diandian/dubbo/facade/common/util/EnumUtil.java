package com.diandian.dubbo.facade.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 枚举类型工具类
 *
 * @author cjunyuan
 * @date 2019/4/4 16:53
 */
public class EnumUtil {
    public static <T extends Enum<T>> T getLabelByValue(Class<T> clazz, String getTypeCodeMethodName, Integer typeCode) {
        T result = null;
        try {
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(getTypeCodeMethodName);
            Integer typeCodeVal = null;
            for (T entity : arr) {
                typeCodeVal = Integer.valueOf(targetMethod.invoke(entity).toString());
                if (typeCodeVal.equals(typeCode)) {
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static <T extends Enum<T>> T getLabelByValue(Class<T> clazz, String getTypeCodeMethodName, String typeCode) {
        T result = null;
        try {
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(getTypeCodeMethodName);
            String typeCodeVal = null;
            for (T entity : arr) {
                typeCodeVal = targetMethod.invoke(entity).toString();
                if (typeCodeVal.equals(typeCode)) {
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }
}
