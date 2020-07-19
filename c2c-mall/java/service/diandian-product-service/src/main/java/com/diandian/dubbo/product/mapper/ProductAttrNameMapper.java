package com.diandian.dubbo.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.facade.model.product.ProductAttrNameModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 产品类目属性名
 *
 * @author zzhihong
 * @date 2019/02/18
 */
public interface ProductAttrNameMapper extends BaseMapper<ProductAttrNameModel> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<ProductAttrNameModel> listPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductAttrNameModel getById(Long id);
}
