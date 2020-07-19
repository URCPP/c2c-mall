package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.merchant.MerchantAttentionModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantAttentionMapper extends BaseMapper<MerchantAttentionModel> {

        List<MerchantAttentionModel> listAttention(@Param("merchantId")Long merchantId);

        List<MerchantAttentionModel> listFans(@Param("focusMerchantId")Long focusMerchantId);


}
