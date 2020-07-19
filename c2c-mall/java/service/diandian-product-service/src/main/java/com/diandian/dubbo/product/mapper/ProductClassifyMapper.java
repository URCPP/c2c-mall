package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.product.ProductClassifyModel;
import com.diandian.dubbo.facade.vo.ProductCategoryVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 17:21 2019/11/1
 * @Modified By:
 */

@Repository
public interface ProductClassifyMapper extends BaseMapper<ProductClassifyModel> {

    List<ProductClassifyModel> list();

    ProductClassifyModel getById(Long id);

    /**
     * 功能描述: 获取第一级类别
     */
    List<ProductCategoryVO> listFirstCategory();
}
