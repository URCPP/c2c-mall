package com.diandian.admin.business.modules.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.biz.mapper.BizWithdrawalApplyMapper;
import com.diandian.admin.business.modules.biz.service.BizPaymentPicService;
import com.diandian.admin.business.modules.biz.service.BizWithdrawalApplyService;
import com.diandian.admin.common.constant.BisinessConstant;
import com.diandian.admin.model.biz.BizPaymentPicModel;
import com.diandian.admin.model.biz.BizWithdrawalApplyModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.biz.AccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizAccountDetailModel;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.service.biz.BizAccountDetailService;
import com.diandian.dubbo.facade.service.biz.BizAccountService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 提现申请表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizWithdrawalApplyService")
@Slf4j
public class BizWithdrawalApplyServiceImpl extends ServiceImpl<BizWithdrawalApplyMapper, BizWithdrawalApplyModel> implements BizWithdrawalApplyService {

	@Autowired
	private BizWithdrawalApplyMapper bizWithdrawalApplyMapper;
	@Autowired
	BizAccountDetailService bizAccountDetailService;
	@Autowired
	BizAccountService bizAccountService;
	@Autowired
	NoGenerator noGenerator;
	@Autowired
	BizPaymentPicService bizPaymentPicService;

	@Override
	public Integer countByOrgId(Long orgId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取前月的第一天0秒
		Calendar cal_1=Calendar.getInstance();//获取当前日期
		cal_1.set(Calendar.HOUR_OF_DAY,0);
		cal_1.set(Calendar.MINUTE,0);
		cal_1.set(Calendar.SECOND,0);
		String firstDay = format.format(cal_1.getTime());
		//获取前月的最后一天最后一秒
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.HOUR_OF_DAY,23);
		cale.set(Calendar.MINUTE,59);
		cale.set(Calendar.SECOND,59);
		String lastDay = format.format(cale.getTime());
		QueryWrapper<BizWithdrawalApplyModel> qw = new QueryWrapper<>();
		qw.eq("agent_id",orgId);
		qw.ge("create_time",firstDay);
		qw.le("create_time",lastDay);
		return bizWithdrawalApplyMapper.selectCount(qw);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveBizWithdrawalApply(BizWithdrawalApplyModel bizWithdrawalApplyModel) {
		bizWithdrawalApplyMapper.insert(bizWithdrawalApplyModel);
		// 新增账号明细 ,提现转出
		BizAccountDetailModel bizAccountDetailModel = new BizAccountDetailModel();
		bizAccountDetailModel.setTradeNo(noGenerator.getTradeNo());
		bizAccountDetailModel.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
		bizAccountDetailModel.setBusType(BizConstant.AccountBusType.WITHDRAW.value());
		bizAccountDetailModel.setTradeAmount(bizWithdrawalApplyModel.getWithdrawalAmount());
		bizAccountDetailModel.setTradeStart(bizWithdrawalApplyModel.getWithdrawalStart());
		bizAccountDetailModel.setTradeEnd(bizWithdrawalApplyModel.getWithdrawalEnd());
		bizAccountDetailModel.setOrgId(bizWithdrawalApplyModel.getAgentId());
		bizAccountDetailService.save(bizAccountDetailModel);
		// 设置 账号可用金额
		bizAccountService.updateBalance(bizAccountDetailModel.getOrgId(),bizAccountDetailModel.getTradeStart(),bizAccountDetailModel.getTradeEnd());
	}

	@Override
	public PageResult listPage(Map<String, Object> params) {
		Page<Map<String,Object>> page = new PageWrapper<Map<String,Object>>(params).getPage();
		Page<Map<String,Object>> resultPage = bizWithdrawalApplyMapper.listWithdrawalApplyPage(page, params);
		return new PageResult(resultPage);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void auditBizWithdrawalApply(BizWithdrawalApplyModel bizWithdrawalApplyModel) {
		bizWithdrawalApplyMapper.updateById(bizWithdrawalApplyModel);
		if (BisinessConstant.AuditState.FAIL.value() == bizWithdrawalApplyModel.getAuditState()){
			failWithdrawalApply(bizWithdrawalApplyModel);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void paymentBizWithdrawalApply(BizWithdrawalApplyModel bizWithdrawalApplyModel,String pic) {
		 bizWithdrawalApplyMapper.updateById(bizWithdrawalApplyModel);
		if (BisinessConstant.PaymentState.SUCCESS.value() == bizWithdrawalApplyModel.getPaymentState()){
             // 保存付款凭证
			 BizPaymentPicModel bizPaymentPicModel = new BizPaymentPicModel();
			 bizPaymentPicModel.setApplyId(bizWithdrawalApplyModel.getId());
			 bizPaymentPicModel.setApplyType(BizConstant.PicType.WITHDRAW.value());
			 bizPaymentPicModel.setPic(pic);
			 bizPaymentPicService.save(bizPaymentPicModel);
		}else if (BisinessConstant.PaymentState.FAIL.value() == bizWithdrawalApplyModel.getPaymentState()){
			 failWithdrawalApply(bizWithdrawalApplyModel);
		}
	}

	private void failWithdrawalApply(BizWithdrawalApplyModel bizWithdrawalApplyModel){
		// 付款失败,新增账号明细,提现失败返还
		BizAccountDetailModel bizAccountDetailModel = new BizAccountDetailModel();
		bizAccountDetailModel.setTradeNo(noGenerator.getTradeNo());
		bizAccountDetailModel.setTradeType(BizConstant.TradeType.INCOME.value());
		bizAccountDetailModel.setBusType(BizConstant.AccountBusType.WITHDRAW_ROLLBACK.value());
		bizAccountDetailModel.setTradeAmount(bizWithdrawalApplyModel.getWithdrawalAmount());
		bizAccountDetailModel.setTradeStart(bizWithdrawalApplyModel.getWithdrawalEnd());
		bizAccountDetailModel.setTradeEnd(bizWithdrawalApplyModel.getWithdrawalStart());
		bizAccountDetailModel.setOrgId(bizWithdrawalApplyModel.getAgentId());
		bizAccountDetailService.save(bizAccountDetailModel);
		// 设置 账号可用金额
		bizAccountService.updateBalance(bizAccountDetailModel.getOrgId(),bizAccountDetailModel.getTradeStart(),bizAccountDetailModel.getTradeEnd());
	}

}
