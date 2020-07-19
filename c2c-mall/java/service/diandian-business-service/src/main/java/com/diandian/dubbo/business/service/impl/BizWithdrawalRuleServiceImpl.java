package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizWithdrawalRuleMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalRuleModel;
import com.diandian.dubbo.facade.service.biz.BizWithdrawalRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 提现规则表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizWithdrawalRuleService")
@Slf4j
public class BizWithdrawalRuleServiceImpl  implements BizWithdrawalRuleService {

	@Autowired
	private BizWithdrawalRuleMapper bizWithdrawalRuleMapper;

	@Override
	public PageResult listPage(Map<String, Object> params) {
		String name = null == params.get("name")?"":params.get("name").toString();
		IPage<BizWithdrawalRuleModel> page = bizWithdrawalRuleMapper.selectPage(
				new PageWrapper<BizWithdrawalRuleModel>(params).getPage(),
				new QueryWrapper<BizWithdrawalRuleModel>().orderByAsc("create_time")
						.like(StrUtil.isNotBlank(name),"name",name)
		);
		return new PageResult(page);
	}

	@Override
	public BizWithdrawalRuleModel getBizWithdrawalRuleModel() {
		QueryWrapper<BizWithdrawalRuleModel> qw = new QueryWrapper<>();
		qw.eq("state",1);
		return bizWithdrawalRuleMapper.selectOne(qw);
	}


	@Override
	public BizWithdrawalRuleModel getByName(String name) {
		QueryWrapper<BizWithdrawalRuleModel> qw = new QueryWrapper<>();
		qw.eq("name",name);
		return bizWithdrawalRuleMapper.selectOne(qw);
	}

	@Override
	public BizWithdrawalRuleModel getById(Long id) {
		return bizWithdrawalRuleMapper.selectById(id);
	}

	@Override
	public void update(BizWithdrawalRuleModel bizWithdrawalRuleModel, Wrapper wrapper) {
		bizWithdrawalRuleMapper.update(bizWithdrawalRuleModel, wrapper);
	}

	@Override
	public void updateById(BizWithdrawalRuleModel bizWithdrawalRuleModel) {
		bizWithdrawalRuleMapper.updateById(bizWithdrawalRuleModel);
	}

	@Override
	public void save(BizWithdrawalRuleModel bizWithdrawalRuleModel) {
		bizWithdrawalRuleMapper.insert(bizWithdrawalRuleModel);
	}

	@Override
	public void removeById(Long id) {
		bizWithdrawalRuleMapper.deleteById(id);
	}
}
