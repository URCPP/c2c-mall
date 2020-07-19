package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizMallNewsMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizMallNewsModel;
import com.diandian.dubbo.facade.service.biz.BizMallNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商城新闻表
 *
 * @author zweize
 * @date 2019/02/28
 */
@Service("bizMallNewsService")
@Slf4j
public class BizMallNewsServiceImpl implements BizMallNewsService {

	@Autowired
	private BizMallNewsMapper bizMallNewsMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String title = null == params.get("title")?"":params.get("title").toString();
        IPage<BizMallNewsModel> page = bizMallNewsMapper.selectPage(
                new PageWrapper<BizMallNewsModel>(params).getPage(),
                new QueryWrapper<BizMallNewsModel>().orderByAsc("create_time")
                        .like(StrUtil.isNotBlank(title),"title",title).eq(null != params.get("is_show"),"is_show", params.get("is_show")));
        return new PageResult(page);
    }

    @Override
    public void save(BizMallNewsModel bizMallNewsModel) {
        bizMallNewsMapper.insert(bizMallNewsModel);
    }

    @Override
    public void updateById(BizMallNewsModel bizMallNewsModel) {
        bizMallNewsMapper.updateById(bizMallNewsModel);
    }

    @Override
    public void deleteById(Long id) {
        bizMallNewsMapper.deleteById(id);
    }

    @Override
    public BizMallNewsModel getById(Long id) {
        return bizMallNewsMapper.selectById(id);
    }
}
