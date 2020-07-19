package com.diandian.dubbo.business.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.diandian.dubbo.business.mapper.*;
import com.diandian.dubbo.common.oss.AliyunStorageUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.biz.OrgApplyInfoDTO;
import com.diandian.dubbo.facade.dto.sys.OrgFormDTO;
import com.diandian.dubbo.facade.model.biz.*;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 机构公用Service类
 * @author cjunyuan
 * @date 2019/4/2 17:10
 */
public class OrgCommonService {

    @Autowired
    protected BizOrgTypeStrategyMapper bizOrgTypeStrategyMapper;

    @Autowired
    protected BizAccountDetailMapper bizAccountDetailMapper;

    @Autowired
    protected BizOrgPrivilegeMapper bizOrgPrivilegeMapper;

    @Autowired
    protected BizBonusDetailMapper bizBonusDetailMapper;

    @Autowired
    protected BizOpenDetailMapper bizOpenDetailMapper;

    @Autowired
    protected BizAccountMapper bizAccountMapper;

    @Autowired
    protected SysOrgTypeMapper sysOrgTypeMapper;

    @Autowired
    protected NoGenerator noGenerator;

    protected final BigDecimal percent = new BigDecimal(100);

    /**
     * 添加申请包含的图片信息
     * @author cjunyuan
     * @date 2019/3/6 19:03
     */
    public void addImageInfo(OrgFormDTO dto, OrgApplyInfoDTO applyDto, BizOrgInfoModel orgInfo, BizOrgApplyModel orgApply){
        if(null != dto && null != orgInfo){
            if(!StringUtils.isBlank(dto.getIdcardPositivePicBase64())){
                String url = addApplyInfoFile(dto.getIdcardPositivePicBase64());
                orgInfo.setIdcardPositivePic(url);
            }
            if(!StringUtils.isBlank(dto.getIdcardReversePicBase64())){
                String url = addApplyInfoFile(dto.getIdcardReversePicBase64());
                orgInfo.setIdcardReversePic(url);
            }
            if(!StringUtils.isBlank(dto.getBankCardPicBase64())){
                String url = addApplyInfoFile(dto.getBankCardPicBase64());
                orgInfo.setBankCardPic(url);
            }
            if(!StringUtils.isBlank(dto.getBusinessLicensePicBase64())){
                String url = addApplyInfoFile(dto.getBusinessLicensePicBase64());
                orgInfo.setBusinessLicensePic(url);
            }
            if(!StringUtils.isBlank(dto.getPowerAttorneyPicBase64())){
                String url = addApplyInfoFile(dto.getPowerAttorneyPicBase64());
                orgInfo.setPowerAttorneyPic(url);
            }
        }
        if(null != applyDto && null != orgApply){
            if(!StringUtils.isBlank(applyDto.getIdcardPositivePicBase64())){
                String url = addApplyInfoFile(applyDto.getIdcardPositivePicBase64());
                orgApply.setIdcardPositivePic(url);
            }
            if(!StringUtils.isBlank(applyDto.getIdcardReversePicBase64())){
                String url = addApplyInfoFile(applyDto.getIdcardReversePicBase64());
                orgApply.setIdcardReversePic(url);
            }
            if(!StringUtils.isBlank(applyDto.getBankCardPicBase64())){
                String url = addApplyInfoFile(applyDto.getBankCardPicBase64());
                orgApply.setBankCardPic(url);
            }
            if(!StringUtils.isBlank(applyDto.getBusinessLicensePicBase64())){
                String url = addApplyInfoFile(applyDto.getBusinessLicensePicBase64());
                orgApply.setBusinessLicensePic(url);
            }
            if(!StringUtils.isBlank(applyDto.getPowerAttorneyPicBase64())){
                String url = addApplyInfoFile(applyDto.getPowerAttorneyPicBase64());
                orgApply.setPowerAttorneyPic(url);
            }
        }
    }

    public void addGiftReward(SysOrgModel benefitOrg){
        //获取机构类型对应的赠送规则
        QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
        orgTypeStrategyWrapper.eq("org_type_id", benefitOrg.getOrgTypeId());
        orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.OPENING_GIFTS.value());
        orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
        List<BizOrgTypeStrategyModel> orgTypeStrategyList = bizOrgTypeStrategyMapper.selectList(orgTypeStrategyWrapper);
        addReward(orgTypeStrategyList, benefitOrg, null, BizConstant.OpenBusType.OPEN_REWARD);
    }

    public void addOpenOrgReward(SysOrgModel parentOrg, SysOrgModel newOrg){
        //查询机构类型对应的开通规则
        QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
        orgTypeStrategyWrapper.eq("org_type_id", parentOrg.getOrgTypeId());
        orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.OPENING_BONUSES.value());
        orgTypeStrategyWrapper.eq("recommend_org_type_id", newOrg.getOrgTypeId());
        orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
        List<BizOrgTypeStrategyModel> recommendOrgTypeStrategyList = bizOrgTypeStrategyMapper.selectList(orgTypeStrategyWrapper);
        addReward(recommendOrgTypeStrategyList, parentOrg, newOrg, BizConstant.OpenBusType.RECOMMEND_REWARD);
    }

    public void addRecommendOrgReward(SysOrgModel recommendOrg, SysOrgModel recommendedOrg){
        //查询推荐机构的特权（实际只要推荐特权）
        QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
        orgTypeStrategyWrapper.eq("org_type_id", recommendOrg.getOrgTypeId());
        orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.REFERRAL_BONUSES.value());
        orgTypeStrategyWrapper.eq("recommend_org_type_id", recommendedOrg.getOrgTypeId());
        orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
        List<BizOrgTypeStrategyModel> recommendOrgTypeStrategyList = bizOrgTypeStrategyMapper.selectList(orgTypeStrategyWrapper);
        addReward(recommendOrgTypeStrategyList, recommendOrg, recommendedOrg, BizConstant.OpenBusType.RECOMMEND_REWARD);
    }

    private void addReward(List<BizOrgTypeStrategyModel> orgTypeStrategyList, SysOrgModel benefitOrg, SysOrgModel fromOrg, BizConstant.OpenBusType busType){
        for (BizOrgTypeStrategyModel orgTypeStrategy : orgTypeStrategyList){
            //奖励机构/软件
            if(BizConstant.RewardType.ORG.value().equals(orgTypeStrategy.getRewardType()) ||
                    BizConstant.RewardType.SOFTWARE.value().equals(orgTypeStrategy.getRewardType())){
                QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper<>();
                orgPrivilegeWrapper.eq("org_id", benefitOrg.getId());
                orgPrivilegeWrapper.eq(BizConstant.RewardType.ORG.value().equals(orgTypeStrategy.getRewardType()), "reward_org_type_id", orgTypeStrategy.getRewardOrgTypeId());
                orgPrivilegeWrapper.eq(BizConstant.RewardType.SOFTWARE.value().equals(orgTypeStrategy.getRewardType()), "reward_software_type_id", orgTypeStrategy.getRewardSoftwareTypeId());
                //查询是否已经存在对应的机构特权信息，存在-修改对应的特权信息数量，不存在-新增
                BizOrgPrivilegeModel oldPrivilege = bizOrgPrivilegeMapper.selectOne(orgPrivilegeWrapper);
                if(null == oldPrivilege){
                    BizOrgPrivilegeModel insertPrivilege = new BizOrgPrivilegeModel();
                    insertPrivilege.setRewardValue(orgTypeStrategy.getRewardValue());
                    insertPrivilege.setRewardTypeId(orgTypeStrategy.getRewardType());
                    insertPrivilege.setRewardTypeName(orgTypeStrategy.getRewardTypeName());
                    insertPrivilege.setRewardOrgTypeId(BizConstant.RewardType.ORG.value().equals(orgTypeStrategy.getRewardType()) ? orgTypeStrategy.getRewardOrgTypeId(): null);
                    insertPrivilege.setRewardSoftwareTypeId(BizConstant.RewardType.SOFTWARE.value().equals(orgTypeStrategy.getRewardType()) ? orgTypeStrategy.getRewardSoftwareTypeId(): null);
                    insertPrivilege.setOrgId(benefitOrg.getId());
                    bizOrgPrivilegeMapper.insert(insertPrivilege);
                } else {
                    BizOrgPrivilegeModel update = new BizOrgPrivilegeModel();
                    update.setId(oldPrivilege.getId());
                    update.setRewardValue(oldPrivilege.getRewardValue().add(orgTypeStrategy.getRewardValue()));
                    bizOrgPrivilegeMapper.updateById(update);
                }
                //添加推荐机构奖励（机构/软件）明细
                BizOpenDetailModel openDetail = new BizOpenDetailModel();
                openDetail.setOrgId(benefitOrg.getId());
                openDetail.setBusType(busType.value());
                openDetail.setFromOrgId(null == fromOrg ? null : fromOrg.getId());
                openDetail.setOpenType(orgTypeStrategy.getRewardType());
                openDetail.setTradeStart(null == oldPrivilege ? 0 : oldPrivilege.getRewardValue().intValue());
                openDetail.setTradeEnd(null == oldPrivilege ? orgTypeStrategy.getRewardValue().intValue() : oldPrivilege.getRewardValue().add(orgTypeStrategy.getRewardValue()).intValue());
                openDetail.setTradeNum(orgTypeStrategy.getRewardValue().intValue());
                openDetail.setTradeNo(noGenerator.getRewardTradeNo());
                openDetail.setTradeType(BizConstant.TradeType.INCOME.value());
                openDetail.setTypeId(BizConstant.ObjectType.ORG.value().equals(orgTypeStrategy.getRewardType()) ? orgTypeStrategy.getRewardOrgTypeId() : orgTypeStrategy.getRewardSoftwareTypeId());
                bizOpenDetailMapper.insert(openDetail);
                //奖励现金
            }  else if (BizConstant.RewardType.CASH.value().equals(orgTypeStrategy.getRewardType()) ||
                    BizConstant.RewardType.FIRST_TIME.value().equals(orgTypeStrategy.getRewardType())){
                //修改机构账户未发放奖金的值
                QueryWrapper<BizAccountModel> accountWrapper = new QueryWrapper<>();
                accountWrapper.eq("org_id", benefitOrg.getId());
                BizAccountModel account = bizAccountMapper.selectOne(accountWrapper);
                AssertUtil.notNull(account, "账户信息不存在");
                BizAccountModel update = new BizAccountModel();
                update.setId(account.getId());
                UpdateWrapper<BizAccountModel> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", account.getId());

                if (BizConstant.RewardType.CASH.value().equals(orgTypeStrategy.getRewardType())) {
                    BigDecimal bonus = orgTypeStrategy.getRewardValue();
                    //生成现金奖励流水
                    //修改机构账户未发放奖金的值
                    BizBonusDetailModel bonusDetail = new BizBonusDetailModel();
                    bonusDetail.setTradeNo(noGenerator.getRewardTradeNo());
                    bonusDetail.setTradeType(BizConstant.TradeType.INCOME.value());
                    bonusDetail.setBusType(0);
                    bonusDetail.setTradeAmount(bonus);
                    bonusDetail.setTradeStart(account.getBonus());
                    bonusDetail.setTradeEnd(account.getBonus().add(bonus));
                    bonusDetail.setOrgId(benefitOrg.getId());
                    //被推荐机构ID
                    bonusDetail.setFromOrgId(null == fromOrg ? null : fromOrg.getId());
                    bizBonusDetailMapper.insert(bonusDetail);
                    update.setBonus(account.getBonus().add(bonus));
                    update.setTotalBonus(account.getTotalBonus().add(bonus));
                    updateWrapper.eq("bonus", account.getBonus());
                    updateWrapper.eq("total_bonus", account.getTotalBonus());
                } else if (BizConstant.RewardType.FIRST_TIME.value().equals(orgTypeStrategy.getRewardType())) {
                    AssertUtil.notNull(fromOrg, "年费奖励的开通对象不能为空");
                    //生成首次开通年费奖励明细
                    SysOrgTypeModel orgType = sysOrgTypeMapper.selectById(fromOrg.getOrgTypeId());
                    //计算奖励年费
                    BigDecimal annualFee = orgType.getOpeningCost().multiply(orgTypeStrategy.getRewardValue().divide(percent));
                    BizAccountDetailModel accountDetail = new BizAccountDetailModel();
                    accountDetail.setBusType(0);
                    accountDetail.setOrgId(benefitOrg.getId());
                    //被推荐机构ID
                    accountDetail.setFromOrgId(fromOrg.getId());
                    accountDetail.setTradeAmount(annualFee);
                    accountDetail.setTradeStart(account.getAvailableBalance());
                    accountDetail.setTradeEnd(account.getAvailableBalance().add(annualFee));
                    accountDetail.setTradeType(BizConstant.TradeType.INCOME.value());
                    accountDetail.setTradeNo(noGenerator.getRewardTradeNo());
                    bizAccountDetailMapper.insert(accountDetail);
                    updateWrapper.eq("available_balance", account.getAvailableBalance());
                    update.setAvailableBalance(account.getAvailableBalance().add(annualFee));
                }
                boolean updateResult = optimisticLockingUpdateOrgAccount(update, updateWrapper, 1);
                AssertUtil.isTrue(updateResult, "机构账户奖励更新失败，请重试");
            }
        }
    }

    /**
     * 添加图片信息
     * @author cjunyuan
     * @date 2019/4/2 17:09
     */
    private String addApplyInfoFile(String base64Code){
        if(!StringUtils.isBlank(base64Code)){
            String suffix = base64Code.split(";")[0];
            String imagePattern = "data:image/(jpg|png|jpeg)";
            if(!Pattern.matches(imagePattern, suffix)){
                throw new DubboException("素材格式错误");
            }
            String imageUrl = AliyunStorageUtil.uploadBase64Img("diandian-business", base64Code);
            return imageUrl;
        }
        return null;
    }

    /**
     *
     * 功能描述: 乐观锁更新机构账户
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/11 21:38
     */
    public boolean optimisticLockingUpdateOrgAccount(BizAccountModel update, UpdateWrapper<BizAccountModel> wrapper, int updateTime){
        if(updateTime <= 5){
            if(bizAccountMapper.update(update, wrapper) < 1){
                optimisticLockingUpdateOrgAccount(update, wrapper, ++updateTime);
            }
            return true;
        } else {
            return false;
        }
    }
}
