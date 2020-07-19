package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.product.ProductSkuStockModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 产品sku库存
 *
 * @author zzhihong
 * @date 2019/02/21
 */
public interface ProductSkuStockMapper extends BaseMapper<ProductSkuStockModel> {

    /**
     * 扣减库存
     *
     * @param skuId
     * @param repositoryId
     * @param currentStock
     * @param subNum
     * @return
     */
    Integer subStockBySkuIdAndRepositoryId(@Param("skuId") Long skuId, @Param("repositoryId") Long repositoryId, @Param("currentStock") Integer currentStock, @Param("subNum") Integer subNum);

    /**
     * 增加库存
     *
     * @param skuId
     * @param repositoryId
     * @param currentStock
     * @param addNum
     * @return
     */
    Integer addStockBySkuIdAndRepositoryId(@Param("skuId") Long skuId, @Param("repositoryId") Long repositoryId, @Param("currentStock") Integer currentStock, @Param("addNum") Integer addNum);

    /**
     * 统计某SKU 库存
     *
     * @param skuId
     * @return
     */
    Integer countStockNum(Long skuId);


    /**
     * SKU ID取库存列表
     *
     * @param skuId
     * @return
     */
    List<ProductSkuStockModel> listBySkuId(Long skuId);
}
