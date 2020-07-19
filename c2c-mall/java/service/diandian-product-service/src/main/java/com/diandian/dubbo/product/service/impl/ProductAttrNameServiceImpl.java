package com.diandian.dubbo.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.product.ProductAttrNameModel;
import com.diandian.dubbo.facade.service.product.ProductAttrNameService;
import com.diandian.dubbo.product.mapper.ProductAttrNameMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 产品类目属性名
 *
 * @author zzhihong
 * @date 2019/02/18
 */
@Service("productAttrNameService")
@Slf4j
public class ProductAttrNameServiceImpl implements ProductAttrNameService {

    @Autowired
    private ProductAttrNameMapper productAttrNameMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        IPage<ProductAttrNameModel> page = productAttrNameMapper.listPage(new PageWrapper<ProductAttrNameModel>(params).getPage(), params);
        return new PageResult(page);
    }

    @Override
    public ProductAttrNameModel getById(Long id) {
        return productAttrNameMapper.getById(id);
    }

    @Override
    public void save(ProductAttrNameModel productAttrNameModel) {
        productAttrNameMapper.insert(productAttrNameModel);
    }

    @Override
    public void updateById(ProductAttrNameModel productAttrNameModel) {
        productAttrNameMapper.updateById(productAttrNameModel);
    }

    @Override
    public void logicDeleteById(Long id) {
        ProductAttrNameModel productAttrNameModel = new ProductAttrNameModel();
        productAttrNameModel.setId(id);
        productAttrNameModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        productAttrNameMapper.updateById(productAttrNameModel);
    }
}
