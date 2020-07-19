package com.diandian.dubbo.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.facade.dto.api.result.MchProductResultDTO;
import com.diandian.dubbo.facade.dto.biz.ProductInfoDTO;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.vo.RepositoryDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 产品信息
 *
 * @author zzhihong
 * @date 2019/02/18
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfoModel> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    List<ProductInfoModel> listPage(@Param("curPage") Integer curPage,@Param("pageSize") Integer pageSize,@Param("params") Map<String, Object> params);
//    IPage<ProductInfoModel> listPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 分页查询（后台）
     *
     * @param page
     * @param params
     * @return
     */
    IPage<ProductInfoModel> listPageBackend(IPage page, @Param("params") Map<String, Object> params);


    /**
     * 前台查询
     * @param page
     * @param params
     * @return
     */
    IPage<ProductInfoModel> listBackend(IPage page, @Param("params") Map<String, Object> params);

    Integer subStock(@Param("id") Long id, @Param("currentStock") Integer currentStock, @Param("subNum") Integer subNum);


    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductInfoModel getById(Long id);

    ProductInfoModel getProductById(Long id);

    List<ProductInfoModel> getByIds(@Param("ids") List<Long> ids);

    List<ProductInfoDTO> getProductBySku(@Param("params") Map<String, Object> params);

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
    List<RepositoryDetailVO> getProductRepositoryStock(@Param("productId") Long productId, @Param("skuId") Long skuId);

    /**
     * 排行数量统计
     * @return
     */
    Map<String,Object> countRangeNum();

    /**
     *
     * 功能描述: api商品列表接口
     *
     * @param ids
     * @return
     * @author cjunyuan
     * @date 2019/5/14 11:03
     */
    List<MchProductResultDTO> apiList( List<Long> ids, @Param("softTypeId") Long softTypeId);

    /**
     *
     * 功能描述: api商品列表接口
     *
     * @param skuId
     * @param softTypeId
     * @return
     * @author cjunyuan
     * @date 2019/5/14 16:42
     */
    MchProductResultDTO apiGetById(@Param("skuId") Long skuId, @Param("softTypeId") Long softTypeId);

    /**
     * 分页查询（商户端）
     *
     * @param page
     * @param params
     * @return
     */
    IPage<ProductInfoModel> getListPageByShopId(IPage page, @Param("params") Map<String, Object> params);

    IPage<ProductInfoModel> listPageByShopId(IPage page, @Param("params") Map<String, Object> params);

    List<ProductInfoModel> getByShopId(@Param("params") Map<String, Object> params);

    List<ProductInfoModel> getExclusiveProduct(@Param("phone") String phone);

}
