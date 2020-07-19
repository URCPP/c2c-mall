package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.*;
import com.diandian.dubbo.business.mapper.merchant.*;
import com.diandian.dubbo.common.oss.AliyunStorageUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.ApplyCheckResultDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantOpenApplyDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantOpenApplyLogQueryDTO;
import com.diandian.dubbo.facade.model.biz.*;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.*;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.service.merchant.MerchantOpenApplyLogService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.vo.merchant.MerchantOpenApplyLogDetailVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantOpenApplyLogListVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 商户软件开通申请记录表
 *
 * @author wbc
 * @date 2019/02/20
 */
@Service("merchantOpenApplyLogService")
@Slf4j
public class MerchantOpenApplyLogServiceImpl implements MerchantOpenApplyLogService {

    @Autowired
    private MerchantOpenApplyLogMapper merchantOpenApplyLogMapper;

    @Autowired
    private BizSoftwareTypeMapper bizSoftwareTypeMapper;

    @Autowired
    private BizSoftwareTypeStrategyMapper bizSoftwareTypeStrategyMapper;

    @Autowired
    private BizOrgPrivilegeMapper bizOrgPrivilegeMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private MerchantSoftInfoMapper merchantSoftInfoMapper;

    @Autowired
    private MerchantSoftHistoryLogMapper merchantSoftHistoryLogMapper;

    @Autowired
    private BizAccountMapper bizAccountMapper;

    @Autowired
    private BizAccountDetailMapper bizAccountDetailMapper;

    @Autowired
    private BizBonusDetailMapper bizBonusDetailMapper;

    @Autowired
    private MerchantAccountInfoMapper merchantAccountInfoMapper;

    @Autowired
    private MerchantAccountHistoryLogMapper merchantAccountHistoryLogMapper;

    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Autowired
    private BizPaymentPicMapper bizPaymentPicMapper;

    @Autowired
    private MerchantWalletInfoMapper merchantWalletInfoMapper;

    @Autowired
    private BizOpenDetailMapper bizOpenDetailMapper;

    @Autowired
    private MerchantOpenOptLogMapper merchantOpenOptLogMapper;

    @Autowired
    private BizOrgTypeStrategyMapper bizOrgTypeStrategyMapper;

    @Autowired
    private NoGenerator noGenerator;

    private final BigDecimal percent = new BigDecimal(100);


    @Override
    public boolean saveOpenApplyLog(MerchantOpenApplyLogModel openApplyLogModel) {
        merchantOpenApplyLogMapper.insert(openApplyLogModel);
        return false;
    }

    @Override
    public PageResult softApplyInfoListPage(MerchantOpenApplyLogQueryDTO dto) {
        Page<MerchantOpenApplyLogListVO> page = new Page<>();
        if (null != dto.getCurPage() && dto.getCurPage() > 0) {
            page.setCurrent(dto.getCurPage());
        }
        if (null != dto.getPageSize() && dto.getPageSize() > 0) {
            page.setSize(dto.getPageSize());
        }
        IPage<MerchantOpenApplyLogListVO> iPage = merchantOpenApplyLogMapper.softApplyInfoListPage(page, dto);
        return new PageResult(iPage);
    }

    @Override
    public MerchantOpenApplyLogModel getById(Long id) {
        return merchantOpenApplyLogMapper.selectById(id);
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public void checkSoftApplyInfo(ApplyCheckResultDTO checkResult) {
        AssertUtil.isTrue(checkResult.getId() != null && checkResult.getId() > 0L, "参数错误");
        AssertUtil.isTrue(checkResult.getAuditState() != null, "审核结果不能为空");
        MerchantOpenApplyLogModel applyInfo = merchantOpenApplyLogMapper.selectById(checkResult.getId());
        AssertUtil.notNull(applyInfo, "申请信息不存在");
        BizSoftwareTypeModel softwareType = bizSoftwareTypeMapper.selectById(applyInfo.getSoftTypeId());
        AssertUtil.notNull(softwareType, "软件类型不存在");
        MerchantInfoModel oldMchInfo = merchantInfoMapper.selectById(applyInfo.getMerchantId());
        AssertUtil.notNull(oldMchInfo, "商户信息不存在");
        AssertUtil.isTrue(!BizConstant.AuditState.AUDIT_PASSED.value().equals(applyInfo.getApplyStateFlag()), "该申请信息已完成审核，请勿重复提交");
        AssertUtil.isTrue(!BizConstant.AuditState.CLOSE_APPLY.value().equals(applyInfo.getApplyStateFlag()), "无法审核已关闭的申请信息");
        StringBuilder optLog = new StringBuilder();
        if (BizConstant.AuditState.AUDIT_PASSED.value().equals(checkResult.getAuditState())) {
            softApplyCheck(applyInfo);
            optLog.append("商户"+oldMchInfo.getLoginName()+"成功开通"+softwareType.getTypeName());
        } else if (BizConstant.AuditState.AUDIT_NOT_PASSED.value().equals(checkResult.getAuditState())) {
            MerchantInfoModel update = new MerchantInfoModel();
            update.setId(oldMchInfo.getId());
            update.setApproveFlag(MerchantConstant.MerchantApproveState.APPROVE_FAIL.value());
            merchantInfoMapper.updateById(update);
            optLog.append("商户"+oldMchInfo.getLoginName()+"开通"+softwareType.getTypeName()+"失败");
        }
        //更新审核信息
        MerchantOpenApplyLogModel update = new MerchantOpenApplyLogModel();
        update.setId(applyInfo.getId());
        update.setAuditFailReason(checkResult.getAuditFailReason());
        update.setApplyStateFlag(checkResult.getAuditState());
        update.setAuditUserId(checkResult.getAuditUserId());
        update.setAuditTime(new Date());
        merchantOpenApplyLogMapper.updateById(update);
        //添加开通操作记录
        MerchantOpenOptLogModel openModel = new MerchantOpenOptLogModel();
        openModel.setBillNo(applyInfo.getBillNo());
        openModel.setMerchantId(applyInfo.getMerchantId());
        openModel.setRecommendId(applyInfo.getRecommendId());
        openModel.setMerchantLoginName(oldMchInfo.getLoginName());
        openModel.setSoftTypeId(oldMchInfo.getSoftTypeId());
        openModel.setOptType(0);
        openModel.setOptRecord(optLog.toString());
        merchantOpenOptLogMapper.insert(openModel);
    }*/

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMerchantOpenApply(MerchantOpenApplyDTO dto) {
        AssertUtil.notBlank(dto.getName(), "商户名称不能为空");
        AssertUtil.isTrue(null != dto.getApplyType(), "操作类型不能为空");
        AssertUtil.isTrue(null != dto.getSoftTypeId() && dto.getSoftTypeId() > 0, "软件类型不能为空");
        BizSoftwareTypeModel softwareType = bizSoftwareTypeMapper.selectById(dto.getSoftTypeId());
        AssertUtil.notNull(softwareType, "软件类型不存在");
        AssertUtil.notBlank(dto.getPhone(), "手机号码不能为空");
        AssertUtil.notBlank(dto.getLoginName(), "登录账号不能为空");
        QueryWrapper<MerchantInfoModel> merchantInfoWrapper = new QueryWrapper<>();
        merchantInfoWrapper.eq("login_name", dto.getLoginName());
        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "登录账号已存在");
        AssertUtil.notBlank(dto.getLoginPassword(), "登录密码不能为空");
        AssertUtil.isTrue(null != dto.getParentId(), "参数错误");
        SysOrgModel org = sysOrgMapper.selectNotDeleteOrgById(dto.getParentId());
        AssertUtil.notNull(org, "参数错误");
        merchantInfoWrapper = new QueryWrapper<>();
        merchantInfoWrapper.eq("name", dto.getName());
        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "商户名称已存在");
        //免费开通
        if (BizConstant.ApplyType.OPEN.value().equals(dto.getApplyType()) && dto.getParentId() != 0) {
            QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper<>();
            orgPrivilegeWrapper.eq("org_id", dto.getParentId());
            orgPrivilegeWrapper.eq("reward_software_type_id", dto.getSoftTypeId());
            BizOrgPrivilegeModel orgPrivilege = bizOrgPrivilegeMapper.selectOne(orgPrivilegeWrapper);
            AssertUtil.isTrue(orgPrivilege != null && orgPrivilege.getRewardValue().compareTo(BigDecimal.ZERO) == 1, "可开通名额不足");
        }
        //商户信息
        MerchantInfoModel merchantInfo = new MerchantInfoModel();
        BeanUtil.copyProperties(dto, merchantInfo);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        merchantInfo.setSoftTypeName(softwareType.getTypeName());
        merchantInfo.setLoginPassword(new Sha256Hash(merchantInfo.getLoginPassword(), salt).toHex());
        merchantInfo.setSalt(salt);
        merchantInfo.setCode(noGenerator.getMerchantNo());
        //merchantInfo.setMallName(dto.getName());
        //merchantInfo.setOpenType(dto.getApplyType());
        //推荐信息判断
        checkParentAndRecommend(dto, merchantInfo);
        //添加图片信息
        addImageInfo(dto, merchantInfo);
        merchantInfo.setApproveFlag(MerchantConstant.MerchantApproveState.APPROVEING.value());
        //AssertUtil.isTrue(StringUtils.isNotBlank(merchantInfo.getIdcard()), "请填写身份证号码");
        //AssertUtil.isTrue(StringUtils.isNotBlank(merchantInfo.getBusinessLicenseCode()), "请填写营业执照编码");
        merchantInfoWrapper = new QueryWrapper<>();
        //merchantInfoWrapper.eq("business_license_code", merchantInfo.getBusinessLicenseCode());
        merchantInfoWrapper.eq("soft_type_id", merchantInfo.getSoftTypeId());
        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
//        if(StringUtils.isNotBlank(merchantInfo.getBusinessLicenseCode()) && merchantInfoMapper.selectCount(merchantInfoWrapper) > 0){
//            throw new DubboException("该营业执照已被使用");
//        }
        //AssertUtil.isTrue(StringUtils.isNotBlank(merchantInfo.getBusinessLicensePic()), "请上传营业执照图片");
        //AssertUtil.isTrue(StringUtils.isNotBlank(merchantInfo.getIdcardPositivePic()), "请上传身份证正面图片");
        //AssertUtil.isTrue(StringUtils.isNotBlank(merchantInfo.getIdcardReversePic()), "请上传身份证反面图片");
        //商户信息
        merchantInfoMapper.insert(merchantInfo);
        //商户申请记录
        MerchantOpenApplyLogModel merchantOpenApplyLog = new MerchantOpenApplyLogModel();
        BeanUtil.copyProperties(dto, merchantOpenApplyLog);
        merchantOpenApplyLog.setMerchantId(merchantInfo.getId());
        merchantOpenApplyLog.setBillNo(noGenerator.getApplyNo());
        merchantOpenApplyLog.setRecommendType(merchantInfo.getRecommendTypeFlag());
        //merchantOpenApplyLog.setParentType(merchantInfo.getParentTypeFlag());
        merchantOpenApplyLogMapper.insert(merchantOpenApplyLog);
        addOpenLog(merchantOpenApplyLog, false);
        //添加打款凭证
        dto.setId(merchantOpenApplyLog.getId());
        addPaymentInfo(dto);

        //商户资金帐户
        MerchantAccountInfoModel mAccNew = new MerchantAccountInfoModel();
        mAccNew.setMerchantId(merchantInfo.getId());
        mAccNew.setShoppingCouponSum(BigDecimal.ZERO);
        mAccNew.setExchangeCouponSum(0);
        mAccNew.setAvailableBalance(BigDecimal.ZERO);
        mAccNew.setFreezeBalance(BigDecimal.ZERO);
        merchantAccountInfoMapper.insert(mAccNew);
        //商户钱包帐户
        MerchantWalletInfoModel mWallet = new MerchantWalletInfoModel();
        mWallet.setMerchantId(merchantInfo.getId());
        mWallet.setAmount(BigDecimal.ZERO);
        mWallet.setAmountSum(BigDecimal.ZERO);
        mWallet.setExchangeNumber(0);
        mWallet.setSurplusExchangeNumber(0);
        merchantWalletInfoMapper.insert(mWallet);
        return true;
    }*/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(MerchantOpenApplyDTO dto) {
        AssertUtil.notBlank(dto.getName(), "商户名称不能为空");
        AssertUtil.isTrue(null != dto.getApplyType(), "操作类型不能为空");
        AssertUtil.notBlank(dto.getPhone(), "手机号码不能为空");
        AssertUtil.notBlank(dto.getIdcard(), "身份证号码不能为空");
        AssertUtil.notBlank(dto.getLoginName(), "登录账号不能为空");
        MerchantOpenApplyLogModel oldApplyLog = merchantOpenApplyLogMapper.selectById(dto.getId());
        AssertUtil.notNull(oldApplyLog, "商户申请信息不存在");
        AssertUtil.isTrue(!BizConstant.AuditState.AUDIT_PASSED.value().equals(oldApplyLog.getApplyStateFlag()), "无法修改审核成功的申请信息");
        AssertUtil.isTrue(!BizConstant.AuditState.CLOSE_APPLY.value().equals(oldApplyLog.getApplyStateFlag()), "无法修改已关闭的申请信息");
        AssertUtil.isTrue(null != oldApplyLog.getMerchantId() && oldApplyLog.getMerchantId() > 0, "参数错误");
        MerchantInfoModel oldMerchantInfo = merchantInfoMapper.selectById(oldApplyLog.getMerchantId());
        AssertUtil.notNull(oldMerchantInfo, "参数错误");
        QueryWrapper<MerchantInfoModel> merchantInfoWrapper = new QueryWrapper<>();
        merchantInfoWrapper.ne("id", dto.getMerchantId());
        merchantInfoWrapper.eq("login_name", dto.getLoginName());
        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "登录账号已存在");

        merchantInfoWrapper = new QueryWrapper<>();
        merchantInfoWrapper.ne("id", dto.getMerchantId());
        merchantInfoWrapper.eq("name", dto.getName());
        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "商户名称已存在");
        AssertUtil.isTrue(StringUtils.isNotBlank(dto.getIdcard()), "请填写身份证号码");
        AssertUtil.isTrue(StringUtils.isNotBlank(dto.getBusinessLicenseCode()), "请填写营业执照编码");

        MerchantInfoModel update = new MerchantInfoModel();
        BeanUtil.copyProperties(dto, update);
        update.setLoginPassword(null);
        update.setId(oldApplyLog.getMerchantId());
        if (StringUtils.isNotBlank(dto.getNewPassword())) {
            AssertUtil.isTrue(oldMerchantInfo.getLoginPassword().equals(new Sha256Hash(dto.getLoginPassword(), oldMerchantInfo.getSalt()).toHex()), "原始密码不正确");
            update.setLoginPassword(new Sha256Hash(dto.getNewPassword(), oldMerchantInfo.getSalt()).toHex());
        }
        //打款凭证
        addPaymentInfo(dto);
        //推荐信息判断
        checkParentAndRecommend(dto, update);
        //添加图片信息
        addImageInfo(dto, update);
        merchantInfoWrapper = new QueryWrapper<>();
        merchantInfoWrapper.ne("id", oldApplyLog.getMerchantId());
        merchantInfoWrapper.eq("soft_type_id", oldApplyLog.getSoftTypeId());
        //merchantInfoWrapper.eq("business_license_code", update.getBusinessLicenseCode());
        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "该营业执照已被使用");
        int updateRes = merchantInfoMapper.updateById(update);

        MerchantOpenApplyLogModel updateApplyLog = new MerchantOpenApplyLogModel();
        BeanUtil.copyProperties(dto, updateApplyLog);
        updateApplyLog.setId(oldApplyLog.getId());
        if (BizConstant.AuditState.AUDIT_NOT_PASSED.value().equals(oldApplyLog.getApplyStateFlag())) {
            updateApplyLog.setApplyStateFlag(BizConstant.AuditState.AUDIT_WAIT.value());
        }
        merchantOpenApplyLogMapper.updateById(updateApplyLog);
        return true;
    }

    @Override
    public MerchantOpenApplyLogDetailVO getMerchantApplyDetail(MerchantOpenApplyLogQueryDTO dto) {
        MerchantOpenApplyLogDetailVO detail = merchantOpenApplyLogMapper.getMerchantApplyDetail(dto);
        if (null != detail.getRecommendId() && detail.getRecommendId() > 0) {
            if (BizConstant.ObjectType.ORG.value().equals(detail.getRecommendTypeFlag())) {
                detail.setRecommendName(sysOrgMapper.getNameById(detail.getRecommendId()));
            } else if (BizConstant.ObjectType.SOFTWARE.value().equals(detail.getRecommendTypeFlag())) {
                detail.setRecommendName(merchantInfoMapper.getNameById(detail.getRecommendId()));
            }
        }
        if (null != detail.getParentId() && detail.getParentId() > 0) {
            if (BizConstant.ObjectType.ORG.value().equals(detail.getParentTypeFlag())) {
                detail.setParentName(sysOrgMapper.getNameById(detail.getParentId()));
            } else if (BizConstant.ObjectType.SOFTWARE.value().equals(detail.getParentTypeFlag())) {
                detail.setParentName(merchantInfoMapper.getNameById(detail.getParentId()));
            }
        }
        return detail;
    }

    /*private void softApplyCheck(MerchantOpenApplyLogModel applyInfo) {
        //判断申请的机构类型是否存在
        BizSoftwareTypeModel softwareType = bizSoftwareTypeMapper.selectById(applyInfo.getSoftTypeId());
        AssertUtil.notNull(softwareType, "软件类型不存在");

        MerchantInfoModel oldMerchant = merchantInfoMapper.selectById(applyInfo.getMerchantId());
        AssertUtil.notNull(oldMerchant, "商户信息不存在");

        MerchantInfoModel update = new MerchantInfoModel();
        update.setId(oldMerchant.getId());
        //update.setMerchantExpireTime(DateUtil.offset(new Date(), DateField.MONTH, softwareType.getOpeningTimeLength()));
        update.setApproveFlag(MerchantConstant.MerchantApproveState.APPROVED.value());
        //update.setOpenType(applyInfo.getApplyType());
        merchantInfoMapper.updateById(update);

        //添加商户开通奖励
        addGiftReward(oldMerchant);
        if (BizConstant.ApplyType.APPLY.value().equals(applyInfo.getApplyType())) {
            if(null == applyInfo.getRecommendId() || null == applyInfo.getRecommendType() ){
                //添加开通商户奖励（与前面不同，上面一个的受益对象是新增商户，这个的受益对象是上级机构）
                if (BizConstant.ObjectType.ORG.value().equals(applyInfo.getParentType())) {
                    addOpenMerchantReward(oldMerchant);
                }
            }
            if (null != applyInfo.getRecommendType() && null != applyInfo.getRecommendId() && applyInfo.getRecommendId() > 0) {
                if (BizConstant.RecommendType.ORG.value().equals(applyInfo.getRecommendType())) {
                    //addOrgRecommendReward(oldMerchant);
                } else if (BizConstant.RecommendType.SOFTWARE.value().equals(applyInfo.getRecommendType())) {
                    addMerchantRecommendReward(oldMerchant);
                } else {
                    throw new DubboException("参数错误");
                }
            }
        }
    }*/

    /*@Override
    public void addGiftReward(MerchantInfoModel merchantInfo){
        //获取软件类型对应的规则
        QueryWrapper<BizSoftwareTypeStrategyModel> softwareTypeStrategyWrapper = new QueryWrapper<>();
        softwareTypeStrategyWrapper.eq("software_type_id", merchantInfo.getSoftTypeId());
        softwareTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.OPENING_GIFTS.value());
        softwareTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);

        List<BizSoftwareTypeStrategyModel> softwareTypeStrategyList = bizSoftwareTypeStrategyMapper.selectList(softwareTypeStrategyWrapper);
        addMchReward(softwareTypeStrategyList, merchantInfo, null);
    }*/

    @Override
    public void addOpenMerchantReward(MerchantInfoModel merchantInfo){
        SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(merchantInfo.getParentId());
        AssertUtil.notNull(parentOrg, "上级信息不存在");

        QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
        orgTypeStrategyWrapper.eq("org_type_id", parentOrg.getOrgTypeId());
        orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.OPENING_BONUSES.value());
        orgTypeStrategyWrapper.eq("recommend_software_type_id", merchantInfo.getSoftTypeId());
        orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
        List<BizOrgTypeStrategyModel> orgTypeStrategyList = bizOrgTypeStrategyMapper.selectList(orgTypeStrategyWrapper);
        addOrgReward(orgTypeStrategyList, parentOrg, merchantInfo, BizConstant.OpenBusType.RECOMMEND_REWARD);
    }

    @Override
    public void addOrgRecommendReward(MerchantInfoModel merchantInfo) {
        SysOrgModel recommendOrg = sysOrgMapper.selectNotDeleteOrgById(merchantInfo.getRecommendId());
        AssertUtil.notNull(recommendOrg, "推荐机构信息不存在");
        //查询推荐软件的特权（实际只要推荐特权）
        QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
        orgTypeStrategyWrapper.eq("org_type_id", recommendOrg.getOrgTypeId());
        orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.REFERRAL_BONUSES.value());
        orgTypeStrategyWrapper.eq("recommend_software_type_id", merchantInfo.getSoftTypeId());
        orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
        List<BizOrgTypeStrategyModel> orgTypeStrategyList = bizOrgTypeStrategyMapper.selectList(orgTypeStrategyWrapper);
        addOrgReward(orgTypeStrategyList, recommendOrg, merchantInfo, BizConstant.OpenBusType.RECOMMEND_REWARD);
    }

    /*@Override
    public void addMerchantRecommendReward(MerchantInfoModel merchantInfo) {
        MerchantInfoModel recommendMerchant = merchantInfoMapper.selectById(merchantInfo.getRecommendId());
        AssertUtil.notNull(recommendMerchant, "推荐商户信息不存在");
        //查询推荐软件的特权（实际只要推荐特权）
        QueryWrapper<BizSoftwareTypeStrategyModel> softwareTypeStrategyWrapper = new QueryWrapper<>();
        softwareTypeStrategyWrapper.eq("software_type_id", recommendMerchant.getSoftTypeId());
        softwareTypeStrategyWrapper.eq("recommend_software_type_id", merchantInfo.getSoftTypeId());
        softwareTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.REFERRAL_BONUSES.value());
        softwareTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
        List<BizSoftwareTypeStrategyModel> softTypeStrategyList = bizSoftwareTypeStrategyMapper.selectList(softwareTypeStrategyWrapper);
        addMchReward(softTypeStrategyList, recommendMerchant, merchantInfo);
    }*/

    @Override
    public Integer count(MerchantOpenApplyLogQueryDTO dto) {
        return merchantOpenApplyLogMapper.count(dto);
    }

    @Override
    public MerchantOpenApplyLogModel getByMerIdAndSoftId(Long merchantId, Long softTypeId) {
        QueryWrapper<MerchantOpenApplyLogModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        qw.eq("soft_type_id", softTypeId);
        return merchantOpenApplyLogMapper.selectOne(qw);
    }

    @Override
    public boolean update(MerchantOpenApplyLogModel openApplyLogModel) {
        merchantOpenApplyLogMapper.updateById(openApplyLogModel);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeApply(Long id){
        MerchantOpenApplyLogModel oldMchApplyInfo = merchantOpenApplyLogMapper.selectById(id);
        AssertUtil.notNull(oldMchApplyInfo, "申请信息不存在");
        AssertUtil.isTrue(!BizConstant.AuditState.AUDIT_PASSED.value().equals(oldMchApplyInfo.getApplyStateFlag()), "无法关闭已经通过审核的申请信息");
        AssertUtil.isTrue(!BizConstant.AuditState.CLOSE_APPLY.value().equals(oldMchApplyInfo.getApplyStateFlag()), "该申请信息已关闭，无需重复操作");
        //如果是开通操作，返还开通名额
        addOpenLog(oldMchApplyInfo, true);
        MerchantOpenApplyLogModel update = new MerchantOpenApplyLogModel();
        update.setApplyStateFlag(BizConstant.AuditState.CLOSE_APPLY.value());
        update.setId(oldMchApplyInfo.getId());
        merchantOpenApplyLogMapper.updateById(update);

        //修改商户信息为已删除状态
        MerchantInfoModel oldMchInfo = merchantInfoMapper.selectById(oldMchApplyInfo.getMerchantId());
        if(null != oldMchInfo){
            MerchantInfoModel updateMchInfo = new MerchantInfoModel();
            updateMchInfo.setId(oldMchInfo.getId());
            updateMchInfo.setDelFlag(BizConstant.STATE_DISNORMAL);
            merchantInfoMapper.updateById(updateMchInfo);
        }
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public void undoApply(Long id){
        MerchantOpenApplyLogModel oldApplyInfo = merchantOpenApplyLogMapper.selectById(id);
        AssertUtil.notNull(oldApplyInfo, "申请信息不存在");
        AssertUtil.isTrue(BizConstant.AuditState.AUDIT_PASSED.value().equals(oldApplyInfo.getApplyStateFlag()), "该申请信息不是审核通过状态");
        MerchantInfoModel oldMchInfo = merchantInfoMapper.selectById(oldApplyInfo.getMerchantId());
        AssertUtil.notNull(oldMchInfo, "参数错误");

        if (BizConstant.ApplyType.APPLY.value().equals(oldApplyInfo.getApplyType())) {
            if(null == oldApplyInfo.getRecommendId() || null == oldApplyInfo.getRecommendType() ){
                if (BizConstant.ObjectType.ORG.value().equals(oldApplyInfo.getParentType())) {
                    SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(oldApplyInfo.getParentId());
                    AssertUtil.notNull(parentOrg, "参数错误");
                    QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
                    orgTypeStrategyWrapper.eq("org_type_id", parentOrg.getOrgTypeId());
                    orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.OPENING_BONUSES.value());
                    orgTypeStrategyWrapper.eq("recommend_software_type_id", oldApplyInfo.getSoftTypeId());
                    orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
                    List<BizOrgTypeStrategyModel> orgTypeStrategyList = bizOrgTypeStrategyMapper.selectList(orgTypeStrategyWrapper);
                    recycleOpenMchReward(orgTypeStrategyList, parentOrg, oldMchInfo);
                }
            }
            if (null != oldApplyInfo.getRecommendType() && null != oldApplyInfo.getRecommendId() && oldApplyInfo.getRecommendId() > 0) {
                if (BizConstant.RecommendType.SOFTWARE.value().equals(oldApplyInfo.getRecommendType())) {
                    MerchantInfoModel recommendMchInfo = merchantInfoMapper.selectById(oldApplyInfo.getRecommendId());
                    QueryWrapper<BizSoftwareTypeStrategyModel> softwareTypeStrategyWrapper = new QueryWrapper<>();
                    softwareTypeStrategyWrapper.eq("software_type_id", recommendMchInfo.getSoftTypeId());
                    softwareTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.REFERRAL_BONUSES.value());
                    softwareTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
                    List<BizSoftwareTypeStrategyModel> softTypeStrategyList = bizSoftwareTypeStrategyMapper.selectList(softwareTypeStrategyWrapper);
                    recycleRecommendMchReward(softTypeStrategyList, recommendMchInfo, oldMchInfo);
                }
            }
        }else if(BizConstant.ApplyType.OPEN.value().equals(oldApplyInfo.getApplyType())){
            if(null != oldMchInfo.getRecommendId() && oldMchInfo.getRecommendId() > 0){
                MerchantInfoModel recommendMch = merchantInfoMapper.selectById(oldMchInfo.getRecommendId());
                AssertUtil.notNull(recommendMch, "参数错误");
                QueryWrapper<MerchantSoftInfoModel> merchantSoftInfoWrapper = new QueryWrapper();
                merchantSoftInfoWrapper.eq("merchant_id", recommendMch.getId());
                merchantSoftInfoWrapper.eq("soft_type_id", oldMchInfo.getSoftTypeId());
                MerchantSoftInfoModel oldMerchantSoftInfo = merchantSoftInfoMapper.selectOne(merchantSoftInfoWrapper);
                AssertUtil.notNull(oldMerchantSoftInfo, "该申请信息不是初始状态，无法撤消");
                MerchantSoftInfoModel update = new MerchantSoftInfoModel();
                update.setId(oldMerchantSoftInfo.getId());
                update.setAvailableOpenNum(oldMerchantSoftInfo.getAvailableOpenNum() + 1);
                merchantSoftInfoMapper.updateById(update);
                MerchantSoftHistoryLogModel log = new MerchantSoftHistoryLogModel();
                log.setMerchantId(recommendMch.getId());
                log.setMerchantName(recommendMch.getName());
                log.setNum(1);
                log.setPreNum(oldMerchantSoftInfo.getAvailableOpenNum());
                log.setPostNum(oldMerchantSoftInfo.getAvailableOpenNum() + 1);
                log.setSoftTypeId(oldMchInfo.getSoftTypeId());
                log.setSoftTypeName(oldMchInfo.getSoftTypeName());
                log.setBusinessType(BizConstant.OpenBusType.UNDO_APPLY.value());
                merchantSoftHistoryLogMapper.insert(log);
            }else {
                SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(oldMchInfo.getParentId());
                AssertUtil.notNull(parentOrg, "参数错误");
                QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper<>();
                orgPrivilegeWrapper.eq("org_id", parentOrg.getId());
                orgPrivilegeWrapper.eq("reward_type_id", BizConstant.ObjectType.SOFTWARE.value());
                orgPrivilegeWrapper.eq("reward_software_type_id", oldMchInfo.getSoftTypeId());
                BizOrgPrivilegeModel oldOrgPrivilege = bizOrgPrivilegeMapper.selectOne(orgPrivilegeWrapper);
                AssertUtil.notNull(oldOrgPrivilege, "该申请信息不是初始状态，无法撤消");
                BizOrgPrivilegeModel update = new BizOrgPrivilegeModel();
                update.setId(oldOrgPrivilege.getId());
                update.setRewardValue(oldOrgPrivilege.getRewardValue().add(BigDecimal.ONE));
                bizOrgPrivilegeMapper.updateById(update);
                BizOpenDetailModel openDetail = new BizOpenDetailModel();
                openDetail.setOrgId(parentOrg.getId());
                openDetail.setBusType(BizConstant.OpenBusType.UNDO_APPLY.value());
                openDetail.setOpenType(oldOrgPrivilege.getRewardTypeId());
                openDetail.setTradeStart(oldOrgPrivilege.getRewardValue().intValue());
                openDetail.setTradeEnd(oldOrgPrivilege.getRewardValue().add(BigDecimal.ONE).intValue());
                openDetail.setTradeNum(1);
                openDetail.setTradeNo(noGenerator.getRewardTradeNo());
                openDetail.setTradeType(BizConstant.TradeType.INCOME.value());
                openDetail.setTypeId(oldOrgPrivilege.getRewardSoftwareTypeId());
                bizOpenDetailMapper.insert(openDetail);
            }
        }
        MerchantInfoModel update = new MerchantInfoModel();
        update.setId(oldMchInfo.getId());
        update.setDelFlag(BizConstant.STATE_DISNORMAL);
        merchantInfoMapper.updateById(update);

        MerchantOpenApplyLogModel updateApply = new MerchantOpenApplyLogModel();
        updateApply.setId(oldApplyInfo.getId());
        updateApply.setApplyStateFlag(BizConstant.AuditState.CLOSE_APPLY.value());
        merchantOpenApplyLogMapper.updateById(updateApply);
    }*/

    private void addOrgReward(List<BizOrgTypeStrategyModel> orgTypeStrategyList, SysOrgModel benefitOrg, MerchantInfoModel fromMch, BizConstant.OpenBusType busType){
        for (BizOrgTypeStrategyModel orgTypeStrategy : orgTypeStrategyList) {
            //奖励机构/软件
            if (BizConstant.RewardType.ORG.value().equals(orgTypeStrategy.getRewardType()) ||
                    BizConstant.RewardType.SOFTWARE.value().equals(orgTypeStrategy.getRewardType())) {
                QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper();
                orgPrivilegeWrapper.eq("org_id", benefitOrg.getId());
                orgPrivilegeWrapper.eq(BizConstant.RewardType.SOFTWARE.value().equals(orgTypeStrategy.getRewardType()), "reward_software_type_id", orgTypeStrategy.getRewardSoftwareTypeId());
                //查询是否已经存在对应的机构特权信息，存在-修改对应的特权信息数量，不存在-新增
                BizOrgPrivilegeModel oldPrivilege = bizOrgPrivilegeMapper.selectOne(orgPrivilegeWrapper);
                if (null == oldPrivilege) {
                    BizOrgPrivilegeModel insertPrivilege = new BizOrgPrivilegeModel();
                    insertPrivilege.setRewardValue(orgTypeStrategy.getRewardValue());
                    insertPrivilege.setRewardSoftwareTypeId(BizConstant.RewardType.SOFTWARE.value().equals(orgTypeStrategy.getRewardType()) ? orgTypeStrategy.getRewardSoftwareTypeId() : null);
                    insertPrivilege.setOrgId(benefitOrg.getId());
                    bizOrgPrivilegeMapper.insert(insertPrivilege);
                } else {
                    BizOrgPrivilegeModel update = new BizOrgPrivilegeModel();
                    update.setId(oldPrivilege.getId());
                    update.setRewardValue(orgTypeStrategy.getRewardValue().add(oldPrivilege.getRewardValue()));
                    bizOrgPrivilegeMapper.updateById(update);
                }
                //添加推荐机构奖励（机构/软件）明细
                BizOpenDetailModel openDetail = new BizOpenDetailModel();
                openDetail.setOrgId(benefitOrg.getId());
                openDetail.setBusType(busType.value());
                openDetail.setFromOrgId(null == fromMch ? null : fromMch.getId());
                openDetail.setOpenType(orgTypeStrategy.getRewardType());
                openDetail.setTradeStart(null == oldPrivilege ? 0 : oldPrivilege.getRewardValue().intValue());
                openDetail.setTradeEnd(null == oldPrivilege ? orgTypeStrategy.getRewardValue().intValue() : oldPrivilege.getRewardValue().add(orgTypeStrategy.getRewardValue()).intValue());
                openDetail.setTradeNum(orgTypeStrategy.getRewardValue().intValue());
                openDetail.setTradeNo(noGenerator.getRewardTradeNo());
                openDetail.setTradeType(BizConstant.TradeType.INCOME.value());
                openDetail.setTypeId(BizConstant.ObjectType.ORG.value().equals(orgTypeStrategy.getRewardType()) ? orgTypeStrategy.getRewardOrgTypeId() : orgTypeStrategy.getRewardSoftwareTypeId());
                bizOpenDetailMapper.insert(openDetail);
                //奖励现金/首次年费
            } else if (BizConstant.RewardType.CASH.value().equals(orgTypeStrategy.getRewardType()) ||
                    BizConstant.RewardType.FIRST_TIME.value().equals(orgTypeStrategy.getRewardType())) {
                //修改机构账户未发放奖金的值
                QueryWrapper<BizAccountModel> accountWrapper = new QueryWrapper<>();
                accountWrapper.eq("org_id", benefitOrg.getId());
                BizAccountModel account = bizAccountMapper.selectOne(accountWrapper);
                AssertUtil.notNull(account, "账户信息不存在");
                if (BizConstant.RewardType.CASH.value().equals(orgTypeStrategy.getRewardType())) {
                    //生成现金奖励流水
                    BizBonusDetailModel bonusDetail = new BizBonusDetailModel();
                    bonusDetail.setTradeNo(noGenerator.getRewardTradeNo());
                    bonusDetail.setTradeType(BizConstant.TradeType.INCOME.value());
                    bonusDetail.setBusType(0);
                    bonusDetail.setTradeAmount(orgTypeStrategy.getRewardValue());
                    bonusDetail.setTradeStart(account.getBonus());
                    bonusDetail.setTradeEnd(account.getBonus().add(orgTypeStrategy.getRewardValue()));
                    bonusDetail.setOrgId(benefitOrg.getId());
                    //被推荐机构ID
                    bonusDetail.setFromOrgId(fromMch.getId());
                    bizBonusDetailMapper.insert(bonusDetail);
                    BizAccountModel update = new BizAccountModel();
                    update.setId(account.getId());
                    UpdateWrapper<BizAccountModel> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", account.getId());
                    //修改奖金余额
                    updateWrapper.eq("bonus", account.getBonus());
                    updateWrapper.eq("total_bonus", account.getTotalBonus());
                    update.setBonus(account.getBonus().add(orgTypeStrategy.getRewardValue()));
                    update.setTotalBonus(account.getTotalBonus().add(orgTypeStrategy.getRewardValue()));
                    //更新账户表
                    boolean updateResult = optimisticLockingUpdateOrgAccount(update, updateWrapper, 1);
                    AssertUtil.isTrue(updateResult, "机构账户推荐奖励更新失败，请重试");
                } else if (BizConstant.RewardType.FIRST_TIME.value().equals(orgTypeStrategy.getRewardType())) {
                    //生成首次开通年费奖励明细
                    BizSoftwareTypeModel softType = bizSoftwareTypeMapper.selectById(fromMch.getSoftTypeId());
                    recursiveFirstOpenReward(benefitOrg, fromMch, softType.getOpeningCost(), BigDecimal.ZERO);
                }
            }
        }
    }

    /*private void addMchReward(List<BizSoftwareTypeStrategyModel> softTypeStrategyList, MerchantInfoModel benefitMch, MerchantInfoModel fromMch){
        for (BizSoftwareTypeStrategyModel softwareTypeStrategy : softTypeStrategyList) {
            //奖励机构/软件
            if (BizConstant.RewardType.ORG.value().equals(softwareTypeStrategy.getRewardType()) ||
                    BizConstant.RewardType.SOFTWARE.value().equals(softwareTypeStrategy.getRewardType())) {
                BizSoftwareTypeModel rewardSoftwareType = bizSoftwareTypeMapper.selectById(softwareTypeStrategy.getRewardSoftwareTypeId());
                AssertUtil.notNull(rewardSoftwareType, "参数错误");
                QueryWrapper<MerchantSoftInfoModel> merchantSoftInfoWrapper = new QueryWrapper();
                merchantSoftInfoWrapper.eq("merchant_id", benefitMch.getId());
                merchantSoftInfoWrapper.eq(BizConstant.RewardType.SOFTWARE.value().equals(softwareTypeStrategy.getRewardType()), "soft_type_id", softwareTypeStrategy.getSoftwareTypeId());
                //查询是否已经存在对应的机构特权信息，存在-修改对应的特权信息数量，不存在-新增
                MerchantSoftInfoModel oldMerchantSoftInfo = merchantSoftInfoMapper.selectOne(merchantSoftInfoWrapper);
                if (null == oldMerchantSoftInfo) {
                    MerchantSoftInfoModel insertMerchantSoftInfo = new MerchantSoftInfoModel();
                    insertMerchantSoftInfo.setAvailableOpenNum(softwareTypeStrategy.getRewardValue().intValue());
                    insertMerchantSoftInfo.setSoftTypeId(BizConstant.RewardType.SOFTWARE.value().equals(softwareTypeStrategy.getRewardType()) ? softwareTypeStrategy.getRewardSoftwareTypeId() : null);
                    insertMerchantSoftInfo.setMerchantId(benefitMch.getId());
                    merchantSoftInfoMapper.insert(insertMerchantSoftInfo);
                } else {
                    MerchantSoftInfoModel update = new MerchantSoftInfoModel();
                    update.setId(oldMerchantSoftInfo.getId());
                    update.setAvailableOpenNum(oldMerchantSoftInfo.getAvailableOpenNum() + softwareTypeStrategy.getRewardValue().intValue());
                    merchantSoftInfoMapper.updateById(update);
                }
                //添加机构奖励（机构/软件）明细
                MerchantSoftHistoryLogModel log = new MerchantSoftHistoryLogModel();
                log.setMerchantId(benefitMch.getId());
                log.setMerchantName(benefitMch.getName());
                log.setNum(softwareTypeStrategy.getRewardValue().intValue());
                log.setPreNum(null == oldMerchantSoftInfo ? 0 : oldMerchantSoftInfo.getAvailableOpenNum());
                log.setPostNum(softwareTypeStrategy.getRewardValue().intValue());
                log.setSoftTypeId(rewardSoftwareType.getId());
                log.setSoftTypeName(rewardSoftwareType.getTypeName());
                log.setBusinessType(BizConstant.OpenBusType.RECOMMEND_REWARD.value());
                merchantSoftHistoryLogMapper.insert(log);
                //首次年费
            } else if (BizConstant.RewardType.FIRST_TIME.value().equals(softwareTypeStrategy.getRewardType())) {
                //修改机构账户未发放奖金的值
                QueryWrapper<MerchantAccountInfoModel> accountWrapper = new QueryWrapper<>();
                accountWrapper.eq("merchant_id", benefitMch.getId());
                MerchantAccountInfoModel account = merchantAccountInfoMapper.selectOne(accountWrapper);
                //生成首次开通年费奖励明细
                BizSoftwareTypeModel softType = bizSoftwareTypeMapper.selectById(fromMch.getSoftTypeId());
                //计算奖励年费
                BigDecimal annualFee = softType.getOpeningCost().multiply(softwareTypeStrategy.getRewardValue().divide(percent));
                MerchantAccountHistoryLogModel accountHistoryLog = new MerchantAccountHistoryLogModel();
                accountHistoryLog.setMerchantId(benefitMch.getId());
                accountHistoryLog.setTypeFlag(MerchantConstant.TypeFlag.RECOMMEND.value());
                //被推荐机构ID
                accountHistoryLog.setPreAmount(account.getAvailableBalance());
                accountHistoryLog.setAmount(annualFee);
                accountHistoryLog.setPostAmount(account.getAvailableBalance().add(annualFee));
                merchantAccountHistoryLogMapper.insert(accountHistoryLog);

                //更新账户表
                MerchantAccountInfoModel update = new MerchantAccountInfoModel();
                UpdateWrapper<MerchantAccountInfoModel> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", account.getId());
                updateWrapper.eq("available_balance", account.getAvailableBalance());
                update.setAvailableBalance(account.getAvailableBalance().add(annualFee));
                boolean updateResult = optimisticLockingUpdateMchAccount(update, updateWrapper, 1);
                AssertUtil.isTrue(updateResult, "商户账户推荐奖励更新失败，请重试");
                SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(benefitMch.getParentId());
                recursiveFirstOpenReward(parentOrg, fromMch, softType.getOpeningCost(), softwareTypeStrategy.getRewardValue());
            }
        }
    }*/

    private void recycleOpenMchReward(List<BizOrgTypeStrategyModel> orgTypeStrategyList, SysOrgModel benefitOrg, MerchantInfoModel fromMch){
        for (BizOrgTypeStrategyModel orgTypeStrategy : orgTypeStrategyList) {
            //奖励机构/软件
            if (BizConstant.RewardType.ORG.value().equals(orgTypeStrategy.getRewardType()) ||
                    BizConstant.RewardType.SOFTWARE.value().equals(orgTypeStrategy.getRewardType())) {
                QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper();
                orgPrivilegeWrapper.eq("org_id", benefitOrg.getId());
                orgPrivilegeWrapper.eq(BizConstant.RewardType.SOFTWARE.value().equals(orgTypeStrategy.getRewardType()), "reward_software_type_id", orgTypeStrategy.getRewardSoftwareTypeId());
                BizOrgPrivilegeModel oldPrivilege = bizOrgPrivilegeMapper.selectOne(orgPrivilegeWrapper);
                AssertUtil.notNull(oldPrivilege, "参数错误");
                AssertUtil.isTrue(oldPrivilege.getRewardValue().compareTo(orgTypeStrategy.getRewardValue()) >= 0, "该申请信息不是初始状态，无法撤消");
                BizOrgPrivilegeModel update = new BizOrgPrivilegeModel();
                update.setId(oldPrivilege.getId());
                update.setRewardValue(orgTypeStrategy.getRewardValue().subtract(oldPrivilege.getRewardValue()));
                bizOrgPrivilegeMapper.updateById(update);
                //添加推荐机构奖励（机构/软件）明细
                BizOpenDetailModel openDetail = new BizOpenDetailModel();
                openDetail.setOrgId(benefitOrg.getId());
                openDetail.setBusType(BizConstant.OpenBusType.UNDO_APPLY.value());
                openDetail.setFromOrgId(fromMch.getId());
                openDetail.setOpenType(orgTypeStrategy.getRewardType());
                openDetail.setTradeStart(oldPrivilege.getRewardValue().intValue());
                openDetail.setTradeEnd(oldPrivilege.getRewardValue().subtract(orgTypeStrategy.getRewardValue()).intValue());
                openDetail.setTradeNum(orgTypeStrategy.getRewardValue().intValue());
                openDetail.setTradeNo(noGenerator.getRewardTradeNo());
                openDetail.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
                openDetail.setTypeId(BizConstant.ObjectType.ORG.value().equals(orgTypeStrategy.getRewardType()) ? orgTypeStrategy.getRewardOrgTypeId() : orgTypeStrategy.getRewardSoftwareTypeId());
                bizOpenDetailMapper.insert(openDetail);
                //奖励现金/首次年费
            } else if (BizConstant.RewardType.CASH.value().equals(orgTypeStrategy.getRewardType()) ||
                    BizConstant.RewardType.FIRST_TIME.value().equals(orgTypeStrategy.getRewardType())) {
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
                    AssertUtil.isTrue(account.getBonus().compareTo(orgTypeStrategy.getRewardValue()) >= 0, "该申请信息不是初始状态，无法撤消");
                    //生成现金奖励流水
                    BizBonusDetailModel bonusDetail = new BizBonusDetailModel();
                    bonusDetail.setTradeNo(noGenerator.getRewardTradeNo());
                    bonusDetail.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
                    bonusDetail.setBusType(BizConstant.BonusBusType.UNDO_APPLY.value());
                    bonusDetail.setTradeAmount(orgTypeStrategy.getRewardValue());
                    bonusDetail.setTradeStart(account.getBonus());
                    bonusDetail.setTradeEnd(account.getBonus().subtract(orgTypeStrategy.getRewardValue()));
                    bonusDetail.setOrgId(benefitOrg.getId());
                    //被推荐机构ID
                    bonusDetail.setFromOrgId(fromMch.getId());
                    bizBonusDetailMapper.insert(bonusDetail);
                    //修改奖金余额
                    updateWrapper.eq("bonus", account.getBonus());
                    updateWrapper.eq("total_bonus", account.getTotalBonus());
                    update.setBonus(account.getBonus().subtract(orgTypeStrategy.getRewardValue()));
                    update.setTotalBonus(account.getTotalBonus().subtract(orgTypeStrategy.getRewardValue()));
                } else if (BizConstant.RewardType.FIRST_TIME.value().equals(orgTypeStrategy.getRewardType())) {
                    //生成首次开通年费奖励明细
                    BizSoftwareTypeModel softType = bizSoftwareTypeMapper.selectById(fromMch.getSoftTypeId());
                    //计算奖励年费
                    BigDecimal annualFee = softType.getOpeningCost().multiply(orgTypeStrategy.getRewardValue().divide(percent));
                    AssertUtil.isTrue(account.getAvailableBalance().compareTo(annualFee) >= 0, "该申请信息不是初始状态，无法撤消");
                    BizAccountDetailModel accountDetail = new BizAccountDetailModel();
                    accountDetail.setBusType(BizConstant.AccountBusType.UNDO_APPLY.value());
                    accountDetail.setOrgId(benefitOrg.getId());
                    //被推荐机构ID
                    accountDetail.setFromOrgId(fromMch.getId());
                    accountDetail.setTradeAmount(annualFee);
                    accountDetail.setTradeStart(account.getAvailableBalance());
                    accountDetail.setTradeEnd(account.getAvailableBalance().subtract(annualFee));
                    accountDetail.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
                    accountDetail.setTradeNo(noGenerator.getRewardTradeNo());
                    bizAccountDetailMapper.insert(accountDetail);
                    updateWrapper.eq("available_balance", account.getAvailableBalance());
                    update.setAvailableBalance(account.getAvailableBalance().subtract(annualFee));
                }
                //更新账户表
                boolean updateResult = optimisticLockingUpdateOrgAccount(update, updateWrapper, 1);
                AssertUtil.isTrue(updateResult, "机构账户推荐奖励更新失败，请重试");
            }
        }
    }

    /*private void recycleRecommendMchReward(List<BizSoftwareTypeStrategyModel> softTypeStrategyList, MerchantInfoModel benefitMch, MerchantInfoModel fromMch){
        for (BizSoftwareTypeStrategyModel softwareTypeStrategy : softTypeStrategyList) {
            //奖励机构/软件
            if (BizConstant.RewardType.ORG.value().equals(softwareTypeStrategy.getRewardType()) ||
                    BizConstant.RewardType.SOFTWARE.value().equals(softwareTypeStrategy.getRewardType())) {
                BizSoftwareTypeModel rewardSoftwareType = bizSoftwareTypeMapper.selectById(softwareTypeStrategy.getRewardSoftwareTypeId());
                AssertUtil.notNull(rewardSoftwareType, "参数错误");
                QueryWrapper<MerchantSoftInfoModel> merchantSoftInfoWrapper = new QueryWrapper();
                merchantSoftInfoWrapper.eq("merchant_id", benefitMch.getId());
                merchantSoftInfoWrapper.eq(BizConstant.RewardType.SOFTWARE.value().equals(softwareTypeStrategy.getRewardType()), "soft_type_id", softwareTypeStrategy.getSoftwareTypeId());
                //查询是否已经存在对应的机构特权信息，存在-修改对应的特权信息数量，不存在-新增
                MerchantSoftInfoModel oldMerchantSoftInfo = merchantSoftInfoMapper.selectOne(merchantSoftInfoWrapper);
                AssertUtil.notNull(oldMerchantSoftInfo, "参数错误");
                AssertUtil.isTrue(oldMerchantSoftInfo.getAvailableOpenNum() >= softwareTypeStrategy.getRewardValue().intValue(), "该申请信息不是初始状态，无法撤消");
                MerchantSoftInfoModel update = new MerchantSoftInfoModel();
                update.setId(oldMerchantSoftInfo.getId());
                update.setAvailableOpenNum(oldMerchantSoftInfo.getAvailableOpenNum() - softwareTypeStrategy.getRewardValue().intValue());
                merchantSoftInfoMapper.updateById(update);
                //添加机构奖励（机构/软件）明细
                MerchantSoftHistoryLogModel log = new MerchantSoftHistoryLogModel();
                log.setMerchantId(benefitMch.getId());
                log.setMerchantName(benefitMch.getName());
                log.setNum(softwareTypeStrategy.getRewardValue().intValue());
                log.setPreNum(oldMerchantSoftInfo.getAvailableOpenNum());
                log.setPostNum(softwareTypeStrategy.getRewardValue().intValue());
                log.setSoftTypeId(rewardSoftwareType.getId());
                log.setSoftTypeName(rewardSoftwareType.getTypeName());
                log.setBusinessType(BizConstant.OpenBusType.UNDO_APPLY.value());
                merchantSoftHistoryLogMapper.insert(log);
                //首次年费
            } else if (BizConstant.RewardType.FIRST_TIME.value().equals(softwareTypeStrategy.getRewardType())) {
                //修改机构账户未发放奖金的值
                QueryWrapper<MerchantAccountInfoModel> accountWrapper = new QueryWrapper<>();
                accountWrapper.eq("merchant_id", benefitMch.getId());
                MerchantAccountInfoModel account = merchantAccountInfoMapper.selectOne(accountWrapper);
                //生成首次开通年费奖励明细
                BizSoftwareTypeModel softType = bizSoftwareTypeMapper.selectById(fromMch.getSoftTypeId());
                //计算奖励年费
                BigDecimal annualFee = softType.getOpeningCost().multiply(softwareTypeStrategy.getRewardValue().divide(percent));
                MerchantAccountHistoryLogModel accountHistoryLog = new MerchantAccountHistoryLogModel();
                accountHistoryLog.setMerchantId(benefitMch.getId());
                accountHistoryLog.setTypeFlag(MerchantConstant.TypeFlag.UNDO_APPLY.value());
                //被推荐机构ID
                accountHistoryLog.setPreAmount(account.getAvailableBalance());
                accountHistoryLog.setAmount(annualFee);
                accountHistoryLog.setPostAmount(account.getAvailableBalance().subtract(annualFee));
                merchantAccountHistoryLogMapper.insert(accountHistoryLog);

                //更新账户表
                MerchantAccountInfoModel update = new MerchantAccountInfoModel();
                UpdateWrapper<MerchantAccountInfoModel> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", account.getId());
                updateWrapper.eq("available_balance", account.getAvailableBalance());
                update.setAvailableBalance(account.getAvailableBalance().subtract(annualFee));
                boolean updateResult = optimisticLockingUpdateMchAccount(update, updateWrapper, 1);
                AssertUtil.isTrue(updateResult, "商户账户推荐奖励更新失败，请重试");
            }
        }
    }*/

    private String addApplyInfoFile(String base64Code) {
        if (!StringUtils.isBlank(base64Code)) {
            String suffix = base64Code.split(";")[0];
            String imagePattern = "data:image/(jpg|png|jpeg)";
            if (!Pattern.matches(imagePattern, suffix)) {
                throw new DubboException("素材格式错误");
            }
            String imageUrl = AliyunStorageUtil.uploadBase64Img("diandian-business", base64Code);
            return imageUrl;
        }
        return null;
    }

    private void checkParentAndRecommend(MerchantOpenApplyDTO dto, MerchantInfoModel merchantInfo) {
        //判断推荐机构ID是否为空
        if (null != dto.getRecommendId() && dto.getRecommendId() > 0) {
            if (BizConstant.ObjectType.ORG.value().equals(dto.getRecommendTypeFlag())) {
                SysOrgModel recommendOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getRecommendId());
                AssertUtil.notNull(recommendOrg, "推荐机构信息不存在");
                AssertUtil.isTrue(recommendOrg.getTreeStr().indexOf(dto.getParentId().toString()) > -1 || recommendOrg.getId().equals(dto.getParentId()), "请选择上级机构下的子机构");
                merchantInfo.setRecommendTypeFlag(BizConstant.ObjectType.ORG.value());
            } else if (BizConstant.ObjectType.SOFTWARE.value().equals(dto.getRecommendTypeFlag())) {
                MerchantInfoModel recommendMerchant = merchantInfoMapper.selectById(dto.getRecommendId());
                AssertUtil.notNull(recommendMerchant, "推荐商户信息不存在");
                AssertUtil.isTrue(recommendMerchant.getTreeStr().indexOf(dto.getParentId().toString()) > -1, "请选择上级机构下的子商户");
                merchantInfo.setRecommendTypeFlag(BizConstant.ObjectType.SOFTWARE.value());
            }
            merchantInfo.setRecommendId(dto.getRecommendId());
        }
        if (BizConstant.ObjectType.ORG.value().equals(dto.getParentTypeFlag())) {
            SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getParentId());
            AssertUtil.notNull(parentOrg, "上级对象信息不存在");
            //merchantInfo.setParentTypeFlag(BizConstant.ObjectType.ORG.value());
            merchantInfo.setTreeStr(StringUtils.isNotBlank(parentOrg.getTreeStr()) ? parentOrg.getTreeStr() + "," + parentOrg.getId() : "" + parentOrg.getId());
        } else if (BizConstant.ObjectType.SOFTWARE.value().equals(dto.getParentTypeFlag())) {
            MerchantInfoModel parentMerchant = merchantInfoMapper.selectById(dto.getParentId());
            AssertUtil.notNull(parentMerchant, "上级对象信息不存在");
            //merchantInfo.setParentTypeFlag(BizConstant.ObjectType.SOFTWARE.value());
            merchantInfo.setTreeStr(StringUtils.isNotBlank(parentMerchant.getTreeStr()) ? parentMerchant.getTreeStr() + "," + parentMerchant.getId() : "" + parentMerchant.getId());
        }
        merchantInfo.setParentId(dto.getParentId());
    }

    private void addImageInfo(MerchantOpenApplyDTO dto, MerchantInfoModel merchantInfo) {
        if (!StringUtils.isBlank(dto.getIdcardPositivePicBase64())) {
            String url = addApplyInfoFile(dto.getIdcardPositivePicBase64());
            //merchantInfo.setIdcardPositivePic(url);
        }
        if (!StringUtils.isBlank(dto.getIdcardReversePicBase64())) {
            String url = addApplyInfoFile(dto.getIdcardReversePicBase64());
            //merchantInfo.setIdcardReversePic(url);
        }
        if (!StringUtils.isBlank(dto.getBankCardPicBase64())) {
            String url = addApplyInfoFile(dto.getBankCardPicBase64());
            //merchantInfo.setBankCardPic(url);
        }
        if (!StringUtils.isBlank(dto.getBusinessLicensePicBase64())) {
            String url = addApplyInfoFile(dto.getBusinessLicensePicBase64());
            //merchantInfo.setBusinessLicensePic(url);
        }
        if (!StringUtils.isBlank(dto.getPowerAttorneyPicBase64())) {
            String url = addApplyInfoFile(dto.getPowerAttorneyPicBase64());
            //merchantInfo.setPowerAttorneyPic(url);
        }
    }

    private void addPaymentInfo(MerchantOpenApplyDTO dto){
        if (!StringUtils.isBlank(dto.getPaymentPicBase64())) {
            String url = addApplyInfoFile(dto.getPaymentPicBase64());
            QueryWrapper<BizPaymentPicModel> paymentPicWrapper = new QueryWrapper<>();
            paymentPicWrapper.eq("apply_id", dto.getId());
            BizPaymentPicModel oldPaymentPic = bizPaymentPicMapper.selectOne(paymentPicWrapper);
            if(null == oldPaymentPic){
                oldPaymentPic = new BizPaymentPicModel();
                oldPaymentPic.setApplyId(dto.getId());
                oldPaymentPic.setPayType(2);
                oldPaymentPic.setPic(url);
                bizPaymentPicMapper.insert(oldPaymentPic);
            }else {
                BizPaymentPicModel updatePayment = new BizPaymentPicModel();
                updatePayment.setId(oldPaymentPic.getId());
                updatePayment.setPic(url);
                bizPaymentPicMapper.updateById(updatePayment);
            }
        }
    }

    private void addOpenLog(MerchantOpenApplyLogModel dto, boolean isAdd) {
        //免费开通
        if (BizConstant.ApplyType.OPEN.value().equals(dto.getApplyType())) {
            if (BizConstant.ObjectType.SOFTWARE.value().equals(dto.getRecommendType())) {
                AssertUtil.isTrue(null != dto.getRecommendId() && dto.getRecommendId() > 0, "参数错误");
                MerchantInfoModel oldMchInfo = merchantInfoMapper.selectById(dto.getRecommendId());
                AssertUtil.notNull(oldMchInfo, "商户信息不存在");
                BizSoftwareTypeModel oldSoftwareType = bizSoftwareTypeMapper.selectById(dto.getSoftTypeId());
                AssertUtil.notNull(oldSoftwareType, "软件类型信息不存在");
                QueryWrapper<MerchantSoftInfoModel> softInfoWrapper = new QueryWrapper<>();
                softInfoWrapper.eq("merchant_id", dto.getRecommendId());
                softInfoWrapper.eq("soft_type_id", dto.getSoftTypeId());
                MerchantSoftInfoModel oldMchSoftInfo = merchantSoftInfoMapper.selectOne(softInfoWrapper);
                MerchantSoftInfoModel update = new MerchantSoftInfoModel();
                update.setId(oldMchSoftInfo.getId());
                BizConstant.OpenBusType busType = null;
                if(isAdd){
                    update.setAvailableOpenNum(oldMchSoftInfo.getAvailableOpenNum() + 1);
                    update.setOpenNum(oldMchSoftInfo.getOpenNum() - 1);
                    busType = BizConstant.OpenBusType.AUDIT_FAILED;
                } else {
                    update.setAvailableOpenNum(oldMchSoftInfo.getAvailableOpenNum() - 1);
                    update.setOpenNum(oldMchSoftInfo.getOpenNum() + 1);
                    busType = BizConstant.OpenBusType.OPEN_SUCCESS;
                }
                merchantSoftInfoMapper.updateById(update);
                MerchantSoftHistoryLogModel historyLog = new MerchantSoftHistoryLogModel();
                historyLog.setSoftTypeId(dto.getSoftTypeId());
                historyLog.setSoftTypeName(oldSoftwareType.getTypeName());
                historyLog.setPreNum(oldMchSoftInfo.getAvailableOpenNum());
                historyLog.setPostNum(update.getAvailableOpenNum());
                historyLog.setNum(1);
                historyLog.setBusinessType(busType.value());
                historyLog.setMerchantName(oldMchInfo.getName());
                historyLog.setMerchantId(dto.getRecommendId());
                merchantSoftHistoryLogMapper.insert(historyLog);
            } else {
                Long orgId = null;
                if (BizConstant.ObjectType.ORG.value().equals(dto.getRecommendType())) {
                    orgId = dto.getRecommendId();
                } else if (null != dto.getParentId() && dto.getParentId() != 0) {
                    orgId = dto.getParentId();
                }
                AssertUtil.isTrue(null != orgId && orgId > 0, "参数错误");
                QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper<>();
                orgPrivilegeWrapper.eq("org_id", orgId);
                orgPrivilegeWrapper.eq("reward_software_type_id", dto.getSoftTypeId());
                BizOrgPrivilegeModel orgPrivilege = bizOrgPrivilegeMapper.selectOne(orgPrivilegeWrapper);
                //更新可开通名额
                BizOrgPrivilegeModel updateOrgPrivilege = new BizOrgPrivilegeModel();
                UpdateWrapper<BizOrgPrivilegeModel> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", orgPrivilege.getId());
                updateWrapper.eq("reward_value", orgPrivilege.getRewardValue());
                BigDecimal tradeEnd = BigDecimal.ZERO;
                BizConstant.OpenBusType busType = null;
                if (isAdd) {
                    tradeEnd = orgPrivilege.getRewardValue().add(BigDecimal.ONE);
                    busType = BizConstant.OpenBusType.AUDIT_FAILED;
                } else {
                    tradeEnd = orgPrivilege.getRewardValue().subtract(BigDecimal.ONE);
                    busType = BizConstant.OpenBusType.OPEN_SUCCESS;
                }
                updateOrgPrivilege.setRewardValue(tradeEnd);
                boolean updateResult = optimisticLockingUpdateOrgPrivilege(updateOrgPrivilege, updateWrapper, 1);
                AssertUtil.isTrue(updateResult, "机构权益更新失败，请重试");
                //添加名称开通记录
                BizOpenDetailModel openDetail = new BizOpenDetailModel();
                openDetail.setOrgId(orgId);
                openDetail.setBusType(busType.value());
                openDetail.setOpenType(BizConstant.ObjectType.SOFTWARE.value());
                openDetail.setTradeStart(orgPrivilege.getRewardValue().intValue());
                openDetail.setTradeEnd(tradeEnd.intValue());
                openDetail.setTradeNum(BigDecimal.ONE.intValue());
                openDetail.setTradeNo(noGenerator.getRewardTradeNo());
                openDetail.setTradeType(isAdd ? BizConstant.TradeType.INCOME.value() : BizConstant.TradeType.EXPENDITURE.value());
                openDetail.setTypeId(dto.getSoftTypeId());
                bizOpenDetailMapper.insert(openDetail);
            }
        }
    }

    /**
     * 功能描述: 乐观锁更新机构权益信息
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/11 21:38
     */
    private boolean optimisticLockingUpdateOrgPrivilege(BizOrgPrivilegeModel update, UpdateWrapper<BizOrgPrivilegeModel> wrapper, int updateTime) {
        if (updateTime <= 5) {
            if (bizOrgPrivilegeMapper.update(update, wrapper) < 1) {
                optimisticLockingUpdateOrgPrivilege(update, wrapper, ++updateTime);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能描述: 乐观锁更新机构账户
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/11 21:38
     */
    private boolean optimisticLockingUpdateOrgAccount(BizAccountModel update, UpdateWrapper<BizAccountModel> wrapper, int updateTime) {
        if (updateTime <= 5) {
            if (bizAccountMapper.update(update, wrapper) < 1) {
                optimisticLockingUpdateOrgAccount(update, wrapper, ++updateTime);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能描述: 乐观锁更新商户账户
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/11 21:39
     */
    private boolean optimisticLockingUpdateMchAccount(MerchantAccountInfoModel update, UpdateWrapper<MerchantAccountInfoModel> wrapper, int updateTime) {
        if (updateTime <= 5) {
            if (merchantAccountInfoMapper.update(update, wrapper) < 1) {
                optimisticLockingUpdateMchAccount(update, wrapper, ++updateTime);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * 功能描述: 递归开通奖励年费
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/4/29 17:37
     */
    private void recursiveFirstOpenReward(SysOrgModel benefitOrg, MerchantInfoModel fromMch, BigDecimal openFee, BigDecimal childRate){
        QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
        orgTypeStrategyWrapper.eq("org_type_id", benefitOrg.getOrgTypeId());
        orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.OPENING_BONUSES.value());
        orgTypeStrategyWrapper.eq("recommend_software_type_id", fromMch.getSoftTypeId());
        orgTypeStrategyWrapper.eq("reward_type", BizConstant.RewardType.FIRST_TIME.value());
        orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
        //计算奖励年费
        BizOrgTypeStrategyModel orgTypeStrategy = bizOrgTypeStrategyMapper.selectOne(orgTypeStrategyWrapper);
        if(null != orgTypeStrategy){
            QueryWrapper<BizAccountModel> accountWrapper = new QueryWrapper<>();
            accountWrapper.eq("org_id", benefitOrg.getId());
            BizAccountModel account = bizAccountMapper.selectOne(accountWrapper);
            AssertUtil.notNull(account, "账户信息不存在");

            BigDecimal annualFee = openFee.multiply(orgTypeStrategy.getRewardValue().subtract(childRate).divide(percent));
            BizAccountDetailModel accountDetail = new BizAccountDetailModel();
            accountDetail.setBusType(0);
            accountDetail.setOrgId(benefitOrg.getId());
            //被推荐机构ID
            accountDetail.setFromOrgId(fromMch.getId());
            accountDetail.setTradeAmount(annualFee);
            accountDetail.setTradeStart(account.getAvailableBalance());
            accountDetail.setTradeEnd(account.getAvailableBalance().add(annualFee));
            accountDetail.setTradeType(BizConstant.TradeType.INCOME.value());
            accountDetail.setTradeNo(noGenerator.getRewardTradeNo());
            bizAccountDetailMapper.insert(accountDetail);

            BizAccountModel update = new BizAccountModel();
            update.setId(account.getId());
            UpdateWrapper<BizAccountModel> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", account.getId());
            updateWrapper.eq("available_balance", account.getAvailableBalance());
            update.setAvailableBalance(account.getAvailableBalance().add(annualFee));
            //更新账户表
            boolean updateResult = optimisticLockingUpdateOrgAccount(update, updateWrapper, 1);
            AssertUtil.isTrue(updateResult, "机构账户推荐奖励更新失败，请重试");
            if (null != benefitOrg.getParentId() && benefitOrg.getParentId() > 0){
                SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(benefitOrg.getParentId());
                recursiveFirstOpenReward(parentOrg, fromMch, openFee, orgTypeStrategy.getRewardValue());
            }
        }
    }
}
