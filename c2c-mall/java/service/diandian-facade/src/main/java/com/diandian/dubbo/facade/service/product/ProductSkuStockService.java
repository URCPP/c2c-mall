package com.diandian.dubbo.facade.service.product;

import com.diandian.dubbo.facade.dto.order.OrderAddStockDTO;
import com.diandian.dubbo.facade.dto.order.OrderSubStockDTO;
import com.diandian.dubbo.facade.model.product.ProductSkuStockModel;

/**
 * 产品sku库存
 *
 * @author zzhihong
 * @date 2019/02/21
 */
public interface ProductSkuStockService {

    /**
     * 新增保存
     *
     * @param productSkuStockModel
     */
    void save(ProductSkuStockModel productSkuStockModel);

    /**
     * 通过ID更新
     *
     * @param productSkuStockModel
     * @return
     */
    boolean updateById(ProductSkuStockModel productSkuStockModel);


    /**
     * 扣减库存
     *
     * @param subStockDTO
     * @return
     */
    boolean subStockBySkuIdAndRepositoryId(OrderSubStockDTO subStockDTO);

    /**
     * 增加库存
     * @param subStockDTO
     * @return
     */
    boolean addStockBySkuIdAndRepositoryId(OrderAddStockDTO addStockDTO);

    /**
     * 统计某一SKU 库存量
     *
     * @param skuId
     * @return
     */
    Integer countStockNum(Long skuId);

    /**
     * 通过ID查询
     */
    ProductSkuStockModel getBySkuAndRepoId(Long skuId, Long repoId);
}
