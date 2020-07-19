package com.diandian.admin.merchant.modules.biz.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.biz.service.MerchantDebitCardInfoService;
import com.diandian.admin.merchant.modules.biz.service.MerchantWithdrawApplyLogService;
import com.diandian.admin.merchant.modules.biz.service.MerchantWithdrawApplyOptLogService;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantWithdrawApplyOptLogVO;
import com.diandian.admin.model.merchant.MerchantDebitCardInfoModel;
import com.diandian.admin.model.merchant.MerchantWithdrawApplyLogModel;
import com.diandian.admin.model.merchant.MerchantWithdrawApplyOptLogModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalRuleModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountHistoryLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.service.biz.BizWithdrawalRuleService;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountHistoryLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/2/26 13:31
 */
@RestController
@RequestMapping("/biz/withdrawApplyOptLog")
@Slf4j
public class MerchantWithdrawApplyOptLogController extends BaseController {
    @Autowired
    private MerchantWithdrawApplyOptLogService withdrawApplyOptLogService;
    @Autowired
    private NoGenerator noGenerator;
    @Autowired
    private MerchantDebitCardInfoService debitCardInfoService;
    @Autowired
    private MerchantWithdrawApplyLogService ApplyLogService;
    @Autowired
    private MerchantAccountInfoService accountInfoService;
    @Autowired
    private MerchantAccountHistoryLogService accountHistoryLogService;
    @Autowired
    private BizWithdrawalRuleService bizWithdrawalRuleService;
    /**
     * 添加提现记录
     *
     * @param withdrawOptLogVO
     * @return
     */
    /*@PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public RespData saveWithdrawApplyOptLog(@RequestBody MerchantWithdrawApplyOptLogVO withdrawOptLogVO) {
        //验证提现密码
        String password = withdrawOptLogVO.getWithdrawPassword();
        BigDecimal withdrawFee = withdrawOptLogVO.getWithdrawFee();
        String applyNo = noGenerator.getApplyNo();
        MerchantInfoModel info = getMerchantInfo();
        AssertUtil.notBlank(password, "提现密码不能为空");
        String withdrawPassword = info.getWithdrawPassword();
        AssertUtil.notBlank(withdrawPassword, "未设置提现密码");
        String salt = info.getSalt();
        String newPassword = new Sha256Hash(password, salt).toHex();
        AssertUtil.isTrue(newPassword.equals(withdrawPassword), "提现密码不正确");
        //修改商户余额信息
        MerchantAccountInfoModel accountInfo = accountInfoService.getByMerchantId(info.getId());
        BigDecimal availableBalance = accountInfo.getAvailableBalance();
        AssertUtil.isTrue(withdrawFee.compareTo(BigDecimal.valueOf(0)) == 1, "请输入正确的提现金额");
        AssertUtil.isTrue(availableBalance.compareTo(withdrawFee) == 1, "可用金额不足");
        BizWithdrawalRuleModel withdrawalRuleModel = bizWithdrawalRuleService.getBizWithdrawalRuleModel();
        AssertUtil.notNull(withdrawalRuleModel, "未设置提现规则");
        //获取当前商户的当日提现次数
        Date date = new Date();
        QueryWrapper<MerchantWithdrawApplyOptLogModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", info.getId());
        wrapper.ge("create_time", DateUtil.beginOfDay(date));
        wrapper.le("create_time", DateUtil.endOfDay(date));
        wrapper.eq("apply_state", 1);
        wrapper.eq("opt_type", 1);
        int count = withdrawApplyOptLogService.count(wrapper);
        int dayMaxNum = withdrawalRuleModel.getDayMaxNum().intValue();
        AssertUtil.isTrue(dayMaxNum >= count,"当日提现次数不足");
        //计算提现手续费
        BigDecimal fee = withdrawalRuleModel.getFee();
        BigDecimal divide = fee.divide(BigDecimal.valueOf(100));
        BigDecimal multiply = divide.multiply(withdrawFee);
        BigDecimal subtract = availableBalance.subtract(withdrawFee);
        BigDecimal result = subtract.subtract(multiply);
        AssertUtil.isTrue(subtract.compareTo(multiply) == 1 , "可用金额不足以提现");
        accountInfo.setAvailableBalance(result);
        accountInfoService.updateMerchantAccountInfo(accountInfo);
        //添加资金账户变动明细
        MerchantAccountHistoryLogModel accountHistoryLog = new MerchantAccountHistoryLogModel();
        accountHistoryLog.setMerchantId(info.getId());
        accountHistoryLog.setTypeFlag(3);
        accountHistoryLog.setPreAmount(availableBalance);
        accountHistoryLog.setAmount(BigDecimal.valueOf(0).subtract(withdrawFee));
        accountHistoryLog.setPostAmount(result);
        accountHistoryLogService.saveAccountHistoryLog(accountHistoryLog);
        //添加提现申请记录
        MerchantDebitCardInfoModel defaultCardInfo = debitCardInfoService.getDefaultCardInfo(info.getId(), 0);
        MerchantWithdrawApplyLogModel applyLogModel = new MerchantWithdrawApplyLogModel();
        applyLogModel.setBillNo(applyNo);
        applyLogModel.setMerchantId(info.getId());
        applyLogModel.setMerchantName(info.getName());
        applyLogModel.setSoftTypeId(info.getSoftTypeId());
        applyLogModel.setSoftTypeName(info.getSoftTypeName());
        applyLogModel.setWithdrawFee(withdrawFee);
        applyLogModel.setWithdrawRate(fee);
        applyLogModel.setApplyState(0);
        applyLogModel.setApplyState(0);
        applyLogModel.setBankCardNo(defaultCardInfo.getCardNumber());
        applyLogModel.setBankCardNo(defaultCardInfo.getCardholderName());
        applyLogModel.setBankName(defaultCardInfo.getBankName());
        ApplyLogService.saveWithdrawApplyLog(applyLogModel);
        //添加提现申请操作记录
        MerchantWithdrawApplyOptLogModel withdrawModel = new MerchantWithdrawApplyOptLogModel();
        withdrawModel.setBillNo(applyNo);
        withdrawModel.setMerchantId(info.getId());
        withdrawModel.setMerchantName(info.getName());
        withdrawModel.setAmount(withdrawFee);
        withdrawModel.setApplyState(0);
        StringBuilder optRecord = new StringBuilder();
        String cardNumber = defaultCardInfo.getCardNumber();
        optRecord.append(info.getName() + "提现" + withdrawFee + "元到尾号为" + cardNumber.substring(cardNumber.length() - 4));
        optRecord.append("的" + defaultCardInfo.getBankName() + "卡");
        withdrawModel.setOptRecord(optRecord.toString());
        withdrawModel.setOptType(1);
        withdrawApplyOptLogService.save(withdrawModel);
        return RespData.ok();
    }*/

    @GetMapping("/listPage")
    public RespData listOptLog(@RequestParam Map<String, Object> params) {
        Long merchantId = getMerchantInfoId();
        params.put("merchantId", merchantId);
        PageResult pageResult = withdrawApplyOptLogService.ListPage(params);
        return RespData.ok(pageResult);
    }
}
