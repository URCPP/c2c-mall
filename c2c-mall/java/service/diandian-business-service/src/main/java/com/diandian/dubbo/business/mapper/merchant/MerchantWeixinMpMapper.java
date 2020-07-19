package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.merchant.MerchantWeixinMpModel;

/**
 * <p>
 * 商户微信公众号信息表 Mapper 接口
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-11
 */
public interface MerchantWeixinMpMapper extends BaseMapper<MerchantWeixinMpModel> {

    /**
     *
     * 功能描述: 根据商户ID获取公众号信息
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/6/28 10:01
     */
    MerchantWeixinMpModel getByMerchantId(Long merchantId);
}
