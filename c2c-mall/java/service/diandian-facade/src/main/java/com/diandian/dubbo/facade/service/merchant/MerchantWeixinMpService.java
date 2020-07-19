package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantWeixinMpModel;

import java.util.List;

/**
 * <p>
 * 商户微信公众号信息表 服务类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-11
 */
public interface MerchantWeixinMpService {

    /**
     *
     * 功能描述: 获取商户的微信公众号信息
     *
     * @param appId
     * @return
     * @author cjunyuan
     * @date 2019/6/27 14:04
     */
    MerchantWeixinMpModel getByAppId(String appId);

    /**
     *
     * 功能描述: 绑定微信公众号
     *
     * @param wechatMp
     * @return
     * @author cjunyuan
     * @date 2019/4/11 16:48
     */
    void bindWeixinMp(MerchantWeixinMpModel wechatMp);

    /**
     *
     * 功能描述: 获取商户公众号信息
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/6/28 9:54
     */
    MerchantWeixinMpModel getByMerchantId(Long merchantId);
}
