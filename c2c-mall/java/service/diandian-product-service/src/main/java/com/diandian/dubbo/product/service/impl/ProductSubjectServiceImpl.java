package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.product.ProductSubjectModel;
import com.diandian.dubbo.facade.service.product.ProductSubjectService;
import com.diandian.dubbo.product.mapper.ProductSubjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 产品专题
 *
 * @author zzhihong
 * @date 2019/02/28
 */
@Service("productSubjectService")
@Slf4j
public class ProductSubjectServiceImpl implements ProductSubjectService {

    @Autowired
    private ProductSubjectMapper productSubjectMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        IPage<ProductSubjectModel> page = productSubjectMapper.selectPage(new PageWrapper<ProductSubjectModel>(params).getPage(),
                new LambdaQueryWrapper<ProductSubjectModel>()
                        .eq(ProductSubjectModel::getDelFlag, BizConstant.STATE_NORMAL)
                        .like(StrUtil.isNotBlank(keyword), ProductSubjectModel::getSubjectName, keyword));
        return new PageResult(page);
    }

    @Override
    public List<ProductSubjectModel> listAll() {
        return productSubjectMapper.selectList(null);
    }

    @Override
    public ProductSubjectModel getById(Long id) {
        return productSubjectMapper.selectById(id);
    }

    @Override
    public void save(ProductSubjectModel productSubjectModel) {
        productSubjectMapper.insert(productSubjectModel);
    }

    @Override
    public void updateById(ProductSubjectModel productSubjectModel) {
        productSubjectMapper.updateById(productSubjectModel);
    }

    @Override
    public void logicDeleteById(Long id) {
        ProductSubjectModel productSubjectModel = new ProductSubjectModel();
        productSubjectModel.setId(id);
        productSubjectModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        productSubjectMapper.updateById(productSubjectModel);
    }

}
