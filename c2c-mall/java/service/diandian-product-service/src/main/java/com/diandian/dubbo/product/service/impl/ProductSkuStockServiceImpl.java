package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.dto.order.OrderAddStockDTO;
import com.diandian.dubbo.facade.dto.order.OrderSubStockDTO;
import com.diandian.dubbo.facade.model.product.ProductSkuStockModel;
import com.diandian.dubbo.facade.service.product.ProductSkuStockService;
import com.diandian.dubbo.product.mapper.ProductSkuStockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产品sku库存
 *
 * @author zzhihong
 * @date 2019/02/21
 */
@Service("productSkuStockService")
@Slf4j
public class ProductSkuStockServiceImpl implements ProductSkuStockService {

    @Autowired
    private ProductSkuStockMapper productSkuStockMapper;

    @Override
    public void save(ProductSkuStockModel productSkuStockModel) {
        productSkuStockMapper.insert(productSkuStockModel);
    }

    @Override
    public boolean updateById(ProductSkuStockModel productSkuStockModel) {
        int result = productSkuStockMapper.updateById(productSkuStockModel);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean subStockBySkuIdAndRepositoryId(OrderSubStockDTO subStockDTO) {
        Integer result = productSkuStockMapper.subStockBySkuIdAndRepositoryId(subStockDTO.getSkuId(), subStockDTO.getRepositoryId(), subStockDTO.getCurrentStock(), subStockDTO.getSubNum());
        //写入事务日志
        /*ProductTransactionSubStockLogModel transactionSubStockLogModel = new ProductTransactionSubStockLogModel();
        transactionSubStockLogModel.setOrderDetailId(subStockDTO.getOrderDetailId());
        transactionSubStockLogModel.setOrderNo(subStockDTO.getOrderNo());
        transactionSubStockLogModel.setRepositoryId(subStockDTO.getRepositoryId());
        transactionSubStockLogModel.setSkuId(subStockDTO.getSkuId());
        transactionSubStockLogModel.setSubNum(subStockDTO.getSubNum());
        transactionSubStockLogService.save(transactionSubStockLogModel);*/
        return ObjectUtil.isNotNull(result) && result > 0;
    }

    @Override
    public boolean addStockBySkuIdAndRepositoryId(OrderAddStockDTO addStockDTO) {
        Integer result = productSkuStockMapper.addStockBySkuIdAndRepositoryId(addStockDTO.getSkuId(), addStockDTO.getRepositoryId(), addStockDTO.getCurrentStock(), addStockDTO.getAddNum());
        return ObjectUtil.isNotNull(result) && result > 0;
    }

    @Override
    public Integer countStockNum(Long skuId) {
        Integer count = productSkuStockMapper.countStockNum(skuId);
        return null == count ? 0 : count;
    }

    @Override
    public ProductSkuStockModel getBySkuAndRepoId(Long skuId, Long repoId) {
        return productSkuStockMapper.selectOne(
                new QueryWrapper<ProductSkuStockModel>()
                        .eq("sku_id", skuId)
                        .eq("repository_id", repoId)
        );
    }
}
