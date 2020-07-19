package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.model.product.ProductAdModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.service.product.ProductAdService;
import com.diandian.dubbo.facade.service.product.ProductShareService;
import com.diandian.dubbo.product.mapper.ProductAdMapper;
import com.diandian.dubbo.product.mapper.ProductInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 产品广告图
 *
 * @author zzhihong
 * @date 2019/03/05
 */
@Service("productAdService")
@Slf4j
public class ProductAdServiceImpl implements ProductAdService {


    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private ProductAdMapper productAdMapper;
    @Autowired
    private ProductShareService productShareService;
    @Value("${softwareTypeId}")
    private Long softwareTypeId;

    @Value("${softwareTypeIdZS}")
    private Long softwareTypeIdZS;

    @Override
    public List<ProductAdModel> list(Map<String, Object> params) {
        params.put("softwareTypeId",softwareTypeId);
        List<ProductAdModel> list=productAdMapper.getProductAd(params);
        for (ProductAdModel productAdModel:list){
            ProductInfoModel productInfoModel = productInfoMapper.getById(productAdModel.getProductId());
            Integer virtualSaleVolume = productInfoModel.getVirtualSaleVolume();
            Integer saleVolume = productInfoModel.getSaleVolume();
            BigDecimal price = productInfoModel.getPrice();
            productAdModel.getSkuList().forEach(sku->{
                BigDecimal bigPrice=BigDecimal.ZERO;
                BigDecimal smallPrice=BigDecimal.ZERO;
                for (ProductSkuPriceModel productSkuPriceModel:sku.getPriceList()){
                    if (softwareTypeId.equals(productSkuPriceModel.getSoftwareTypeId())){
                        bigPrice=productSkuPriceModel.getPrice();
                        /*Long merchantId = Long.valueOf(params.get("merchantId").toString());
                        if(productShareService.checkSkuId(merchantId,sku.getId())){
                            Map<String, BigDecimal> priceMap = productShareService.getPriceMap(merchantId, sku.getId());
                            BigDecimal price = priceMap.get("price");
                            bigPrice = price;
                        }*/
                    }
                    if (softwareTypeIdZS.equals(productSkuPriceModel.getSoftwareTypeId())){
                        smallPrice=productSkuPriceModel.getPrice();
                    }
                }
                productAdModel.setPriceBZ(bigPrice);
                productAdModel.setPriceDifference(bigPrice.subtract(smallPrice));
                productAdModel.setPrice(price);
                productAdModel.setVirtualSaleVolume(virtualSaleVolume);
                productAdModel.setSaleVolume(saleVolume);
                /*sku.getPriceList().forEach(price->{
                    BigDecimal bigPrice=BigDecimal.ZERO;
                    BigDecimal smallPrice=BigDecimal.ZERO;
                    if (softwareTypeId.equals(price.getSoftwareTypeId())){
                        bigPrice=price.getPrice();
                    }
                    if (softwareTypeIdZS.equals(price.getSoftwareTypeId())){
                        smallPrice=price.getPrice();
                    }
                    productAdModel.setPrice(bigPrice);
                    productAdModel.setPriceDifference(bigPrice.subtract(smallPrice));
                });*/
            });
        }
        return list;
        /*String type = (String) params.get("type");
        List<ProductAdModel> productAdModelList = productAdMapper.selectList(
                new LambdaQueryWrapper<ProductAdModel>()
                        .eq(StrUtil.isNotBlank(type), ProductAdModel::getAdType, Integer.valueOf(type)));
        return productAdModelList;*/
    }

    @Override
    public void save(List<ProductAdModel> productAdModelList) {
        productAdModelList.forEach(ad -> {
            if (ad.getId() == null) {
                productAdMapper.insert(ad);
            } else {
                productAdMapper.updateById(ad);
            }
        });
    }

    @Override
    public void update(List<ProductAdModel> productAdModelList) {
        productAdModelList.forEach(ad -> {
            productAdMapper.updateById(ad);
        });
    }

    @Override
    public void deleteById(Long id) {
        productAdMapper.deleteById(id);
    }

    @Override
    public List<ProductAdModel> listAd(String type) {
       return productAdMapper.selectList(
                new QueryWrapper<ProductAdModel>()
                        .eq("ad_type",type)
                        .orderByAsc("sort")
        );
    }


    @Override
    public void add(ProductAdModel productAdModel,String createBy) {
        productAdModel.setCreateBy(createBy);
        productAdMapper.insert(productAdModel);
    }

    @Override
    public void amend(ProductAdModel productAdModel,String createBy) {
        productAdModel.setCreateBy(createBy);
        productAdMapper.updateById(productAdModel);
    }

    @Override
    public void show(ProductAdModel productAdModel) {
       productAdModel.setState(productAdModel.getState() == 0 ? 1 : 0);
       productAdMapper.updateById(productAdModel);
    }

    @Override
    public List<ProductAdModel> listAll(String type) {
       return productAdMapper.selectList(
                new QueryWrapper<ProductAdModel>()
                        .eq("ad_type",type)
                        .eq("state",0)
                        .orderByAsc("sort")
        );
    }

}
