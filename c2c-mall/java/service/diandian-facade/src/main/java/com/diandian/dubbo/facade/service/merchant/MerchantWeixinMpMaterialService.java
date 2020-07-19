package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.MerchantWeixinMpMaterialQueryDTO;

import java.util.Map;

/**
 * <p>
 * 商户微信公众号素材表 服务类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-06-26
 */
public interface MerchantWeixinMpMaterialService {

    /**
     *
     * 功能描述: 从微信公众号同步素材
     *
     * @param appId
     * @return
     * @author cjunyuan
     * @date 2019/6/26 18:01
     */
    void syncMaterialFromWeixin(String appId);

    /**
     *
     * 功能描述: 公众号素材分页查询功能
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/6/27 18:09
     */
    PageResult listForPage(MerchantWeixinMpMaterialQueryDTO dto);
}
