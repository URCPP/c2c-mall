package com.diandian.dubbo.business.service.impl.merchant;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantOpenPlatformMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantProductInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.TransportTypeConstant;
import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.api.query.GetProductDetailDTO;
import com.diandian.dubbo.facade.dto.api.query.GetProductListDTO;
import com.diandian.dubbo.facade.dto.api.result.MchProductResultDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantProductInfoDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.model.transport.TransportInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductSkuService;
import com.diandian.dubbo.facade.service.transport.TransportInfoService;
import com.diandian.dubbo.facade.vo.ProductStateNumberVO;
import com.diandian.dubbo.facade.vo.SkuVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantProductInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 商户预售商品信息表
 *
 * @author jbh
 * @date 2019/02/21
 */
@Service("merchantProductInfoService")
@Slf4j
public class MerchantProductInfoServiceImpl implements MerchantProductInfoService {
    @Autowired
    private MerchantProductInfoMapper merchantProductInfoMapper;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private TransportInfoService transportInfoService;
    @Autowired
    private MerchantOpenPlatformMapper merchantOpenPlatformMapper;
    @Autowired
    private MerchantInfoMapper merchantInfoMapper;
    @Autowired
    private MerchantInfoServiceImpl merchantInfoService;

    @Override
    public PageResult listProductInfo(Map<String, Object> params) {

        Page<MerchantProductInfoVO> page = new PageWrapper<MerchantProductInfoVO>(params).getPage();

        IPage<MerchantProductInfoVO> iPage = merchantProductInfoMapper.listMerchantProductPage(page, params);
        List<MerchantProductInfoVO> records = iPage.getRecords();
        if (records.size() > 0) {
            for (MerchantProductInfoVO record : records) {
                Long skuId = record.getSkuId();
                SkuVO sku = productSkuService.getBySkuId(skuId);
                record.setSku(sku);
            }
        }
        return new PageResult(iPage);
    }

    @Override
    public void updateProduct(MerchantProductInfoModel model) {
        merchantProductInfoMapper.updateById(model);
    }

    @Override
    public MerchantProductInfoModel getProduct(Long id) {
        QueryWrapper<MerchantProductInfoModel> qw = new QueryWrapper<>();
        qw.eq("id", id);
        qw.eq("del_flag", BizConstant.STATE_NORMAL);
        return merchantProductInfoMapper.selectOne(qw);
    }

    @Override
    public boolean saveMerProduct(MerchantInfoModel merchantInfo, SkuVO sku) {

        QueryWrapper<MerchantProductInfoModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantInfo.getId());
        qw.eq("product_id", sku.getProductId());
        qw.eq("sku_id", sku.getSkuId());

        MerchantProductInfoModel mpim = merchantProductInfoMapper.selectOne(qw);

        //添加商户预售商品记录
        MerchantProductInfoModel merchantProductInfoModel = new MerchantProductInfoModel();
        merchantProductInfoModel.setMerchantId(merchantInfo.getId());
        merchantProductInfoModel.setProductId(sku.getProductId());
        merchantProductInfoModel.setSkuId(sku.getSkuId());
        merchantProductInfoModel.setProductStateFlag(BizConstant.MerProductState.OFF_AWAY.value());
        merchantProductInfoModel.setSort(0);
        merchantProductInfoModel.setExchangeNum(0);
        merchantProductInfoModel.setSkuName(sku.getSkuName());
        merchantProductInfoModel.setCategoryName(sku.getCategoryName());
        merchantProductInfoModel.setTransportIds(sku.getGetTransportIds());
        merchantProductInfoModel.setCategoryId(sku.getCategoryId());

        Integer exchangePrice = 0;
        BigDecimal productPrice = BigDecimal.ZERO;

        List<ProductSkuPriceModel> priceList = sku.getPriceList();
        if (priceList.size() > 0) {
            for (ProductSkuPriceModel productSkuPriceModel : priceList) {
                if (productSkuPriceModel.getSoftwareTypeId().equals(merchantInfo.getSoftTypeId())) {
                    exchangePrice = productSkuPriceModel.getExchangeIntegral().intValue();
                    // todo 商家兑换商品成本价格 加服务费
                    BigDecimal serviceRate = sku.getServiceRate() == null ? BigDecimal.ZERO : sku.getServiceRate();
                    BigDecimal serviceFee = serviceRate.divide(new BigDecimal(100 + ""), 2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1.0 + ""));
                    productPrice = productSkuPriceModel.getPrice().multiply(serviceFee);
                    break;
                }
            }
        }
        merchantProductInfoModel.setExchangePrice(exchangePrice);
        merchantProductInfoModel.setProductCost(productPrice);
        merchantProductInfoModel.setProductStateFlag(BizConstant.STATE_NORMAL);
        if (null == mpim) {
            merchantProductInfoMapper.insert(merchantProductInfoModel);
        }else {
            merchantProductInfoModel.setId(mpim.getId());
            merchantProductInfoModel.setDelFlag(BizConstant.STATE_NORMAL);
            merchantProductInfoModel.setCreateTime(new Date());
            merchantProductInfoMapper.updateById(merchantProductInfoModel);
        }

        return true;
    }

    @Override
    public List<MerchantProductInfoModel> list(Long merchantId) {
        QueryWrapper<MerchantProductInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId);
        wrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        return merchantProductInfoMapper.selectList(wrapper);
    }

    @Override
    public MerchantProductInfoDTO getProductTotal(Map<String, Object> params) {
        return merchantProductInfoMapper.getProductTotal(params);
    }

    @Override
    public MerchantProductInfoModel getProductByMidAndSid(Long merchantId, Long skuId) {
        QueryWrapper<MerchantProductInfoModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        qw.eq("sku_id", skuId);
        return merchantProductInfoMapper.selectOne(qw);
    }

    @Override
    public void updateProductStateBatch(List<Long> idList, Integer state) {
        MerchantProductInfoModel model = new MerchantProductInfoModel();
        model.setDelFlag(state);
        merchantProductInfoMapper.update(model, new LambdaQueryWrapper<MerchantProductInfoModel>().in(MerchantProductInfoModel::getProductId, idList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveMerProduct(MerchantInfoModel merchantInfo, List<SkuVO> products){
        for (SkuVO product : products){
            if (product.getIsSelectedShow()) {
                String transportIdsStr = product.getGetTransportIds();
                transportIdsStr = this.getProductTransportId(transportIdsStr);
                product.setGetTransportIds(transportIdsStr.split(",")[0]);
                this.saveMerProduct(merchantInfo, product);
            }
        }
    }

    @Override
    public void batchSaveMerProductState(Long merchantId, Long productId, Integer state){
        UpdateWrapper<MerchantProductInfoModel> uw = new UpdateWrapper<>();
        uw.eq(null != merchantId,"merchant_id", merchantId);
        uw.eq(null != productId,"product_id", productId);
        MerchantProductInfoModel update = new MerchantProductInfoModel();
        update.setProductStateFlag(state);
        merchantProductInfoMapper.update(update, uw);
    }

    @Override
    public ProductStateNumberVO statisticsProductState(Long merchantId, List<Long> productIds){
        return merchantProductInfoMapper.statisticsProductState(merchantId, productIds);
    }

    @Override
    public PageResult apiListPage(GetProductListDTO dto){
        Long merchantId = merchantOpenPlatformMapper.getMerchantIdByAppId(dto.getAppId());
        //MerchantInfoModel oldMchInfo = merchantInfoService.apiCheckMchIsNormal(merchantId);
        Map<String, Object> params = new HashMap<>();
        if(null != dto.getPage()){
            params.put("curPage", dto.getPage().toString());
        }
        if(null != dto.getPageSize()){
            params.put("pageSize", dto.getPageSize().toString());
        }
        params.put("categoryId", dto.getCategoryId());
        params.put("merchantId", merchantId);
        IPage<MchProductResultDTO> iPage = merchantProductInfoMapper.apiListPage(new PageWrapper<>(params).getPage(), params);
        iPage.setRecords(productInfoService.apiListPage(iPage.getRecords(), 123L));
        return new PageResult(iPage);
    }

    @Override
    public MchProductResultDTO apiGetById(GetProductDetailDTO dto){
        Long merchantId = merchantOpenPlatformMapper.getMerchantIdByAppId(dto.getAppId());
        //MerchantInfoModel oldMchInfo = merchantInfoService.apiCheckMchIsNormal(merchantId);
        MchProductResultDTO mchProductResult = merchantProductInfoMapper.apiGetById(dto.getProductId());
        if(null == mchProductResult){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40009_CODE, IntegralStoreConstant.ERROR_40009_MESSAGE);
        }
        return productInfoService.apiGetById(mchProductResult, 123L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncUpdateMchProduct(ProductInfoModel product){
        List<MerchantProductInfoModel> mchProductList = merchantProductInfoMapper.selectList(new LambdaQueryWrapper<MerchantProductInfoModel>().eq(MerchantProductInfoModel::getProductId, product.getId()));
        for (MerchantProductInfoModel mchProduct: mchProductList){
            MerchantProductInfoModel update = new MerchantProductInfoModel();
            UpdateWrapper<MerchantProductInfoModel> uw = new UpdateWrapper<>();
            MerchantInfoModel mchInfo = merchantInfoMapper.selectById(mchProduct.getMerchantId());
            if(null == mchInfo){
                throw new DubboException("商品信息错误，请联系技术人员");
            }
            uw.eq("id", mchProduct.getId());
            boolean needUpdate = false;
            if(StrUtil.isNotBlank(product.getTransportIds())){
                String productTransportId = this.getProductTransportId(product.getTransportIds());
                if(StrUtil.isBlank(productTransportId)){
                    needUpdate = true;
                    update.setProductStateFlag(BizConstant.MerProductState.OFF_AWAY.value());
                    uw.set("transport_ids", null);
                }else if(StrUtil.isNotBlank(mchProduct.getTransportIds()) && productTransportId.indexOf(mchProduct.getTransportIds()) == -1){
                    needUpdate = true;
                    update.setTransportIds(productTransportId.split(",")[0]);
                }
            }
            if(!BizConstant.ProductState.PUT_AWAY.value().equals(product.getState())){
                update.setProductStateFlag(BizConstant.MerProductState.OFF_AWAY.value());
            }
            if(!product.getCategoryId().equals(mchProduct.getCategoryId())){
                needUpdate = true;
                update.setCategoryId(product.getCategoryId());
                update.setCategoryName(product.getCategoryName());
            }
            for (ProductSkuModel sku : product.getSkuList()){
                if(!mchProduct.getSkuId().equals(sku.getId())){
                    continue;
                }
                for (ProductSkuPriceModel skuPrice : sku.getPriceList()){
                    if(!mchInfo.getSoftTypeId().equals(skuPrice.getSoftwareTypeId())){
                        continue;
                    }
                    BigDecimal serviceRate = product.getServiceRate() == null ? BigDecimal.ZERO : product.getServiceRate();
                    BigDecimal serviceFee = serviceRate.divide(new BigDecimal(100 + ""), 2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1.0 + ""));
                    BigDecimal mchProductPrice = skuPrice.getPrice().multiply(serviceFee);
                    if(mchProduct.getProductCost().compareTo(mchProductPrice) != 0){
                        needUpdate = true;
                        update.setProductCost(mchProductPrice);
                    }
                }
            }
            if(needUpdate){
                int i = merchantProductInfoMapper.update(update, uw);
                if(i <= 0){
                    throw new DubboException("积分商城商品同步更新失败");
                }
            }
        }
    }

    @Override
    public Integer countByProductId(Long productId){
        return merchantProductInfoMapper.countByProductId(productId);
    }

    private String getProductTransportId(String transportIdsStr){
        List<TransportInfoModel> transportList = transportInfoService.listByType(TransportTypeConstant.ONESELF.getValue());
        int totalLength = transportIdsStr.length();
        for (TransportInfoModel transport : transportList){
            String idStr = transport.getId().toString();
            int index = transportIdsStr.indexOf(transport.getId().toString());
            if(index == -1){
                continue;
            }
            int length = idStr.length();
            if((index + length) == totalLength){
                String newStr = transportIdsStr.substring(index == 0 ? index : (index - 1), totalLength);
                transportIdsStr = transportIdsStr.replace(newStr, "");
            }else{
                String newStr = transportIdsStr.substring(index, index + length + 1);
                transportIdsStr = transportIdsStr.replace(newStr, "");
            }
            totalLength = transportIdsStr.length();
        }
        return transportIdsStr;
    }
}
