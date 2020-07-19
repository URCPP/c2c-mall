package com.diandian.dubbo.facade.service.product;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.vo.SkuVO;

import java.util.List;
import java.util.Map;

/**
 * 产品sku
 *
 * @author zzhihong
 * @date 2019/02/20
 */
public interface ProductSkuService {

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    ProductSkuModel getById(Long id);


    /**
     * 新增保存
     * @param productSkuModel
     */
    void save(ProductSkuModel productSkuModel);

    /**
     * 通过ID更新
     *
     * @param productSkuModel
     */
    boolean updateById(ProductSkuModel productSkuModel);

    /**
     * 商品SKU 列表分页
     * @param params
     * @return
     */
    PageResult listSkuPage(Map<String,Object> params);

    /**
     *  获取单个SKU
     * @param id
     * @return
     */
    SkuVO getBySkuId(Long id);

    ProductSkuModel getSkuById(Long id,Long softwareId);

    /**
     *
     */
    SkuVO getBySkuIdAndUserId(Long userId,Long skuId);

    List<ProductSkuModel> getByShopId(Long shopId);
}
