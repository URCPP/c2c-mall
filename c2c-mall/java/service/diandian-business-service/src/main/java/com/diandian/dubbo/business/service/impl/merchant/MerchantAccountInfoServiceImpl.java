package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantAccountHistoryLogMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantAccountInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountHistoryLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 商户帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Service("merchantAccountInfoService")
@Slf4j
public class MerchantAccountInfoServiceImpl implements MerchantAccountInfoService {

    @Autowired
    private MerchantAccountInfoMapper merchantAccountInfoMapper;

    @Autowired
    private MerchantAccountHistoryLogMapper merchantAccountHistoryLogMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private UserConfigurationService userConfigurationService;

    @Override
    public boolean saveAcc(MerchantAccountInfoModel mAcc) {
        merchantAccountInfoMapper.insert(mAcc);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAccount(Long merchantId, BigDecimal transactionAmount, Integer businessType, Integer transactionMode,Integer transactionType,String transactionNumber,Long source) {
        MerchantAccountInfoModel merchantAccountInfoModel=merchantAccountInfoMapper.selectOne(
                new QueryWrapper<MerchantAccountInfoModel>()
                        .eq("merchant_id",merchantId)
        );
        //添加记录
        MerchantAccountHistoryLogModel merchantAccountHistoryLogModel=new MerchantAccountHistoryLogModel();
        merchantAccountHistoryLogModel.setMerchantId(merchantId);
        merchantAccountHistoryLogModel.setTransactionAmount(transactionAmount);
        merchantAccountHistoryLogModel.setBusinessType(businessType);
        merchantAccountHistoryLogModel.setTransactionMode(transactionMode);
        merchantAccountHistoryLogModel.setTransactionType(transactionType);
        merchantAccountHistoryLogModel.setDelFlag(BizConstant.STATE_NORMAL);
        merchantAccountHistoryLogModel.setTransactionNumber(transactionNumber);
        merchantAccountHistoryLogModel.setSource(source);
        if(1==transactionMode){//余额
            merchantAccountHistoryLogModel.setAmountBeforeTransaction(merchantAccountInfoModel.getAvailableBalance());
            merchantAccountHistoryLogModel.setAmountAfterTransaction(1==transactionType
                    ?merchantAccountInfoModel.getAvailableBalance().add(transactionAmount)
                    :merchantAccountInfoModel.getAvailableBalance().subtract(transactionAmount));
            merchantAccountInfoModel.setAvailableBalance(merchantAccountHistoryLogModel.getAmountAfterTransaction());
        }else if(2==transactionMode){//冻结余额
            merchantAccountHistoryLogModel.setAmountBeforeTransaction(merchantAccountInfoModel.getFreezeBalance());
            merchantAccountHistoryLogModel.setAmountAfterTransaction(1==transactionType
                    ?merchantAccountInfoModel.getFreezeBalance().add(transactionAmount)
                    :merchantAccountInfoModel.getFreezeBalance().subtract(transactionAmount));
            merchantAccountInfoModel.setFreezeBalance(merchantAccountHistoryLogModel.getAmountAfterTransaction());
        }
        merchantAccountHistoryLogMapper.insert(merchantAccountHistoryLogModel);
        merchantAccountInfoMapper.updateById(merchantAccountInfoModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void releaseFrozenAmount(Long merchantId) {
        AssertUtil.isNull(merchantAccountHistoryLogMapper.getFreezeBalanceByDate(merchantId), "今日已领取");
            MerchantAccountInfoModel merchantAccountInfoModel=merchantAccountInfoMapper.selectOne(
                    new QueryWrapper<MerchantAccountInfoModel>()
                            .eq("merchant_id",merchantId)
            );
            //获取返佣率
            BigDecimal rateOfReturn=userConfigurationService.findAll().getFreezeCommission();
            //计算返佣金额
            BigDecimal transactionAmount=merchantAccountInfoModel.getFreezeBalance().multiply(rateOfReturn);
            //扣除冻结金额
            MerchantAccountHistoryLogModel merchantAccountHistoryLogModel=new MerchantAccountHistoryLogModel();
            merchantAccountHistoryLogModel.setMerchantId(merchantId);
            merchantAccountHistoryLogModel.setBusinessType(9);
            merchantAccountHistoryLogModel.setTransactionMode(2);
            merchantAccountHistoryLogModel.setTransactionType(2);
            merchantAccountHistoryLogModel.setTransactionAmount(transactionAmount);
            merchantAccountHistoryLogModel.setAmountBeforeTransaction(merchantAccountInfoModel.getFreezeBalance());
            merchantAccountHistoryLogModel.setAmountAfterTransaction(merchantAccountInfoModel.getFreezeBalance().subtract(transactionAmount));
            merchantAccountHistoryLogModel.setTransactionNumber(RandomUtil.randomNumbers(18));
            merchantAccountHistoryLogMapper.insert(merchantAccountHistoryLogModel);
            merchantAccountInfoModel.setFreezeBalance(merchantAccountHistoryLogModel.getAmountAfterTransaction());
            //添加到余额
            merchantAccountHistoryLogModel=new MerchantAccountHistoryLogModel();
            merchantAccountHistoryLogModel.setMerchantId(merchantId);
            merchantAccountHistoryLogModel.setBusinessType(9);
            merchantAccountHistoryLogModel.setTransactionMode(1);
            merchantAccountHistoryLogModel.setTransactionType(1);
            merchantAccountHistoryLogModel.setTransactionAmount(transactionAmount);
            merchantAccountHistoryLogModel.setAmountBeforeTransaction(merchantAccountInfoModel.getAvailableBalance());
            merchantAccountHistoryLogModel.setAmountAfterTransaction(merchantAccountInfoModel.getAvailableBalance().add(transactionAmount));
            merchantAccountHistoryLogModel.setTransactionNumber(RandomUtil.randomNumbers(18));
            merchantAccountHistoryLogMapper.insert(merchantAccountHistoryLogModel);
            merchantAccountInfoModel.setAvailableBalance(merchantAccountHistoryLogModel.getAmountAfterTransaction());
            merchantAccountInfoMapper.insert(merchantAccountInfoModel);
    }

    @Override
    public MerchantAccountInfoModel getByMerchantId(Long merchantId) {
        return merchantAccountInfoMapper.selectOne(new QueryWrapper<MerchantAccountInfoModel>().eq("merchant_id",merchantId));
    }

    /**
     * 计算推荐奖
     *
     * @param merchantId
     * @return R
     */
    @Override
    public void openingAward(Long merchantId,Integer level){
        MerchantInfoModel merchantInfoModel=merchantInfoMapper.selectById(merchantId);
        UserConfiguration userConfiguration=userConfigurationService.findAll();
        BigDecimal money=BigDecimal.ZERO;
        if(level==2){
            money=userConfiguration.getUpgradeMerchant();
        }else if(level==3){
            money=userConfiguration.getUpgradePartner();
        }
        this.saveAccount(merchantId,money,3,1,2,RandomUtil.randomNumbers(18),null);
        this.saveAccount(merchantId,money,12,2,1,RandomUtil.randomNumbers(18),null);
        if (null!=merchantInfoModel.getParentId()){
            MerchantInfoModel parentMerchant=merchantInfoMapper.selectById(merchantInfoModel.getParentId());
            //等级是商户及以上的
            if (parentMerchant.getLevel()>1){
                //如果等级是合伙人新增记录，是商户继续添加上级的下级收益
                if(level==2){
                    this.saveAccount(parentMerchant.getId(),userConfiguration.getDirectMember(),4,1,1,RandomUtil.randomNumbers(18),merchantId);
                    if (parentMerchant.getLevel()==2){
                        this.subordinateIncome(parentMerchant.getId(),userConfiguration.getDirectMember());
                    }
                }else if(level==3&&parentMerchant.getLevel()==3){
                    this.saveAccount(parentMerchant.getId(),userConfiguration.getUpgradePartner(),8,1,1,RandomUtil.randomNumbers(18),merchantId);
                }
            }
        }

    }

    /**
     * 计算下级收益
     *
     * @param merchantId,profit
     * @return R
     */
    @Override
    public void subordinateIncome(Long merchantId, BigDecimal profit){
        MerchantInfoModel merchantInfoModel=merchantInfoMapper.selectById(merchantId);
        if(null!=merchantInfoModel.getParentId()){
            MerchantInfoModel parentMerchant=merchantInfoMapper.selectById(merchantInfoModel.getParentId());
            UserConfiguration userConfiguration=userConfigurationService.findAll();
            //如果等级为合伙人新增记录否则继续查找上级
            if(parentMerchant.getLevel()==3){
                this.saveAccount(parentMerchant.getId(),profit.multiply(userConfiguration.getJuniorPartnerIncome()),7,1,1,RandomUtil.randomNumbers(18),merchantId);
            }else{
                if(null!=parentMerchant.getId()){
                    this.subordinateIncome(parentMerchant.getId(),profit);
                }
            }
        }
    }

}
