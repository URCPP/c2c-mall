package com.diandian.dubbo.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.product.ProductAdModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 产品广告图
 *
 * @author zzhihong
 * @date 2019/03/05
 */
public interface ProductAdMapper extends BaseMapper<ProductAdModel> {

    List<ProductAdModel> getProductAd(@Param("params") Map<String, Object> params);

}
