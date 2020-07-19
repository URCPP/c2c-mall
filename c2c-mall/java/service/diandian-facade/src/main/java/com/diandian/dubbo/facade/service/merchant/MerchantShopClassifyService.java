package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.merchant.MerchantShopClassifyModel;

import java.util.List;
import java.util.Map;

/**
 *
 * 商户商铺分类服务类
 *
 * @Author: byp
 * @Description:
 * @Date: Created in 20:37 2019/9/24
 * @Modified By:
 */
public interface MerchantShopClassifyService {

    List<MerchantShopClassifyModel> listOne();

    List<MerchantShopClassifyModel> listTwo(Long id);

    MerchantShopClassifyModel getCategoryById(Long id);

    PageResult listPage(Map<String, Object> params);

    void save(MerchantShopClassifyModel merchantShopClassifyModel);

    void deleteById(Long id);

    void updateById(MerchantShopClassifyModel merchantShopClassifyModel);

}
