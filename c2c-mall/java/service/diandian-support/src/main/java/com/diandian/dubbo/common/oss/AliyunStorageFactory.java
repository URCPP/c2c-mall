package com.diandian.dubbo.common.oss;


import com.diandian.dubbo.common.util.SpringContextHolder;

/**
 * @author zzhihong
 * @date 2018-09-20
 */
public final class AliyunStorageFactory {

    private static AliyunStorageProperties properties;

    static {
        AliyunStorageFactory.properties = SpringContextHolder.getBean(AliyunStorageProperties.class);
    }

    public static AliyunStorageService build(){
        return new AliyunStorageService(properties);
    }

}
