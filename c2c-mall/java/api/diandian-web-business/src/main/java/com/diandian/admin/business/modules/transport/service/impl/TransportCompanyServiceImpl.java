package com.diandian.admin.business.modules.transport.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.transport.mapper.TransportCompanyMapper;
import com.diandian.admin.business.modules.transport.service.TransportCompanyService;
import com.diandian.admin.model.transport.TransportCompanyModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 运输公司
 *
 * @author zzhihong
 * @date 2019/02/28
 */
@Service("transportCompanyService")
@Slf4j
public class TransportCompanyServiceImpl extends ServiceImpl<TransportCompanyMapper, TransportCompanyModel> implements TransportCompanyService {

    @Autowired
    private TransportCompanyMapper transportCompanyMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        String shopId = null == params.get("shopId")?"":params.get("shopId").toString();
        IPage<TransportCompanyModel> page = transportCompanyMapper.selectPage(new PageWrapper<TransportCompanyModel>(params).getPage(),
                new LambdaQueryWrapper<TransportCompanyModel>()
                        .eq(TransportCompanyModel::getDelFlag,0)
                        .eq(StrUtil.isNotBlank(shopId),TransportCompanyModel::getShopId,shopId)
                        .like(StrUtil.isNotBlank(keyword),TransportCompanyModel::getTransportCompanyName,keyword));
        return new PageResult(page);
    }

    @Override
    public void logicDeleteById(Long id) {
        TransportCompanyModel transportCompanyModel = new TransportCompanyModel();
        transportCompanyModel.setId(id);
        transportCompanyModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        transportCompanyMapper.updateById(transportCompanyModel);
    }
}
