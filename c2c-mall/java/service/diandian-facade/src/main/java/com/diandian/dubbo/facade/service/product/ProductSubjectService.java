package com.diandian.dubbo.facade.service.product;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.product.ProductSubjectModel;

import java.util.List;
import java.util.Map;

/**
 * 产品专题
 *
 * @author zzhihong
 * @date 2019/02/28
 */
public interface ProductSubjectService {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 查询所有
     * @return
     */
    List<ProductSubjectModel> listAll();

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    ProductSubjectModel getById(Long id);

    /**
     * 新增保存
     *
     * @param productSubjectModel
     */
    void save(ProductSubjectModel productSubjectModel);

    /**
     * 通过ID更新
     *
     * @param productSubjectModel
     */
    void updateById(ProductSubjectModel productSubjectModel);

    /**
     * 通过ID逻辑删除
     *
     * @param id
     */
    void logicDeleteById(Long id);

}
