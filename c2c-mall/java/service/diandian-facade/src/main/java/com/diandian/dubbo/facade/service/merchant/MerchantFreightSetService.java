package com.diandian.dubbo.facade.service.merchant;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.dubbo.facade.model.merchant.MerchantFreightSetModel;
import com.diandian.dubbo.facade.model.merchant.MerchantIntegralMallBannerModel;

import java.util.List;

/**
 * 商户运费设置表
 *
 * @author wbc
 * @date 2019/02/20
 */
public interface MerchantFreightSetService{
    /**
     * 根据商户id查询
     * @param id
     * @return
     */
    MerchantFreightSetModel getByMerchantId(Long id);

    /**
     * 添加运费设置记录
     * @param freightSetModel
     */
    void saveFreightSet(MerchantFreightSetModel freightSetModel);

    /**
     * 修改运费设置记录
     * @param freightSetModel
     */
    void updateFreightSet(MerchantFreightSetModel freightSetModel);

    /**
     *
     * 功能描述: 修改商户自定义商城基础配置
     *
     * @param freightSetModel
     * @param list
     * @return
     * @author cjunyuan
     * @date 2019/4/30 13:34
     */
    void saveFreightSet(MerchantFreightSetModel freightSetModel, List<MerchantIntegralMallBannerModel> list);
}
