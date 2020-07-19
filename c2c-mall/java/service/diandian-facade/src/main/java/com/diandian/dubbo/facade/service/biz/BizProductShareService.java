package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.model.merchant.MerchantAccountHistoryLogModel;

import java.util.Map;

public interface BizProductShareService {
    void insert(MerchantAccountHistoryLogModel merchantAccountHistoryLogModel);

    //void grantFreezingBalance();
}
