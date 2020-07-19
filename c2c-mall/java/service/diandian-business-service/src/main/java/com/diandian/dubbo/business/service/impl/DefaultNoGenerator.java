package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author x
 * @date 2019-02-18
 */
@Service("defaultNoGenerator")
public class DefaultNoGenerator implements NoGenerator {

    @Override
    public String getApplyNo() {
        return String.format(BizConstant.NO_TYPE_APPLY_ORDER, IdWorker.getIdStr());
    }

    @Override
    public String getRechargeNo() {
        return String.format(BizConstant.NO_TYPE_RECHARGE, IdWorker.getIdStr());
    }

    @Override
    public String getExchangeNo() {
        return String.format(BizConstant.NO_TYPE_EXCHANGE, IdWorker.getIdStr());
    }

    @Override
    public String getShopOrderNo() {
        return String.format(BizConstant.NO_TYPE_SHOP_ORDER, IdWorker.getIdStr());
    }

    @Override
    public String getPublicOrderNo() {
        return String.format(BizConstant.PUBLIC_SHOP_ORDER, IdWorker.getIdStr());
    }

    @Override
    public String getRewardTradeNo() {
        return String.format(BizConstant.NO_TYPE_ORG_REWARD, IdWorker.getIdStr());
    }

    @Override
    public String getTradeNo() {
        return String.format(BizConstant.NO_TYPE_ORG_REWARD, IdWorker.getIdStr());
    }

    @Override
    public String getOrgAccountNo() { return String.format(BizConstant.NO_TYPE_ORG_ACCOUNT, IdWorker.getIdStr()); }

    @Override
    public String getOrgNo() {
        StringBuilder sbd = new StringBuilder(BizConstant.NO_TYPE_ORG_PREFIX)
                .append(DateUtil.format(new Date(), "yyMMdd"))
                .append(RandomUtil.randomNumbers(4));
        return sbd.toString();
    }

    @Override
    public String getMerchantNo() {
        StringBuilder sbd = new StringBuilder(BizConstant.NO_TYPE_MERCHANT_PREFIX)
                .append(DateUtil.format(new Date(), "yyMMdd"))
                .append(RandomUtil.randomNumbers(4));
        return sbd.toString();
    }

    @Override
    public String getMemberNo() {
        StringBuilder sbd = new StringBuilder(BizConstant.NO_TYPE_MERCHANT_PREFIX)
                .append(DateUtil.format(new Date(), "yyMMdd"))
                .append(RandomUtil.randomNumbers(4));
        return sbd.toString();
    }

    @Override
    public String getWithdrawalNo() {
        return String.format(BizConstant.NO_WITHDRAWAL_APPLY, IdWorker.getIdStr());
    }

    @Override
    public String getMerchantPayNo() {
        return String.format(BizConstant.NO_MERCHANT_PAY, IdWorker.getIdStr());
    }
    @Override
    public String getMerchantOfflinePayNo() {
        return String.format(BizConstant.NO_MERCHANT_OFFLINE_PAY, IdWorker.getIdStr());
    }
}
