package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizAccountMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.biz.AccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizAccountDetailModel;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.model.biz.BizCommissionDetailModel;
import com.diandian.dubbo.facade.service.biz.BizAccountDetailService;
import com.diandian.dubbo.facade.service.biz.BizAccountService;
import com.diandian.dubbo.facade.service.biz.BizCommissionDetailService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 机构账号表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizAccountService")
@Slf4j
public class BizAccountServiceImpl implements BizAccountService {

	@Autowired
	private BizAccountMapper accountMapper;
	@Autowired
	NoGenerator noGenerator;
	@Autowired
	BizAccountDetailService bizAccountDetailService;
	@Autowired
	BizCommissionDetailService bizCommissionDetailService;

	@Override
	public PageResult listPage(Map<String, Object> params) {
		Page<BizAccountModel> page = new PageWrapper<BizAccountModel>(params).getPage();
		Page resultPage = accountMapper.listAccountPage(page,params);
		return new PageResult(resultPage);
	}

	@Override
	public BizAccountModel getById(Long accountId){
		return accountMapper.selectById(accountId);
	}


	@Override
	public boolean updateById(BizAccountModel account) {
		Integer result = accountMapper.updateById(account);
		return null != result && result >= 1;
	}

	@Override
	public BizAccountModel getOne(AccountQueryDTO dto){
		QueryWrapper<BizAccountModel> wrapper = new QueryWrapper<>();
		wrapper.eq(null != dto.getId() && dto.getId() > 0, "id", dto.getId());
		wrapper.eq(null != dto.getOrgId() && dto.getOrgId() > 0, "org_id", dto.getOrgId());
		return accountMapper.selectOne(wrapper);
	}

	@Override
	public List<BizAccountModel> list(BizAccountModel bizAccountModel) {
		QueryWrapper<BizAccountModel> qw = new QueryWrapper<>();
		return accountMapper.selectList(qw);
	}

	@Override
	public BizAccountModel getByOrgId(Long orgId) {
		QueryWrapper<BizAccountModel> qw = new QueryWrapper<>();
		qw.eq("org_id",orgId);
		return accountMapper.selectOne(qw);
	}

	@Override
	public boolean updateBalance(Long orgId, BigDecimal oldBalance, BigDecimal newBalance) {
		return accountMapper.updateBalance(orgId,oldBalance,newBalance) > 0;
	}

	@Override
	public boolean updateCommission(Long orgId, BigDecimal oldCommission, BigDecimal newCommission) {
		return accountMapper.updateCommission(orgId,oldCommission,newCommission) > 0;
	}

	@Override
	public boolean updateBonus(Long orgId, BigDecimal oldBonus, BigDecimal newBonus) {
		return accountMapper.updateBonus(orgId,oldBonus,newBonus) > 0;
	}

	@Override
	public boolean updateBonusTransferBalance(Long orgId, BigDecimal oldBonus, BigDecimal newBonus) {
		return accountMapper.updateBonusTransferBalance(orgId,oldBonus,newBonus)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateCommissionTransferBalance(BizAccountModel bizAccountModel) {
		// 新增账号明细
		BizAccountDetailModel bizAccountDetailModel = new BizAccountDetailModel();
		bizAccountDetailModel.setTradeNo(noGenerator.getTradeNo());
		bizAccountDetailModel.setTradeType(BizConstant.TradeType.INCOME.value());
		// 待结算转入
		bizAccountDetailModel.setBusType(BizConstant.AccountBusType.SALES.value());
		bizAccountDetailModel.setTradeAmount(bizAccountModel.getCommission());
		bizAccountDetailModel.setTradeStart(bizAccountModel.getAvailableBalance());
		bizAccountDetailModel.setTradeEnd(bizAccountModel.getAvailableBalance().add(bizAccountModel.getCommission()));
		bizAccountDetailModel.setOrgId(bizAccountModel.getOrgId());
		bizAccountDetailService.save(bizAccountDetailModel);

		// 增加销售结算明细
		BizCommissionDetailModel bizCommissionDetailModel = new BizCommissionDetailModel();
		bizCommissionDetailModel.setOrderDetailId(bizAccountDetailModel.getId());
		bizCommissionDetailModel.setOrderNo(bizAccountDetailModel.getTradeNo());
		bizCommissionDetailModel.setTradeNo(noGenerator.getTradeNo());
		bizCommissionDetailModel.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
		bizCommissionDetailModel.setBusType(BizConstant.CommissionBusType.TRANSFER_BALANCE.value());
		bizCommissionDetailModel.setTradeAmount(bizAccountModel.getCommission());
		bizCommissionDetailModel.setTradeStart(bizAccountModel.getCommission());
		bizCommissionDetailModel.setTradeEnd(BigDecimal.ZERO);
		bizCommissionDetailModel.setOrgId(bizAccountModel.getOrgId());
		bizCommissionDetailService.save(bizCommissionDetailModel);

		// 设置 账号可用金额，待结算金额
		return accountMapper.updateCommissionTransferBalance(bizAccountModel.getOrgId()) > 0;
	}
}
