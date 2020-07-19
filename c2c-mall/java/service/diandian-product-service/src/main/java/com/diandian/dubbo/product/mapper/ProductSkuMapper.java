package com.diandian.dubbo.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.vo.SkuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 产品sku
 *
 * @author zzhihong
 * @date 2019/02/20
 */
public interface ProductSkuMapper extends BaseMapper<ProductSkuModel> {

    /**
     * 商品SKU 列表 分页
     * @param page
     * @param params
     * @return
     */
    IPage<SkuVO> listSkuPage(Page<SkuVO> page, @Param("params") Map<String,Object> params);

    /**
     * 产品ID取SKU列表
     * @param productId
     * @return
     */
    List<ProductSkuModel> listByProductId(Long productId);

    /**
     *
     * 功能描述: 获取产品的sku信息
     *
     * @param productId
     * @param skuId
     * @return
     * @author cjunyuan
     * @date 2019/4/26 11:35
     */
    SkuVO getSkuVOById(@Param("productId") Long productId, @Param("skuId") Long skuId);

    ProductSkuModel getSkuById(@Param("id") Long id,@Param("softwareId") Long softwareId);

    List<ProductSkuModel>getByShopId(@Param("shopId")Long shopId);
}
