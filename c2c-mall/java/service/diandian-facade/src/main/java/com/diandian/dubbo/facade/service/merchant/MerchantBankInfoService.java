package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantBankInfoModel;

import java.util.List;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 11:57 2019/10/28
 * @Modified By:
 */
public interface MerchantBankInfoService {
    List<MerchantBankInfoModel> list(Long merchantId);

    void deleteBankById(Long id);
}
