package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantRemitLogMapper;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.merchant.MerchantRemitDTO;
import com.diandian.dubbo.facade.dto.merchant.RemitAuditDTO;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.*;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import com.diandian.dubbo.facade.service.merchant.*;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.vo.merchant.MerchantRemitLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 商户汇款记录明细表
 *
 * @author jbh
 * @date 2019/03/29
 */
@Service("merchantRemitLogService")
@Slf4j
public class MerchantRemitLogServiceImpl implements MerchantRemitLogService {

    @Autowired
    private MerchantRemitLogMapper merchantRemitLogMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private NoGenerator noGenerator;
    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;
    @Autowired
    private MerchantProductInfoService merchantProductInfoService;
    @Autowired
    private MerchantWalletHistoryLogService merchantWalletHistoryLogService;
    @Autowired
    private BizMallConfigService bizMallConfigService;
    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;
    @Autowired
    private MerchantAccountHistoryLogService merchantAccountHistoryLogService;


    @Override
    @Transactional
    public String addRemit(MerchantRemitDTO dto) {
        //验证
        Long merchantId = dto.getMerchantId();
        AssertUtil.notNull(merchantId, "商户不存在");
        MerchantInfoModel merchant = merchantInfoMapper.selectById(merchantId);
        AssertUtil.notNull(merchant, "商户不存在");
        BigDecimal amount = dto.getAmount() == null ? BigDecimal.ZERO : dto.getAmount();
        AssertUtil.isFalse(amount.compareTo(BigDecimal.ZERO) < 0, "金额有误，请确认");

        //添加商户凭证审核记录
        String orderNo = noGenerator.getMerchantOfflinePayNo();
        MerchantRemitLogModel log = new MerchantRemitLogModel();
        log.setMerchantId(merchant.getId());
        log.setMerchantName(merchant.getName());
        log.setOrderNo(orderNo);
        log.setType(dto.getType());
        log.setAmount(amount);
        log.setAuditFlag(MerchantConstant.RemitAuditState.CREATE.value());
        merchantRemitLogMapper.insert(log);

        //商城状态改为开通审核
        //merchant.setMallRemiteOrderNo(orderNo);
        //merchant.setMallOpenFlag(MerchantConstant.MallOpenFlag.AUDIT.value());
        merchantInfoMapper.updateById(merchant);

        return orderNo;
    }

    @Override
    @Transactional
    public boolean updRemit(MerchantRemitDTO dto) {
        String orderNo = dto.getOrderNo();
        AssertUtil.notBlank(orderNo, "订单不存在");
        String proofUrl = dto.getProofUrl();
        AssertUtil.notBlank(proofUrl, "请上传凭证");

        QueryWrapper<MerchantRemitLogModel> qw = new QueryWrapper<>();
        qw.eq("order_no", orderNo);
//        qw.eq("audit_flag",MerchantConstant.RemitAuditState.CREATE.value());
        MerchantRemitLogModel merchantRemitLogModel = merchantRemitLogMapper.selectOne(qw);
        if (null != merchantRemitLogModel) {
//            Integer type = merchantRemitLogModel.getType();
            merchantRemitLogModel.setProofUrl(proofUrl);
            merchantRemitLogModel.setAuditFlag(MerchantConstant.RemitAuditState.WAIT_AUDIT.value());
            merchantRemitLogMapper.updateById(merchantRemitLogModel);
//            if (MerchantConstant.MerchantRemitType.OPEN_MALL.value() == type) {
//                Long merchantId = merchantRemitLogModel.getMerchantId();
//                MerchantInfoModel merchantInfoModel = merchantInfoMapper.selectById(merchantId);
//                merchantInfoModel.setMallRemiteOrderNo(orderNo);
//                merchantInfoModel.setMallOpenFlag(MerchantConstant.MallOpenFlag.AUDIT.value());
//                merchantInfoMapper.updateById(merchantInfoModel);
//            }
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean sendBack(Long merchantInfoId) {
        AssertUtil.notNull(merchantInfoId, "商户不存在");

        MerchantInfoModel merchantInfoModel = merchantInfoMapper.selectById(merchantInfoId);

        //添加退还审核记录
        MerchantWalletInfoModel walletInfo = merchantWalletInfoService.getWalletInfo(merchantInfoId);
        BigDecimal amount = walletInfo.getAmount() == null ? BigDecimal.ZERO : walletInfo.getAmount();
        BigDecimal marginAmount = walletInfo.getMarginAmount() == null ? BigDecimal.ZERO : walletInfo.getMarginAmount();
        AssertUtil.isTrue(marginAmount.compareTo(BigDecimal.ZERO) == 1, "商户保证金为零，无法退还");
        String orderNo = noGenerator.getMerchantOfflinePayNo();
        MerchantRemitLogModel log = new MerchantRemitLogModel();
        log.setMerchantId(merchantInfoModel.getId());
        log.setMerchantName(merchantInfoModel.getName());
        log.setOrderNo(orderNo);
        log.setType(MerchantConstant.MerchantRemitType.SEND_BACK.value());
        log.setAmount(amount.add(marginAmount));
        log.setAuditFlag(MerchantConstant.RemitAuditState.WAIT_AUDIT.value());
        merchantRemitLogMapper.insert(log);

        //修改商家商城开通状态 退还审核中
        //merchantInfoModel.setMallRemiteOrderNo(orderNo);
        //merchantInfoModel.setMallOpenFlag(MerchantConstant.MallOpenFlag.BACK_AUDIT.value());
        merchantInfoMapper.updateById(merchantInfoModel);

        return true;
    }

    @Override
    public MerchantRemitLogModel getRemitOrder(Long merchantId) {
        AssertUtil.notNull(merchantId, "商户不存在");
        MerchantInfoModel merchant = merchantInfoMapper.selectById(merchantId);
        AssertUtil.notNull(merchant, "商户不存在");
        MerchantRemitLogModel merchantRemitLogModel = new MerchantRemitLogModel();
        String mallRemiteOrderNo = "";
        if (StrUtil.isNotBlank(mallRemiteOrderNo)) {
            QueryWrapper<MerchantRemitLogModel> qw = new QueryWrapper<>();
            qw.eq("order_no", mallRemiteOrderNo);
            merchantRemitLogModel = merchantRemitLogMapper.selectOne(qw);
        }
        return merchantRemitLogModel;
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {

        Page<MerchantRemitLogModel> page = new PageWrapper<MerchantRemitLogModel>(params).getPage();
        IPage<MerchantRemitLogModel> iPage = merchantRemitLogMapper.listPage(page, params);
        return new PageResult(iPage);
    }

    @Override
    public MerchantRemitLogModel getById(Long id) {
        return merchantRemitLogMapper.selectById(id);
    }

    /*@Override
    @Transactional
    public boolean audit(RemitAuditDTO dto) {
        Long id = dto.getId();
        MerchantRemitLogModel remitLog = merchantRemitLogMapper.selectById(id);
        if (null != remitLog) {
            Integer type = remitLog.getType();
            Integer auditFlag = dto.getAuditFlag();
            if (null != auditFlag) {
                //审核失败
                //退还审核失败
                if (auditFlag == MerchantConstant.RemitAuditState.AUDIT_NOT_PASSED.value()) {
                    remitLog.setAuditFailReason(dto.getAuditFailReason());
                    MerchantInfoModel updObj = new MerchantInfoModel();
                    MerchantInfoModel merchantInfoModel = merchantInfoMapper.selectById(remitLog.getMerchantId());
                    if (type == MerchantConstant.MerchantRemitType.SEND_BACK.value()) {
                        // 积分商城开通
//                        updObj.setId(merchantInfoModel.getId());
//                        updObj.setMallOpenFlag(MerchantConstant.MallOpenFlag.OPEN.value());
//                        updObj.setMallRemiteOrderNo("");
//                        merchantInfoMapper.updateById(updObj);
                    } else if (type == MerchantConstant.MerchantRemitType.OPEN_MALL.value()) {
                        //积分商城关闭
//                        updObj.setId(merchantInfoModel.getId());
//                        updObj.setMallOpenFlag(MerchantConstant.MallOpenFlag.CLOSE.value());
//                        updObj.setMallRemiteOrderNo("");
//                        merchantInfoMapper.updateById(updObj);
                    }

                } else {
                    if (auditFlag == MerchantConstant.RemitAuditState.AUDIT_PASSED.value()) {
                        if (null != type) {
                            Long merchantId = remitLog.getMerchantId();
                            BigDecimal amount = remitLog.getAmount();
                            MerchantWalletInfoModel walletInfo = merchantWalletInfoService.getWalletInfo(merchantId);
                            Long wId = walletInfo.getId();
                            BigDecimal availAmount = walletInfo.getAmount() == null ? BigDecimal.ZERO : walletInfo.getAmount();
                            BigDecimal wMargin = walletInfo.getMarginAmount() == null ? BigDecimal.ZERO : walletInfo.getMarginAmount();
//                            BigDecimal amountSum = walletInfo.getAmountSum() == null ? BigDecimal.ZERO : walletInfo.getAmountSum();

                            MerchantWalletInfoModel oldWallet = new MerchantWalletInfoModel();
                            oldWallet.setId(wId);
                            oldWallet.setAmount(walletInfo.getAmount());
                            oldWallet.setExchangeNumber(walletInfo.getExchangeNumber());
                            oldWallet.setSurplusExchangeNumber(walletInfo.getSurplusExchangeNumber());

                            //商城开通
                            if (type == MerchantConstant.MerchantRemitType.OPEN_MALL.value()) {
                                // 更新商户钱包
                                if (null != walletInfo) {
                                    BigDecimal marginAmount = BigDecimal.ZERO;
                                    BizMallConfigModel margin = bizMallConfigService.getByEngName("margin_amount");
                                    if (null != margin)
                                        marginAmount = new BigDecimal(margin.getMallValue() + "");

                                    BigDecimal subtract = amount.subtract(marginAmount);
                                    if (subtract.compareTo(BigDecimal.ZERO) < 0) {
                                        throw new DubboException("充值金额不足");
                                    }

                                    walletInfo.setAmount(availAmount.add(subtract));
                                    walletInfo.setMarginAmount(wMargin.add(marginAmount));
//                                    walletInfo.setAmountSum(amountSum.add(amount));
                                    int result = merchantWalletInfoService.updateByOld(walletInfo, oldWallet);
                                    if (result == 0) {
                                        throw new DubboException("开通失败，请重试");
                                    }

                                    // 添加商户钱包变动记录
                                    MerchantWalletHistoryLogModel walletHis = new MerchantWalletHistoryLogModel();
                                    walletHis.setMerchantId(merchantId);
                                    walletHis.setPreAmount(availAmount);
                                    walletHis.setAmount(subtract);
                                    walletHis.setPostAmount(availAmount.add(subtract));
                                    walletHis.setTypeFlag(MerchantConstant.MerchantWalletLogType.STORED.value());
                                    String opt = "商家 " + remitLog.getMerchantName() + "开通兑换商城，充值 " + subtract.setScale(2,BigDecimal.ROUND_DOWN) + " 元";
                                    walletHis.setOptRecord(opt);
                                    merchantWalletHistoryLogService.save(walletHis);

                                    MerchantWalletHistoryLogModel walletHis2 = new MerchantWalletHistoryLogModel();
                                    walletHis2.setMerchantId(merchantId);
                                    walletHis2.setPreAmount(wMargin);
                                    walletHis2.setAmount(marginAmount);
                                    walletHis2.setPostAmount(wMargin.add(marginAmount));
                                    walletHis2.setTypeFlag(MerchantConstant.MerchantWalletLogType.MARGIN.value());
                                    String opt2 = "商家 " + remitLog.getMerchantName() + "开通兑换商城，保证金 " + marginAmount.setScale(2,BigDecimal.ROUND_DOWN) + " 元";
                                    walletHis2.setOptRecord(opt2);
                                    merchantWalletHistoryLogService.save(walletHis2);

                                    // 积分商城开通
                                    MerchantInfoModel merchantInfoModel = merchantInfoMapper.selectById(merchantId);
                                    //merchantInfoModel.setMallOpenFlag(MerchantConstant.MallOpenFlag.OPEN.value());
                                   // merchantInfoModel.setMallRemiteOrderNo("");
                                    merchantInfoMapper.updateById(merchantInfoModel);
                                }

                            }
                            //储备金退还
                            else if (type == MerchantConstant.MerchantRemitType.SEND_BACK.value()) {
                                if (null != walletInfo) {
                                    // 商户帐户变动
                                    BigDecimal balance = BigDecimal.ZERO;
                                    MerchantAccountInfoModel mAcc = merchantAccountInfoService.getByMerchantId(merchantId);
                                    Long mId = mAcc.getId();
                                    BigDecimal availableBalance = mAcc.getAvailableBalance();
                                    if (availableBalance != null) {
                                        balance = availableBalance;
                                    }

                                    BigDecimal add = balance.add(availAmount);
                                    BigDecimal postAmount = add.add(wMargin);
                                    merchantAccountInfoService.updateBalance(merchantId, balance, postAmount);

                                    // 帐户变动记录
                                    MerchantAccountHistoryLogModel accountHistoryLogModel = new MerchantAccountHistoryLogModel();
                                    accountHistoryLogModel.setMerchantId(merchantId);
                                    accountHistoryLogModel.setAmount(availAmount.add(wMargin));
                                    accountHistoryLogModel.setPreAmount(balance);
                                    accountHistoryLogModel.setPostAmount(postAmount);
                                    accountHistoryLogModel.setTypeFlag(MerchantConstant.AccountHistoryType.REFUND.value());
                                    merchantAccountHistoryLogService.saveAccountHistoryLog(accountHistoryLogModel);

                                    //钱包变动
                                    walletInfo.setAmount(BigDecimal.ZERO);
                                    walletInfo.setMarginAmount(BigDecimal.ZERO);
                                    int res = merchantWalletInfoService.updateByOld(walletInfo, oldWallet);
                                    if (0 == res) {
                                        throw new DubboException("退还失败，请重试");
                                    }
                                    //钱包变动记录
                                    MerchantWalletHistoryLogModel walletHis = new MerchantWalletHistoryLogModel();
                                    walletHis.setMerchantId(merchantId);
                                    walletHis.setPreAmount(availAmount);
                                    walletHis.setAmount(availAmount.multiply(new BigDecimal("-1")));
                                    walletHis.setPostAmount(BigDecimal.ZERO);
                                    walletHis.setTypeFlag(MerchantConstant.MerchantWalletLogType.REFUND.value());
                                    String opt = "商家 " + remitLog.getMerchantName() + "关闭兑换商城，扣除 " + availAmount.setScale(2,BigDecimal.ROUND_DOWN) + " 元 ，到可用余额";
                                    walletHis.setOptRecord(opt);
                                    merchantWalletHistoryLogService.save(walletHis);

                                    MerchantWalletHistoryLogModel walletHis2 = new MerchantWalletHistoryLogModel();
                                    walletHis2.setMerchantId(merchantId);
                                    walletHis2.setPreAmount(wMargin);
                                    walletHis2.setAmount(wMargin.multiply(new BigDecimal("-1")));
                                    walletHis2.setPostAmount(BigDecimal.ZERO);
                                    walletHis2.setTypeFlag(MerchantConstant.MerchantWalletLogType.REFUND.value());
                                    String opt2 = "商家 " + remitLog.getMerchantName() + "关闭兑换商城，扣除保证金" + wMargin.setScale(2,BigDecimal.ROUND_DOWN) + " 元 ，到可用余额";
                                    walletHis.setOptRecord(opt2);
                                    merchantWalletHistoryLogService.save(walletHis2);


                                    // 商城关闭
                                    MerchantInfoModel merchantInfoModel = merchantInfoMapper.selectById(merchantId);
                                    //merchantInfoModel.setMallOpenFlag(MerchantConstant.MallOpenFlag.CLOSE.value());
                                    //merchantInfoModel.setMallRemiteOrderNo("");
                                    merchantInfoMapper.updateById(merchantInfoModel);
                                }


                            } else if (type == MerchantConstant.MerchantRemitType.WALLET.value()) {
                                // 更新商户钱包
                                if (null != walletInfo) {

                                    walletInfo.setAmount(availAmount.add(amount));
//                                    walletInfo.setAmountSum(amountSum.add(amount));
                                    merchantWalletInfoService.updateByOld(walletInfo, oldWallet);

                                    // 添加商户钱包变动记录
                                    MerchantWalletHistoryLogModel walletHis = new MerchantWalletHistoryLogModel();
                                    walletHis.setMerchantId(merchantId);
                                    walletHis.setPreAmount(availAmount);
                                    walletHis.setAmount(amount);
                                    walletHis.setPostAmount(availAmount.add(amount));
                                    walletHis.setTypeFlag(MerchantConstant.MerchantWalletLogType.STORED.value());
                                    String opt2 = "商家 " + remitLog.getMerchantName() + " 充值储备金，金额" + amount.setScale(2,BigDecimal.ROUND_DOWN) + " 元";
                                    walletHis.setOptRecord(opt2);
                                    merchantWalletHistoryLogService.save(walletHis);

                                }
                            }
                        }
                    }
                }

                remitLog.setAuditFlag(auditFlag);
                remitLog.setAuditor(dto.getAuditor());
                remitLog.setAuditTime(new Date());

                merchantRemitLogMapper.updateById(remitLog);
//                int i = 1/0;
            }

        }


        return false;
    }*/

    @Override
    @Transactional
    public boolean cancelRemit(Long merchantInfoId) {

        MerchantInfoModel merchant = merchantInfoMapper.selectById(merchantInfoId);
        AssertUtil.notNull(merchant, "订单不存在");

        //取消审核订单
        //String mallRemiteOrderNo = merchant.getMallRemiteOrderNo();
        QueryWrapper<MerchantRemitLogModel> qw = new QueryWrapper<>();
        //qw.eq("order_no", mallRemiteOrderNo);
        MerchantRemitLogModel log = merchantRemitLogMapper.selectOne(qw);
        if (null != log) {
            log.setAuditFlag(MerchantConstant.RemitAuditState.AUDIT_CANCEL.value());
            merchantRemitLogMapper.updateById(log);

            // 积分商城开通
            MerchantInfoModel merchantInfoModel = merchantInfoMapper.selectById(merchantInfoId);
            //merchantInfoModel.setMallOpenFlag(MerchantConstant.MallOpenFlag.OPEN.value());
            //merchantInfoModel.setMallRemiteOrderNo("");
            merchantInfoMapper.updateById(merchantInfoModel);
        }

        return true;
    }

    @Override
    public PageResult listMallOpenPage(Map<String, Object> params){
        Page<MerchantRemitLogVO> page = new PageWrapper<MerchantRemitLogVO>(params).getPage();
        IPage<MerchantRemitLogVO> iPage = merchantRemitLogMapper.listMallOpenPage(page, params);
        return new PageResult(iPage);
    }
}
