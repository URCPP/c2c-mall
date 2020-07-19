package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.RedisConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.api.query.GetCategoryListDTO;
import com.diandian.dubbo.facade.dto.api.result.CategoryListResultDTO;
import com.diandian.dubbo.facade.model.product.ProductCategoryModel;
import com.diandian.dubbo.facade.service.product.ProductCategoryService;
import com.diandian.dubbo.facade.vo.ProductCategoryVO;
import com.diandian.dubbo.product.mapper.ProductCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * 产品类目
 *
 * @author zzhihong
 * @date 2019/02/15
 */
@Service("productCategoryService")
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${aliyun.oss.domain}")
    private String imgDomain;

    @Override
    public List<ProductCategoryModel> list() {
        return productCategoryMapper.list();
    }

    @Override
    public ProductCategoryModel getById(Long id) {
        return productCategoryMapper.getById(id);
    }

    @Override
    public void updateById(ProductCategoryModel productCategoryModel) {
        productCategoryMapper.updateById(productCategoryModel);
    }

    @Override
    public void save(ProductCategoryModel productCategoryModel) {
        productCategoryMapper.insert(productCategoryModel);
    }

    @Override
    public void logicDeleteById(Long id) {
        //判断是否存在子级
        List<ProductCategoryModel> existChildList = productCategoryMapper.selectList(new QueryWrapper<ProductCategoryModel>().eq("del_flag", BizConstant.STATE_NORMAL).eq("parent_id", id));
        if (CollectionUtil.isNotEmpty(existChildList)) {
            throw new DubboException("存在子级类目,不允许删除");
        }

        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        productCategoryModel.setId(id);
        productCategoryModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        productCategoryMapper.updateById(productCategoryModel);
    }

    @Override
    public List<ProductCategoryModel> listProductCategory() {
        QueryWrapper<ProductCategoryModel> qw = new QueryWrapper<>();
        qw.eq("del_flag", BizConstant.STATE_NORMAL);
        qw.eq("parent_id", 0);
        List<ProductCategoryModel> list = productCategoryMapper.selectList(qw);
        //递归获得一级类别的子类加ID 存 redis
        if (list.size() > 0) {
            for (ProductCategoryModel pc : list) {
                Long id = pc.getId();
                String srt = stringRedisTemplate.opsForValue().get(RedisConstant.PRODUCT_CATEGORY_FOR_SEARCH + id);
                if (StrUtil.isBlank(srt)){
                    HashSet<Long> idSet = new HashSet<>();
                    idSet.add(id);
                    addSub(id, idSet);
                    stringRedisTemplate.opsForValue().set(RedisConstant.PRODUCT_CATEGORY_FOR_SEARCH + id, JSON.toJSONString(idSet));
                }
            }
        }
        return list;
    }

    @Override
    public List<ProductCategoryVO> listFirstCategory() {
        return productCategoryMapper.listFirstCategory();
    }

    @Override
    public List<CategoryListResultDTO> apiList(GetCategoryListDTO dto){
        return productCategoryMapper.apiList(dto, imgDomain);
    }

    private void addSub(Long id, HashSet<Long> idSet) {

        QueryWrapper<ProductCategoryModel> qw = new QueryWrapper<>();
        qw.eq("del_flag", BizConstant.STATE_NORMAL);
        qw.eq("parent_id", id);
        List<ProductCategoryModel> list = productCategoryMapper.selectList(qw);
        if (list.size() > 0) {
            for (ProductCategoryModel categoryModel : list) {
                Long gid = categoryModel.getId();
                idSet.add(gid);
                addSub(gid, idSet);
            }
        }
    }
}
