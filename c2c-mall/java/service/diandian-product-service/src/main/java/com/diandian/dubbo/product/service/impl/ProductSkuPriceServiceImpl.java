package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.model.product.ProductShareModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.service.product.ProductShareService;
import com.diandian.dubbo.facade.service.product.ProductSkuPriceService;
import com.diandian.dubbo.product.mapper.ProductShareMapper;
import com.diandian.dubbo.product.mapper.ProductSkuPriceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品sku商户价格
 *
 * @author zzhihong
 * @date 2019/02/20
 */
@Service("productSkuPriceService")
@Slf4j
public class ProductSkuPriceServiceImpl implements ProductSkuPriceService {

    @Autowired
    private ProductSkuPriceMapper productSkuPriceMapper;

    @Autowired
    private ProductShareService productShareService;

    @Override
    public void save(ProductSkuPriceModel productSkuPriceModel) {
        productSkuPriceMapper.insert(productSkuPriceModel);
    }

    @Override
    public boolean updateById(ProductSkuPriceModel productSkuPriceModel) {
        int result = productSkuPriceMapper.updateById(productSkuPriceModel);
        return result > 0;
    }

    @Override
    public List<ProductSkuPriceModel> listProductPrice(Long skuId) {
        List<ProductSkuPriceModel> list = new ArrayList<>();
        if (ObjectUtil.isNotNull(skuId)) {
            QueryWrapper<ProductSkuPriceModel> qw = new QueryWrapper<>();
            qw.eq("sku_id", skuId);
            qw.orderByAsc("price");
            list = productSkuPriceMapper.selectList(qw);
        }
        return list;
    }

    @Override
    public ProductSkuPriceModel getBySkuAndSoftTypeId(Long skuId, Long softwareTypeId) {
        return productSkuPriceMapper.selectOne(
                new QueryWrapper<ProductSkuPriceModel>()
                        .eq("sku_id", skuId)
                        .eq("software_type_id", softwareTypeId)
        );
    }

    @Override
    public ProductSkuPriceModel getBySkuAndSoftTypeId(Long userId, Long skuId, Long softwareTypeId) {
        ProductSkuPriceModel productSkuPriceModel = getBySkuAndSoftTypeId(skuId, softwareTypeId);
        //判断是否为分享加价商品
        if(productShareService.checkSkuId(userId, skuId)){
            Map<String, BigDecimal> priceMap = productShareService.getPriceMap(userId, skuId);
            BigDecimal price = productSkuPriceModel.getPrice().add(priceMap.get("addPrice"));
            productSkuPriceModel.setPrice(price);
        }
        return productSkuPriceModel;
    }

    @Override
    public List<ProductSkuPriceModel> listProductPrice(Long userId, Long skuId) {
        List<ProductSkuPriceModel> productSkuPriceModels = listProductPrice(skuId);
        //判断是否为分享加价商品
        if(productShareService.checkSkuId(userId, skuId)){
            Map<String, BigDecimal> priceMap = productShareService.getPriceMap(userId, skuId);
            //获取标准版价格
            ProductSkuPriceModel productSkuPriceModel = productSkuPriceModels.get(2);
            BigDecimal prePrice = productSkuPriceModel.getPrice();
            //加价
            BigDecimal price = prePrice.add(priceMap.get("addPrice"));
            productSkuPriceModel.setPrice(price);
            productSkuPriceModels.set(2,productSkuPriceModel);
        }
        return productSkuPriceModels;
    }

    @Override
    public void delete(Long id) {
        productSkuPriceMapper.deleteById(id);
    }


}
