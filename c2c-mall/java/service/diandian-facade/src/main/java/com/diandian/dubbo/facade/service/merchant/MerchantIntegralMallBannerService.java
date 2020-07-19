package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.api.BaseDTO;
import com.diandian.dubbo.facade.dto.api.result.MchIntegralMallBannerListResultDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantIntegralMallBannerModel;

import java.util.List;

/**
 * <p>
 * 商户积分商城banner表 服务类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-30
 */
public interface MerchantIntegralMallBannerService {

    /**
     *
     * 功能描述: 查询商户积分商城banner列表
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/4/30 11:24
     */
    List<MerchantIntegralMallBannerModel> listByMchId(Long merchantId);

    /**
     *
     * 功能描述: 保存商户积分商城banner信息
     *
     * @param merchantId
     * @param list
     * @return
     * @author cjunyuan
     * @date 2019/4/30 11:25
     */
    void batchSave(Long merchantId, List<MerchantIntegralMallBannerModel> list);

    /**
     *
     * 功能描述: api获取商户积分商城的广告图片列表
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/17 14:34
     */
    List<MchIntegralMallBannerListResultDTO> apiGetList(BaseDTO dto);
}
