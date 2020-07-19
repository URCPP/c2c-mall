package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantProductInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantWalletInfoMapper;
import com.diandian.dubbo.common.ons.AliyunOnsUtil;
import com.diandian.dubbo.common.redis.lock.LockInfo;
import com.diandian.dubbo.common.redis.lock.LockTemplate;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.api.query.OrderQueryDTO;
import com.diandian.dubbo.facade.dto.api.result.OrderPayResultDTO;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantFreightSetModel;
import com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletHistoryLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantFreightSetService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletHistoryLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户钱包帐户信息表
 *
 * @author jbh
 * @date 2019/02/21
 */
@Service("merchantWalletInfoService")
@Slf4j
public class MerchantWalletInfoServiceImpl implements MerchantWalletInfoService {

    @Autowired
    private MerchantWalletInfoMapper merchantWalletInfoMapper;
    @Autowired
    private MemberOrderOptLogService memberOrderOptLogService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private MerchantWalletHistoryLogService merchantWalletHistoryLogService;
    @Autowired
    private LockTemplate lockTemplate;
    @Autowired
    private AliyunOnsUtil aliyunOnsUtil;
    @Autowired
    private MerchantProductInfoMapper merchantProductInfoMapper;
    @Autowired
    private MerchantFreightSetService merchantFreightSetService;
    @Autowired
    private MerchantInfoService merchantInfoService;


    @Override
    public boolean save(MerchantWalletInfoModel merchantWalletInfoModel) {
        merchantWalletInfoMapper.insert(merchantWalletInfoModel);
        return true;
    }

    @Override
    public MerchantWalletInfoModel getWalletInfo(Long merchantId) {
        QueryWrapper<MerchantWalletInfoModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        return merchantWalletInfoMapper.selectOne(qw);
    }

    @Override
    public boolean updateById(MerchantWalletInfoModel walletInfo) {
        merchantWalletInfoMapper.updateById(walletInfo);
        return true;
    }

    @Override
    public boolean updateAmount(Long merchantId, BigDecimal oldAmount, BigDecimal newAmount) {
        return merchantWalletInfoMapper.updateAmount(merchantId, oldAmount, newAmount) > 0;
    }

    @Override
    public boolean updateMarginAmount(Long merchantId, BigDecimal oldAmount, BigDecimal newAmount, BigDecimal marginAmount) {
        return merchantWalletInfoMapper.updateMarginAmount(merchantId, oldAmount, newAmount, marginAmount) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByOld(MerchantWalletInfoModel walletInfo, MerchantWalletInfoModel oldWallet) {
        QueryWrapper<MerchantWalletInfoModel> qw = new QueryWrapper<>();
        qw.eq("id", oldWallet.getId());
        qw.eq("amount", oldWallet.getAmount());
        qw.eq("surplus_exchange_number", oldWallet.getSurplusExchangeNumber());
        qw.eq("exchange_number", oldWallet.getExchangeNumber());
        int update = merchantWalletInfoMapper.update(walletInfo, qw);
        return update;
    }

    /**
     * 更新商户钱包
     *
     * @param orderNo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMerchantWallet(String orderNo) {
        MemberOrderOptLogModel orderOptLog = memberOrderOptLogService.getOrderOptLogByOrderNo(orderNo);
        if (ObjectUtil.isNull(orderOptLog)) {
            throw new DubboException("订单不存在！");
        }
        Long merchantId = orderOptLog.getMerchantId();
        Long memberId = orderOptLog.getMemberId();
        LockInfo accLock = null;
        try {
            accLock = lockTemplate.lock(String.format("MERCHANT_WALLET_%s_%s", merchantId, orderNo), 15000, 5000);
            if (ObjectUtil.isNull(accLock)) {
                throw new DubboException("系统繁忙,请稍后再试");
            }

            //商户钱包变动  wubc 20190409
            MerchantWalletInfoModel walletInfo = getWalletInfo(merchantId);
            MerchantWalletInfoModel old = new MerchantWalletInfoModel();
            MerchantWalletInfoModel upd = new MerchantWalletInfoModel();
            old.setId(walletInfo.getId());
            old.setAmount(walletInfo.getAmount());
            old.setSurplusExchangeNumber(walletInfo.getSurplusExchangeNumber());
            old.setExchangeNumber(walletInfo.getExchangeNumber());

            BigDecimal curWalletAmount = walletInfo.getAmount();
            OrderInfoModel orderInfo = orderInfoService.getByOrderNo(orderNo);
            BigDecimal actualAmount = BigDecimal.ZERO;
            //是否运费承担(0 会员, 1 商家)
            if (orderOptLog.getAssumeFreightFlag() == MemberConstant.AssumeFreight.MERCHANT.getValue()) {
                actualAmount = orderInfo.getActualAmount();
            } else {
                List<OrderDetailModel> orderDetailModels = orderDetailService.listByOrderNoAndState(orderNo, 0);
                BigDecimal freight = new BigDecimal(0);
                for (OrderDetailModel orderDetail : orderDetailModels) {
                    freight = freight.add(orderDetail.getTransportFee());
                }
                actualAmount = orderInfo.getActualAmount().subtract(freight);
            }
            BigDecimal newWalletAmount = curWalletAmount.subtract(actualAmount);
            if(newWalletAmount.compareTo(BigDecimal.ZERO) == -1){
                throw new DubboException("兑换失败，商户储备金不足");
            }
            upd.setAmount(newWalletAmount);
            BigDecimal amountSum = walletInfo.getAmountSum() == null ? BigDecimal.ZERO : walletInfo.getAmountSum();
            upd.setAmountSum(amountSum.add(actualAmount));
            Integer surplusExchangeNumber = walletInfo.getSurplusExchangeNumber() == null ? 0 : walletInfo.getSurplusExchangeNumber();//未兑换
            Integer exchangeNumber = walletInfo.getExchangeNumber() == null ? 0 : walletInfo.getExchangeNumber();//己兑换
            Integer exchangeCouponNum = orderOptLog.getExchangeCouponNum() == null ? 0 : orderOptLog.getExchangeCouponNum();
            if ((surplusExchangeNumber - exchangeCouponNum) > 0) {
                upd.setSurplusExchangeNumber(surplusExchangeNumber - exchangeCouponNum);
                upd.setExchangeNumber(exchangeNumber + exchangeCouponNum);
            }
            int i = updateByOld(upd, old);
            if (i <= 0) {
                throw new DubboException("商户储备金扣除失败");
            }
//            QueryWrapper<MerchantWalletInfoModel> qw = new QueryWrapper<>();
//            qw.eq("id", walletInfo.getId());
//            qw.eq("amount", curWalletAmount);
//            qw.ge("amount", actualAmount);
//            Integer update = merchantWalletInfoMapper.update(walletInfo, qw);

            //更新会员订单操作记录
            orderOptLog.setOrderState(MemberConstant.OrderState.ORDER_PAID_AMOUNT.getValue());
            memberOrderOptLogService.updateMemberOrderOptLog(orderOptLog);
            //添加商户钱包帐户变更明细
            MerchantWalletHistoryLogModel walletLog = new MerchantWalletHistoryLogModel();
            walletLog.setMerchantId(merchantId);
            walletLog.setMerchantName(orderOptLog.getMerchantName());
            walletLog.setTypeFlag(MerchantConstant.MerchantWalletLogType.EXCHANGE_PRODUCT.value());
            walletLog.setMemberAccount(orderOptLog.getMemberAccount());
            walletLog.setPreAmount(curWalletAmount);
            walletLog.setAmount(actualAmount.multiply(new BigDecimal(-1.0)));
            walletLog.setPostAmount(curWalletAmount.subtract(actualAmount));
            String optRecord = "会员 " + orderOptLog.getMemberAccount() + " 在商家 " + orderOptLog.getMerchantName() + " 兑换商品，花费 " + actualAmount.setScale(2,BigDecimal.ROUND_DOWN) + " 元,消耗积分 " + orderOptLog.getExchangeCouponNum();
            walletLog.setOptRecord(optRecord);
            merchantWalletHistoryLogService.save(walletLog);
            //更新兑换数量
            List<OrderDetailModel> orderDetails = orderDetailService.listByOrderNo(orderNo);
            orderDetails.forEach(el -> {
                MerchantProductInfoModel merchantProductInfo = merchantProductInfoMapper.selectOne(new QueryWrapper<MerchantProductInfoModel>().eq("merchant_id", merchantId).eq("sku_id", el.getSkuId()));
                merchantProductInfo.setExchangeNum(merchantProductInfo.getExchangeNum() + el.getNum());
                merchantProductInfoMapper.updateById(merchantProductInfo);
            });
            //发送消息 发消息更新订单状态
            aliyunOnsUtil.doSendTradeAsync(String.format("orderState_%s", orderNo), orderNo, "orderState");
        } catch (InterruptedException e) {
            log.error("商户帐号锁异常", e);
            throw new DubboException("商户帐号异常,请稍后再试");
        } finally {
            if (ObjectUtil.isNotNull(accLock)) {
                lockTemplate.releaseLock(accLock);
            }
        }
    }

    @Override
    public OrderPayResultDTO apiPayOrder(OrderQueryDTO dto){
        OrderInfoModel oldOrderInfo = orderInfoService.getByMchOrderNo(dto.getMchOrderNo());
        if(null == oldOrderInfo){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40026_CODE, IntegralStoreConstant.ERROR_40026_MESSAGE);
        }
        if(BizConstant.STATE_DISNORMAL.equals(oldOrderInfo.getPayState())){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40029_CODE, IntegralStoreConstant.ERROR_40029_MESSAGE);
        }
        Long merchantId = oldOrderInfo.getMerchantId();
        //MerchantInfoModel oldMchInfo = merchantInfoService.apiCheckMchIsNormal(merchantId);
        MerchantFreightSetModel freightSet = merchantFreightSetService.getByMerchantId(merchantId);
        if(null == freightSet){
            throw new DubboException("" + IntegralStoreConstant.ERROR_41004_CODE, IntegralStoreConstant.ERROR_41004_MESSAGE);
        }
        OrderPayResultDTO orderPayResult = new OrderPayResultDTO();
        orderPayResult.setMchOrderNo(dto.getMchOrderNo());
        LockInfo accLock = null;
        try {
            accLock = lockTemplate.lock(String.format("MERCHANT_WALLET_%s_%s", merchantId, oldOrderInfo.getOrderNo()), 15000, 5000);
            if (ObjectUtil.isNull(accLock)) {
                throw new DubboException("" + IntegralStoreConstant.ERROR_40000_CODE, IntegralStoreConstant.ERROR_40000_MESSAGE);
            }

            //商户钱包变动
            MerchantWalletInfoModel walletInfo = getWalletInfo(merchantId);
            MerchantWalletInfoModel upd = new MerchantWalletInfoModel();

            BigDecimal curWalletAmount = walletInfo.getAmount();
            BigDecimal actualAmount = oldOrderInfo.getActualAmount();
            //是否运费承担(0 会员, 1 商家)
            orderPayResult.setTotalAmount(oldOrderInfo.getActualAmount().setScale(2).toString());
            BigDecimal newWalletAmount = curWalletAmount.subtract(actualAmount);
            if(newWalletAmount.compareTo(BigDecimal.ZERO) == -1){
                throw new DubboException("" + IntegralStoreConstant.ERROR_40027_CODE, IntegralStoreConstant.ERROR_40027_MESSAGE);
            }
            upd.setAmount(newWalletAmount);
            BigDecimal amountSum = walletInfo.getAmountSum() == null ? BigDecimal.ZERO : walletInfo.getAmountSum();
            upd.setAmountSum(amountSum.add(actualAmount));
            UpdateWrapper<MerchantWalletInfoModel> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", walletInfo.getId());
            updateWrapper.eq("amount", walletInfo.getAmount());
            updateWrapper.eq("amount_sum", walletInfo.getAmountSum());

            boolean walletUpdateResult = optimisticLockingUpdateMchWallet(upd, actualAmount, updateWrapper, 1);

            if (!walletUpdateResult) {
                throw new DubboException("" + IntegralStoreConstant.ERROR_40000_CODE, IntegralStoreConstant.ERROR_40000_MESSAGE);
            }
            //添加商户钱包帐户变更明细
            MerchantWalletHistoryLogModel walletLog = new MerchantWalletHistoryLogModel();
            walletLog.setMerchantId(merchantId);
            //walletLog.setMerchantName(oldMchInfo.getName());
            walletLog.setTypeFlag(MerchantConstant.MerchantWalletLogType.EXCHANGE_PRODUCT.value());
            walletLog.setPreAmount(curWalletAmount);
            walletLog.setAmount(actualAmount.multiply(new BigDecimal(-1.0)));
            walletLog.setPostAmount(curWalletAmount.subtract(actualAmount));
            String optRecord = "商户通过开放平台兑换商品，花费 " + actualAmount.setScale(2,BigDecimal.ROUND_DOWN) + " 元";
            walletLog.setOptRecord(optRecord);
            merchantWalletHistoryLogService.save(walletLog);
            //更新兑换数量
            List<OrderDetailModel> orderDetails = orderDetailService.listByOrderNo(oldOrderInfo.getOrderNo());
            orderDetails.forEach(el -> {
                if(BizConstant.OrderState.CLOSE_ORDER.value().equals(el.getState())){
                    throw new DubboException("" + IntegralStoreConstant.ERROR_40028_CODE, IntegralStoreConstant.ERROR_40028_MESSAGE);
                }
                MerchantProductInfoModel merchantProductInfo = merchantProductInfoMapper.selectOne(new QueryWrapper<MerchantProductInfoModel>().eq("merchant_id", merchantId).eq("sku_id", el.getSkuId()));
                merchantProductInfo.setExchangeNum(merchantProductInfo.getExchangeNum() + el.getNum());
                merchantProductInfoMapper.updateById(merchantProductInfo);
            });
            //发送消息 发消息更新订单状态
            aliyunOnsUtil.doSendTradeAsync(String.format("orderState_%s", oldOrderInfo.getOrderNo()), oldOrderInfo.getOrderNo(), "orderState");
        } catch (InterruptedException e) {
            log.error("商户帐号锁异常", e);
            throw new DubboException("商户帐号异常,请稍后再试");
        } finally {
            if (ObjectUtil.isNotNull(accLock)) {
                lockTemplate.releaseLock(accLock);
            }
        }
        return orderPayResult;
    }

    private boolean optimisticLockingUpdateMchWallet(MerchantWalletInfoModel update, BigDecimal amount, UpdateWrapper<MerchantWalletInfoModel> wrapper, int updateTime){
        if(updateTime <= 5){
            if(merchantWalletInfoMapper.update(update, wrapper) < 1){
                MerchantWalletInfoModel oldMchWallet = getWalletInfo(update.getMerchantId());
                if(oldMchWallet.getAmount().compareTo(amount) == -1){
                    throw new DubboException("" + IntegralStoreConstant.ERROR_40027_CODE, IntegralStoreConstant.ERROR_40027_MESSAGE);
                }
                wrapper.eq("amount", oldMchWallet.getAmount());
                wrapper.eq("amount_sum", oldMchWallet.getAmountSum());
                update.setAmount(oldMchWallet.getAmount().subtract(amount));
                update.setAmountSum(oldMchWallet.getAmountSum().add(amount));
                optimisticLockingUpdateMchWallet(update, amount, wrapper, ++updateTime);
            }
            return true;
        } else {
            return false;
        }
    }
}
