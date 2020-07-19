package com.diandian.dubbo.facade.service.product;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.api.result.MchProductResultDTO;
import com.diandian.dubbo.facade.dto.biz.ProductInfoDTO;
import com.diandian.dubbo.facade.dto.biz.ProductInfoQueryDTO;
import com.diandian.dubbo.facade.dto.order.OrderSubStockDTO;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuStockModel;
import com.diandian.dubbo.facade.vo.RepositoryDetailVO;
import com.diandian.dubbo.facade.vo.TransportInfoVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 产品信息
 *
 * @author zzhihong
 * @date 2019/02/18
 */
public interface ProductInfoService {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 分页查询（后台）
     *
     * @param params
     * @return
     */
    PageResult listPageBackend(Map<String, Object> params);


    /**
     * 分页查询-商城app
     * @param params
     * @return
     */
    PageResult listBackend(Map<String,Object> params);


    /**
     * 分页查询（商户）
     *
     * @param params
     * @return
     */
    PageResult listPageByShopId(Map<String, Object> params);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    ProductInfoModel getById(Long id);

    ProductInfoModel getProductById(Long id);

    /**
     * 新增保存
     *
     * @param productInfoModel
     */
    void save(ProductInfoModel productInfoModel);

    /**
     * 通过ID更新
     *
     * @param productInfoModel
     */
    void updateById(ProductInfoModel productInfoModel);



    void updateState(Long id,Integer state,Long merchantId);

    /**
     * 批量更新状态
     *
     * @param idList
     * @param state
     */
    void updateStateByIdBatch(List<Long> idList, Integer state,Long userId);

    /**
     * 通过ID逻辑删除
     *
     * @param id
     * @param userId
     */
    void logicDeleteById(Long id, Long userId);

    /**
     * 通过SKU查商品详情
     */
    List<ProductInfoDTO> getProductInfoBySku(Map<String, Object> params);

    /**
     *
     * 功能描述: 批量更新商品的折扣
     *
     * @param idList
     * @param discount
     * @param userId
     * @return
     * @author cjunyuan
     * @date 2019/4/11 14:28
     */
    void batchUpdateDiscountById(List<Long> idList, BigDecimal discount, Long userId);

    /**
     *
     * 功能描述：获取商品的仓库库存
     *
     * @param productId
     * @param skuId
     * @return
     * @author cjunyuan
     * @date 2019/4/25 15:26
     */
    List<RepositoryDetailVO> getProductRepositoryStock(Long productId, Long skuId);

    /**
     *
     * 功能描述:
     *
     * @param productId
     * @return
     * @author cjunyuan
     * @date 2019/4/22 19:58
     */
    List<TransportInfoVO> getProductTransportList(Long productId, List<Integer> transportTypeIds);

    /**
     * 排行数量统计
     * @return
     */
    Map<String,Object> countRangeNum();

    /**
     *
     * 功能描述: 根据条件计算商品数量
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/7 9:59
     */
    Integer countProductInfo(ProductInfoQueryDTO dto);

    /**
     *
     * 功能描述: api商品列表接口
     *
     * @param list
     * @param softTypeId
     * @return
     * @author cjunyuan
     * @date 2019/5/14 11:03
     */
    List<MchProductResultDTO> apiListPage(List<MchProductResultDTO> list, Long softTypeId);

    /**
     *
     * 功能描述: api商品列表接口
     *
     * @param dto
     * @param softTypeId
     * @return
     * @author cjunyuan
     * @date 2019/5/14 16:42
     */
    MchProductResultDTO apiGetById(MchProductResultDTO dto, Long softTypeId);

    /**
     *
     * @param ids
     * @return
     */
    List<ProductInfoModel> getByIds(List<Long> ids);

    /**
     *
     * 功能描述: 商品单表查询
     *
     * @param productId
     * @return
     * @author cjunyuan
     * @date 2019/5/21 12:03
     */
    ProductInfoModel getSingleTableById(Long productId);

    /**
     *
     * 功能描述: 批量设置商品标签的值
     *
     * @param idList
     * @param tag
     * @param flag
     * @return
     * @author cjunyuan
     * @date 2019/5/29 16:19
     */
    void batchUpdateTagById(List<Long> idList, String tag, Integer flag, Long userId);

    PageResult getListPageByShopId(Map<String, Object> params);

    /**
     * 通过userId和商品productId查询
     *
     * @param shareId
     * @param productId
     * @return
     */
    ProductInfoModel getByShareId(Long productId,String phone,Long shareId);

    /**
     * 通过userId和商品skuId查询
     *
     * @param userId
     * @param skuId
     * @return
     */
    ProductInfoModel getBySkuIdAndUserId(Long userId,Long skuId);

    List<ProductInfoModel> getByShopId(Map<String, Object> params);

    PageResult getProductByShopId(Map<String, Object> params);

    List<Map> getExclusiveProduct(String phone,Long memberId);

    List<ProductInfoModel> getExclusiveProductCount(String phone);

    void updateExclusiveRate(ProductInfoModel productInfoModel);

    void addProductSave(ProductInfoModel productInfoModel);

    void updateProduct(ProductInfoModel productInfoModel);

    boolean subStock(OrderSubStockDTO productInfoModel);
}
