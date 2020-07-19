
package com.diandian.admin.common.oss;

import com.diandian.admin.common.util.SpringContextHolder;


/**
 * @author zzhihong
 * @date 2018-09-20
 */

public final class AliyunStorageFactory {

    private static AliyunStorageProperties properties;

    static {
        AliyunStorageFactory.properties = SpringContextHolder.getBean("aliyunStorageProperties");
    }

    public static AliyunStorageService build(){
        return new AliyunStorageService(properties);
    }

}

