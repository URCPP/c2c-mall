package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;

import java.util.List;


/**
 * 产品sku商户价格
 *
 * @author zzhihong
 * @date 2019/02/20
 */
public interface ProductSkuPriceMapper extends BaseMapper<ProductSkuPriceModel> {

    /**
     * SKU ID取价格列表
     * @param skuId
     * @return
     */
    List<ProductSkuPriceModel> listBySkuId(Long skuId);
}
