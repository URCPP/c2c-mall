package com.diandian.dubbo.facade.service.product;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.product.ProductBrandModel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-07
 */
public interface ProductBrandService {

    /**
     *
     * 功能描述: 查询单个
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/5/7 11:17
     */
    ProductBrandModel getById(Long id);

    /**
     *
     * 功能描述: 列表
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/5/7 9:46
     */
    PageResult listPage(Map<String, Object> params);

    /**
     *
     * 功能描述: 列表
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/5/7 9:46
     */
    List<ProductBrandModel> list(Map<String, Object> params);

    /**
     *
     * 功能描述: 添加
     *
     * @param productBrand
     * @return
     * @author cjunyuan
     * @date 2019/5/7 9:43
     */
    void save(ProductBrandModel productBrand);

    /**
     *
     * 功能描述: 更新
     *
     * @param productBrand
     * @return
     * @author cjunyuan
     * @date 2019/5/7 9:44
     */
    void updateById(ProductBrandModel productBrand);

    /**
     *
     * 功能描述: 删除
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/5/7 9:47
     */
    void deleteById(Long id);
}
