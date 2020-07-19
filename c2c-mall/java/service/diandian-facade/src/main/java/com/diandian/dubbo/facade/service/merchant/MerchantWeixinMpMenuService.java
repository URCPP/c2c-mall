package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.merchant.MerchantWeixinMpMenuDTO;

import java.util.List;

/**
 * <p>
 * 微信公众号菜单表 服务类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-18
 */
public interface MerchantWeixinMpMenuService {

    /**
     *
     * 功能描述: 批量保存菜单
     *
     * @param menus
     * @return
     * @author cjunyuan
     * @date 2019/4/18 9:57
     */
    void batchSave(List<MerchantWeixinMpMenuDTO> menus);
}
