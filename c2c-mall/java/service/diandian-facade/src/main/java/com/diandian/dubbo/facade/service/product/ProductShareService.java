package com.diandian.dubbo.facade.service.product;

import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductShareModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品加价
 *
 * @author chensong
 * @date 2019/09/03
 */
public interface ProductShareService {

    /**
     * 商品加价
     * @param params
     * @return
     */
    public Map<String, Object> addPrice(Map<String, Object> params);

    /**
     * 绑定用户
     * @param id
     * @param userId
     */
    public void bind(Long id, Long userId);

    /**
     * 验证用户是否加价
     * @param userId
     * @param skuId
     * @return
     */
    public boolean checkSkuId(Long userId, Long skuId);

    /**
     * 根据用户Id和SkuId获取增加价格和实际价格
     */
    public Map<String,BigDecimal> getPriceMap(Long userId, Long skuId);

    /**
     * 根据用户Id和skuId获取分享信息
     */
    public ProductShareModel getProductShare(Long userId, Long skuId);

    /**
     * 根据分享Id获取分享信息
     */
    public ProductShareModel getProductShare(Long shareId);

    /**
     * 设置分享状态
     */
    public void setFlagById(ProductShareModel productShareModel);

    /**
     * 删除分享记录
     */
    public void deleteById(ProductShareModel productShareModel);
}
