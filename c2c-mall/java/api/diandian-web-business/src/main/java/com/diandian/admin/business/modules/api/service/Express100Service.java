package com.diandian.admin.business.modules.api.service;

/**
 * 快递100接口service
 * @author cjunyuan
 * @date 2019/5/15 20:33
 */
public interface Express100Service {

    /**
     *
     * 功能描述: 订阅运输信息推送
     *
     * @param key
     * @param transportNo
     * @param phone
     * @return
     * @author cjunyuan
     * @date 2019/5/15 20:34
     */
    boolean subscription(String key, String transportNo, String phone);
}
