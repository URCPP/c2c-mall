package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.BizOrgInfoMapper;
import com.diandian.dubbo.facade.model.biz.BizOrgInfoModel;
import com.diandian.dubbo.facade.service.biz.BizOrgInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizOrgInfoService")
@Slf4j
public class BizOrgInfoServiceImpl implements BizOrgInfoService {

	@Autowired
	private BizOrgInfoMapper bizOrgInfoMapper;

	@Override
	public BizOrgInfoModel getBizOrgInfoById(Long orgId){
		QueryWrapper<BizOrgInfoModel> wrapper = new QueryWrapper<>();
		wrapper.eq("org_id", orgId);
		return bizOrgInfoMapper.selectOne(wrapper);
	}
}
