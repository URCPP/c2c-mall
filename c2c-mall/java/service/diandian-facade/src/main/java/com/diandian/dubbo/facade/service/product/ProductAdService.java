package com.diandian.dubbo.facade.service.product;


import com.diandian.dubbo.facade.model.product.ProductAdModel;

import java.util.List;
import java.util.Map;

/**
 * 产品广告图
 *
 * @author zzhihong
 * @date 2019/03/05
 */
public interface ProductAdService{
    List<ProductAdModel> list(Map<String,Object> params);
    void save(List<ProductAdModel> productAdModelList);
    void update(List<ProductAdModel> productAdModelList);
    void deleteById(Long id);

    List<ProductAdModel> listAd(String type);
    void add(ProductAdModel productAdModel,String createBy);
    void amend(ProductAdModel productAdModel,String createBy);
    void show(ProductAdModel productAdModel);

    List<ProductAdModel> listAll(String type);
}
