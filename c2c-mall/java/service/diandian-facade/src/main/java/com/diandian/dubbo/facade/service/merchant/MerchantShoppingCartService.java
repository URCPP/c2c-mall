package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.merchant.MerchantShoppingCartModel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author zzj
 * @since 2019-02-26
 */

public interface MerchantShoppingCartService {

    PageResult listPage(Map<String, Object> params);

    MerchantShoppingCartModel getById(Long id);

    void updateById(MerchantShoppingCartModel merchantShoppingCartModel);

    void save(MerchantShoppingCartModel merchantShoppingCartModel);

    void deleteById(Long id);

    void deleteByMchIdAndSkuIdList(Long merchantId, List<Long> skuIdList);
}
