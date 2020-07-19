package com.diandian.dubbo.facade.service.product;

import com.diandian.dubbo.facade.model.product.ProductClassifyModel;

import java.util.List;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 17:01 2019/11/1
 * @Modified By:
 */
public interface ProductClassifyService {
    /**
     * 查询菜单list
     *
     * @return
     */
    List<ProductClassifyModel> list();

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    ProductClassifyModel getById(Long id);

    /**
     * 通过ID更新
     *
     * @param productClassifyModel
     */
    void updateById(ProductClassifyModel productClassifyModel);

    /**
     * 新增保存
     *
     * @param productClassifyModel
     */
    void save(ProductClassifyModel productClassifyModel);

    /**
     * 删除
     * @param id
     */
    void DeleteById(Long id);

}
