package com.diandian.dubbo.facade.service.material;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.model.material.res.MaterialDetailRes;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;

import java.util.List;
import java.util.Map;



public interface MaterialDetailService {

    PageResult listPage(Map<String,Object> params);

    MaterialDetailModel getById(Long id);

    void updateById(MaterialDetailModel MaterialDetailModel);

    void save(MaterialDetailModel materialDetailModel);

    List<MaterialDetailModel> FindList(Map<String,Object> params);

    boolean clickCollect(Long id);

    void clickShare(Long id,Long merchantId);

    List<MaterialDetailRes> findMerchantMaterial(Long MerchantId, Integer id, MerchantInfoModel meId);

    int CountImage(Long merchantId);

    int CountNews(Long productId);

    int CountMe(Long productId,Long merchantId);



}
