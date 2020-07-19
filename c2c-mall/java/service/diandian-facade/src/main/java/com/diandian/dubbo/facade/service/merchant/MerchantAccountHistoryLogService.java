package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountHistoryLogModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商户资金账户变动明细表
 *
 * @author jbh
 * @date 2019/02/26
 */
public interface MerchantAccountHistoryLogService {

    /**
     * 添加资金账户变动记录
     *
     * @param accountHistoryLogModel
     */
    void saveAccountHistoryLog(MerchantAccountHistoryLogModel accountHistoryLogModel);

    PageResult listPage(Map params);

    Map getById(Long id);
}
