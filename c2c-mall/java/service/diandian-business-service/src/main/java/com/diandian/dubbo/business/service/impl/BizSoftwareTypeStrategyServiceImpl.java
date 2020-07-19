package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizSoftwareTypeStrategyMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeStrategyModel;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeStrategyService;
import com.diandian.dubbo.facade.vo.SoftWareStrategyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 软件类型策略
 *
 * @author zweize
 * @date 2019/02/19
 */
@Service("bizSoftwareTypeStrategyService")
@Slf4j
public class BizSoftwareTypeStrategyServiceImpl implements BizSoftwareTypeStrategyService {

	@Autowired
	private BizSoftwareTypeStrategyMapper bizSoftwareTypeStrategyMapper;


	@Override
	public PageResult listPage(Map<String, Object> params) {
		Page<SoftWareStrategyVO> page = new PageWrapper<SoftWareStrategyVO>(params).getPage();
		Page<SoftWareStrategyVO> resultPage = bizSoftwareTypeStrategyMapper.listBizSoftwareTypeStrategyPage(page, params);
		return new PageResult(resultPage);
	}

	@Override
	public BizSoftwareTypeStrategyModel getById(Long id) {
		return bizSoftwareTypeStrategyMapper.selectById(id);
	}

	@Override
	public void save(BizSoftwareTypeStrategyModel bizSoftwareTypeStrategyModel) {
		bizSoftwareTypeStrategyMapper.insert(bizSoftwareTypeStrategyModel);
	}

	@Override
	public void updateById(BizSoftwareTypeStrategyModel bizSoftwareTypeStrategyModel) {
		bizSoftwareTypeStrategyMapper.updateById(bizSoftwareTypeStrategyModel);
	}

	@Override
	public void removeById(Long id) {
		bizSoftwareTypeStrategyMapper.deleteById(id);
	}

	@Override
	public BizSoftwareTypeStrategyModel getByRewardType(Integer strategyType,Integer rewardType) {
		QueryWrapper<BizSoftwareTypeStrategyModel> qw = new QueryWrapper<>();
		qw.eq("state",BizConstant.STATE_NORMAL);
		qw.eq("strategy_type",strategyType);
		qw.eq("reward_type",rewardType);
		qw.orderByDesc("create_time");
		List<BizSoftwareTypeStrategyModel> list = bizSoftwareTypeStrategyMapper.selectList(qw);
		return list.isEmpty() ? null : list.get(0);
	}
}
