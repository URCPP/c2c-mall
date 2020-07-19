package com.diandian.dubbo.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.biz.ProductInfoQueryDTO;
import com.diandian.dubbo.facade.model.product.ProductBrandModel;
import com.diandian.dubbo.facade.service.product.ProductBrandService;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.product.mapper.ProductBrandMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-07
 */
@Service("productBrandService")
@Slf4j
public class ProductBrandServiceImpl implements ProductBrandService {

    @Autowired
    private ProductBrandMapper productBrandMapper;

    @Autowired
    private ProductInfoService productInfoService;

    @Override
    public ProductBrandModel getById(Long id){
        return productBrandMapper.selectById(id);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String brandName = (String) params.get("brandName");
        QueryWrapper<ProductBrandModel> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(brandName), "brand_name", brandName);
        qw.orderByAsc("sort").orderByDesc("id");
        IPage<ProductBrandModel> iPage = productBrandMapper.selectPage(new PageWrapper<ProductBrandModel>(params).getPage(), qw);
        return new PageResult(iPage);
    }

    @Override
    public List<ProductBrandModel> list(Map<String, Object> params) {
        QueryWrapper<ProductBrandModel> qw = new QueryWrapper<>();
        qw.orderByAsc("sort").orderByDesc("id");
        return productBrandMapper.selectList(qw);
    }

    @Override
    public void save(ProductBrandModel productBrand) {
        productBrandMapper.insert(productBrand);
    }

    @Override
    public void updateById(ProductBrandModel productBrand) {
        if(null == productBrand.getId()){
            throw new DubboException("品牌更新失败");
        }
        ProductBrandModel oldProductBrand = productBrandMapper.selectById(productBrand.getId());
        if(null == oldProductBrand){
            throw new DubboException("更新失败，品牌信息不存在");
        }
        productBrandMapper.updateById(productBrand);
    }

    @Override
    public void deleteById(Long id) {
        ProductBrandModel oldProductBrand = productBrandMapper.selectById(id);
        if(null == oldProductBrand){
            throw new DubboException("删除失败，品牌信息不存在");
        }
        ProductInfoQueryDTO query = new ProductInfoQueryDTO();
        query.setBrandId(id);
        Integer productCnt = productInfoService.countProductInfo(query);
        if(null != productCnt && productCnt > 0){
            throw new DubboException("删除失败，品牌信息正在被使用");
        }
        productBrandMapper.deleteById(id);
    }
}
