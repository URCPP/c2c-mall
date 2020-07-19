package com.diandian.dubbo.business.mapper.merchant;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.api.result.MchProductResultDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantProductInfoDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel;
import com.diandian.dubbo.facade.vo.ProductStateNumberVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantProductInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商户预售商品信息表
 *
 * @author jbh
 * @date 2019/02/21
 */
public interface MerchantProductInfoMapper extends BaseMapper<MerchantProductInfoModel> {

    /**
     * 商户预售商品列表 分页
     * @param page
     * @param params
     * @return
     */
    IPage<MerchantProductInfoVO> listMerchantProductPage(Page page, @Param("params") Map<String,Object> params);

    /**
     * 获取商品总数量
     * @param params
     * @return
     */
    MerchantProductInfoDTO  getProductTotal(@Param("params") Map<String,Object> params);

    /**
     *
     * 功能描述: 统计商品状态数量
     *
     * @param productIds
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/4/28 10:20
     */
    ProductStateNumberVO statisticsProductState(@Param("merchantId") Long merchantId, @Param("ids") List<Long> productIds);

    /**
     *
     * 功能描述: api商户商品列表接口
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/5/14 11:27
     */
    IPage<MchProductResultDTO> apiListPage(Page page, @Param("params") Map<String,Object> params);

    /**
     *
     * 功能描述: api商品列表接口
     *
     * @param productId
     * @return
     * @author cjunyuan
     * @date 2019/5/14 16:42
     */
    MchProductResultDTO apiGetById(@Param("productId") Long productId);

    /**
     *
     * 功能描述: 计算正在使用该商品的积分商城商品数量
     *
     * @param productId
     * @return
     * @author cjunyuan
     * @date 2019/5/21 11:48
     */
    Integer countByProductId(@Param("productId") Long productId);
}
