package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizBusinessInformationMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizBusinessInformationModel;
import com.diandian.dubbo.facade.service.biz.BizBusinessInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-06 15:57
 */
@Service("bizBusinessInformationService")
@Slf4j
public class BizBusinessInformationServiceImpl implements BizBusinessInformationService {

    @Autowired
    private BizBusinessInformationMapper bizBusinessInformationMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        IPage<BizBusinessInformationModel> page=bizBusinessInformationMapper.listPage(
                new PageWrapper<BizBusinessInformationModel>(params).getPage(),params);
        return new PageResult(page);
    }

    @Override
    public BizBusinessInformationModel getByShopId(Long shopId) {
        return bizBusinessInformationMapper.getByShopId(shopId);
    }
}