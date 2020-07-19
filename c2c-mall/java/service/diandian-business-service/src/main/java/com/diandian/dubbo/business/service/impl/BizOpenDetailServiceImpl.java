package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizOpenDetailMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.service.biz.BizOpenDetailService;
import com.diandian.dubbo.facade.vo.OrgOpenDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构开通明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizOpenDetailService")
@Slf4j
public class BizOpenDetailServiceImpl implements BizOpenDetailService {

	@Autowired
	private BizOpenDetailMapper bizOpenDetailMapper;


	@Override
	public PageResult listPage(OrgAccountQueryDTO query) {
		Page<OrgOpenDetailVO> page = new Page<>();
        if(null != query.getPageSize() && query.getPageSize() > 0){
            page.setSize(query.getPageSize());
        }
        if(null != query.getCurPage() && query.getCurPage() > 0){
            page.setCurrent(query.getCurPage());
        }
        page = bizOpenDetailMapper.orgOpenDetailListPage(page, query);
		return new PageResult(page);
	}
}
