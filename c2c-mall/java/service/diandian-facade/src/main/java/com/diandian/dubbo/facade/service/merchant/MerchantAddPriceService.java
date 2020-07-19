package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantAddPriceModel;

import java.util.List;

public interface MerchantAddPriceService {

    /**
     * 查找flag=2 订单已完成
     * @author linshuofeng/1275339166@qq.com
     * @date 2019/9/9 0009
     **/
    List<MerchantAddPriceModel> selectOrderComplete();

    int updateById(MerchantAddPriceModel merchantAddPriceModel);

    void insert(MerchantAddPriceModel merchantAddPriceModel);
}
