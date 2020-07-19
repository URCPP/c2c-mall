package com.diandian.dubbo.business.mapper.material;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.model.material.res.MaterialDetailRes;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface MaterialDetailMapper extends BaseMapper<MaterialDetailModel> {

    List<MaterialDetailRes> findList(@Param("merchantId")Long merchantId,@Param("softwareTypeId")Long softwareTypeId,@Param("businessMapper")String businessMapper,@Param("productMapper")String productMapper);

    List<MaterialDetailRes> findMyCollect(@Param("merchantId")Long merchantId, @Param("softwareTypeId")Long softwareTypeId, @Param("collectState")Integer collectState,@Param("businessMapper")String businessMapper,@Param("productMapper")String productMapper);

    List<MaterialDetailModel> findProductId(@Param("params")Map<String, Object> params);

    int CountNews(@Param("productId") Long productId);

    int CountMe(@Param("productId")Long productId,@Param("merchantId") Long merchantId);

}

