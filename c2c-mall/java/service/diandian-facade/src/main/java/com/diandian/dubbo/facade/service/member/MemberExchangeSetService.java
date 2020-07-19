package com.diandian.dubbo.facade.service.member;

import com.diandian.dubbo.facade.model.member.MemberExchangeSetModel;

/**
 * 会员兑换券充值设置表
 *
 * @author wbc
 * @date 2019/02/15
 */
public interface MemberExchangeSetService {

    /**
     * 修改设置
     *
     * @param memberExchangeSetModel
     * @return
     */
    boolean updateSet(MemberExchangeSetModel memberExchangeSetModel);

    /**
     * @return
     */
    MemberExchangeSetModel getSet();

    /**
     * 根据商户ID查询规则
     * @param merchantId
     * @return
     */
    MemberExchangeSetModel getSetByMerchantId(Long merchantId);
}
