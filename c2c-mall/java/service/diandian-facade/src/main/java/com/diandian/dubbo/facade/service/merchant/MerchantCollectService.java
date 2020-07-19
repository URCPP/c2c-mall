package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.model.merchant.MerchantCollectModel;

import java.util.List;

public interface MerchantCollectService {

    List<MaterialDetailModel> listCollect(Long merchantId, Long productId);

    void saveCollect(MerchantCollectModel merchantCollectModel);

    boolean delCollect(Long id,Long merchantId);

    MerchantCollectModel isCollect(Long id,Long merchantId);

    int CountCollect(Long merchantId);

}
