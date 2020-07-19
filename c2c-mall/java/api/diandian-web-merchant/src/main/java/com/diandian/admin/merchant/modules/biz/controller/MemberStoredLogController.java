package com.diandian.admin.merchant.modules.biz.controller;

import cn.hutool.core.util.ObjectUtil;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.member.MemberInfoDTO;
import com.diandian.dubbo.facade.dto.member.MemberStoredLogDTO;
import com.diandian.dubbo.facade.model.member.*;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.service.member.*;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 储值管理
 *
 * @author jbuhuan
 * @date 2019/2/18 19:14
 */
@RestController
@RequestMapping("/biz/memberStoredLog")
@Slf4j
public class MemberStoredLogController extends BaseController {
    @Autowired
    private MemberStoredLogService memberStoredLogService;
    @Autowired
    private MemberMerchantRelationService merchantRelationService;
    @Autowired
    private NoGenerator noGenerator;
    @Autowired
    private MemberOrderOptLogService OrderOptLogService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberExchangeHistoryLogService memberExchangeHistoryLogService;
    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private MemberMerchantRelationService memberMerchantRelationService;

    /**
     * @Author wubc
     * @Description //TODO 会员储值
     * @Date 14:56 2019/4/2
     * @Param [storedLogDTO]
     * @return com.diandian.admin.common.util.RespData
     **/
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public RespData insertStoredLog(@RequestBody MemberStoredLogDTO storedLogDTO) {

        MerchantInfoModel merchantInfo = getMerchantInfo();
        // 1. 验证 会员(包括是否为平台会员，是否为该商户会员)
        Long merchantInfoId = merchantInfo.getId();
        String memberAccount = storedLogDTO.getMemberAccount();
        AssertUtil.notBlank(memberAccount, "会员账号不能为空");
        MemberInfoModel infoModel = memberInfoService.getByMemberAccount(memberAccount);
        AssertUtil.notNull(infoModel,"该会员不存，请先注册会员！");
//        MemberMerchantRelationModel relationModel = merchantRelationService.getMemberAccount(merchantInfoId, memberAccount);
//        if (ObjectUtil.isNull(infoModel)) {
//            //初始化储值会员账号
//            MemberInfoDTO memberDTO = new MemberInfoDTO();
//            memberDTO.setMemberAcc(memberAccount);
//            memberDTO.setMemberPwd("123456");
//            memberDTO.setMerchantId(merchantInfo.getId());
//            memberDTO.setType(2);
//            memberInfoService.memberRegister(memberDTO);
//        }
//        MemberInfoModel memberInfoModel = memberInfoService.getByMemberAccount(memberAccount);
        MemberMerchantRelationModel relationModel = merchantRelationService.getMemberAccount(merchantInfoId, memberAccount);
        if (null == relationModel){
            relationModel = new MemberMerchantRelationModel();
            relationModel.setMerchantId(merchantInfoId);
            MerchantInfoModel merchant = merchantInfoService.getMerchantInfoById(merchantInfoId);
            relationModel.setMerchantLoginName(merchant.getLoginName());
            relationModel.setMemberId(infoModel.getId());
            relationModel.setMemberLoginName(infoModel.getAccountNo());
            relationModel.setAvailBalance(BigDecimal.ZERO);
            relationModel.setFreezeBalance(BigDecimal.ZERO);
            relationModel.setConsumeTimesSum(0);
            relationModel.setExchangeCouponSum(0);
            relationModel.setExchangeCouponNum(0);
            relationModel.setShoppingCouponSum(BigDecimal.ZERO);
            relationModel.setShoppingCouponAmount(BigDecimal.ZERO);
            memberMerchantRelationService.save(relationModel);
        }
        String merchantName = getMerchantInfo().getName();
        Date date = new Date();

        // 2. 添加会员储值记录
        MemberStoredLogModel storedLogModel = new MemberStoredLogModel();
        String shopOrderNo = noGenerator.getShopOrderNo();
        storedLogModel.setBillNo(shopOrderNo);
        storedLogModel.setMerchantId(merchantInfoId);
        storedLogModel.setMerchantName(merchantName);
        storedLogModel.setMemberId(infoModel.getId());
        storedLogModel.setMemberAccount(memberAccount);
        storedLogModel.setMemberNickName(infoModel.getNiceName());
        storedLogModel.setMemberBalance(relationModel.getAvailBalance());
        storedLogModel.setStoredAmount(storedLogDTO.getStoredAmount());
        storedLogModel.setRealAmount(storedLogDTO.getRealAmount());
        storedLogModel.setExchangeCouponNum(storedLogDTO.getExchangeCouponNum());
        storedLogModel.setShoppingCouponAmount(storedLogDTO.getShoppingCouponAmount());
        storedLogModel.setDescription("充值成功");
        memberStoredLogService.insertMemberStoredLog(storedLogModel);

        // 3. 添加 会员订单操作记录 （类型 会员储值）
        MemberOrderOptLogModel optLogModel = new MemberOrderOptLogModel();
        optLogModel.setOrderNo(shopOrderNo);
        optLogModel.setOrderTypeFlag(0);
        optLogModel.setMerchantId(merchantInfoId);
        optLogModel.setMerchantName(merchantName);
        optLogModel.setMemberId(infoModel.getId());
        optLogModel.setMemberAccount(memberAccount);
        StringBuilder optRecord = new StringBuilder();
        optRecord.append(getMerchantInfo().getLoginName() + "\t给用户\t" + memberAccount + "\t储值\t");
        optRecord.append(storedLogDTO.getStoredAmount() + "\t到账\t" + storedLogDTO.getRealAmount());
        if (storedLogDTO.getExchangeCouponNum() > 0) {
            optRecord.append("\t赠送\t" + storedLogDTO.getExchangeCouponNum() + "兑换积分");
        }
        if (storedLogDTO.getShoppingCouponAmount().intValue() > 0) {
            optRecord.append("\t赠送\t" + storedLogDTO.getShoppingCouponAmount() + "购物券");
        }
        optLogModel.setOptRecord(optRecord.toString());
        optLogModel.setCreateTime(date);
        optLogModel.setUpdateTime(date);
        OrderOptLogService.insertOrderOptLog(optLogModel);

        // 4. 更新会员的资金帐户信息
        int exchangeCouponNum = relationModel.getExchangeCouponNum() == null ? 0 : relationModel.getExchangeCouponNum();
        int exchangeCouponSum = relationModel.getExchangeCouponSum() == null ? 0 : relationModel.getExchangeCouponSum();
        BigDecimal shoppingCouponAmount = relationModel.getShoppingCouponAmount() == null ? BigDecimal.ZERO : relationModel.getShoppingCouponAmount();
        BigDecimal shoppingCouponSum = relationModel.getShoppingCouponSum() == null ? BigDecimal.ZERO : relationModel.getShoppingCouponSum();
        relationModel.setAvailBalance(relationModel.getAvailBalance().add(storedLogDTO.getRealAmount()));
        relationModel.setExchangeCouponNum(exchangeCouponNum + storedLogDTO.getExchangeCouponNum());
        relationModel.setExchangeCouponSum(exchangeCouponSum + storedLogDTO.getExchangeCouponNum());
        relationModel.setShoppingCouponAmount(shoppingCouponAmount.add(storedLogDTO.getRealAmount()));
        relationModel.setShoppingCouponSum(shoppingCouponSum.add(storedLogDTO.getRealAmount()));
        merchantRelationService.updateAcc(relationModel);

        // 5.添加商户会员兑换台帐明细
        MemberExchangeHistoryLogModel exchangeHistoryLogModel = new MemberExchangeHistoryLogModel();
        exchangeHistoryLogModel.setBillNo(shopOrderNo);
        exchangeHistoryLogModel.setTypeFlag(MemberConstant.ExchangeHistoryType.ISSUE.value());
        exchangeHistoryLogModel.setBillTypeFlag(MemberConstant.MemberExchangeType.MEMBER_STORED.getValue());
        exchangeHistoryLogModel.setMerchantId(merchantInfoId);
        exchangeHistoryLogModel.setMerchantAccount(merchantInfo.getLoginName());
        exchangeHistoryLogModel.setMemberId(infoModel.getId());
        exchangeHistoryLogModel.setMemberAccount(infoModel.getAccountNo());
        exchangeHistoryLogModel.setExchangeNumber(storedLogDTO.getExchangeCouponNum());
        exchangeHistoryLogModel.setBeforeNumber(relationModel.getExchangeCouponNum());
        exchangeHistoryLogModel.setAfterNumber(relationModel.getExchangeCouponNum() + storedLogDTO.getExchangeCouponNum());
        memberExchangeHistoryLogService.save(exchangeHistoryLogModel);

        // 商户钱包可用兑换券更新
        MerchantWalletInfoModel walletInfo = merchantWalletInfoService.getWalletInfo(merchantInfoId);
        if (null != walletInfo) {
            Integer surplusExchangeNumber = walletInfo.getSurplusExchangeNumber();
            Long wId = walletInfo.getId();
            BigDecimal amount = walletInfo.getAmount();
            Integer exchangeNumber = walletInfo.getExchangeNumber();

            MerchantWalletInfoModel oldWallet = new MerchantWalletInfoModel();
            oldWallet.setId(wId);
            oldWallet.setSurplusExchangeNumber(surplusExchangeNumber);
            oldWallet.setAmount(amount);
            oldWallet.setExchangeNumber(exchangeNumber);

            walletInfo.setSurplusExchangeNumber(storedLogDTO.getExchangeCouponNum() + (surplusExchangeNumber == null ? 0 : surplusExchangeNumber));
            int i = merchantWalletInfoService.updateByOld(walletInfo, oldWallet);
        }


        return RespData.ok();
    }

    /**
     * 会员储值记录分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        params.put("merchantId", merchantInfo.getId());
        PageResult page = memberStoredLogService.listPage(params);
        return RespData.ok(page);
    }
}
