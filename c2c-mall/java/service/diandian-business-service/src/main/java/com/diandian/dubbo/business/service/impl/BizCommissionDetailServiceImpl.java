package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizCommissionDetailMapper;
import com.diandian.dubbo.common.util.DateToolUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.model.biz.BizCommissionDetailModel;
import com.diandian.dubbo.facade.model.biz.BizOrgInfoModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.order.OrderAfterSaleApplyModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.service.biz.BizAccountService;
import com.diandian.dubbo.facade.service.biz.BizCommissionDetailService;
import com.diandian.dubbo.facade.service.biz.BizConfigService;
import com.diandian.dubbo.facade.service.biz.BizOrgInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.order.OrderAfterSaleApplyService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeService;
import com.diandian.dubbo.facade.vo.OrgAccountDetailVO;
import com.diandian.dubbo.facade.vo.OrgAccountStatementVO;
import com.diandian.dubbo.facade.vo.StatisticsSalesOverviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


/**
 * 销售结算明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizCommissionDetailService")
@Slf4j
public class BizCommissionDetailServiceImpl implements BizCommissionDetailService {

	private final BigDecimal percent = new BigDecimal(100);

	@Autowired
	BizCommissionDetailMapper bizCommissionDetailMapper;
	@Autowired
	BizAccountService bizAccountService;
	@Autowired
	NoGenerator noGenerator;
	@Autowired
	OrderInfoService orderInfoService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	MerchantInfoService merchantInfoService;
	@Autowired
	BizOrgInfoService bizOrgInfoService;
	@Autowired
	SysOrgTypeService sysOrgTypeService;
	@Autowired
	OrderAfterSaleApplyService orderAfterSaleApplyService;

	@Override
	public PageResult listPage(Map<String, Object> params) {
		Date startTime = null;
		Date endTime = null;
		if (null == params.get("startTime") && StrUtil.isNotBlank(params.get("startTime").toString())){
			 startTime = DateUtil.beginOfDay(DateUtil.parseDate(params.get("startTime").toString()));
		}
		if (null == params.get("endTime") && StrUtil.isNotBlank(params.get("endTime").toString())){
			 endTime = DateUtil.beginOfDay(DateUtil.parseDate(params.get("endTime").toString()));
		}
		IPage<BizCommissionDetailModel> page = bizCommissionDetailMapper.selectPage(
				new PageWrapper<BizCommissionDetailModel>(params).getPage(),
				new QueryWrapper<BizCommissionDetailModel>().orderByAsc("create_time")
						.eq(null != params.get("orgId"), "org_id", params.get("orgId"))
						.eq(StrUtil.isNotBlank(String.valueOf(params.get("tradeType"))),"trade_type",params.get("tradeType"))
						.eq(StrUtil.isNotBlank(String.valueOf(params.get("busType"))),"bus_type",params.get("busType"))
						.ge(null != startTime,"create_time", startTime)
						.le(null != endTime,"create_time", endTime)
		);
		return new PageResult(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateSaleCommissionReward(OrderDetailModel orderDetailModel, BigDecimal taxPoint) {
		//获取订单信息
		OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderDetailModel.getOrderNo());
		MerchantInfoModel merchantInfoModel = merchantInfoService.getMerchantInfoById(orderInfoModel.getMerchantId());
		AssertUtil.notNull(merchantInfoModel,"商户不存在");
		// 个人销售业绩奖励
		personCommission(orderDetailModel,merchantInfoModel.getParentId(),merchantInfoModel.getId(),BizConstant.TradeType.INCOME.value(), true, taxPoint);
		// 团队销售业绩奖励
		teamCommission(orderDetailModel, merchantInfoModel.getParentId(), merchantInfoModel.getId(), BizConstant.TradeType.INCOME.value(), taxPoint);
		// 更新订单分润状态
		orderDetailModel.setShareFlag(BizConstant.OrderShareFlag.SHARE.value());
		orderDetailModel.setUpdateTime(new Date());
		orderDetailService.updateById(orderDetailModel);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateReturnCommissionReward(OrderDetailModel orderDetailModel, BigDecimal taxPoint) {
		List<OrderAfterSaleApplyModel> list = orderAfterSaleApplyService.listByOrderDetailId(orderDetailModel.getId());
		if (!list.isEmpty() && list.get(0).getState() == BizConstant.OrderAfterSaleState.COMPLETE.value()){
			//获取订单信息
			OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderDetailModel.getOrderNo());
			MerchantInfoModel merchantInfoModel = merchantInfoService.getMerchantInfoById(orderInfoModel.getMerchantId());
			AssertUtil.notNull(merchantInfoModel, "商户不存在");
			// 个人销售业绩奖励回退
			personCommission(orderDetailModel, merchantInfoModel.getParentId(), merchantInfoModel.getId(),BizConstant.TradeType.EXPENDITURE.value(), true, taxPoint);
			// 团队销售业绩奖励回退
			teamCommission(orderDetailModel, merchantInfoModel.getParentId(), merchantInfoModel.getId(), BizConstant.TradeType.EXPENDITURE.value(), taxPoint);
			// 更新订单分润状态
			orderDetailModel.setShareFlag(BizConstant.OrderShareFlag.RETURN_SHARE.value());
			orderDetailModel.setUpdateTime(new Date());
			orderDetailService.updateById(orderDetailModel);
		}
	}

	private void personCommission(OrderDetailModel orderDetailModel, Long orgId, Long merchantId,Integer tradeType, boolean isDirect, BigDecimal taxPoint){
		BizOrgInfoModel bizOrgInfoModel = bizOrgInfoService.getBizOrgInfoById(orgId);
		AssertUtil.notNull(bizOrgInfoModel,"机构不存在");
		SysOrgTypeModel sysOrgTypeModel = sysOrgTypeService.getById(bizOrgInfoModel.getOrgTypeId());
		BigDecimal proportion = isDirect ? sysOrgTypeModel.getPersonalDirectProportion() : sysOrgTypeModel.getPersonalProportion();
		if (proportion.compareTo(BigDecimal.ZERO) > 0){
			// 获取机构账号信息
			BizAccountModel bizAccountModel = bizAccountService.getByOrgId(orgId);
			// 增加销售结算明细
			BigDecimal tradeAmount = orderDetailModel.getPrice().multiply(new BigDecimal(orderDetailModel.getNum())).multiply(proportion).divide(percent);
			if(taxPoint.compareTo(BigDecimal.ZERO) <= 0){
				tradeAmount =  tradeAmount.setScale(2, RoundingMode.DOWN);
			}else {
				tradeAmount = tradeAmount.subtract(tradeAmount.multiply(taxPoint.divide(percent))).setScale(2, RoundingMode.DOWN);
			}
			BizCommissionDetailModel bizCommissionDetailModel = new BizCommissionDetailModel();
			bizCommissionDetailModel.setOrderDetailId(orderDetailModel.getId());
			bizCommissionDetailModel.setOrderNo(orderDetailModel.getOrderNo());
			bizCommissionDetailModel.setTradeNo(noGenerator.getTradeNo());
			bizCommissionDetailModel.setTradeType(tradeType);
			bizCommissionDetailModel.setTradeAmount(tradeAmount);
			bizCommissionDetailModel.setTradeStart(bizAccountModel.getCommission());
			if (BizConstant.TradeType.INCOME.value() == tradeType){
				bizCommissionDetailModel.setTradeEnd(bizAccountModel.getCommission().add(tradeAmount));
				bizCommissionDetailModel.setBusType(BizConstant.CommissionBusType.PERSON.value());
			} else {
				bizCommissionDetailModel.setTradeEnd(bizAccountModel.getCommission().subtract(tradeAmount));
				bizCommissionDetailModel.setBusType(BizConstant.CommissionBusType.REFUND.value());
			}
			bizCommissionDetailModel.setOrgId(orgId);
			bizCommissionDetailModel.setFromOrgId(merchantId);
			bizCommissionDetailMapper.insert(bizCommissionDetailModel);
			// 更新账号 待结算金额
			boolean accountUpdateResult = bizAccountService.updateCommission(orgId, bizCommissionDetailModel.getTradeStart(), bizCommissionDetailModel.getTradeEnd());
			if(!accountUpdateResult){
				log.error("个人总销售业绩分润【账户更新】执行失败，失败订单{}，失败账户{}", orderDetailModel, bizAccountModel);
			}
			if (null != bizOrgInfoModel.getParentId()){
				personCommission(orderDetailModel,bizOrgInfoModel.getParentId(),merchantId,tradeType, false, taxPoint);
			}
		}
	}

	private void teamCommission(OrderDetailModel orderDetailModel, Long parentId, Long fromObjectId, Integer tradeType, BigDecimal taxPoint){
		BizOrgInfoModel parentOrg = bizOrgInfoService.getBizOrgInfoById(parentId);
		BizOrgInfoModel recommendOrgInfo = bizOrgInfoService.getBizOrgInfoById(parentOrg.getRecommendId());
		if (null != recommendOrgInfo){
			SysOrgTypeModel sysOrgTypeModel = sysOrgTypeService.getById(recommendOrgInfo.getOrgTypeId());
			if (sysOrgTypeModel.getTeamProportion().compareTo(BigDecimal.ZERO) > 0){
				// 获取机构账号信息
				BizAccountModel bizAccountModel = bizAccountService.getByOrgId(recommendOrgInfo.getOrgId());
				// 增加销售结算明细
				BigDecimal tradeAmount = orderDetailModel.getPrice().multiply(new BigDecimal(orderDetailModel.getNum())).multiply(sysOrgTypeModel.getTeamProportion()).divide(percent);
				if(taxPoint.compareTo(BigDecimal.ZERO) <= 0){
					tradeAmount = tradeAmount.setScale(2, RoundingMode.DOWN);
				}else {
					tradeAmount = tradeAmount.subtract(tradeAmount.multiply(taxPoint.divide(percent))).setScale(2, RoundingMode.DOWN);
				}
				BizCommissionDetailModel bizCommissionDetailModel = new BizCommissionDetailModel();
				bizCommissionDetailModel.setOrderDetailId(orderDetailModel.getId());
				bizCommissionDetailModel.setOrderNo(orderDetailModel.getOrderNo());
				bizCommissionDetailModel.setTradeNo(noGenerator.getTradeNo());
				bizCommissionDetailModel.setTradeType(tradeType);
				bizCommissionDetailModel.setTradeAmount(tradeAmount);
				bizCommissionDetailModel.setTradeStart(bizAccountModel.getCommission());
				bizCommissionDetailModel.setOrgId(recommendOrgInfo.getOrgId());
				bizCommissionDetailModel.setFromOrgId(fromObjectId);
				if (BizConstant.TradeType.INCOME.value().equals(tradeType)){
					bizCommissionDetailModel.setBusType(BizConstant.CommissionBusType.TEAM.value());
					bizCommissionDetailModel.setTradeEnd(bizAccountModel.getCommission().add(tradeAmount));
				}else {
					bizCommissionDetailModel.setBusType(BizConstant.CommissionBusType.REFUND.value());
					bizCommissionDetailModel.setTradeEnd(bizAccountModel.getCommission().subtract(tradeAmount));
				}
				bizCommissionDetailMapper.insert(bizCommissionDetailModel);
				// 更新账号 待结算金额
				boolean accountUpdateResult = bizAccountService.updateCommission(recommendOrgInfo.getOrgId(),bizCommissionDetailModel.getTradeStart(),bizCommissionDetailModel.getTradeEnd());
				if(!accountUpdateResult){
					log.error("团队总销售业绩分润【账户更新】执行失败，失败订单{}，失败账户{}", orderDetailModel, bizAccountModel);
				}
			}
		}
		if (null != parentOrg.getParentId()){
			teamCommission(orderDetailModel,parentOrg.getParentId(),fromObjectId, tradeType, taxPoint);
		}
	}

	@Override
	public void save(BizCommissionDetailModel bizCommissionDetailModel) {
		bizCommissionDetailMapper.insert(bizCommissionDetailModel);
	}

	@Override
	public List<OrgAccountStatementVO> statisticsEveryDay(OrgAccountQueryDTO query){
		List<String> dateList = DateToolUtil.getBetweenDate(DateToolUtil.parseDate(query.getStartTime()), DateToolUtil.parseDate(query.getEndTime()));
		if(BizConstant.StatisticalUnit.MONTH.equals(query.getUnit())){
			dateList = DateToolUtil.getBetweenMonth(DateToolUtil.parseDate(query.getStartTime()), DateToolUtil.parseDate(query.getEndTime()));
		}
		List<OrgAccountStatementVO> list = new ArrayList<>();
		if(null == query.getUnit()){
			query.setUnit(BizConstant.StatisticalUnit.DAY);
		}
		List<OrgAccountStatementVO> statisticsList = bizCommissionDetailMapper.statisticsEveryDay(query);
		for (int i = 0;i < dateList.size();i++){
			String date = dateList.get(i);
			OrgAccountStatementVO vo = new OrgAccountStatementVO();
			vo.setAccountType(3);
			vo.setDate(date);
			vo.setIncomeCnt(0);
			vo.setIncomeAmount(BigDecimal.ZERO);
			vo.setExpenditureCnt(0);
			vo.setExpenditureAmount(BigDecimal.ZERO);
			for (OrgAccountStatementVO old : statisticsList){
				if(null != old && date.equals(old.getDate())){
					BeanUtil.copyProperties(old, vo);
					statisticsList.remove(old);
                    break;
				}
			}
			list.add(vo);
		}
		return list;
	}

	@Override
	public PageResult listPage(OrgAccountQueryDTO query){
		Page<OrgAccountDetailVO> page = new Page<>();
		if(null != query.getPageSize() && query.getPageSize() > 0){
			page.setSize(query.getPageSize());
		}
		if(null != query.getCurPage() && query.getCurPage() > 0){
			page.setCurrent(query.getCurPage());
		}
		page = bizCommissionDetailMapper.orgCommissionDetailListPage(page, query);
		return new PageResult(page);
	}

	@Override
	public StatisticsSalesOverviewVO statisticsTotalAmount(Map<String, Object> params){
		return bizCommissionDetailMapper.statisticsTotalAmount(params);
	}
}
