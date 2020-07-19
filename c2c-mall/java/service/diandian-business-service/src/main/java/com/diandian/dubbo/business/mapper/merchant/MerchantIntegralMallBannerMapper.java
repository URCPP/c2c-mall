package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.api.BaseDTO;
import com.diandian.dubbo.facade.dto.api.result.MchIntegralMallBannerListResultDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantIntegralMallBannerModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商户积分商城banner表 Mapper 接口
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-30
 */
public interface MerchantIntegralMallBannerMapper extends BaseMapper<MerchantIntegralMallBannerModel> {

    /**
     *
     * 功能描述: api获取商户积分商城的广告图片列表
     *
     * @param merchantId
     * @param imgDomain
     * @return
     * @author cjunyuan
     * @date 2019/5/17 14:34
     */
    List<MchIntegralMallBannerListResultDTO> apiGetList(@Param("mchId") Long merchantId, @Param("domain") String imgDomain);
}
