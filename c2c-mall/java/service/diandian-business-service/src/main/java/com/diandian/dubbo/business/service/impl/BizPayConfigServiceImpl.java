package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizPayConfigMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizPayConfigModel;
import com.diandian.dubbo.facade.service.biz.BizPayConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 支付管理表
 *
 * @author zweize
 * @date 2019/03/04
 */
@Service("bizPayConfigService")
@Slf4j
public class BizPayConfigServiceImpl implements BizPayConfigService {

	@Autowired
	private BizPayConfigMapper bizPayConfigMapper;

	@Override
	public PageResult listPage(Map<String, Object> params) {
		String payName = null == params.get("payName")?"":params.get("payName").toString();
		IPage<BizPayConfigModel> page = bizPayConfigMapper.selectPage(
				new PageWrapper<BizPayConfigModel>(params).getPage(),
				new QueryWrapper<BizPayConfigModel>().orderByAsc("create_time")
						.like(StrUtil.isNotBlank(payName),"pay_name",payName)
		);
		return new PageResult(page);
	}

	@Override
	public BizPayConfigModel getById(Long id) {
		return bizPayConfigMapper.selectById(id);
	}

	@Override
	public void updateById(BizPayConfigModel bizPayConfigModel) {
		bizPayConfigMapper.updateById(bizPayConfigModel);
	}

	@Override
	public void save(BizPayConfigModel bizPayConfigModel) {
		bizPayConfigMapper.insert(bizPayConfigModel);
	}

	@Override
	public void updateStateByIdBatch(List<Long> ids, Integer state) {
		BizPayConfigModel  bizPayConfigModel = new BizPayConfigModel();
		bizPayConfigModel.setState(state);
		bizPayConfigMapper.update(bizPayConfigModel, new LambdaQueryWrapper<BizPayConfigModel>().in(BizPayConfigModel::getId, ids));
	}

	@Override
	public List<BizPayConfigModel> list() {
		QueryWrapper<BizPayConfigModel> qw = new QueryWrapper<>();
		qw.orderByAsc("sort_num");
		return bizPayConfigMapper.selectList(qw);
	}
}
