package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizMallHelpMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.model.biz.BizMallHelpModel;
import com.diandian.dubbo.facade.service.biz.BizMallHelpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 商城帮助配置表
 *
 * @author zweize
 * @date 2019/02/27
 */
@Service("bizMallHelpService")
@Slf4j
public class BizMallHelpServiceImpl implements BizMallHelpService {

	@Autowired
	private BizMallHelpMapper bizMallHelpMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Page<BizMallHelpModel> page = new PageWrapper<BizMallHelpModel>(params).getPage();
        Page resultPage = bizMallHelpMapper.listHelpPage(page,params);
        return new PageResult(resultPage);
    }

    @Override
    public void save(BizMallHelpModel bizMallHelpModel) {
        bizMallHelpMapper.insert(bizMallHelpModel);
    }

    @Override
    public void updateById(BizMallHelpModel bizMallHelpModel) {
        bizMallHelpMapper.updateById(bizMallHelpModel);
    }

    @Override
    public void deleteById(Long id) {
        bizMallHelpMapper.deleteById(id);
    }

    @Override
    public BizMallHelpModel getById(Long id) {
        return bizMallHelpMapper.selectById(id);
    }

    @Override
    public List<BizMallHelpModel> listCataLog() {
        QueryWrapper<BizMallHelpModel> qw = new QueryWrapper<>();
        qw.eq("type",0);
        return bizMallHelpMapper.selectList(qw);
    }

    @Override
    public List<BizMallHelpModel> listContent() {
        QueryWrapper<BizMallHelpModel> qw = new QueryWrapper<>();
        qw.eq("type",1);
        return bizMallHelpMapper.selectList(qw);
    }
}
