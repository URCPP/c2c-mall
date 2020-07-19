package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantSoftHistoryLogModel;

/**
 * 商户软件变动历史表
 *
 * @author wbc
 * @date 2019/02/19
 */
public interface MerchantSoftHistoryLogService {

    /**
     * 添加变更记录
     *
     * @param merchantSoftHistoryLogModel
     * @return
     */
    boolean save(MerchantSoftHistoryLogModel merchantSoftHistoryLogModel);
}
