package com.diandian.dubbo.facade.common.enums;

/**
 * 轮询规则枚举
 *
 * @author x
 * @date 2018-12-13
 */
public enum RouteRuleEnum {

    /**
     * 轮询
     */
    ROUND_ROBIN,

    /**
     * 最少收款金额
     */
    LEAST_PROCEED_AMOUNT,

    /**
     * 最少收款笔数
     */
    LEAST_PROCEED_NUM;
}
