package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;

import java.util.Map;


/**
 * 商户的会员兑换订单记录表
 *
 * @author jbh
 * @date 2019/02/20
 */
public interface MerchantExchangeOrderLogService {
    /**
     * 商户的会员兑换订单记录列表
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);
}
