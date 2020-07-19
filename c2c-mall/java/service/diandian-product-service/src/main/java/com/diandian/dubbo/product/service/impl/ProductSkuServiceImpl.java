package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.RedisConstant;
import com.diandian.dubbo.facade.common.constant.TransportTypeConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.model.transport.TransportInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductShareService;
import com.diandian.dubbo.facade.service.product.ProductSkuPriceService;
import com.diandian.dubbo.facade.service.product.ProductSkuService;
import com.diandian.dubbo.facade.service.product.ProductSkuStockService;
import com.diandian.dubbo.facade.service.transport.TransportInfoService;
import com.diandian.dubbo.facade.vo.SkuVO;
import com.diandian.dubbo.product.mapper.ProductSkuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品sku
 *
 * @author zzhihong
 * @date 2019/02/20
 */
@Service("productSkuService")
@Slf4j
public class ProductSkuServiceImpl implements ProductSkuService {

    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private ProductSkuPriceService productSkuPriceService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MerchantProductInfoService merchantProductInfoService;
    @Autowired
    private TransportInfoService transportInfoService;
    @Autowired
    private ProductShareService productShareService;

    @Override
    public ProductSkuModel getById(Long id) {
        return productSkuMapper.selectById(id);
    }

    @Override
    public void save(ProductSkuModel productSkuModel) {
        productSkuMapper.insert(productSkuModel);
    }

    @Override
    public boolean updateById(ProductSkuModel productSkuModel) {
        int result = productSkuMapper.updateById(productSkuModel);
        return result > 0;
    }

    @Override
    public PageResult listSkuPage(Map<String, Object> params) {
        //已加入预售区的商品列表
        Long merchantId = (Long) params.get("merchantId");
        List<MerchantProductInfoModel> list = merchantProductInfoService.list(merchantId);
        String categoryId = (String) params.get("categoryId");
        if (null != categoryId) {
            String srt = stringRedisTemplate.opsForValue().get(RedisConstant.PRODUCT_CATEGORY_FOR_SEARCH + categoryId);
            if (StrUtil.isNotBlank(srt)) {
                JSONArray categoryIdList = JSON.parseArray(srt);
                params.put("cIdList", categoryIdList);
            }
        }
        params.put("state", BizConstant.ProductState.PUT_AWAY.value());
        params.put("delFlag", 0);
        //到付自提的IDS
        List<TransportInfoModel> express = transportInfoService.listByType(TransportTypeConstant.ONESELF.getValue());
//        List<TransportInfoModel> pinkage = transportInfoService.listByType(TransportTypeConstant.PINKAGE.getValue());
//        express.addAll(pinkage);
        Set<Long> tradeIdSet = express.stream().map(TransportInfoModel::getId).collect(Collectors.toSet());
//        Set<String> tradeIdStrSet = tradeIdSet.stream().map(i -> String.valueOf(i)).collect(Collectors.toSet());
//        String ids = tradeIdStrSet.stream().collect(Collectors.joining(","));
        List<Long> ids = new ArrayList<>(tradeIdSet);
        params.put("transportTypeId", ids);

        Page<SkuVO> page = new PageWrapper<SkuVO>(params).getPage();
        IPage<SkuVO> iPage = productSkuMapper.listSkuPage(page, params);
        List<SkuVO> records = iPage.getRecords();
//        List<SkuVO> newList = new ArrayList();
        if (records.size() > 0) {
            for (SkuVO record : records) {
                Long skuId = record.getSkuId();
                Integer stockNum = productSkuStockService.countStockNum(skuId);
                record.setStockNumber(stockNum);
                List<ProductSkuPriceModel> productSkuPriceModels = productSkuPriceService.listProductPrice(skuId);
                record.setPriceList(productSkuPriceModels);
                //  判断该商品是否被 该商户选中 是  isSeleted 置成 1
                if (list != null && list.size() > 0) {
                    for (MerchantProductInfoModel product : list) {
                        if (product.getSkuId().equals(record.getSkuId())) {
                            boolean flag = true;
                            record.setIsSelected(flag);
                            record.setIsSelectedShow(flag);
//                            iPage.setTotal(iPage.getTotal()-1);
                            break;
                        }
                    }
                }
//                if (record.getIsSelected().equals(0)) {
//                    newList.add(record);
//                }
            }
        }
//        iPage.setRecords(newList);
        return new PageResult(iPage);
    }

    @Override
    public SkuVO getBySkuId(Long id) {
        SkuVO skuVO = productSkuMapper.getSkuVOById(null, id);
        Long skuId = skuVO.getSkuId();
        Integer stockNum = productSkuStockService.countStockNum(skuId);
        skuVO.setStockNumber(stockNum);
        List<ProductSkuPriceModel> productSkuPriceModels = productSkuPriceService.listProductPrice(skuId);
        skuVO.setPriceList(productSkuPriceModels);
        return skuVO;
    }

    @Override
    public ProductSkuModel getSkuById(Long id,Long softwareId) {
        return productSkuMapper.getSkuById(id,softwareId);
    }

    @Override
    public SkuVO getBySkuIdAndUserId(Long userId, Long skuId) {
        SkuVO skuVO = getBySkuId(skuId);
        if(productShareService.checkSkuId(userId, skuId)){
            Map<String, BigDecimal> priceMap = productShareService.getPriceMap(userId, skuId);
            //修改标准版商品的价格
            List<ProductSkuPriceModel> productSkuPriceModels = skuVO.getPriceList();
            ProductSkuPriceModel productSkuPriceModel = productSkuPriceModels.get(2);
            productSkuPriceModel.setPrice(priceMap.get("price"));
            productSkuPriceModels.set(2,productSkuPriceModel);
            skuVO.setPriceList(productSkuPriceModels);
        }
        return skuVO;
    }

    @Override
    public List<ProductSkuModel> getByShopId(Long shopId) {
        return productSkuMapper.getByShopId(shopId);
    }
}
