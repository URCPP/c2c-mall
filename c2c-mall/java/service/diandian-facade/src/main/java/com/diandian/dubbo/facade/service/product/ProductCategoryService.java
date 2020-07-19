package com.diandian.dubbo.facade.service.product;


import com.diandian.dubbo.facade.dto.api.query.GetCategoryListDTO;
import com.diandian.dubbo.facade.dto.api.result.CategoryListResultDTO;
import com.diandian.dubbo.facade.model.product.ProductCategoryModel;
import com.diandian.dubbo.facade.vo.ProductCategoryVO;

import java.util.List;

/**
 * 产品类目
 *
 * @author zzhihong
 * @date 2019/02/15
 */
public interface ProductCategoryService {

    /**
     * 查询菜单list
     *
     * @return
     */
    List<ProductCategoryModel> list();

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    ProductCategoryModel getById(Long id);

    /**
     * 通过ID更新
     *
     * @param productCategoryModel
     */
    void updateById(ProductCategoryModel productCategoryModel);

    /**
     * 新增保存
     *
     * @param productCategoryModel
     */
    void save(ProductCategoryModel productCategoryModel);

    /**
     * 逻辑删除
     *
     * @param id
     */
    void logicDeleteById(Long id);

    /**
     * 商品一级类别包含子类别的idList 存redis
     * @return
     */
    List<ProductCategoryModel> listProductCategory();

    /**
     *
     * 功能描述: 获取第一级类别
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/4/30 17:19
     */
    List<ProductCategoryVO> listFirstCategory();

    /**
     *
     * 功能描述: api分类列表接口
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/14 10:19
     */
    List<CategoryListResultDTO> apiList(GetCategoryListDTO dto);
}
