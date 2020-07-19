package com.diandian.dubbo.facade.service.merchant;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.api.query.GetProductDetailDTO;
import com.diandian.dubbo.facade.dto.api.query.GetProductListDTO;
import com.diandian.dubbo.facade.dto.api.result.MchProductResultDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantProductInfoDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.vo.ProductStateNumberVO;
import com.diandian.dubbo.facade.vo.SkuVO;

import java.util.List;
import java.util.Map;

/**
 * 商户预售商品信息表
 *
 * @author jbh
 * @date 2019/02/21
 */
public interface MerchantProductInfoService {
    /**
     * 预售商品列表、分页
     *
     * @param params
     * @return
     */
    PageResult listProductInfo(Map<String, Object> params);

    /**
     * 修改商品信息
     *
     * @param model
     */
    void updateProduct(MerchantProductInfoModel model);

    /**
     * 根据id查询商品信息
     *
     * @param id
     */
    MerchantProductInfoModel getProduct(Long id);

    /**
     * 商户添加预售商品
     *
     * @param merchantInfo
     * @param sku
     * @return
     */
    boolean saveMerProduct(MerchantInfoModel merchantInfo, SkuVO sku);

    /**
     * 已加入预售区商品列表
     * @return
     */
    List<MerchantProductInfoModel>  list(Long merchantId);

    /**
     * 获取商品总数量
     * @param params
     * @return
     */
    MerchantProductInfoDTO  getProductTotal(Map<String,Object> params);

    /**
     * @Author wubc
     * @Description // 根据商户ID 和 SKUID  查记录
     * @Date 19:25 2019/3/22
     * @Param [merchantId, skuId]
     * @return com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel
     **/
    MerchantProductInfoModel getProductByMidAndSid(Long merchantId, Long skuId);

    /**
     * @Author wubc
     * @Description // 批量修改兑换商品状态  根据productIdList
     * @Date 22:03 2019/4/3
     * @Param [idList]
     * @return void
     **/
    void updateProductStateBatch(List<Long> idList,Integer state);

    /**
     *
     * 功能描述: 批量保存商户预售商品
     *
     * @param products
     * @return
     * @author cjunyuan
     * @date 2019/4/22 22:01
     */
    void batchSaveMerProduct(MerchantInfoModel merchantInfo, List<SkuVO> products);

    /**
     *
     * 功能描述: 批量保存商户商品状态
     *
     * @param merchantId
     * @param productId
     * @param state
     * @return
     * @author cjunyuan
     * @date 2019/4/22 22:01
     */
    void batchSaveMerProductState(Long merchantId, Long productId, Integer state);

    /**
     *
     * 功能描述: 统计订单中的商品状态对应的数量
     *
     * @param productIds
     * @return
     * @author cjunyuan
     * @date 2019/4/28 10:01
     */
    ProductStateNumberVO statisticsProductState(Long merchantId, List<Long> productIds);

    /**
     *
     * 功能描述: apiu商品列表接口
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/14 11:03
     */
    PageResult apiListPage(GetProductListDTO dto);

    /**
     *
     * 功能描述: api商品列表接口
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/14 16:42
     */
    MchProductResultDTO apiGetById(GetProductDetailDTO dto);

    /**
     *
     * 功能描述: 运营平台更新商品同时更新积分商城商品
     *
     * @param product 商品
     * @return
     * @author cjunyuan
     * @date 2019/5/21 10:26
     */
    void syncUpdateMchProduct(ProductInfoModel product);

    /**
     *
     * 功能描述: 计算正在使用该商品的积分商城商品数量
     *
     * @param productId
     * @return
     * @author cjunyuan
     * @date 2019/5/21 11:48
     */
    Integer countByProductId(Long productId);
}
