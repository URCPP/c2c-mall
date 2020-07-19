package com.diandian.dubbo.business.service.impl.member;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.member.MemberExchangeLogMapper;
import com.diandian.dubbo.business.mapper.member.MemberInfoMapper;
import com.diandian.dubbo.business.mapper.member.MemberOrderOptLogMapper;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.member.MemberExchangeDTO;
import com.diandian.dubbo.facade.dto.member.MemberInfoDTO;
import com.diandian.dubbo.facade.model.member.*;
import com.diandian.dubbo.facade.service.member.*;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 会员卡券兑换记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Service("memberExchangeLogService")
@Slf4j
public class MemberExchangeLogServiceImpl implements MemberExchangeLogService {

    @Autowired
    private MemberExchangeLogMapper memberExchangeLogMapper;

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private MemberExchangeSetService memberExchangeSetService;

    @Autowired
    private MemberMerchantRelationService memberMerchantRelationService;

    @Autowired
    private MemberOrderOptLogMapper memberOrderOptLogMapper;

    @Autowired
    private NoGenerator noGenerator;

    @Autowired
    private MemberExchangeHistoryLogService memberExchangeHistoryLogService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MerchantInfoService merchantInfoService;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Page<MemberExchangeLogModel> page = new PageWrapper<MemberExchangeLogModel>(params).getPage();
        IPage<MemberExchangeLogModel> iPage = memberExchangeLogMapper.listPage(page, params);
        return new PageResult(iPage);
    }

    @Override
    @Transactional
    public String exchange(MemberExchangeDTO dto) {

        BigDecimal amount = dto.getAmount();//充值金额
        AssertUtil.notNull(amount, "充值金额不能为空");
        String memberAccount = dto.getMemberAccount();//会员帐号
        AssertUtil.notBlank(memberAccount, "会员帐号不能为空");
        Long merchantId = dto.getMerchantId();

        QueryWrapper<MemberInfoModel> qw = new QueryWrapper<>();
        qw.eq("account_no", memberAccount);
        MemberInfoModel memberInfoModel = memberInfoMapper.selectOne(qw);
        AssertUtil.notNull(memberInfoModel,"该会员不存在，请先注册会员!");

        MemberMerchantRelationModel memberAcc = memberMerchantRelationService.getMemberAccount(merchantId, memberAccount);
        if (null == memberAcc){
            memberAcc = new MemberMerchantRelationModel();
            memberAcc.setMerchantId(dto.getMerchantId());
            MerchantInfoModel merchant = merchantInfoService.getMerchantInfoById(dto.getMerchantId());
            memberAcc.setMerchantLoginName(merchant.getLoginName());
            memberAcc.setMemberId(memberInfoModel.getId());
            memberAcc.setMemberLoginName(memberInfoModel.getAccountNo());
            memberAcc.setAvailBalance(BigDecimal.ZERO);
            memberAcc.setFreezeBalance(BigDecimal.ZERO);
            memberAcc.setConsumeTimesSum(0);
            memberAcc.setExchangeCouponSum(0);
            memberAcc.setExchangeCouponNum(0);
            memberAcc.setShoppingCouponSum(BigDecimal.ZERO);
            memberAcc.setShoppingCouponAmount(BigDecimal.ZERO);
            memberMerchantRelationService.save(memberAcc);
        }
        //计算兑换额度
        MemberExchangeSetModel exchangeRule = memberExchangeSetService.getSetByMerchantId(merchantId);
        BigDecimal amountBase = exchangeRule.getAmountBase();
        Integer exchangeRate = exchangeRule.getExchangeRate();
        BigDecimal exchangeRateBig = new BigDecimal(exchangeRate + "");
        BigDecimal baseBig = new BigDecimal(1.0 + "");
        BigDecimal shoppingRate = exchangeRule.getShoppingRate();
        int exchange = amount.divide(amountBase).multiply(exchangeRateBig).divide(baseBig, 0).intValue();//兑换额度
        //不兑换兑换券
        if (dto.getIsExchangeSelFlag().equals(1)) {
            exchange = 0;
        }
        BigDecimal shopping = amount.divide(amountBase).multiply(shoppingRate).divide(baseBig, 0);//兑换购物额度
        //不兑换购物券
        if (dto.getIsShoppingSelFlag().equals(1)) {
            shopping = BigDecimal.ZERO;
        }

        //会员帐户变更
        String billno = noGenerator.getExchangeNo();//兑换订单单号
        Integer exchangeCouponNum = memberAcc.getExchangeCouponNum() == null ? 0 : memberAcc.getExchangeCouponNum();
        Integer exchangeCouponSum = memberAcc.getExchangeCouponSum() == null ? 0 : memberAcc.getExchangeCouponSum();
        memberAcc.setExchangeCouponNum(exchangeCouponNum + exchange);
        memberAcc.setExchangeCouponSum(exchangeCouponSum + exchange);
        BigDecimal shoppingCouponAmount = memberAcc.getShoppingCouponAmount() == null ? BigDecimal.ZERO : memberAcc.getShoppingCouponAmount();
        BigDecimal shoppingCouponSum = memberAcc.getShoppingCouponSum() == null ? BigDecimal.ZERO : memberAcc.getShoppingCouponSum();
        memberAcc.setShoppingCouponAmount(shoppingCouponAmount.add(shopping));
        memberAcc.setShoppingCouponSum(shoppingCouponSum.add(shopping));
        memberMerchantRelationService.updateAcc(memberAcc);

        //会员兑换记录
        MemberExchangeLogModel memberExchangeLogModel = new MemberExchangeLogModel();
        memberExchangeLogModel.setBillNo(billno);
        memberExchangeLogModel.setTypeFlag(MemberConstant.MemberExchangeType.MERCHANT_EXCHANGE.getValue());
        memberExchangeLogModel.setStateFlag(MemberConstant.MemberExchangeState.EXCHANGE_SUCCESS.getValue());
        memberExchangeLogModel.setMerchantId(merchantId);
        memberExchangeLogModel.setMerchantName(dto.getMerchantName());
        memberExchangeLogModel.setMemberId(memberInfoModel.getId());
        memberExchangeLogModel.setMemberAccount(memberAccount);
        memberExchangeLogModel.setMemberBalance(memberAcc.getAvailBalance());
        memberExchangeLogModel.setMemberNickName(memberInfoModel.getNiceName());
        memberExchangeLogModel.setAmount(amount);
        memberExchangeLogModel.setExchangCouponQuota(exchange);
        memberExchangeLogModel.setShoppingCouponQuota(shopping);
        memberExchangeLogModel.setBeforeExchange(exchangeCouponNum);
        memberExchangeLogModel.setBeforeShopping(shoppingCouponAmount);
        memberExchangeLogMapper.insert(memberExchangeLogModel);

        // 添加商户会员兑换台帐明细
        MemberExchangeHistoryLogModel exchangeHistoryLogModel = new MemberExchangeHistoryLogModel();
        exchangeHistoryLogModel.setBillNo(billno);
        exchangeHistoryLogModel.setTypeFlag(MemberConstant.ExchangeHistoryType.ISSUE.value());
        exchangeHistoryLogModel.setBillTypeFlag(MemberConstant.MemberExchangeType.MERCHANT_EXCHANGE.getValue());
        exchangeHistoryLogModel.setMerchantId(merchantId);
        exchangeHistoryLogModel.setMerchantAccount(dto.getMechantAcc());
        exchangeHistoryLogModel.setMemberId(memberInfoModel.getId());
        exchangeHistoryLogModel.setMemberAccount(memberInfoModel.getAccountNo());
        exchangeHistoryLogModel.setExchangeNumber(exchange);
        exchangeHistoryLogModel.setBeforeNumber(exchangeCouponNum);
        exchangeHistoryLogModel.setAfterNumber(exchangeCouponNum + exchange);
        memberExchangeHistoryLogService.save(exchangeHistoryLogModel);

        //会员订单操作记录
        MemberOrderOptLogModel memberOrderOptLogModel = new MemberOrderOptLogModel();
        memberOrderOptLogModel.setOrderNo(billno);
        memberOrderOptLogModel.setOrderTypeFlag(MemberConstant.BillType.COUPON_EXCHANGE.getValue());
        memberOrderOptLogModel.setMerchantId(merchantId);
        memberOrderOptLogModel.setMerchantName(dto.getMerchantName());
        memberOrderOptLogModel.setMemberId(memberInfoModel.getId());
        memberOrderOptLogModel.setMemberAccount(memberAccount);
        memberOrderOptLogModel.setOptStateFlag(MemberConstant.BillState.BILL_SUCCESS.getValue());
        String record = dto.getMechantAcc() + "给" + memberAccount + "兑换兑换券：" + exchange + "，购物券:" + shopping;
        memberOrderOptLogModel.setOptRecord(record);
        memberOrderOptLogMapper.insert(memberOrderOptLogModel);

        //返回兑换额度
        JSONObject obj = new JSONObject();
        obj.put("exchange", exchange);
        obj.put("shopping", shopping);
        return obj.toJSONString();
    }
}
