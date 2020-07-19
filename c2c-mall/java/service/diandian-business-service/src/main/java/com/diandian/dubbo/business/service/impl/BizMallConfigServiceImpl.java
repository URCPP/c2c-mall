package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizMallConfigMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商城目录配置
 *
 * @author zweize
 * @date 2019/02/27
 */
@Service("bizMallConfigService")
@Slf4j
public class BizMallConfigServiceImpl implements BizMallConfigService {

    @Autowired
    private BizMallConfigMapper bizMallConfigMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String mallKey = null == params.get("mallKey") ? "" : params.get("mallKey").toString();
        IPage<BizMallConfigModel> page = bizMallConfigMapper.selectPage(
                new PageWrapper<BizMallConfigModel>(params).getPage(),
                new QueryWrapper<BizMallConfigModel>().orderByAsc("create_time")
                        .like(StrUtil.isNotBlank(mallKey), "mall_key", mallKey));
        return new PageResult(page);
    }

    @Override
    public BizMallConfigModel getById(Long id) {
        return bizMallConfigMapper.selectById(id);
    }

    @Override
    public BizMallConfigModel getByEngName(String engName) {
        QueryWrapper<BizMallConfigModel> qw = new QueryWrapper<>();
        qw.eq("mall_key", engName);
        return bizMallConfigMapper.selectOne(qw);
    }

    @Override
    public void save(BizMallConfigModel bizMallConfigModel) {
        bizMallConfigMapper.insert(bizMallConfigModel);
    }

    @Override
    public void updateById(BizMallConfigModel bizMallConfigModel) {
        bizMallConfigMapper.updateById(bizMallConfigModel);
    }

    @Override
    public void deleteById(Long id) {
        bizMallConfigMapper.deleteById(id);
    }

}
