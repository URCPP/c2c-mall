package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantAttentionModel;

import java.util.List;

public interface MerchantAttentionService {

    List<MerchantAttentionModel> findAttention(Long merchantId);

    List<MerchantAttentionModel> findFans(Long focusMerchantId);

    MerchantAttentionModel findOnlyOne(Long merchantId,Long focusMerchantId);

    void saveAttention(MerchantAttentionModel mer);

    void delAttention(Long merchantId,Long id);

    void updateFlag(Long id);

    int CountAttention(Long merchantId);

    int CountFans(Long focusMerchantId);

    MerchantAttentionModel isFollow(Long me,Long he);
}
