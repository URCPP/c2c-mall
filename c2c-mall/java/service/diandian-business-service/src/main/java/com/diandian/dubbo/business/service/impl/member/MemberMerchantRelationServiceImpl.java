package com.diandian.dubbo.business.service.impl.member;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.member.MemberInfoMapper;
import com.diandian.dubbo.business.mapper.member.MemberMerchantRelationMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.common.redis.lock.LockInfo;
import com.diandian.dubbo.common.redis.lock.LockTemplate;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.model.member.*;
import com.diandian.dubbo.facade.service.member.MemberExchangeHistoryLogService;
import com.diandian.dubbo.facade.service.member.MemberMerchantRelationService;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商户下的会员帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Service("memberMerchantRelationService")
@Slf4j
public class MemberMerchantRelationServiceImpl implements MemberMerchantRelationService {

    @Autowired
    private MemberMerchantRelationMapper memberMerchantRelationMapper;
    @Autowired
    private MemberOrderOptLogService memberOrderOptLogService;
    @Autowired
    private MemberExchangeHistoryLogService memberExchangeHistoryLogService;
    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;
    @Autowired
    private LockTemplate lockTemplate;
    @Autowired
    private MerchantInfoMapper merchantInfoMapper;
    @Autowired
    private MemberInfoMapper memberInfoMapper;


    @Override
    public MemberMerchantRelationModel getMemberAccount(Long merchantId, String memberAccount) {
        AssertUtil.isTrue(ObjectUtil.isNotNull(merchantId), "商户不能为空");
        AssertUtil.isTrue(StrUtil.isNotBlank(memberAccount), "会员不能为空");
        QueryWrapper<MemberMerchantRelationModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        qw.eq("member_login_name", memberAccount);
        return memberMerchantRelationMapper.selectOne(qw);
    }

    @Override
    public boolean updateAcc(MemberMerchantRelationModel memberAcc) {
        memberMerchantRelationMapper.updateById(memberAcc);
        return true;
    }

    @Override
    public List<MemberMerchantRelationModel> listMemberInfo(Map<String, Object> params) {
        return memberMerchantRelationMapper.listMemberInfo(params);
    }

    @Override
    public MemberMerchantRelationModel getMemberAccByMerIdAndMemId(Long merchantId, Long memberId) {
        AssertUtil.isTrue(ObjectUtil.isNotNull(merchantId), "商户不能为空");
        AssertUtil.isTrue(ObjectUtil.isNotNull(memberId), "会员不能为空");
        QueryWrapper<MemberMerchantRelationModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        qw.eq("member_id", memberId);
        MemberMerchantRelationModel memberMerchantRelationModel = memberMerchantRelationMapper.selectOne(qw);
        if (null == memberMerchantRelationModel) {

            memberMerchantRelationModel = new MemberMerchantRelationModel();
            memberMerchantRelationModel.setMemberId(memberId);
            memberMerchantRelationModel.setMerchantId(merchantId);
            memberMerchantRelationModel.setExchangeCouponSum(0);
            memberMerchantRelationModel.setExchangeCouponNum(0);
            memberMerchantRelationModel.setShoppingCouponAmount(BigDecimal.ZERO);
            memberMerchantRelationModel.setShoppingCouponSum(BigDecimal.ZERO);
            memberMerchantRelationModel.setAvailBalance(BigDecimal.ZERO);
            memberMerchantRelationModel.setFreezeBalance(BigDecimal.ZERO);
            memberMerchantRelationModel.setConsumeTimesSum(0);
            MerchantInfoModel mer = merchantInfoMapper.selectById(merchantId);
            if (null != mer) {
                memberMerchantRelationModel.setMerchantLoginName(mer.getLoginName());
            }
            MemberInfoModel mem = memberInfoMapper.selectById(memberId);
            if (null != mem) {
                memberMerchantRelationModel.setMemberLoginName(mem.getAccountNo());
            }

            memberMerchantRelationMapper.insert(memberMerchantRelationModel);
        }
        return memberMerchantRelationModel;
    }

    @Override
    public boolean updateAccByNum(MemberMerchantRelationModel memberAcc, MemberMerchantRelationModel oldMemberAcc) {
        QueryWrapper<MemberMerchantRelationModel> qw = new QueryWrapper<>();
        qw.eq("id", oldMemberAcc.getId());
        qw.eq("exchange_coupon_num", oldMemberAcc.getExchangeCouponNum());
        qw.eq("exchange_coupon_sum", oldMemberAcc.getExchangeCouponSum());
        int update = memberMerchantRelationMapper.update(memberAcc, qw);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMemberAccount(String orderNo) {
        MemberOrderOptLogModel orderOptLog = memberOrderOptLogService.getOrderOptLogByOrderNo(orderNo);
        if (ObjectUtil.isNull(orderOptLog)) {
            throw new DubboException("订单不存在！");
        }
        Long merchantId = orderOptLog.getMerchantId();
        Long memberId = orderOptLog.getMemberId();
        LockInfo accLock = null;
        try {
            accLock = lockTemplate.lock(String.format("ORDER_OPT_%s_%s", memberId, orderNo), 15000, 5000);
            if (ObjectUtil.isNull(accLock)) {
                throw new DubboException("系统繁忙,请稍后再试");
            }
            MemberMerchantRelationModel memberAcc = memberMerchantRelationMapper.selectOne(
                    new QueryWrapper<MemberMerchantRelationModel>()
                            .eq("merchant_id", merchantId)
                            .eq("member_id", memberId)
            );
            if (ObjectUtil.isNull(memberAcc)) {
                throw new DubboException("会员不存在");
            }
            Integer curExchangeCouponNum = memberAcc.getExchangeCouponNum();
            memberAcc.setExchangeCouponNum(curExchangeCouponNum - orderOptLog.getExchangeCouponNum());
            QueryWrapper<MemberMerchantRelationModel> qw = new QueryWrapper<>();
            qw.eq("id", memberAcc.getId());
            qw.eq("exchange_coupon_num", curExchangeCouponNum);
            qw.ge("exchange_coupon_num", orderOptLog.getExchangeCouponNum());
            Integer update = memberMerchantRelationMapper.update(memberAcc, qw);
            if (ObjectUtil.isNull(update) || update <= 0) {
                throw new DubboException("会员兑换券扣除失败");
            }
            orderOptLog.setOrderState(MemberConstant.OrderState.ORDER_PAID_COUPON.getValue());
            memberOrderOptLogService.updateMemberOrderOptLog(orderOptLog);
            //会员帐户变动记录
            MemberExchangeHistoryLogModel historyLogModel = new MemberExchangeHistoryLogModel();
            historyLogModel.setBillNo(orderNo);
            historyLogModel.setTypeFlag(MemberConstant.ExchangeHistoryType.EXPEND.value());
            historyLogModel.setBillTypeFlag(MemberConstant.MemberExchangeType.MEMBER_INTEGRAL_EXCHANGE.getValue());
            historyLogModel.setExchangeNumber(orderOptLog.getExchangeCouponNum() * -1);//记负帐
            historyLogModel.setBeforeNumber(curExchangeCouponNum);
            historyLogModel.setAfterNumber(memberAcc.getExchangeCouponNum());
            historyLogModel.setMerchantId(merchantId);
            historyLogModel.setMemberAccount(memberAcc.getMerchantLoginName());
            historyLogModel.setMemberId(memberAcc.getMemberId());
            historyLogModel.setMemberAccount(memberAcc.getMemberLoginName());
            memberExchangeHistoryLogService.save(historyLogModel);
            //更新商户钱包
            merchantWalletInfoService.updateMerchantWallet(orderNo);
        } catch (InterruptedException e) {
            log.error("会员帐号锁异常", e);
            throw new DubboException("会员帐号异常,请稍后再试");
        } finally {
            if (ObjectUtil.isNotNull(accLock)) {
                lockTemplate.releaseLock(accLock);
            }
        }
    }

    @Override
    public int save(MemberMerchantRelationModel relationModel) {
        return memberMerchantRelationMapper.insert(relationModel);
    }
}
