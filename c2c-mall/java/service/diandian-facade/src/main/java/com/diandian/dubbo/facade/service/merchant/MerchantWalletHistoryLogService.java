package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.ExchangeDTO;
import com.diandian.dubbo.facade.dto.order.OrderInfoDTO;
import com.diandian.dubbo.facade.dto.order.OrderNoDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletHistoryLogModel;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 商户钱包变动历史表
 *
 * @author jbh
 * @date 2019/02/21
 */
public interface MerchantWalletHistoryLogService {

    /**
     * 商户钱包帐户变更列表 分页
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    void save(MerchantWalletHistoryLogModel merchantWalletHistoryLogModel);

    /**
     *
     * 功能描述: 获取商户最低充值金额
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/5/27 14:17
     */
    BigDecimal getMerchantMinRechargeAmount(Long merchantId);
}
