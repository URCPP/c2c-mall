package com.diandian.dubbo.facade.service.product;


import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;

import java.util.List;

/**
 * 产品sku商户价格
 *
 * @author zzhihong
 * @date 2019/02/20
 */
public interface ProductSkuPriceService {

    /**
     * 新增保存
     *
     * @param productSkuPriceModel
     */
    void save(ProductSkuPriceModel productSkuPriceModel);

    /**
     * 通过ID更新
     *
     * @param productSkuPriceModel
     * @return
     */
    boolean updateById(ProductSkuPriceModel productSkuPriceModel);

    /**
     * 获取SKU的价格列表
     * @param skuId
     * @return
     */
    List<ProductSkuPriceModel> listProductPrice(Long skuId);

    /**
     * 通过ID查询
     */
    ProductSkuPriceModel getBySkuAndSoftTypeId(Long skuId, Long softwareTypeId);

    /**
     * 根据用户ID和商品ID查询价格
     */
    ProductSkuPriceModel getBySkuAndSoftTypeId(Long userId, Long skuId, Long softwareTypeId);

    /**
     * 根据用户ID和商品ID获取SKU价格列表
     */
    List<ProductSkuPriceModel> listProductPrice(Long userId, Long skuId);

    void delete(Long id);

}
