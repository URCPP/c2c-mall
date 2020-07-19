package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizOrgApplyMapper;
import com.diandian.dubbo.business.mapper.BizOrgInfoMapper;
import com.diandian.dubbo.business.mapper.BizPaymentPicMapper;
import com.diandian.dubbo.business.mapper.SysOrgMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.business.service.OrgCommonService;
import com.diandian.dubbo.common.oss.AliyunStorageUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.biz.ApplyCheckResultDTO;
import com.diandian.dubbo.facade.dto.biz.OrgApplyInfoDTO;
import com.diandian.dubbo.facade.dto.biz.OrgApplyQueryDTO;
import com.diandian.dubbo.facade.model.biz.*;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.service.biz.BizOrgApplyService;
import com.diandian.dubbo.facade.vo.OrgApplyInfoDetailVO;
import com.diandian.dubbo.facade.vo.OrgApplyInfoListVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 机构申请表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizOrgApplyService")
@Slf4j
public class BizOrgApplyServiceImpl extends OrgCommonService implements BizOrgApplyService {

	@Autowired
	private SysOrgMapper sysOrgMapper;

	@Autowired
	private BizOrgApplyMapper bizOrgApplyMapper;

	@Autowired
	private BizOrgInfoMapper bizOrgInfoMapper;

	@Autowired
	private MerchantInfoMapper merchantInfoMapper;

	@Autowired
	private BizPaymentPicMapper bizPaymentPicMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(OrgApplyInfoDTO dto){
		SysOrgTypeModel orgType = sysOrgTypeMapper.selectById(dto.getOrgTypeId());
		AssertUtil.notNull(orgType, "机构类型不存在");
		QueryWrapper<SysOrgModel> orgWrapper = new QueryWrapper<>();
		orgWrapper.eq("org_name", dto.getOrgName());
		orgWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
		AssertUtil.isTrue(sysOrgMapper.selectCount(orgWrapper) == 0, "机构名称已存在");
		QueryWrapper<BizOrgApplyModel> orgApplyWrapper = new QueryWrapper<>();
		orgApplyWrapper.eq("org_name", dto.getOrgName());
		orgApplyWrapper.ne("audit_state", BizConstant.AuditState.CLOSE_APPLY.value());
		AssertUtil.isTrue(bizOrgApplyMapper.selectCount(orgApplyWrapper) == 0, "机构名称已存在");
		//参数校验
		checkSaveOrUpdateParams(dto);
		BizOrgApplyModel orgApply = new BizOrgApplyModel();
		BizPaymentPicModel paymentPic = new BizPaymentPicModel();
		//推荐对象和原始对象判断
		checkObject(dto);
		BeanUtil.copyProperties(dto, orgApply);
		orgApply.setOrgTypeName(orgType.getTypeName());
		orgApply.setApplyNo(noGenerator.getApplyNo());
		//添加身份证图片、营业执照图片
		addImageInfo(null, dto, null, orgApply);
		AssertUtil.isTrue(StringUtils.isNotBlank(orgApply.getIdcard()), "请填写身份证号码");
		AssertUtil.isTrue(StringUtils.isNotBlank(orgApply.getBusinessLicenseCode()), "请填写营业执照编码");
		//判断申请列表中的营业执照是否存在
		orgApplyWrapper = new QueryWrapper<>();
		orgApplyWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
		orgApplyWrapper.eq("org_type_id", dto.getOrgTypeId());
		orgApplyWrapper.ne("audit_state", BizConstant.AuditState.CLOSE_APPLY.value());
		AssertUtil.isTrue(bizOrgApplyMapper.selectCount(orgApplyWrapper) == 0, "该营业执照已被使用");
		//判断机构信息列表中的营业执照是否存在
		QueryWrapper<BizOrgInfoModel> orgInfoWrapper = new QueryWrapper<>();
		orgInfoWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
		orgInfoWrapper.eq("org_type_id", dto.getOrgTypeId());
		orgInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
		AssertUtil.isTrue(bizOrgInfoMapper.selectCount(orgInfoWrapper) == 0, "该营业执照已被使用");
		AssertUtil.isTrue(StringUtils.isNotBlank(orgApply.getBusinessLicensePic()), "请上传营业执照图片");
		AssertUtil.isTrue(StringUtils.isNotBlank(orgApply.getIdcardPositivePic()), "请上传身份证正面图片");
		AssertUtil.isTrue(StringUtils.isNotBlank(orgApply.getIdcardReversePic()), "请上传身份证反面图片");
		bizOrgApplyMapper.insert(orgApply);
		dto.setId(orgApply.getId());
		//添加打款图片
		addPaymentInfo(dto);
		//开通操作逻辑-扣除可开通名额，添加扣除记录
		if(BizConstant.ApplyType.OPEN.value().equals(dto.getApplyType())){
			QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper<>();
			orgPrivilegeWrapper.eq("org_id", dto.getParentId());
			orgPrivilegeWrapper.eq("reward_org_type_id", dto.getOrgTypeId());
			BizOrgPrivilegeModel orgPrivilege = bizOrgPrivilegeMapper.selectOne(orgPrivilegeWrapper);
			AssertUtil.isTrue(orgPrivilege != null && orgPrivilege.getRewardValue().compareTo(BigDecimal.ZERO) == 1, "可开通名额不足");
			addOpenLog(orgApply, orgPrivilege, false);
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById(OrgApplyInfoDTO dto){
		BizOrgApplyModel oldOrgApply = bizOrgApplyMapper.selectById(dto.getId());
		AssertUtil.notNull(oldOrgApply, "机构申请信息不存在");
		AssertUtil.isTrue(!BizConstant.AuditState.CLOSE_APPLY.value().equals(oldOrgApply.getAuditState()), "无法修改已关闭的申请信息");
		AssertUtil.isTrue(!BizConstant.AuditState.AUDIT_PASSED.value().equals(oldOrgApply.getAuditState()), "无法修改审核成功的申请信息");
		QueryWrapper<SysOrgModel> orgWrapper = new QueryWrapper<>();
		orgWrapper.ne("apply_id", oldOrgApply.getId());
		orgWrapper.eq("org_name", dto.getOrgName());
		orgWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
		AssertUtil.isTrue(sysOrgMapper.selectCount(orgWrapper) == 0, "机构名称已存在");
		QueryWrapper<BizOrgApplyModel> orgApplyWrapper = new QueryWrapper<>();
		orgApplyWrapper.ne("id", oldOrgApply.getId());
		orgApplyWrapper.eq("org_name", dto.getOrgName());
		orgApplyWrapper.ne("audit_state", BizConstant.AuditState.CLOSE_APPLY.value());
		AssertUtil.isTrue(bizOrgApplyMapper.selectCount(orgApplyWrapper) == 0, "机构名称已存在");
		AssertUtil.isTrue(StringUtils.isNotBlank(dto.getIdcard()), "请填写身份证号码");
		AssertUtil.isTrue(StringUtils.isNotBlank(dto.getBusinessLicenseCode()), "请填写营业执照编码");

		//判断机构信息列表中的营业执照是否存在
		QueryWrapper<BizOrgInfoModel> orgInfoWrapper = new QueryWrapper<>();
		orgInfoWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
		orgInfoWrapper.eq("org_type_id", dto.getOrgTypeId());
		orgInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
		AssertUtil.isTrue(bizOrgInfoMapper.selectCount(orgInfoWrapper) == 0, "该营业执照已被使用");
		//判断申请列表中的营业执照是否存在
		orgApplyWrapper = new QueryWrapper<>();
		orgApplyWrapper.ne("id", oldOrgApply.getId());
		orgApplyWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
		orgApplyWrapper.eq("org_type_id", dto.getOrgTypeId());
		orgApplyWrapper.ne("audit_state", BizConstant.AuditState.CLOSE_APPLY.value());
		AssertUtil.isTrue(bizOrgApplyMapper.selectCount(orgApplyWrapper) == 0, "该营业执照已被使用");

		//参数校验
		checkSaveOrUpdateParams(dto);
		BizOrgApplyModel update = new BizOrgApplyModel();
		//推荐对象和原始对象判断
		checkObject(dto);
		BeanUtil.copyProperties(dto, update);
		update.setId(oldOrgApply.getId());
		update.setParentId(oldOrgApply.getParentId());
		update.setOrgTypeId(oldOrgApply.getOrgTypeId());
		update.setApplyType(oldOrgApply.getApplyType());
		if(BizConstant.AuditState.AUDIT_NOT_PASSED.value().equals(oldOrgApply.getAuditState())){
			update.setAuditState(BizConstant.AuditState.AUDIT_WAIT.value());
		}
		//添加身份证、营业执照图片
		addImageInfo(null, dto, null, update);
		//添加打款信息
		addPaymentInfo(dto);
		int updateRes = bizOrgApplyMapper.updateById(update);
		return updateRes >= 1;
	}

	@Override
	public PageResult orgApplyInfoListPage(Map<String, Object> params){
		Page<OrgApplyInfoListVO> page = new PageWrapper<OrgApplyInfoListVO>(params).getPage();
		page = bizOrgApplyMapper.orgApplyInfoListPage(page, params);
		return new PageResult(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void checkOrgApplyInfo(ApplyCheckResultDTO checkResult){
		AssertUtil.isTrue(checkResult.getId() != null && checkResult.getId() > 0L, "参数错误");
		AssertUtil.isTrue(checkResult.getAuditState() != null, "审核结果不能为空");
		BizOrgApplyModel applyInfo = bizOrgApplyMapper.selectById(checkResult.getId());
		AssertUtil.notNull(applyInfo, "申请信息不存在");
		AssertUtil.isTrue(BizConstant.AuditState.AUDIT_NOT_PASSED.value().equals(applyInfo.getAuditState())
				|| BizConstant.AuditState.AUDIT_WAIT.value().equals(applyInfo.getAuditState()), "无法审核已关闭或已通过的申请信息");
		if(BizConstant.AuditState.AUDIT_PASSED.value().equals(checkResult.getAuditState())){
			orgApplyCheck(applyInfo);
		}
		//更新审核信息
		BizOrgApplyModel update = new BizOrgApplyModel();
		update.setId(applyInfo.getId());
		update.setAuditFailReason(checkResult.getAuditFailReason());
		update.setAuditState(checkResult.getAuditState());
		update.setAuditUserId(checkResult.getAuditUserId());
		update.setAuditTime(new Date());
		bizOrgApplyMapper.updateById(update);
	}

	@Override
	public OrgApplyInfoDetailVO getOrgApplyInfoDetail(long id){
		OrgApplyInfoDetailVO detail = bizOrgApplyMapper.getOrgApplyInfoDetail(id);
		if(null != detail.getOriginalId() && detail.getOriginalId() > 0){
			if(BizConstant.ObjectType.ORG.value().equals(detail.getOriginalType())){
				detail.setOriginalName(sysOrgMapper.getNameById(detail.getOriginalId()));
			}else if (BizConstant.ObjectType.SOFTWARE.value().equals(detail.getOriginalType())){
				detail.setOriginalName(merchantInfoMapper.getNameById(detail.getOriginalId()));
			}
		}
		if(null != detail.getRecommendId() && detail.getRecommendId() > 0){
			detail.setRecommendName(sysOrgMapper.getNameById(detail.getRecommendId()));
		}
		if(null != detail.getParentId() && detail.getParentId() > 0){
			detail.setParentName(sysOrgMapper.getNameById(detail.getParentId()));
		}
		return detail;
	}

	@Override
	public boolean removeById(Long id){
		BizOrgApplyModel oldOrgApply = bizOrgApplyMapper.selectById(id);
		AssertUtil.notNull(oldOrgApply, "申请信息不存在");
		Integer result = bizOrgApplyMapper.deleteById(id);
		return null != result && result >= 0;
	}

	@Override
	public BizOrgApplyModel getById(Long id){
		return bizOrgApplyMapper.selectById(id);
	}

	@Override
	public Integer count(OrgApplyQueryDTO dto){
		QueryWrapper<BizOrgApplyModel> wrapper = new QueryWrapper<>();
		wrapper.eq(null != dto.getAuditState(), "audit_state", dto.getAuditState());
		wrapper.eq(null != dto.getApplyType(), "apply_type", dto.getApplyType());
		wrapper.ge(StringUtils.isNotBlank(dto.getStartTime()), "create_time", dto.getStartTime());
		wrapper.le(StringUtils.isNotBlank(dto.getEndTime()), "create_time", dto.getEndTime());
		wrapper.like(StringUtils.isNotBlank(dto.getTreeStr()), "tree_str", dto.getTreeStr());
		return bizOrgApplyMapper.selectCount(wrapper);
	}

	/**
	 *
	 * 功能描述: 添加或者更新申请信息的参数校验
	 *
	 * @param
	 * @return
	 * @author cjunyuan
	 * @date 2019/3/5 9:09
	 */
	private void checkSaveOrUpdateParams(OrgApplyInfoDTO dto){
		AssertUtil.notBlank(dto.getOrgName(), "机构名称不能为空");
		AssertUtil.isTrue(null != dto.getApplyType(), "操作类型不能为空");
		AssertUtil.isTrue(null != dto.getOrgTypeId() && dto.getOrgTypeId() > 0, "机构类型不能为空");
		AssertUtil.notBlank(dto.getPhone(), "手机号码不能为空");
		AssertUtil.notBlank(dto.getIdcard(), "身份证号码不能为空");
		//申请/开通权限判断
		AssertUtil.isTrue(null != dto.getParentId() && dto.getParentId() != 0, "您没有申请的权限");
		SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getParentId());
		AssertUtil.notNull(parentOrg, "参数错误");
		SysOrgTypeModel parentOrgType = sysOrgTypeMapper.selectById(parentOrg.getOrgTypeId());
		SysOrgTypeModel applyOrgType = sysOrgTypeMapper.selectById(dto.getOrgTypeId());
		AssertUtil.isTrue(applyOrgType.getLevel() >= parentOrgType.getLevel(), "您只能申请或开通比自己级别低的机构");
	}

	private void orgApplyCheck(BizOrgApplyModel applyInfo){
		//判断申请的机构类型是否存在，申请的时候已经将免费开通名额扣除，所以审核时无需进行判断
		QueryWrapper<SysOrgTypeModel> orgTypeWrapper = new QueryWrapper<>();
		orgTypeWrapper.eq("id", applyInfo.getOrgTypeId());
		AssertUtil.isTrue(sysOrgTypeMapper.selectCount(orgTypeWrapper) > 0, "机构类型不存在");

		//添加机构
		QueryWrapper<SysOrgModel> orgWrapper = new QueryWrapper<>();
		orgWrapper.eq("apply_id", applyInfo.getId());
		AssertUtil.isTrue(sysOrgMapper.selectCount(orgWrapper) == 0, "请勿重复审核申请信息");
		SysOrgModel org = new SysOrgModel();
		AssertUtil.isTrue(null != applyInfo.getParentId() && applyInfo.getParentId() != 0, "上级机构不能为空");
		SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(applyInfo.getParentId());
		AssertUtil.notNull(parentOrg, "上级机构不能为空");
		org.setParentId(applyInfo.getParentId());
		org.setTreeStr(StringUtils.isNotBlank(parentOrg.getTreeStr()) ? parentOrg.getTreeStr()+ "," + parentOrg.getId() : "" + parentOrg.getId());
		org.setOrgName(applyInfo.getOrgName());
		org.setOrgTypeId(applyInfo.getOrgTypeId());
		org.setApplyId(applyInfo.getId());
		org.setApproveFlag(MerchantConstant.MerchantApproveState.APPROVED.value());
		sysOrgMapper.insert(org);
		//添加机构账户信息
		addOrgAccount(org.getId());
		//添加机构信息
		addOrgInfo(applyInfo, org);
		//添加机构开通奖励
		addGiftReward(org);
		SysOrgModel recommendOrg = null;
		if(BizConstant.ApplyType.APPLY.value().equals(applyInfo.getApplyType())){
			//判断推荐对象是否存在，是-只添加推荐奖励，否-添加开通奖励
			if(null == applyInfo.getRecommendId() || null == applyInfo.getRecommendType() ){
				//添加开通机构奖励（与前面不同，上面一个的受益对象是新增机构，这个的受益对象是上级机构）
				addOpenOrgReward(parentOrg, org);
			}
			//添加推荐机构/商户奖励，目前机构推荐只能由同级或者上级推荐，无法由商户自己推荐，所以不存在商户推荐机构奖励
			if(BizConstant.ObjectType.ORG.value().equals(applyInfo.getRecommendType()) &&
					null != applyInfo.getRecommendId() && applyInfo.getRecommendId() > 0){
				recommendOrg = sysOrgMapper.selectNotDeleteOrgById(applyInfo.getRecommendId());
				AssertUtil.notNull(recommendOrg, "推荐机构信息不存在");
				addRecommendOrgReward(recommendOrg, org);
			} else if(BizConstant.ObjectType.SOFTWARE.value().equals(applyInfo.getRecommendType())){

			}
		}
		//修改原来（机构/软件）的父级信息
		editObjectInfo(applyInfo, org);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void closeApply(Long id){
		BizOrgApplyModel oldOrgApply = bizOrgApplyMapper.selectById(id);
		AssertUtil.notNull(oldOrgApply, "申请信息不存在");
		AssertUtil.isTrue(!BizConstant.AuditState.AUDIT_PASSED.value().equals(oldOrgApply.getAuditState()), "无法关闭已经通过审核的申请信息");
		AssertUtil.isTrue(!BizConstant.AuditState.CLOSE_APPLY.value().equals(oldOrgApply.getAuditState()), "该申请信息已关闭，无需重复操作");
		if(BizConstant.ApplyType.OPEN.value().equals(oldOrgApply.getApplyType())){
			//如果是开通操作，返还开通名额
			QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper<>();
			orgPrivilegeWrapper.eq("org_id", oldOrgApply.getParentId());
			orgPrivilegeWrapper.eq("reward_org_type_id", oldOrgApply.getOrgTypeId());
			BizOrgPrivilegeModel orgPrivilege = bizOrgPrivilegeMapper.selectOne(orgPrivilegeWrapper);
			addOpenLog(oldOrgApply, orgPrivilege, true);
		}
		BizOrgApplyModel update = new BizOrgApplyModel();
		update.setAuditState(BizConstant.AuditState.CLOSE_APPLY.value());
		update.setId(oldOrgApply.getId());
		bizOrgApplyMapper.updateById(update);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean undoApply(Long id){
		BizOrgApplyModel oldOrgApply = bizOrgApplyMapper.selectById(id);
		AssertUtil.notNull(oldOrgApply, "申请信息不存在");
		AssertUtil.isTrue(BizConstant.AuditState.AUDIT_PASSED.value().equals(oldOrgApply.getAuditState()), "该申请信息不是审核通过状态");
		QueryWrapper<SysOrgModel> orgQueryWrapper = new QueryWrapper<>();
		orgQueryWrapper.eq("apply_id", oldOrgApply.getId());
		orgQueryWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
		SysOrgModel oldOrg = sysOrgMapper.selectOne(orgQueryWrapper);
		AssertUtil.notNull(oldOrg, "参数错误");
		orgQueryWrapper = new QueryWrapper<>();
		orgQueryWrapper.likeLeft( "tree_str", new StringBuilder(oldOrg.getTreeStr()).append(",").append(oldOrg.getId()).toString());
		AssertUtil.isTrue(sysOrgMapper.selectCount(orgQueryWrapper) <= 0, "该申请信息不是初始状态，无法撤消");
		QueryWrapper<MerchantInfoModel> mchQueryWrapper = new QueryWrapper<>();
		mchQueryWrapper.likeLeft( "tree_str", new StringBuilder(oldOrg.getTreeStr()).append(",").append(oldOrg.getId()).toString());
		AssertUtil.isTrue(merchantInfoMapper.selectCount(mchQueryWrapper) <= 0, "该申请信息不是初始状态，无法撤消");
		SysOrgModel updateOrg = new SysOrgModel();
		updateOrg.setId(oldOrg.getId());
		updateOrg.setDelFlag(BizConstant.STATE_DISNORMAL);
		sysOrgMapper.updateById(updateOrg);
		QueryWrapper<BizOrgInfoModel> orgInfoWrapper = new QueryWrapper<>();
		orgInfoWrapper.eq("org_id", oldOrg.getId());
		orgInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
		BizOrgInfoModel oldOrgInfo = bizOrgInfoMapper.selectOne(orgInfoWrapper);
		AssertUtil.notNull(oldOrgInfo, "参数错误");
		BizOrgInfoModel updateOrgInfo = new BizOrgInfoModel();
		updateOrgInfo.setId(oldOrgInfo.getId());
		updateOrgInfo.setDelFlag(BizConstant.STATE_DISNORMAL);
		bizOrgInfoMapper.updateById(updateOrgInfo);
		BizOrgApplyModel updateApply = new BizOrgApplyModel();
		updateApply.setId(oldOrgApply.getId());
		updateApply.setAuditState(BizConstant.AuditState.CLOSE_APPLY.value());
		bizOrgApplyMapper.updateById(updateApply);
		SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(oldOrgApply.getParentId());
		AssertUtil.notNull(parentOrg, "参数错误");
		if(BizConstant.ApplyType.APPLY.value().equals(oldOrgApply.getApplyType())){
			//回收开通机构奖励
			if(null == oldOrgApply.getRecommendId() || null == oldOrgApply.getRecommendType()){
				QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
				orgTypeStrategyWrapper.eq("org_type_id", parentOrg.getOrgTypeId());
				orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.OPENING_BONUSES.value());
				orgTypeStrategyWrapper.eq("recommend_org_type_id", oldOrg.getOrgTypeId());
				orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
				List<BizOrgTypeStrategyModel> openOrgTypeStrategyList = bizOrgTypeStrategyMapper.selectList(orgTypeStrategyWrapper);
				undoReward(openOrgTypeStrategyList, parentOrg, oldOrg);
			}
			//回收推荐机构奖励
			if(null != oldOrgApply.getRecommendId() && oldOrgApply.getRecommendId() > 0){
				SysOrgModel recommendOrg = sysOrgMapper.selectNotDeleteOrgById(oldOrgApply.getRecommendId());
				QueryWrapper<BizOrgTypeStrategyModel> orgTypeStrategyWrapper = new QueryWrapper<>();
				orgTypeStrategyWrapper.eq("org_type_id", recommendOrg.getOrgTypeId());
				orgTypeStrategyWrapper.eq("strategy_type", BizConstant.StrategyType.REFERRAL_BONUSES.value());
				orgTypeStrategyWrapper.eq("recommend_org_type_id", oldOrg.getOrgTypeId());
				orgTypeStrategyWrapper.eq("state", BizConstant.STATE_NORMAL);
				List<BizOrgTypeStrategyModel> recommendOrgTypeStrategyList = bizOrgTypeStrategyMapper.selectList(orgTypeStrategyWrapper);
				undoReward(recommendOrgTypeStrategyList, recommendOrg, oldOrg);
			}
		}else if(BizConstant.ApplyType.OPEN.value().equals(oldOrgApply.getApplyType())){
			QueryWrapper<BizOrgPrivilegeModel> orgPrivilegeWrapper = new QueryWrapper<>();
			orgPrivilegeWrapper.eq("org_id", parentOrg.getId());
			orgPrivilegeWrapper.eq("reward_type_id", BizConstant.ObjectType.ORG.value());
			orgPrivilegeWrapper.eq("reward_org_type_id", oldOrg.getOrgTypeId());
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
			openDetail.setTypeId(BizConstant.ObjectType.ORG.value().equals(oldOrgPrivilege.getRewardTypeId()) ? oldOrgPrivilege.getRewardOrgTypeId() : oldOrgPrivilege.getRewardSoftwareTypeId());
			bizOpenDetailMapper.insert(openDetail);
		}
		return true;
	}

	private void undoReward(List<BizOrgTypeStrategyModel> orgTypeStrategyList, SysOrgModel benefitOrg, SysOrgModel fromOrg){
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
				AssertUtil.notNull(oldPrivilege, "参数错误");
				AssertUtil.isTrue(oldPrivilege.getRewardValue().compareTo(orgTypeStrategy.getRewardValue()) >= 0, "该申请信息不是初始状态，无法撤消");
				BizOrgPrivilegeModel update = new BizOrgPrivilegeModel();
				update.setId(oldPrivilege.getId());
				update.setRewardValue(oldPrivilege.getRewardValue().subtract(orgTypeStrategy.getRewardValue()));
				bizOrgPrivilegeMapper.updateById(update);
				//添加推荐机构奖励（机构/软件）明细
				BizOpenDetailModel openDetail = new BizOpenDetailModel();
				openDetail.setOrgId(benefitOrg.getId());
				openDetail.setBusType(BizConstant.OpenBusType.UNDO_APPLY.value());
				openDetail.setOpenType(orgTypeStrategy.getRewardType());
				openDetail.setTradeStart(oldPrivilege.getRewardValue().intValue());
				openDetail.setTradeEnd(oldPrivilege.getRewardValue().subtract(orgTypeStrategy.getRewardValue()).intValue());
				openDetail.setTradeNum(orgTypeStrategy.getRewardValue().intValue());
				openDetail.setTradeNo(noGenerator.getRewardTradeNo());
				openDetail.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
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
					AssertUtil.isTrue(account.getBonus().compareTo(bonus) >= 0, "该申请信息不是初始状态，无法撤消");
					//生成现金奖励流水，修改机构账户未发放奖金的值
					BizBonusDetailModel bonusDetail = new BizBonusDetailModel();
					bonusDetail.setTradeNo(noGenerator.getRewardTradeNo());
					bonusDetail.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
					bonusDetail.setBusType(BizConstant.BonusBusType.UNDO_APPLY.value());
					bonusDetail.setTradeAmount(bonus);
					bonusDetail.setTradeStart(account.getBonus());
					bonusDetail.setTradeEnd(account.getBonus().subtract(bonus));
					bonusDetail.setOrgId(benefitOrg.getId());
					//被推荐机构ID
					bonusDetail.setFromOrgId(fromOrg.getId());
					bizBonusDetailMapper.insert(bonusDetail);
					update.setBonus(account.getBonus().subtract(bonus));
					update.setTotalBonus(account.getTotalBonus().subtract(bonus));
					updateWrapper.eq("bonus", account.getBonus());
					updateWrapper.eq("total_bonus", account.getTotalBonus());
				} else if (BizConstant.RewardType.FIRST_TIME.value().equals(orgTypeStrategy.getRewardType())) {
					//生成首次开通年费奖励明细
					SysOrgTypeModel orgType = sysOrgTypeMapper.selectById(fromOrg.getOrgTypeId());
					//计算奖励年费
					BigDecimal annualFee = orgType.getOpeningCost().multiply(orgTypeStrategy.getRewardValue().divide(percent));
					AssertUtil.isTrue(account.getAvailableBalance().compareTo(annualFee) >= 0, "该申请信息不是初始状态，无法撤消");
					BizAccountDetailModel accountDetail = new BizAccountDetailModel();
					accountDetail.setBusType(BizConstant.AccountBusType.UNDO_APPLY.value());
					accountDetail.setOrgId(benefitOrg.getId());
					//被推荐机构ID
					accountDetail.setTradeAmount(annualFee);
					accountDetail.setTradeStart(account.getAvailableBalance());
					accountDetail.setTradeEnd(account.getAvailableBalance().subtract(annualFee));
					accountDetail.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
					accountDetail.setTradeNo(noGenerator.getRewardTradeNo());
					bizAccountDetailMapper.insert(accountDetail);
					updateWrapper.eq("available_balance", account.getAvailableBalance());
					update.setAvailableBalance(account.getAvailableBalance().subtract(annualFee));
				}
				boolean updateResult = optimisticLockingUpdateOrgAccount(update, updateWrapper, 1);
				AssertUtil.isTrue(updateResult, "机构账户奖励更新失败，请重试");
			}
		}
	}

	/**
	 *
	 * 功能描述: 添加机构信息
	 *
	 * @param
	 * @return
	 * @author cjunyuan
	 * @date 2019/2/26 10:35
	 */
	private void addOrgInfo(BizOrgApplyModel applyInfo, SysOrgModel org){
		BizOrgInfoModel orgInfo = new BizOrgInfoModel();
		orgInfo.setOrgId(org.getId());
		orgInfo.setOrgName(org.getOrgName());
		orgInfo.setOrgCode(noGenerator.getOrgNo());
		orgInfo.setOpenType(applyInfo.getApplyType());
		BeanUtil.copyProperties(applyInfo, orgInfo);
		bizOrgInfoMapper.insert(orgInfo);
	}

	/**
	 *
	 * 功能描述: 添加机构账户
	 *
	 * @param
	 * @return
	 * @author cjunyuan
	 * @date 2019/2/26 10:35
	 */
	private void addOrgAccount(Long orgId){
		BizAccountModel account = new BizAccountModel();
		account.setOrgId(orgId);
		account.setAccountNo(noGenerator.getOrgAccountNo());
		bizAccountMapper.insert(account);
	}

	/**
	 *
	 * 功能描述: 修改专用（修改上级和同级信息）
	 *
	 * @param
	 * @return
	 * @author cjunyuan
	 * @date 2019/2/26 10:35
	 */
	private void editObjectInfo(BizOrgApplyModel applyInfo, SysOrgModel org){
		if(null != applyInfo.getOriginalId() && applyInfo.getOriginalId() > 0){
			if(BizConstant.ObjectType.SOFTWARE.value().equals(applyInfo.getOriginalType())){
				MerchantInfoModel merchantInfo = merchantInfoMapper.selectById(applyInfo.getOriginalId());
				AssertUtil.notNull(merchantInfo, "商户信息不存在");
				MerchantInfoModel updateMerchantInfo = new MerchantInfoModel();
				updateMerchantInfo.setId(merchantInfo.getId());
				updateMerchantInfo.setParentId(org.getId());
				updateMerchantInfo.setTreeStr(StringUtils.isNotBlank(org.getTreeStr()) ? org.getTreeStr()+ "," + org.getId() : "" + org.getId());
				merchantInfoMapper.updateById(updateMerchantInfo);
			}else if (BizConstant.ObjectType.ORG.value().equals(applyInfo.getOriginalType())){
				QueryWrapper<BizOrgInfoModel> orgInfoWrapper = new QueryWrapper<>();
				orgInfoWrapper.eq("org_id", applyInfo.getOriginalId());
				BizOrgInfoModel orgInfo = bizOrgInfoMapper.selectOne(orgInfoWrapper);
				AssertUtil.notNull(orgInfo, "原始机构信息不存在");
				BizOrgInfoModel updateOrgInfo = new BizOrgInfoModel();
				updateOrgInfo.setId(orgInfo.getId());
				updateOrgInfo.setParentId(org.getId());
				bizOrgInfoMapper.updateById(updateOrgInfo);
				SysOrgModel originalOrg = sysOrgMapper.selectNotDeleteOrgById(applyInfo.getOriginalId());
				AssertUtil.notNull(originalOrg, "原始机构不存在");
				SysOrgModel updateOrg = new SysOrgModel();
				updateOrg.setId(originalOrg.getId());
				updateOrg.setParentId(org.getId());
				updateOrg.setTreeStr(StringUtils.isNotBlank(org.getTreeStr()) ? org.getTreeStr()+ "," + org.getId() : "" + org.getId());
				sysOrgMapper.updateById(updateOrg);
			}
		}
	}

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

	private void checkObject(OrgApplyInfoDTO dto){
		//判断推荐机构ID是否为空，是-推荐人ID与上级ID相同，否-判断推荐人ID是否存在
		AssertUtil.isTrue(null != dto.getParentId() && dto.getParentId() > 0, "请选择上级机构");
		SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getParentId());
		AssertUtil.notNull(parentOrg, "请选择上级机构");
		dto.setTreeStr(StringUtils.isNotBlank(parentOrg.getTreeStr()) ? parentOrg.getTreeStr()+ "," + parentOrg.getId() : "" + parentOrg.getId());
		SysOrgModel recommendOrg = null;
		MerchantInfoModel recommendMerchant = null;
		if(null != dto.getRecommendId() && dto.getRecommendId() > 0){
			if(BizConstant.ObjectType.ORG.value().equals(dto.getRecommendType())){
				recommendOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getRecommendId());
				AssertUtil.notNull(recommendOrg, "推荐机构信息不存在");
				AssertUtil.isTrue(recommendOrg.getTreeStr().indexOf(dto.getParentId().toString()) > -1, "推荐机构必须属于上级团队");
				dto.setRecommendName(recommendOrg.getOrgName());
			}else if(BizConstant.ObjectType.SOFTWARE.value().equals(dto.getRecommendType())){
				recommendMerchant = merchantInfoMapper.selectById(dto.getRecommendId());
				AssertUtil.notNull(recommendMerchant, "推荐商户信息不存在");
				AssertUtil.isTrue(recommendMerchant.getTreeStr().indexOf(dto.getParentId().toString()) > -1, "推荐商户必须属于上级团队");
				dto.setRecommendName(recommendMerchant.getName());
			}
		}
		//判断修改机构树申请的类型是否对应要修改的机构/商户ID
		if(null != dto.getOriginalId() && dto.getOriginalId() > 0){
			if(BizConstant.ObjectType.ORG.value().equals(dto.getOriginalType()) && dto.getOriginalId() > 0){
				SysOrgModel originalOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getOriginalId());
				AssertUtil.notNull(originalOrg, "修改的机构信息不存在");
				if(null != recommendOrg){
					AssertUtil.isTrue(originalOrg.getTreeStr().indexOf(recommendOrg.getId().toString()) > -1, "原始机构必须属于推荐机构");
				}
				AssertUtil.isTrue(originalOrg.getTreeStr().indexOf(dto.getParentId().toString()) > -1, "原始机构必须属于上级团队");
				dto.setOriginalName(originalOrg.getOrgName());
			} else if(BizConstant.ObjectType.SOFTWARE.value().equals(dto.getOriginalType()) && dto.getOriginalId() > 0){
				MerchantInfoModel originalMerchant = merchantInfoMapper.selectById(dto.getOriginalId());
				AssertUtil.notNull(originalMerchant, "修改的商户信息不存在");
				if(null != recommendOrg){
					AssertUtil.isTrue(originalMerchant.getTreeStr().indexOf(recommendOrg.getId().toString()) > -1, "原始商户必须属于推荐机构");
				}
				if(null != recommendMerchant){
					AssertUtil.isTrue(originalMerchant.getTreeStr().indexOf(recommendMerchant.getId().toString()) > -1, "原始商户必须属于推荐商户");
				}
				AssertUtil.isTrue(originalMerchant.getTreeStr().indexOf(dto.getParentId().toString()) > -1, "原始商户必须属于上级团队");
				dto.setOriginalName(originalMerchant.getName());
			}
		}
	}

	private void addOpenLog(BizOrgApplyModel dto, BizOrgPrivilegeModel orgPrivilege, boolean isAdd){
		//免费开通
		if(null != dto.getParentId() && dto.getParentId() != 0){
			//更新可开通名额
			BizOrgPrivilegeModel updateOrgPrivilege = new BizOrgPrivilegeModel();
			UpdateWrapper<BizOrgPrivilegeModel> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", orgPrivilege.getId());
			updateWrapper.eq("reward_value", orgPrivilege.getRewardValue());
			BigDecimal tradeEnd = BigDecimal.ZERO;
			BizConstant.OpenBusType busType = null;
			if(isAdd){
				tradeEnd = orgPrivilege.getRewardValue().add(BigDecimal.ONE);
				busType = BizConstant.OpenBusType.AUDIT_FAILED;
			} else {
				tradeEnd = orgPrivilege.getRewardValue().subtract(BigDecimal.ONE);
				busType = BizConstant.OpenBusType.OPEN_SUCCESS;
			}
			updateOrgPrivilege.setRewardValue(tradeEnd);
			boolean updateResult = optimisticLockingUpdateOrgPrivilege(updateOrgPrivilege, updateWrapper, 1);
			AssertUtil.isTrue(updateResult, "机构权益更新失败，请重试");
			//添加名称开通消耗记录
			BizOpenDetailModel openDetail = new BizOpenDetailModel();
			openDetail.setOrgId(dto.getParentId());
			openDetail.setBusType(busType.value());
			openDetail.setOpenType(BizConstant.ObjectType.ORG.value());
			openDetail.setTradeStart(orgPrivilege.getRewardValue().intValue());
			openDetail.setTradeEnd(tradeEnd.intValue());
			openDetail.setTradeNum(BigDecimal.ONE.intValue());
			openDetail.setTradeNo(noGenerator.getRewardTradeNo());
			openDetail.setTradeType(isAdd ? BizConstant.TradeType.INCOME.value() : BizConstant.TradeType.EXPENDITURE.value());
			openDetail.setTypeId(dto.getOrgTypeId());
			bizOpenDetailMapper.insert(openDetail);
		}
	}

	private void addPaymentInfo(OrgApplyInfoDTO dto){
		if(!StringUtils.isBlank(dto.getPaymentPicBase64())){
			String url = addApplyInfoFile(dto.getPaymentPicBase64());
			QueryWrapper<BizPaymentPicModel> paymentPicWrapper = new QueryWrapper<>();
			paymentPicWrapper.eq("apply_id", dto.getId());
			BizPaymentPicModel oldPaymentPic = bizPaymentPicMapper.selectOne(paymentPicWrapper);
			if(null == oldPaymentPic){
				oldPaymentPic = new BizPaymentPicModel();
				oldPaymentPic.setApplyId(dto.getId());
				oldPaymentPic.setPayType(0);
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

	/**
	 *
	 * 功能描述: 乐观锁更新机构权益信息
	 *
	 * @param
	 * @return
	 * @author cjunyuan
	 * @date 2019/3/11 21:38
	 */
	private boolean optimisticLockingUpdateOrgPrivilege(BizOrgPrivilegeModel update, UpdateWrapper<BizOrgPrivilegeModel> wrapper, int updateTime){
		if(updateTime <= 5){
			if(bizOrgPrivilegeMapper.update(update, wrapper) < 1){
				optimisticLockingUpdateOrgPrivilege(update, wrapper, ++updateTime);
			}
			return true;
		} else {
			return false;
		}
	}
}
