package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.model.merchant.MerchantCollectModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantCollectMapper extends BaseMapper<MerchantCollectModel> {

    List<MaterialDetailModel> findCollect(@Param("merchantId") Long merchantId, @Param("productId")Long productId);
}
