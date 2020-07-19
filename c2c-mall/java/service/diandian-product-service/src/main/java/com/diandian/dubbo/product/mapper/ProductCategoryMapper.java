package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.api.query.GetCategoryListDTO;
import com.diandian.dubbo.facade.dto.api.result.CategoryListResultDTO;
import com.diandian.dubbo.facade.model.product.ProductCategoryModel;
import com.diandian.dubbo.facade.vo.ProductCategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 产品类目
 *
 * @author zzhihong
 * @date 2019/02/15
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategoryModel> {

    List<ProductCategoryModel> list();

    ProductCategoryModel getById(Long id);

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
     * @param imgDomain 图片访问域名
     * @return
     * @author cjunyuan
     * @date 2019/5/14 10:20
     */
    List<CategoryListResultDTO> apiList(@Param("dto") GetCategoryListDTO dto, @Param("domain") String imgDomain);

}
