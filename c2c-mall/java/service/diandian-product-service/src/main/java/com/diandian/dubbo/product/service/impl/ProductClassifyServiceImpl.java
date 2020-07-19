package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.model.product.ProductCategoryModel;
import com.diandian.dubbo.facade.model.product.ProductClassifyModel;
import com.diandian.dubbo.facade.service.product.ProductClassifyService;
import com.diandian.dubbo.product.mapper.ProductClassifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 17:07 2019/11/1
 * @Modified By:
 */
@Service("productClassifyService")
public class ProductClassifyServiceImpl implements ProductClassifyService {

    @Autowired
    private ProductClassifyMapper productClassifyMapper;

    @Override
    public List<ProductClassifyModel> list() {
        return productClassifyMapper.list();
    }

    @Override
    public ProductClassifyModel getById(Long id) {
        return productClassifyMapper.getById(id);
    }

    @Override
    public void updateById(ProductClassifyModel productClassifyModel) {
        productClassifyMapper.updateById(productClassifyModel);
    }

    @Override
    public void save(ProductClassifyModel productClassifyModel) {
        productClassifyMapper.insert(productClassifyModel);
    }

    @Override
    public void DeleteById(Long id) {
        List<ProductClassifyModel> existChildList = productClassifyMapper.selectList(new QueryWrapper<ProductClassifyModel>()
                .eq("category_parent", id)
                .eq("del_flag", BizConstant.STATE_NORMAL)
        );
        if (CollectionUtil.isNotEmpty(existChildList)) {
            throw new DubboException("存在子级类目,不允许删除");
        }
        ProductClassifyModel pc=new ProductClassifyModel();
        pc.setId(id);
        pc.setDelFlag(BizConstant.STATE_DISNORMAL);
        productClassifyMapper.updateById(pc);
    }
}
