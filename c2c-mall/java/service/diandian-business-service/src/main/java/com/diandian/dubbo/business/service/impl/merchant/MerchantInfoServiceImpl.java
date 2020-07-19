package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizPaymentPicMapper;
import com.diandian.dubbo.business.mapper.BizSoftwareTypeMapper;
import com.diandian.dubbo.business.mapper.SysOrgMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantSoftInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.common.util.TreeUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.member.MemberExchangeHistoryLogDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoFormDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;
import com.diandian.dubbo.facade.model.biz.BizPaymentPicModel;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.model.member.MemberExchangeHistoryLogModel;
import com.diandian.dubbo.facade.model.member.MemberMerchantRelationModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.*;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.service.member.MemberExchangeHistoryLogService;
import com.diandian.dubbo.facade.service.member.MemberMerchantRelationService;
import com.diandian.dubbo.facade.service.merchant.*;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import com.diandian.dubbo.facade.vo.member.MemberExchangeHistoryLogVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantCountMemberVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoDetailVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoListVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantTeamAppVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 商户信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
@Service("merchantInfoService")
@Slf4j
public class MerchantInfoServiceImpl implements MerchantInfoService {

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;

    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;

    @Autowired
    private MemberMerchantRelationService memberMerchantRelationService;

    @Autowired
    private MemberExchangeHistoryLogService memberExchangeHistoryLogService;

    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Autowired
    private UserConfigurationService userConfigurationService;


    @Override
    public MerchantInfoModel login(String phone, String password) {
        MerchantInfoModel merchantInfoModel=
                merchantInfoMapper.selectOne(new QueryWrapper<MerchantInfoModel>()
                        .eq("phone",phone)
                        .eq("login_password",password)
                );
        return merchantInfoModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String phone, String password, String invitationCode) {
        MerchantInfoModel mer=
                merchantInfoMapper.selectOne(new QueryWrapper<MerchantInfoModel>().eq("phone",phone));
        AssertUtil.isNull(mer, "用户名己存在");
        //手机号码不存在则注册
        if (mer == null) {
            mer = MerchantInfoModel.builder().phone(phone).approveFlag(2).softTypeId(1104994625655316481L).loginName(phone).
                    loginPassword(password).
                    softTypeName("标准版").build();
            if(StrUtil.isNotBlank(invitationCode)){
                MerchantInfoModel  merInfo=this.getOneByInvitationCode(invitationCode);
                AssertUtil.notNull(merInfo,"邀请人信息不存在");
                mer.setParentId(merInfo.getId());
            }
            String iniCode=this.generateInviteCode(0);
            AssertUtil.notNull(iniCode,"邀请码生成失败");
            mer.setCode(iniCode);
            //开通新商户,初始化商户信息帐户,商户资金帐户及软件帐户
            //信息帐户
            merchantInfoMapper.insert(mer);
            Long idNew = mer.getId();//新商户ID
            //商户资金帐户
            MerchantAccountInfoModel mAccNew = new MerchantAccountInfoModel();
            mAccNew.setMerchantId(idNew);
            mAccNew.setAvailableBalance(BigDecimal.ZERO);
            mAccNew.setFreezeBalance(BigDecimal.ZERO);
            merchantAccountInfoService.saveAcc(mAccNew);
        }
    }

    @Override
    public MerchantInfoModel getOneByPhone(String phone) {
        return merchantInfoMapper.selectOne(new QueryWrapper<MerchantInfoModel>().eq("phone",phone));
    }

    @Override
    public MerchantInfoModel getOneByInvitationCode(String invitationCode) {
        return merchantInfoMapper.selectOne(new QueryWrapper<MerchantInfoModel>().eq("code",invitationCode));
    }

    @Override
    public MerchantInfoModel getMerchantInfoById(Long merchantId) {
        return merchantInfoMapper.selectById(merchantId);
    }

    @Override
    public List<IntactTreeVO> getIntactTreeMerchantPart(List<IntactTreeVO> list, Long parentId) {
        if (null != list && list.size() > 0) {
            for (IntactTreeVO vo : list) {
                Map<String, Object> params = new HashMap<>();
                params.put("parentId", vo.getId());
                List<IntactTreeVO> merchantPartList = merchantInfoMapper.getIntactTreeMerchantPart(params);
                if (merchantPartList.isEmpty()) {
                    continue;
                }
                getMerchantIntactTree(list, merchantPartList, true);
            }
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("parentId", parentId);
            list = merchantInfoMapper.getIntactTreeMerchantPart(params);
        }
        return list;
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        MerchantInfoModel merchantInfo = (MerchantInfoModel) params.get("merchantInfo");
//        String loginName = merchantInfo.getLoginName();
        String merchantName = (String) params.get("merchantName");
        String merchantPhone = (String) params.get("merchantPhone");
        String disableFlag = (String) params.get("disableFlag");
        String softTypeId = (String) params.get("softTypeId");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");


        Page<MerchantInfoModel> page = new PageWrapper<MerchantInfoModel>(params).getPage();

        QueryWrapper<MerchantInfoModel> qw = new QueryWrapper<>();
        qw.eq("recommend_id", merchantInfo.getId());
        qw.eq("del_flag", BizConstant.STATE_NORMAL);
        if (StrUtil.isNotBlank(disableFlag)) {
            qw.eq("disabled_flag", Integer.parseInt(disableFlag));
        }
        if (StrUtil.isNotBlank(softTypeId)) {
            qw.eq("soft_type_id", Long.parseLong(softTypeId));
        }

        if (StrUtil.isNotBlank(startTime)) {
            qw.gt("create_time", DateUtil.beginOfDay(DateUtil.parseDate(startTime)));
        }

        if (StrUtil.isNotBlank(endTime)) {
            qw.lt("create_time", DateUtil.endOfDay(DateUtil.parseDate(endTime)));
        }

        qw.like(StrUtil.isNotBlank(merchantName), "name", merchantName);
        qw.like(StrUtil.isNotBlank(merchantPhone), "phone", merchantPhone);
        qw.select("id,create_time,disabled_flag,name,code,login_name,leader,phone,soft_type_id,soft_type_name,merchant_expire_time,approve_flag");

        IPage<MerchantInfoModel> ipage = merchantInfoMapper.selectPage(page, qw);
        return new PageResult(ipage);
    }

    @Override
    public MerchantInfoModel getByUsername(String username) {
        return merchantInfoMapper.getByUsername(username);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void saveMerchant(MerchantInfoModel merchantInfo, MerchantInfoModel bizMerchantInfoModelNew) {
//        String loginNameNew = bizMerchantInfoModelNew.getLoginName();
//        MerchantInfoModel mInfo = getByUsername(loginNameNew);
//        AssertUtil.isNull(mInfo, "用户名己存在");
//
//        Long softTypeId = bizMerchantInfoModelNew.getSoftTypeId();
//        BizSoftwareTypeModel softTypeCur = bizSoftwareTypeService.getSoftTypeById(softTypeId);
//        AssertUtil.isTrue(ObjectUtil.isNotNull(softTypeCur), "开通软件未配置");
//
//        Integer openType = bizMerchantInfoModelNew.getOpenType();
//        if (openType == MerchantConstant.OpenType.FREE_OPEN.value()) {
//            Long mId = merchantInfo.getId();
//            MerchantSoftInfoModel softAccCur = merchantSoftInfoService.getSoftAcc(mId, softTypeId);
//            AssertUtil.notNull(softAccCur, "该商户未赠送");
//
//            Integer availableOpenNum = softAccCur.getAvailableOpenNum() == null ? 0 : softAccCur.getAvailableOpenNum();
//            Integer openNum = softAccCur.getOpenNum() == null ? 0 : softAccCur.getOpenNum();
//            AssertUtil.isTrue(availableOpenNum > 0, "赠送开通数量达到上限");
//
//            // 1.商户软件帐户变更
//            int postAvailableOpenNum = availableOpenNum - 1;
//            softAccCur.setAvailableOpenNum(postAvailableOpenNum);
//            softAccCur.setOpenNum(openNum + 1);
//            merchantSoftInfoService.updateSoftAccById(softAccCur);
//            // 2.帐户变更记录
//            MerchantSoftHistoryLogModel mAccHisCur = new MerchantSoftHistoryLogModel();
//            mAccHisCur.setMerchantId(mId);
//            mAccHisCur.setMerchantName(merchantInfo.getName());
//            mAccHisCur.setSoftTypeId(softTypeId);
//            mAccHisCur.setPreNum(availableOpenNum);
//            mAccHisCur.setNum(1);
//            mAccHisCur.setBusinessType(BizConstant.OpenBusType.OPEN_SUCCESS.value());
//            mAccHisCur.setPostNum(postAvailableOpenNum);
//            merchantSoftHistoryLogService.save(mAccHisCur);
//        }
//
//        // 3.开通新商户,初始化商户信息帐户,商户资金帐户及软件帐户
//        //商户基本信息帐户
//        bizMerchantInfoModelNew.setRecommendId(merchantInfo.getId());//推荐人
//        bizMerchantInfoModelNew.setRecommendTypeFlag(BizConstant.ObjectType.SOFTWARE.value());
//        bizMerchantInfoModelNew.setParentId(merchantInfo.getParentId());//上级
//        SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(merchantInfo.getParentId());
//        AssertUtil.notNull(parentOrg, "参数错误");
//        bizMerchantInfoModelNew.setTreeStr(StringUtils.isNotBlank(parentOrg.getTreeStr()) ? parentOrg.getTreeStr() + "," + parentOrg.getId() : "" + parentOrg.getId());
//        bizMerchantInfoModelNew.setParentTypeFlag(merchantInfo.getParentTypeFlag());
//        //  商户编号
//        String code = noGenerator.getMerchantNo();
//        bizMerchantInfoModelNew.setCode(code);
//        bizMerchantInfoModelNew.setMallName(bizMerchantInfoModelNew.getName());//2019-4-2 wubc
//
//        //sha256加密
//        String salt = RandomStringUtils.randomAlphanumeric(20);
//        String password = new Sha256Hash(bizMerchantInfoModelNew.getLoginPassword(), salt).toHex();
//        bizMerchantInfoModelNew.setLoginPassword(password);
//        bizMerchantInfoModelNew.setWithdrawPassword(password);
//        bizMerchantInfoModelNew.setOptionPassword(password);
//        bizMerchantInfoModelNew.setSalt(salt);
//
//        bizMerchantInfoModelNew.setSoftTypeName(softTypeCur.getTypeName());
//
//        Integer openingTimeLength = softTypeCur.getOpeningTimeLength() == null ? 0 : softTypeCur.getOpeningTimeLength();
//        DateTime expireTime = DateUtil.offsetMonth(new Date(), openingTimeLength);//商户过期时间
//        bizMerchantInfoModelNew.setMerchantExpireTime(expireTime);
//
//        bizMerchantInfoModelNew.setApproveFlag(MerchantConstant.MerchantApproveState.UNAPPROVE.value());//认证状态
//        bizMerchantInfoModelNew.setMallOpenFlag(MerchantConstant.MallOpenFlag.CLOSE.value());//自定义商城开通状态
//
//        bizMerchantInfoModelNew.setDelFlag(BizConstant.STATE_NORMAL);
//        bizMerchantInfoModelNew.setDisabledFlag(BizConstant.STATE_NORMAL);
//        bizMerchantInfoModelNew.setMallOpenFlag(MerchantConstant.MallOpenFlag.CLOSE.value());
//        bizMerchantInfoModelNew.setCreateBy(merchantInfo.getId());
//
//        merchantInfoMapper.insert(bizMerchantInfoModelNew);
//        Long idNew = bizMerchantInfoModelNew.getId();//新商户ID
//        //商户资金帐户
//        MerchantAccountInfoModel mAccNew = new MerchantAccountInfoModel();
//        mAccNew.setMerchantId(idNew);
//        mAccNew.setShoppingCouponSum(BigDecimal.ZERO);
//        mAccNew.setExchangeCouponSum(0);
//        mAccNew.setAvailableBalance(BigDecimal.ZERO);
//        mAccNew.setFreezeBalance(BigDecimal.ZERO);
//        merchantAccountInfoService.save(mAccNew);
//
//        //商户钱包帐户
//        MerchantWalletInfoModel mWallet = new MerchantWalletInfoModel();
//        mWallet.setMerchantId(idNew);
//        mWallet.setSurplusExchangeNumber(0);
//        mWallet.setExchangeNumber(0);
//        mWallet.setAmountSum(BigDecimal.ZERO);
//        mWallet.setAmount(BigDecimal.ZERO);
//        merchantWalletInfoService.save(mWallet);
//
//        //商户软件帐户
//        List<BizSoftwareTypeModel> softList = bizSoftwareTypeService.listSoftType();
//        if (softList.size() > 0) {
//            for (int i = 0; i < softList.size(); i++) {
//                BizSoftwareTypeModel soft = softList.get(i);
//                Long softId = soft.getId();
//                MerchantSoftInfoModel softAccEx = merchantSoftInfoService.getSoftAcc(idNew, softId);
//                if (null == softAccEx) {
//                    MerchantSoftInfoModel mSoftAcc = new MerchantSoftInfoModel();
//                    mSoftAcc.setMerchantId(idNew);
//                    mSoftAcc.setSoftTypeId(softId);
//                    mSoftAcc.setOpenNum(0);
//                    mSoftAcc.setAvailableOpenNum(0);
//                    merchantSoftInfoService.saveSoftAcc(mSoftAcc);
//                }
//            }
//        }
//
//        //默认商家包邮
////        MerchantFreightSetModel merchantFreightSetModel = new MerchantFreightSetModel();
////        merchantFreightSetModel.setMerchantId(idNew);
////        merchantFreightSetModel.setFreightTemplate("商家包邮");
////        merchantFreightSetModel.setFreightStateFlag(BizConstant.STATE_NORMAL);
////        merchantFreightSetModel.setAssumeFlag(MerchantConstant.FreightSet.MERCHANT.value());
////        merchantFreightSetService.saveFreightSet(merchantFreightSetModel);
//
//    }

    @Override
    public boolean renew(Long merchantId, Long renewMechantId) {
        // 商户续费
        return true;
    }

    @Override
    public boolean updateById(MerchantInfoModel merchantInfoModel) {
        merchantInfoMapper.updateById(merchantInfoModel);
        return true;
    }

    @Override
    public boolean removeById(Long id) {
        merchantInfoMapper.deleteById(id);
        return true;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean insertMerchant(MerchantInfoFormDTO dto) {
//        AssertUtil.notBlank(dto.getLoginName(), "登录账号不能为空");
//        AssertUtil.notBlank(dto.getLoginPassword(), "登录密码不能为空");
//        AssertUtil.notBlank(dto.getLeader(), "联系人不能为空");
//        AssertUtil.notBlank(dto.getName(), "商户名称不能为空");
//        AssertUtil.notBlank(dto.getPhone(), "手机号码不能为空");
//        AssertUtil.isTrue(null != dto.getSoftTypeId(), "软件类型不能为空");
//        AssertUtil.isTrue(null != dto.getOpenType(), "开通类型不能为空");
//        AssertUtil.isTrue(dto.getOpenType() == 0 || dto.getOpenType() == 1, "参数错误");
//        BizSoftwareTypeModel softwareType = bizSoftwareTypeService.getSoftTypeById(dto.getSoftTypeId());
//        AssertUtil.notNull(softwareType, "软件类型信息不存在");
//        QueryWrapper<MerchantInfoModel> merchantInfoWrapper = new QueryWrapper<>();
//        merchantInfoWrapper.eq("login_name", dto.getLoginName());
//        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
//        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "登录账号已存在");
//        merchantInfoWrapper = new QueryWrapper<>();
//        merchantInfoWrapper.eq("name", dto.getName());
//        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
//        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "商户名称已存在");
//        //添加商户信息
//        MerchantInfoModel merchantInfo = new MerchantInfoModel();
//        BeanUtil.copyProperties(dto, merchantInfo);
//        merchantInfo.setCode(noGenerator.getMerchantNo());
//        merchantInfo.setSoftTypeName(softwareType.getTypeName());
//        String salt = RandomStringUtils.randomAlphanumeric(20);
//        merchantInfo.setSalt(salt);
//        merchantInfo.setLoginPassword(new Sha256Hash(dto.getLoginPassword(), salt).toHex());
//        merchantInfo.setSoftTypeName(softwareType.getTypeName());
//        merchantInfo.setMallName(dto.getName());
//        if (StringUtils.isNotBlank(dto.getMerchantExpireTime())) {
//            merchantInfo.setMerchantExpireTime(DateUtil.parseDateTime(dto.getMerchantExpireTime()));
//        }
//
//        //上级和推荐信息判断
//        checkParentAndRecommend(dto, merchantInfo);
//        //添加图片信息
//        addImageInfo(dto, merchantInfo);
//        if (StringUtils.isNotBlank(dto.getBusinessLicenseCode())) {
//            merchantInfoWrapper = new QueryWrapper<>();
//            merchantInfoWrapper.eq("business_license_code", merchantInfo.getBusinessLicenseCode());
//            merchantInfoWrapper.eq("soft_type_id", merchantInfo.getSoftTypeId());
//            merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
//            AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "该营业执照已被使用");
//        }
//        merchantInfoMapper.insert(merchantInfo);
//        if (BizConstant.ApplyType.APPLY.value().equals(merchantInfo.getOpenType())) {
//            if (MerchantConstant.MerchantApproveState.APPROVED.value().equals(merchantInfo.getApproveFlag())) {
//                //添加商户开通奖励
//                merchantOpenApplyLogService.addGiftReward(merchantInfo);
//            }
//        }
//        if (null == merchantInfo.getRecommendId() || null == merchantInfo.getRecommendTypeFlag()) {
//            //添加开通商户奖励（与前面不同，上面一个的受益对象是新增商户，这个的受益对象是上级商户）
//            if (BizConstant.ObjectType.ORG.value().equals(merchantInfo.getParentTypeFlag()) &&
//                    BizConstant.ApplyType.APPLY.value().equals(merchantInfo.getOpenType())) {
//                merchantOpenApplyLogService.addOpenMerchantReward(merchantInfo);
//            }
//        }
//        //添加推荐奖励
//        if (null != merchantInfo.getRecommendTypeFlag() && BizConstant.ApplyType.APPLY.value().equals(merchantInfo.getOpenType())) {
//            if (BizConstant.RecommendType.ORG.value().equals(merchantInfo.getRecommendTypeFlag())) {
//                merchantOpenApplyLogService.addOrgRecommendReward(merchantInfo);
//            } else if (BizConstant.RecommendType.SOFTWARE.value().equals(merchantInfo.getRecommendTypeFlag())) {
//                merchantOpenApplyLogService.addMerchantRecommendReward(merchantInfo);
//            } else {
//                throw new DubboException("参数错误");
//            }
//        }
//        //商户资金帐户
//        MerchantAccountInfoModel mAccNew = new MerchantAccountInfoModel();
//        mAccNew.setMerchantId(merchantInfo.getId());
//        mAccNew.setShoppingCouponSum(BigDecimal.ZERO);
//        mAccNew.setExchangeCouponSum(0);
//        mAccNew.setAvailableBalance(BigDecimal.ZERO);
//        mAccNew.setFreezeBalance(BigDecimal.ZERO);
//        merchantAccountInfoService.save(mAccNew);
//
//        //商户钱包帐户
//        MerchantWalletInfoModel mWallet = new MerchantWalletInfoModel();
//        mWallet.setMerchantId(merchantInfo.getId());
//        mWallet.setSurplusExchangeNumber(0);
//        mWallet.setExchangeNumber(0);
//        mWallet.setAmountSum(BigDecimal.ZERO);
//        mWallet.setAmount(BigDecimal.ZERO);
//        merchantWalletInfoService.save(mWallet);
//
//        //todo 添加运费设置
//        //商户软件帐户
//        List<BizSoftwareTypeModel> softList = bizSoftwareTypeService.listSoftType();
//        if (softList.size() > 0) {
//            for (int i = 0; i < softList.size(); i++) {
//                BizSoftwareTypeModel soft = softList.get(i);
//                Long softId = soft.getId();
//                MerchantSoftInfoModel softAccEx = merchantSoftInfoService.getSoftAcc(merchantInfo.getId(), softId);
//                if (null == softAccEx) {
//                    MerchantSoftInfoModel mSoftAcc = new MerchantSoftInfoModel();
//                    mSoftAcc.setMerchantId(merchantInfo.getId());
//                    mSoftAcc.setSoftTypeId(softId);
//                    mSoftAcc.setOpenNum(0);
//                    mSoftAcc.setAvailableOpenNum(0);
//                    merchantSoftInfoService.saveSoftAcc(mSoftAcc);
//                }
//            }
//        }
//
//        //默认商家包邮
////        MerchantFreightSetModel merchantFreightSetModel = new MerchantFreightSetModel();
////        merchantFreightSetModel.setMerchantId(merchantInfo.getId());
////        merchantFreightSetModel.setFreightTemplate("商家包邮");
////        merchantFreightSetModel.setFreightStateFlag(BizConstant.STATE_NORMAL);
////        merchantFreightSetModel.setAssumeFlag(MerchantConstant.FreightSet.MERCHANT.value());
////        merchantFreightSetService.saveFreightSet(merchantFreightSetModel);
//
//        return true;
//    }

//    @Override
//    public boolean updateMerchant(MerchantInfoFormDTO dto) {
//        AssertUtil.notBlank(dto.getLoginName(), "登录名不能为空");
//        AssertUtil.notBlank(dto.getLeader(), "联系人不能为空");
//        AssertUtil.notBlank(dto.getName(), "商户名称不能为空");
//        AssertUtil.notBlank(dto.getPhone(), "手机号码不能为空");
//        AssertUtil.isTrue(null != dto.getId() && dto.getId() > 0, "商户信息不存在");
//        MerchantInfoModel oldMerchantInfo = merchantInfoMapper.selectById(dto.getId());
//        AssertUtil.notNull(oldMerchantInfo, "商户信息不存在");
//        QueryWrapper<MerchantInfoModel> merchantInfoWrapper = new QueryWrapper<>();
//        merchantInfoWrapper.ne("id", oldMerchantInfo.getId());
//        merchantInfoWrapper.eq("login_name", dto.getLoginName());
//        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
//        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "登录账号已存在");
//        merchantInfoWrapper = new QueryWrapper<>();
//        merchantInfoWrapper.eq("name", dto.getName());
//        merchantInfoWrapper.ne("id", oldMerchantInfo.getId());
//        merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
//        AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "商户名称已存在");
//        if (StringUtils.isNotBlank(dto.getBusinessLicenseCode())) {
//            merchantInfoWrapper = new QueryWrapper<>();
//            merchantInfoWrapper.ne("id", oldMerchantInfo.getId());
//            merchantInfoWrapper.eq("soft_type_id", oldMerchantInfo.getSoftTypeId());
//            merchantInfoWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
//            merchantInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
//            AssertUtil.isTrue(merchantInfoMapper.selectCount(merchantInfoWrapper) == 0, "该营业执照已被使用");
//        }
//        //修改商户信息
//        MerchantInfoModel update = new MerchantInfoModel();
//        update.setId(oldMerchantInfo.getId());
//        update.setDisabledFlag(dto.getDisabledFlag());
//        update.setName(dto.getName());
//        update.setLeader(dto.getLeader());
//        update.setPhone(dto.getPhone());
//        if (StringUtils.isNotBlank(dto.getMerchantExpireTime())) {
//            update.setMerchantExpireTime(DateUtil.parseDateTime(dto.getMerchantExpireTime()));
//        }
//        update.setApproveFlag(dto.getApproveFlag());
//        update.setLoginName(dto.getLoginName());
//        update.setOneCategoryId(dto.getOneCategoryId());
//        update.setOneCategory(dto.getOneCategory());
//        update.setTwoCategoryId(dto.getTwoCategoryId());
//        update.setTwoCategory(dto.getTwoCategory());
//        update.setConsume(dto.getConsume());
//        update.setDiningStartTime(dto.getDiningStartTime());
//        update.setDiningEndTime(dto.getDiningEndTime());
//        update.setProvinceCode(dto.getProvinceCode());
//        update.setProvinceName(dto.getProvinceName());
//        update.setCityCode(dto.getCityCode());
//        update.setCityName(dto.getCityName());
//        update.setAreaCode(dto.getAreaCode());
//        update.setAreaName(dto.getAreaName());
//        update.setAddress(dto.getAddress());
//        update.setIdcard(dto.getIdcard());
//        update.setOpenType(dto.getOpenType());
//        update.setBusinessLicenseCode(dto.getBusinessLicenseCode());
//        if (StringUtils.isNotBlank(dto.getNewPassword())) {
//            AssertUtil.isTrue(oldMerchantInfo.getLoginPassword().equals(new Sha256Hash(dto.getLoginPassword(), oldMerchantInfo.getSalt()).toHex()), "原始密码不正确");
//            update.setLoginPassword(new Sha256Hash(dto.getNewPassword(), oldMerchantInfo.getSalt()).toHex());
//        }
//        //上级和推荐信息判断
//        checkParentAndRecommend(dto, update);
//        //添加图片信息
//        addImageInfo(dto, update);
//        merchantInfoMapper.updateById(update);
//        return true;
//    }

    @Override
    public PageResult queryMerchantListVO(Map<String, Object> params) {
        Page<MerchantInfoListVO> page = new PageWrapper<MerchantInfoListVO>(params).getPage();
        IPage<MerchantInfoListVO> iPage = merchantInfoMapper.queryMerchantListVO(page, params);
        return new PageResult(iPage);
    }

    @Override
    public MerchantInfoDetailVO getMerchantDetailVO(Long id) {
        MerchantInfoDetailVO detail = merchantInfoMapper.getMerchantDetailVO(id);
        if (null != detail.getRecommendId() && detail.getRecommendId() > 0) {
            if (BizConstant.ObjectType.ORG.value().equals(detail.getRecommendType())) {
                detail.setRecommendName(sysOrgMapper.getNameById(detail.getRecommendId()));
            } else if (BizConstant.ObjectType.SOFTWARE.value().equals(detail.getRecommendType())) {
                detail.setRecommendName(merchantInfoMapper.getNameById(detail.getRecommendId()));
            }
        }
        if (null != detail.getParentId() && detail.getParentId() > 0) {
            detail.setParentName(sysOrgMapper.getNameById(detail.getParentId()));
        }
        return detail;
    }

    @Override
    public MerchantCountMemberVO countMember(Long merchantInfoId, String type) {
        MerchantCountMemberVO vo = new MerchantCountMemberVO();
        if (StrUtil.isNotBlank(type)) {
            Date date = new Date();
            JSONArray dataX = new JSONArray();
            JSONArray data = new JSONArray();
            Integer totalMember = 0;
            Integer newMember = 0;
            Integer activeMember = 0;

            //累积会员数
            Map<String, Object> params = new HashMap<>();
            params.put("merchantId", merchantInfoId);
            List<MemberMerchantRelationModel> totalList = memberMerchantRelationService.listMemberInfo(params);
            totalMember = totalList.size();

            //新增会员数
            Integer typeInt = Integer.parseInt(type);
            for (int i = 0; i < 8; i++) {
                //周
                if (typeInt.equals(0)) {
                    DateTime dateTime = DateUtil.offsetDay(date, (7 - i) * -1);
                    String curDate = DateUtil.format(dateTime, "yyyy-MM-dd");
                    dataX.add(curDate);
                    params.put("date", curDate);
//                    List<MemberMerchantRelationModel> list = memberMerchantRelationService.listMemberInfo(params);
//                    newMember += list.size();
//                    data.add(list.size());
                }
                //月
                else if (typeInt.equals(1)) {
                    DateTime dateTime = DateUtil.offsetMonth(date, (7 - i) * -1);
                    String month = DateUtil.format(dateTime, "yyyy-MM");
                    dataX.add(month);
                    params.put("month", month);
//                    List<MemberMerchantRelationModel> list = memberMerchantRelationService.listMemberInfo(params);
//                    newMember += list.size();
//                    data.add(list.size());
                }
                //年
                else if (typeInt.equals(2)) {
                    DateTime dateTime = DateUtil.offset(date, DateField.YEAR, (7 - i) * -1);
                    String year = DateUtil.format(dateTime, "yyyy");
                    dataX.add(year);
                    params.put("year", year);
//                    List<MemberMerchantRelationModel> list = memberMerchantRelationService.listMemberInfo(params);
//                    newMember += list.size();
//                    data.add(list.size());
                }

                List<MemberExchangeHistoryLogModel> logList = memberExchangeHistoryLogService.listMemberInfo(params);
                activeMember += logList.size();

                List<MemberMerchantRelationModel> list = memberMerchantRelationService.listMemberInfo(params);
                newMember += list.size();
                data.add(list.size());


            }
            vo.setData(data);
            vo.setDataX(dataX);
            vo.setNewMember(newMember);
            vo.setTotalMember(totalMember);
            vo.setActiveMember(activeMember);
        }
        return vo;
    }

    @Override
    public MemberExchangeHistoryLogVO countIntegral(Long merchantId, String type) {
        //兑换统计
        MemberExchangeHistoryLogVO vo = new MemberExchangeHistoryLogVO();
        if (StrUtil.isNotBlank(type)) {
            Date date = new Date();
            JSONArray dataX = new JSONArray();
            JSONArray dataGiveOut = new JSONArray();
            JSONArray dataConsume = new JSONArray();

            Map<String, Object> params = new HashMap<>();
            params.put("merchantId", merchantId);
            MemberExchangeHistoryLogDTO exchangeHistoryNum = memberExchangeHistoryLogService.getExchangeHistoryNum(params);
            long addUpGiveOutExchangeNum = exchangeHistoryNum.getGiveOutTotalSum();
            long addUpConsumeExchangeNum = exchangeHistoryNum.getConsumeTotalSum();
            long giveOutExchangeNum = 0;
            long consumeExchangeNum = 0;
            Integer typeInt = Integer.parseInt(type);
            for (int i = 0; i < 8; i++) {
                //周
                if (typeInt.equals(0)) {
                    DateTime dateTime = DateUtil.offsetDay(date, (7 - i) * -1);
                    String curDate = DateUtil.format(dateTime, "yyyy-MM-dd");
                    dataX.add(curDate);
                    params.put("date", curDate);

                }
                //月
                else if (typeInt.equals(1)) {
                    DateTime dateTime = DateUtil.offsetMonth(date, (7 - i) * -1);
                    String month = DateUtil.format(dateTime, "yyyy-MM");
                    dataX.add(month);
                    params.put("month", month);
                }
                //年
                else if (typeInt.equals(2)) {
                    DateTime dateTime = DateUtil.offset(date, DateField.YEAR, (7 - i) * -1);
                    String year = DateUtil.format(dateTime, "yyyy");
                    dataX.add(year);
                    params.put("year", year);
                }

                MemberExchangeHistoryLogDTO historyNum = memberExchangeHistoryLogService.getExchangeHistoryNum(params);
                long exchangeNum = historyNum.getGiveOutTotalSum();
                long consumeNum = historyNum.getConsumeTotalSum();
                giveOutExchangeNum += exchangeNum;
                consumeExchangeNum += consumeNum;
                dataGiveOut.add(exchangeNum);
                dataConsume.add(Math.abs(consumeNum));

            }
            vo.setDataGiveOut(dataGiveOut);
            vo.setDataConsume(dataConsume);
            vo.setDataX(dataX);
            vo.setAddUpConsumeExchangeNum(Math.abs(addUpConsumeExchangeNum));
            vo.setAddUpGiveOutExchangeNum(addUpGiveOutExchangeNum);
            vo.setConsumeExchangeNum(Math.abs(consumeExchangeNum));
            vo.setGiveOutExchangeNum(giveOutExchangeNum);
        }
        return vo;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean submitApprove(MerchantInfoModel model) {
//
//
//        // 1. 更新商户基本信息(认证信息)
//        String loginName = model.getLoginName();
//        MerchantInfoModel merchantInfoModel = getByUsername(loginName);
//        AssertUtil.isTrue(ObjectUtil.isNotNull(merchantInfoModel), "商户不存在");
//        MerchantOpenApplyLogModel openApplyLogModelOld = merchantOpenApplyLogService.getByMerIdAndSoftId(merchantInfoModel.getId(), merchantInfoModel.getSoftTypeId());
////        AssertUtil.isNull(openApplyLogModelOld,"您己提交过认证信息");
//        //验证营业执照是否存在
//        if (StringUtils.isNotBlank(model.getBusinessLicenseCode())) {
//            QueryWrapper<MerchantInfoModel> qw = new QueryWrapper<>();
//            qw.eq("business_license_code", model.getBusinessLicenseCode());
//            qw.eq("del_flag", BizConstant.STATE_NORMAL);
//            qw.eq("soft_type_id", model.getSoftTypeId());
//            qw.ne("id", merchantInfoModel.getId());
//            MerchantInfoModel info = merchantInfoMapper.selectOne(qw);
//            AssertUtil.isNull(info, "该营业执照己认证过");
//        }
//
//        MerchantInfoModel updObj = new MerchantInfoModel();
//
//        updObj.setId(merchantInfoModel.getId());
//        updObj.setApproveFlag(MerchantConstant.MerchantApproveState.APPROVEING.value());
//
//        updObj.setIdcard(model.getIdcard());
//        updObj.setIdcardPositivePic(model.getIdcardPositivePic());
//        updObj.setIdcardReversePic(model.getIdcardReversePic());
//
//        updObj.setBusinessLicenseCode(model.getBusinessLicenseCode());
//        updObj.setBusinessLicensePic(model.getBusinessLicensePic());
//
//        updObj.setProvinceCode(model.getProvinceCode());
//        updObj.setProvinceName(model.getProvinceName());
//        updObj.setCityCode(model.getCityCode());
//        updObj.setCityName(model.getCityName());
//        updObj.setAreaCode(model.getAreaCode());
//        updObj.setAreaName(model.getAreaName());
//        updObj.setAddress(model.getAddress());
//
//        updObj.setInfoIsPersonal(model.getInfoIsPersonal());
//        updObj.setPowerAttorneyPic(model.getPowerAttorneyPic());
//
//        merchantInfoMapper.updateById(updObj);
//
//        // 2. 添加商户的开通申请记录 状态 待审核
//        if (null == openApplyLogModelOld) {
//            String billNo = noGenerator.getApplyNo();//单号
//            Long softTypeId = merchantInfoModel.getSoftTypeId();
//            BizSoftwareTypeModel soft = bizSoftwareTypeService.getSoftTypeById(softTypeId);
//            MerchantOpenApplyLogModel openApplyLogModel = new MerchantOpenApplyLogModel();
//            Integer openType = merchantInfoModel.getOpenType();
//            if (openType.equals(MerchantConstant.OpenType.PAY_OPEN.value())) {
//                openApplyLogModel.setApplyType(BizConstant.ApplyType.APPLY.value());
//            } else if (openType.equals(MerchantConstant.OpenType.FREE_OPEN.value())) {
//                openApplyLogModel.setApplyType(BizConstant.ApplyType.OPEN.value());
//            }
//            openApplyLogModel.setBillNo(billNo);
//            openApplyLogModel.setMerchantId(merchantInfoModel.getId());
//            openApplyLogModel.setSoftTypeId(softTypeId);
//            openApplyLogModel.setTypeFlag(MerchantConstant.SoftApplyType.OPEN.value());
//
//            openApplyLogModel.setFee(soft.getOpeningCost());
//
//            openApplyLogModel.setApplyStateFlag(MerchantConstant.AuditState.WAIT_AUDIT.value());
//            openApplyLogModel.setRecommendType(merchantInfoModel.getRecommendTypeFlag());
//            openApplyLogModel.setRecommendId(merchantInfoModel.getRecommendId());
//            openApplyLogModel.setParentType(merchantInfoModel.getParentTypeFlag());
//            openApplyLogModel.setParentId(merchantInfoModel.getParentId());
//
//            merchantOpenApplyLogService.saveOpenApplyLog(openApplyLogModel);
//            Long applyId = openApplyLogModel.getId();
//            if (openType.equals(MerchantConstant.OpenType.PAY_OPEN.value()) && StringUtils.isNotBlank(model.getPaymentPic())) {
//                BizPaymentPicModel bizPaymentPicModel = new BizPaymentPicModel();
//                bizPaymentPicModel.setApplyId(applyId);
//                bizPaymentPicModel.setPic(model.getPaymentPic());
//                bizPaymentPicModel.setPayType(2);
//                bizPaymentPicMapper.insert(bizPaymentPicModel);
//            }
//
//
//        } else {
//            //  扣除软件名额
//            Long recommendId = merchantInfoModel.getRecommendId();
//            Long softTypeId = merchantInfoModel.getSoftTypeId();
//            QueryWrapper<MerchantSoftInfoModel> qws = new QueryWrapper<>();
//            qws.eq("merchant_id", recommendId);
//            qws.eq("soft_type_id", softTypeId);
//            MerchantSoftInfoModel recommendSoft = merchantSoftInfoMapper.selectOne(qws);
//            if (null != recommendSoft) {
//                Long oldId = recommendSoft.getId();
//                Integer availableOpenNum = recommendSoft.getAvailableOpenNum();
//                Integer openNum = recommendSoft.getOpenNum();
//                AssertUtil.isTrue(((availableOpenNum == null ? 0 : availableOpenNum) - 1) > 0, "开通名额不足");
//                recommendSoft.setAvailableOpenNum((availableOpenNum == null ? 0 : availableOpenNum) - 1);
//                recommendSoft.setOpenNum((openNum == null ? 0 : openNum) + 1);
//                QueryWrapper<MerchantSoftInfoModel> upd = new QueryWrapper<>();
//                upd.eq("id", oldId);
//                upd.eq("available_open_num", availableOpenNum);
//                upd.eq("open_num", openNum);
//                merchantSoftInfoMapper.update(recommendSoft, upd);
//            }
//
//            // 申请记录状态变更为待审核
//            Integer applyStateFlag = openApplyLogModelOld.getApplyStateFlag();
//            if (applyStateFlag == (MerchantConstant.AuditState.AUDIT_NOT_PASSED.value())) {
//                openApplyLogModelOld.setApplyStateFlag(MerchantConstant.AuditState.WAIT_AUDIT.value());
//                merchantOpenApplyLogService.update(openApplyLogModelOld);
//            }
//        }
//
//        return true;
//    }

//    @Override
//    public boolean openCustMall(MerchantInfoModel merchantInfo) {
//
//        //  开通自定义商城
//
//        // 1. 调用支付
//
//        // 2. 支付成功后 修改商户开通状诚
//        MerchantInfoModel merchantInfoModel = merchantInfoMapper.selectById(merchantInfo.getId());
//        merchantInfoModel.setMallOpenFlag(MerchantConstant.MallOpenFlag.OPEN.value());
//        merchantInfoMapper.updateById(merchantInfoModel);
//        return false;
//    }

    @Override
    public Integer count(MerchantInfoQueryDTO dto) {
        QueryWrapper<MerchantInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq(null != dto.getMerchantId() && dto.getMerchantId() > 0, "id", dto.getMerchantId());
        wrapper.eq(StringUtils.isNotBlank(dto.getName()), "org_name", dto.getName());
        wrapper.eq(null != dto.getApproveFlag(), "approve_flag", dto.getApproveFlag());
        wrapper.eq(null != dto.getOpenType(), "open_type", dto.getOpenType());
        wrapper.like(StringUtils.isNotBlank(dto.getTreeStr()), "tree_str", dto.getTreeStr());
        return merchantInfoMapper.selectCount(wrapper);
    }

    @Override
    public List<Long> queryMerchantIdList(MerchantInfoQueryDTO dto) {
        return merchantInfoMapper.queryMerchantIdList(dto);
    }

    @Override
    public void resetPassword(Long mchId) {
        MerchantInfoModel oldMchInfo = merchantInfoMapper.selectById(mchId);
        AssertUtil.notNull(oldMchInfo, "商户信息不存在");

        MerchantInfoModel update = new MerchantInfoModel();
        update.setId(oldMchInfo.getId());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        update.setLoginPassword(new Sha256Hash("123456", salt).toHex());
        update.setSalt(salt);
        merchantInfoMapper.updateById(update);
    }

    @Override
    public List<Map<String, Object>> getExportMchList(Map<String, Object> params) {
        return merchantInfoMapper.getExportMchList(params);
    }

//    @Override
//    public MerchantInfoModel apiCheckMchIsNormal(Long merchantId) {
//        if (null == merchantId) {
//            throw new DubboException("" + IntegralStoreConstant.ERROR_41000_CODE, IntegralStoreConstant.ERROR_41000_MESSAGE);
//        }
//        QueryWrapper<MerchantInfoModel> qw = new QueryWrapper<>();
//        qw.eq("id", merchantId);
//        qw.eq("del_flag", BizConstant.STATE_NORMAL);
//        MerchantInfoModel mchInfo = merchantInfoMapper.selectOne(qw);
//        if (null == mchInfo) {
//            throw new DubboException("" + IntegralStoreConstant.ERROR_41000_CODE, IntegralStoreConstant.ERROR_41000_MESSAGE);
//        }
//        if (!mchInfo.getApproveFlag().equals(MerchantConstant.MerchantApproveState.APPROVED.value())) {
//            throw new DubboException("" + IntegralStoreConstant.ERROR_41001_CODE, IntegralStoreConstant.ERROR_41001_MESSAGE);
//        }
//        if (BizConstant.STATE_DISNORMAL.equals(mchInfo.getDisabledFlag())) {
//            throw new DubboException("" + IntegralStoreConstant.ERROR_41002_CODE, IntegralStoreConstant.ERROR_41002_MESSAGE);
//        }
//        if (System.currentTimeMillis() > mchInfo.getMerchantExpireTime().getTime()) {
//            throw new DubboException("" + IntegralStoreConstant.ERROR_41003_CODE, IntegralStoreConstant.ERROR_41003_MESSAGE);
//        }
//        if (null == mchInfo.getSoftTypeId()) {
//            throw new DubboException("" + IntegralStoreConstant.ERROR_41001_CODE, IntegralStoreConstant.ERROR_41001_MESSAGE);
//        }
//        BizSoftwareTypeModel softwareType = bizSoftwareTypeMapper.selectById(mchInfo.getSoftTypeId());
//        if (null == softwareType) {
//            throw new DubboException("" + IntegralStoreConstant.ERROR_41001_CODE, IntegralStoreConstant.ERROR_41001_MESSAGE);
//        }
//        return mchInfo;
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updateDiamondSoftByPhone(String phone) {
//        MerchantInfoModel merchantInfoModel1 = merchantInfoMapper.selectOne(new QueryWrapper<MerchantInfoModel>().eq("phone", phone));
//        if (merchantInfoModel1 == null){
//            MerchantInfoModel merchantInfoModel = MerchantInfoModel.builder().phone(phone).approveFlag(2).softTypeId(1104994513978195969L).
//                    softTypeName("钻石版").merchantExpireTime(DateUtil.offset(new Date(),DateField.YEAR,10)).build();
//            //开通新商户,初始化商户信息帐户,商户资金帐户及软件帐户
//            //信息帐户
//            merchantInfoMapper.insert(merchantInfoModel);
//            Long idNew = merchantInfoModel.getId();//新商户ID
//            //商户资金帐户
//            MerchantAccountInfoModel mAccNew = new MerchantAccountInfoModel();
//            mAccNew.setMerchantId(idNew);
//            mAccNew.setShoppingCouponSum(BigDecimal.ZERO);
//            mAccNew.setExchangeCouponSum(0);
//            mAccNew.setAvailableBalance(BigDecimal.ZERO);
//            mAccNew.setFreezeBalance(BigDecimal.ZERO);
//            merchantAccountInfoService.saveAcc(mAccNew);
//            //商户钱包帐户
//            MerchantWalletInfoModel mWallet = new MerchantWalletInfoModel();
//            mWallet.setMerchantId(idNew);
//            mWallet.setAmount(BigDecimal.ZERO);
//            mWallet.setAmountSum(BigDecimal.ZERO);
//            mWallet.setExchangeNumber(0);
//            mWallet.setSurplusExchangeNumber(0);
//            merchantWalletInfoService.save(mWallet);
//            //商户软件帐户
//            List<BizSoftwareTypeModel> softList = bizSoftwareTypeService.listSoftType();
//            if (softList.size() > 0) {
//                for (int i = 0; i < softList.size(); i++) {
//                    BizSoftwareTypeModel soft = softList.get(i);
//                    Long softId = soft.getId();
//                    MerchantSoftInfoModel softAccEx = merchantSoftInfoService.getSoftAcc(idNew, softId);
//                    if (null == softAccEx) {
//                        MerchantSoftInfoModel mSoftAcc = new MerchantSoftInfoModel();
//                        mSoftAcc.setMerchantId(idNew);
//                        mSoftAcc.setSoftTypeId(softId);
//                        mSoftAcc.setOpenNum(0);
//                        mSoftAcc.setAvailableOpenNum(0);
//                        merchantSoftInfoService.saveSoftAcc(mSoftAcc);
//                    }
//                }
//            }
//        }else if (!merchantInfoModel1.getSoftTypeName().equals("钻石版")){
//            MerchantInfoModel merchantInfoModel = MerchantInfoModel.builder().softTypeId(1104994513978195969L)
//                    .softTypeName("钻石版").build();
//            merchantInfoMapper.update(merchantInfoModel,
//                    new UpdateWrapper<MerchantInfoModel>().eq("phone", phone));
//        }
//
//    }

    @Override
    public int updateByPhone(MerchantInfoModel merchantInfoModel) {
        return merchantInfoMapper.update(merchantInfoModel
                ,new UpdateWrapper<MerchantInfoModel>().eq("phone",merchantInfoModel.getPhone()));
    }

    @Override
    public int count(String phone) {
        return merchantInfoMapper.count(phone,null);
    }

    @Override
    public List<MerchantInfoModel> getMerchantInfoByIdList(Long id, String name, String phone, Integer level, Integer delFlag) {
        return merchantInfoMapper.getMerchantInfoByIdList(id, name, phone, level, delFlag);
    }

    @Override
    public MerchantInfoModel getListByParentPhone(String parentPhone) {
        return merchantInfoMapper.getListByParentPhone(parentPhone);
    }

    @Override
    public List<MerchantTeamAppVo> getMerchantList(Long id,String phone, String name) {
        return merchantInfoMapper.getMerchantList(id,phone,name);
    }

    @Override
    public Integer getByParentId(Long id) {
        return merchantInfoMapper.getByParentId(id);
    }

//    @Override
//    public Integer countAllNumber(Long id) {
//        return merchantInfoMapper.countAllNumber(id);
//    }


//    @Override
//    public Long TotalCountDirectTeam(Long id, String phone, String name) {
//        return merchantInfoMapper.TotalCountDirectTeam(id,phone,name);
//    }

    @Override
    public PageResult listMerchantPage(Map<String, Object> params) {
        Page<MerchantInfoModel> page = new PageWrapper<MerchantInfoModel>(params).getPage();
        IPage<MerchantInfoModel> ipage = merchantInfoMapper.listMerchantPage(page, params);
        return new PageResult(ipage);
    }

    @Override
    public PageResult listMerchantPageName(Map<String, Object> params) {
        Page<MerchantInfoModel> page = new PageWrapper<MerchantInfoModel>(params).getPage();
        IPage<MerchantInfoModel> ipage = merchantInfoMapper.listMerchantPageName(page, params);
        return new PageResult(ipage);
    }

    @Override
    public MerchantInfoModel getByShopId(Long shopId) {
        return merchantInfoMapper.getByShopId(shopId);
    }

    @Override
    public MerchantInfoModel getByOrderNo(String orderNo) {
        return merchantInfoMapper.getByOrderNo(orderNo);
    }


    private String generateInviteCode(int generateTimes){
        String inviteCode = RandomUtil.randomNumbers(6);
        Integer count = merchantInfoMapper.count(null, inviteCode);
        if (count > 0 && generateTimes <= 5){
            inviteCode = this.generateInviteCode(++generateTimes);
        }else if (count > 0){
            return null;
        }
        return inviteCode;
    }


    private void checkParentAndRecommend(MerchantInfoFormDTO dto, MerchantInfoModel merchantInfo) {
        if (BizConstant.ObjectType.ORG.value().equals(dto.getParentTypeFlag())) {
            SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getParentId());
            AssertUtil.notNull(parentOrg, "上级对象信息不存在");
            merchantInfo.setParentId(dto.getParentId());
            //merchantInfo.setParentTypeFlag(BizConstant.ObjectType.ORG.value());
            merchantInfo.setTreeStr(StringUtils.isNotBlank(parentOrg.getTreeStr()) ? parentOrg.getTreeStr() + "," + parentOrg.getId() : "" + parentOrg.getId());
        } else if (BizConstant.ObjectType.SOFTWARE.value().equals(dto.getParentTypeFlag())) {
            MerchantInfoModel parentMerchant = merchantInfoMapper.selectById(dto.getParentId());
            AssertUtil.notNull(parentMerchant, "上级对象信息不存在");
            merchantInfo.setParentId(dto.getParentId());
            //merchantInfo.setParentTypeFlag(BizConstant.ObjectType.SOFTWARE.value());
            merchantInfo.setTreeStr(StringUtils.isNotBlank(parentMerchant.getTreeStr()) ? parentMerchant.getTreeStr() + "," + parentMerchant.getId() : "" + parentMerchant.getId());
        }
        if (BizConstant.ObjectType.ORG.value().equals(dto.getRecommendTypeFlag())) {
            SysOrgModel recommendOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getRecommendId());
            AssertUtil.notNull(recommendOrg, "推荐对象信息不存在");
            merchantInfo.setRecommendId(recommendOrg.getId());
            merchantInfo.setRecommendTypeFlag(BizConstant.ObjectType.ORG.value());
        } else if (BizConstant.ObjectType.SOFTWARE.value().equals(dto.getRecommendTypeFlag())) {
            MerchantInfoModel recommendMerchant = merchantInfoMapper.selectById(dto.getRecommendId());
            AssertUtil.notNull(recommendMerchant, "推荐对象信息不存在");
            merchantInfo.setRecommendId(recommendMerchant.getId());
            merchantInfo.setRecommendTypeFlag(BizConstant.ObjectType.SOFTWARE.value());
        }
    }

    private void addImageInfo(MerchantInfoFormDTO dto, MerchantInfoModel merchantInfo) {
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

    private String addApplyInfoFile(String base64Code) {
        if (!StringUtils.isBlank(base64Code)) {
            String suffix = base64Code.split(";")[0];
            String imagePattern = "data:image/(jpg|png|jpeg)";
            if (!Pattern.matches(imagePattern, suffix)) {
                throw new DubboException("素材格式错误");
            }
            String imageUrl = "";//AliyunStorageUtil.uploadBase64Img("diandian-business", base64Code);
            return imageUrl;
        }
        return null;
    }

    private void getMerchantIntactTree(List<IntactTreeVO> list, List<IntactTreeVO> merchantPartList, boolean isBuildTree) {
        List<IntactTreeVO> tempList = new ArrayList<>();
        for (IntactTreeVO item : list) {
            if (null != item.getChildren() && item.getChildren().size() > 0) {
                getMerchantIntactTree(item.getChildren(), merchantPartList, false);
            }
            if (merchantPartList.isEmpty()) {
                continue;
            }
            tempList.add(item);
        }
        if (isBuildTree) {
            TreeUtil.buildIntactTree(tempList, merchantPartList, false, merchantPartList.size());
        }
    }

}
