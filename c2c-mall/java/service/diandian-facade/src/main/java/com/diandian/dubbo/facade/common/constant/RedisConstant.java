package com.diandian.dubbo.facade.common.constant;

/**
 * @author x
 * @date 2018-11-22
 */
public class RedisConstant {

    /**
     * 订单超时时间(MS)
     */
    public static final long ORDER_DELAY_LOCK_EXPIRE = 60000 * 30;

    /**
     * 一种类别的子类别IDS
     */
    public final static String PRODUCT_CATEGORY_FOR_SEARCH = "PROUDCT_CATEGORY_SUB_IDS::";
}
