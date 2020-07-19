package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.dubbo.business.mapper.BizOrgTypeStrategyMapper;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.biz.OrgTypeStrategyQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOrgTypeStrategyModel;
import com.diandian.dubbo.facade.service.biz.BizOrgTypeStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 机构类型策略
 *
 * @author zweize
 * @date 2019/02/19
 */
@Service("bizOrgTypeStrategyService")
@Slf4j
public class BizOrgTypeStrategyServiceImpl implements BizOrgTypeStrategyService {

	@Autowired
	private BizOrgTypeStrategyMapper bizOrgTypeStrategyMapper;

	@Override
	public PageResult listPage(Map<String, Object> params) {
		String strategyTypeName = null == params.get("strategyTypeName")?"":params.get("strategyTypeName").toString();
		IPage<BizOrgTypeStrategyModel> page = bizOrgTypeStrategyMapper.selectPage(
				new PageWrapper<BizOrgTypeStrategyModel>(params).getPage(),
				new QueryWrapper<BizOrgTypeStrategyModel>().orderByAsc("create_time")
						.like(StrUtil.isNotBlank(strategyTypeName),"strategy_type_name",strategyTypeName)
				        .eq(null != params.get("orgTypeId"),"org_type_id",params.get("orgTypeId"))
		);
		return new PageResult(page);
	}

	@Override
	public BizOrgTypeStrategyModel getById(Long id) {
		return bizOrgTypeStrategyMapper.selectById(id);
	}

	@Override
	public List<BizOrgTypeStrategyModel> list(OrgTypeStrategyQueryDTO dto){
		QueryWrapper<BizOrgTypeStrategyModel> wrapper = new QueryWrapper<>();
		wrapper.eq(null != dto.getOrgTypeId() && dto.getOrgTypeId() > 0, "org_type_id", dto.getOrgTypeId());
		wrapper.eq(null != dto.getStrategyType(), "strategy_type", dto.getStrategyType());
		return bizOrgTypeStrategyMapper.selectList(wrapper);
	}

	@Override
	public boolean updateById(BizOrgTypeStrategyModel orgTypeStrategy) {
		Integer result = bizOrgTypeStrategyMapper.updateById(orgTypeStrategy);
		return null != result && result >= 1;
	}

	@Override
	public boolean save(BizOrgTypeStrategyModel orgTypeStrategy) {
		Integer result = bizOrgTypeStrategyMapper.insert(orgTypeStrategy);
		return null != result && result >= 1;
	}

	@Override
	public boolean removeById(Long id) {
		Integer result = bizOrgTypeStrategyMapper.deleteById(id);
		return null != result && result >= 0;
	}

	@Override
	public BizOrgTypeStrategyModel getOne(OrgTypeStrategyQueryDTO dto){
		QueryWrapper<BizOrgTypeStrategyModel> wrapper = new QueryWrapper<>();
		wrapper.eq(null != dto.getOrgTypeId() && dto.getOrgTypeId() > 0, "org_type_id", dto.getOrgTypeId());
		wrapper.eq(null != dto.getRewardType(), "reward_type", dto.getRewardType());
		wrapper.eq(null != dto.getStrategyType(), "strategy_type", dto.getStrategyType());
		return bizOrgTypeStrategyMapper.selectOne(wrapper);
	}

}
