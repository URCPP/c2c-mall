package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizMallSuccessCaseMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizMallSuccessCaseModel;
import com.diandian.dubbo.facade.service.biz.BizMallSuccessCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商城成功案例表
 *
 * @author zweize
 * @date 2019/03/06
 */
@Service("bizMallSuccessCaseService")
@Slf4j
public class BizMallSuccessCaseServiceImpl implements BizMallSuccessCaseService {

	@Autowired
	private BizMallSuccessCaseMapper bizMallSuccessCaseMapper;

	@Override
	public PageResult listPage(Map<String, Object> params) {
		Page<BizMallSuccessCaseModel> page = new PageWrapper<BizMallSuccessCaseModel>(params).getPage();
		Page resultPage = bizMallSuccessCaseMapper.listCasePage(page,params);
		return new PageResult(resultPage);
	}

	@Override
	public void save(BizMallSuccessCaseModel bizMallSuccessCaseModel) {
		bizMallSuccessCaseMapper.insert(bizMallSuccessCaseModel);
	}

	@Override
	public void updateById(BizMallSuccessCaseModel bizMallSuccessCaseModel) {
		bizMallSuccessCaseMapper.updateById(bizMallSuccessCaseModel);
	}

	@Override
	public void deleteById(Long id) {
		bizMallSuccessCaseMapper.deleteById(id);
	}

	@Override
	public BizMallSuccessCaseModel getById(Long id) {
		return bizMallSuccessCaseMapper.selectById(id);
	}

	@Override
	public List<BizMallSuccessCaseModel> listCataLog() {
		QueryWrapper<BizMallSuccessCaseModel> qw = new QueryWrapper<>();
		qw.eq("type",0);
		return bizMallSuccessCaseMapper.selectList(qw);
	}
}
