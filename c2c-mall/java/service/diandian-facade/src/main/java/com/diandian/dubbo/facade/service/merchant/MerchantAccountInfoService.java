package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;

import java.math.BigDecimal;

/**
 * 商户帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface MerchantAccountInfoService {

     void saveAccount(Long merchantId, BigDecimal transactionAmount, Integer businessType, Integer transactionMode,Integer transactionType,String transactionNumber,Long source);

     void releaseFrozenAmount(Long merchantId);

     MerchantAccountInfoModel getByMerchantId(Long merchantId);

     boolean saveAcc(MerchantAccountInfoModel mAccNew);

     void openingAward(Long merchantId,Integer level);

     void subordinateIncome(Long merchantId, BigDecimal profit);
}
