package com.diandian.dubbo.business.service.impl;


import com.diandian.dubbo.business.mapper.merchant.MerchantAddPriceMapper;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountHistoryLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAddPriceModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.service.biz.BizProductShareService;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountHistoryLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther chensong
 * @Date 2019/9/8
 */
@Service("bizProductShareService")
@Slf4j
public class BizProductShareServiceImpl implements BizProductShareService {

    @Autowired
    private MerchantAccountHistoryLogService merchantAccountHistoryLogService;
    @Autowired
    private MerchantAddPriceMapper merchantAddPriceMapper;
    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;


    @Override
    public void insert(MerchantAccountHistoryLogModel merchantAccountHistoryLogModel) {
        merchantAccountHistoryLogService.saveAccountHistoryLog(merchantAccountHistoryLogModel);
    }

    /*@Override
    public void grantFreezingBalance() {
        List<MerchantAddPriceModel> merchantAddPriceModels = merchantAddPriceMapper.selectRecord();
        for (MerchantAddPriceModel merchantAddPriceModel : merchantAddPriceModels) {
            MerchantAccountHistoryLogModel merchantAccountHistoryLogModel = new MerchantAccountHistoryLogModel();
            //设置商户Id
            Long merchantId = merchantAddPriceModel.getMerchantId();
            merchantAccountHistoryLogModel.setMerchantId(merchantId);
            //设置更新前金额
            MerchantWalletInfoModel walletInfo = merchantWalletInfoService.getWalletInfo(merchantId);
            BigDecimal amount = walletInfo.getAmount();
            merchantAccountHistoryLogModel.setPreAmount(amount);
            //设置转账金额
            BigDecimal addPrice = merchantAddPriceModel.getAddPrice();
            merchantAccountHistoryLogModel.setAmount(addPrice);
            //设置更新后金额
            BigDecimal postPrice = amount.add(addPrice);
            merchantAccountHistoryLogModel.setPostAmount(postPrice);
            //设置转账类型
            merchantAccountHistoryLogModel.setTypeFlag(7);
            merchantAccountHistoryLogService.saveAccountHistoryLog(merchantAccountHistoryLogModel);
            merchantAddPriceModel.setFlag(1);
        }
    }*/
}
