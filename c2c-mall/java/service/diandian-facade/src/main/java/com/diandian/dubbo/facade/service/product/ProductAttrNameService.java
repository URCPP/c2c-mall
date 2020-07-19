package com.diandian.dubbo.facade.service.product;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.product.ProductAttrNameModel;

import java.util.Map;

/**
 * 产品类目属性名
 *
 * @author zzhihong
 * @date 2019/02/18
 */
public interface ProductAttrNameService {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    ProductAttrNameModel getById(Long id);

    /**
     * 新增保存
     *
     * @param productAttrNameModel
     */
    void save(ProductAttrNameModel productAttrNameModel);

    /**
     * 通过ID更新
     *
     * @param productAttrNameModel
     */
    void updateById(ProductAttrNameModel productAttrNameModel);

    /**
     * 通过ID逻辑删除
     *
     * @param id
     */
    void logicDeleteById(Long id);

}
