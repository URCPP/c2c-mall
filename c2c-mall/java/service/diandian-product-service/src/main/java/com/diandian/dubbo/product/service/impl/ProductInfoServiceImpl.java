package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.api.result.MchProductResultDTO;
import com.diandian.dubbo.facade.dto.api.result.extend.MchProductResultExtendDTO;
import com.diandian.dubbo.facade.dto.biz.ProductInfoDTO;
import com.diandian.dubbo.facade.dto.biz.ProductInfoQueryDTO;
import com.diandian.dubbo.facade.dto.order.OrderSubStockDTO;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.service.merchant.MerchantProductInfoService;
import com.diandian.dubbo.facade.service.product.*;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.transport.TransportInfoService;
import com.diandian.dubbo.facade.vo.RepositoryDetailVO;
import com.diandian.dubbo.facade.vo.TransportInfoVO;
import com.diandian.dubbo.product.mapper.OrderDetailMapper;
import com.diandian.dubbo.product.mapper.ProductInfoMapper;
import com.diandian.dubbo.product.mapper.ProductSkuMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * 产品信息
 *
 * @author zzhihong
 * @date 2019/02/18
 */
@Service("productInfoService")
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSkuPriceService productSkuPriceService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private MerchantProductInfoService merchantProductInfoService;
    @Autowired
    private TransportInfoService transportInfoService;
    @Autowired
    private BizSoftwareTypeService bizSoftwareTypeService;
    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private ProductShareService productShareService;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Value("${aliyun.oss.domain}")
    private String imgDomain;

    @Value("${softwareTypeId}")
    private Long softwareTypeId;

    @Value("${softwareTypeIdZS}")
    private Long softwareTypeIdZS;

    @Value("${pusherId}")
    private Long pusherId;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String searchCategoryIds = (String) params.get("searchCategoryIds");
        if (StrUtil.isNotBlank(searchCategoryIds)) {
            params.put("searchCatelogIds", Arrays.asList(searchCategoryIds.split(",")));
        }
        Integer pageSize = Integer.valueOf((String) params.get("pageSize"));
        Integer curPage = (Integer.valueOf((String) params.get("curPage")) - 1) * pageSize;
        List<ProductInfoModel> list = productInfoMapper.listPage(curPage, pageSize, params);
        for (ProductInfoModel productInfoModel : list) {
            productInfoModel.getSkuList().forEach(sku -> {
                BigDecimal bigPrice = BigDecimal.ZERO;
                BigDecimal smallPrice = BigDecimal.ZERO;
                for (ProductSkuPriceModel productSkuPriceModel : sku.getPriceList()) {
                    if (softwareTypeId.equals(productSkuPriceModel.getSoftwareTypeId())) {
                        bigPrice = productSkuPriceModel.getPrice();
                        /*Long merchantId = Long.valueOf(params.get("merchantId").toString());
                        if(productShareService.checkSkuId(merchantId,sku.getId())){
                            Map<String, BigDecimal> priceMap = productShareService.getPriceMap(merchantId, sku.getId());
                            BigDecimal price = priceMap.get("price");
                            bigPrice = price;
                        }*/
                    }
                    if (softwareTypeIdZS.equals(productSkuPriceModel.getSoftwareTypeId())) {
                        smallPrice = productSkuPriceModel.getPrice();
                    }
                }
                productInfoModel.setPriceBZ(bigPrice);
                productInfoModel.setPriceDifference(bigPrice.subtract(smallPrice));
            });
        }
//        IPage<ProductInfoModel> page = productInfoMapper.listPage(new PageWrapper<ProductInfoModel>(params).getPage(), params);
        Page page = new Page();
        page.setRecords(list);
        return new PageResult(page);
    }

    @Override
    public PageResult listPageBackend(Map<String, Object> params) {
        String searchCategoryIds = (String) params.get("searchCategoryIds");
        String searchShopName = (String) params.get("searchShopName");
        if (StrUtil.isNotBlank(searchCategoryIds)) {
            params.put("searchCatelogIds", Arrays.asList(searchCategoryIds.split(",")));
        }
        if (StrUtil.isNotBlank(searchShopName)) {
            params.put("shopName", searchShopName);
        }
        IPage<ProductInfoModel> page = productInfoMapper.listPageBackend(new PageWrapper<ProductInfoModel>(params).getPage(), params);
        return new PageResult(page);
    }

    @Override
    public PageResult listBackend(Map<String, Object> params) {
        String searchCategoryIds = (String) params.get("searchCategoryIds");
        String searchShopName = (String) params.get("searchShopName");
        if (StrUtil.isNotBlank(searchCategoryIds)) {
            params.put("searchCatelogIds", Arrays.asList(searchCategoryIds.split(",")));
        }
        if (StrUtil.isNotBlank(searchShopName)) {
            params.put("shopName", searchShopName);
        }
        IPage<ProductInfoModel> page = productInfoMapper.listBackend(new PageWrapper<ProductInfoModel>(params).getPage(), params);
        return new PageResult(page);
    }

    @Override
    public PageResult listPageByShopId(Map<String, Object> params) {
        String searchCategoryIds = (String) params.get("searchCategoryIds");
        if (StrUtil.isNotBlank(searchCategoryIds)) {
            params.put("searchCatelogIds", Arrays.asList(searchCategoryIds.split(",")));
        }
        IPage<ProductInfoModel> page = productInfoMapper.getListPageByShopId(new PageWrapper<ProductInfoModel>(params).getPage(), params);
        return new PageResult(page);
    }

    @Override
    public ProductInfoModel getById(Long id) {
        return productInfoMapper.getById(id);
    }

    @Override
    public ProductInfoModel getProductById(Long id) {
        return productInfoMapper.getProductById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ProductInfoModel productInfoModel) {
        List<ProductSkuModel> skuList = productInfoModel.getSkuList();
        if (CollectionUtil.isEmpty(skuList)) {
            throw new DubboException("无法保存SKU信息");
        }
        productInfoMapper.insert(productInfoModel);
        skuList.forEach(skuItem -> {
            //新增SKU
            skuItem.setProductId(productInfoModel.getId());
            skuItem.setSkuName(this.genSkuName(productInfoModel.getProductName(), skuItem));
            skuItem.setShopId(productInfoModel.getShopId());
            productSkuService.save(skuItem);
            //新增SKU价格
            List<BizSoftwareTypeModel> softwareTypeModels = bizSoftwareTypeService.getOrderBySoftware();
            ProductSkuPriceModel productSkuPriceModel;
            for (BizSoftwareTypeModel bizSoftwareTypeModel : softwareTypeModels) {
                productSkuPriceModel = new ProductSkuPriceModel();
                productSkuPriceModel.setSoftwareTypeId(bizSoftwareTypeModel.getId());
                productSkuPriceModel.setSoftwareTypeName(bizSoftwareTypeModel.getTypeName());
                productSkuPriceModel.setSkuId(skuItem.getId());
                if (softwareTypeId.equals(bizSoftwareTypeModel.getId())) {
                    productSkuPriceModel.setPrice(productInfoModel.getStandardPrice());
                    productSkuPriceModel.setExchangeIntegral(productSkuPriceModel.getPrice());
                } else {
                    ShopInfoModel shopInfoModel = shopInfoService.getById(productInfoModel.getShopId());
                    //productSkuPriceModel.setPrice(productInfoModel.getStandardPrice().subtract(productInfoModel.getStandardPrice().multiply(shopInfoModel.getAgentProfit().add(shopInfoModel.getFloorPriceProportion()).add(shopInfoModel.getPlatformProfit())).multiply(mallProductShareBenefitPercentage)));
                    //productSkuPriceModel.setExchangeIntegral(productSkuPriceModel.getPrice());
                }
                productSkuPriceService.save(productSkuPriceModel);
            }
            //新增SKU库存
            skuItem.getStockList().forEach(stockItem -> {
                stockItem.setSkuId(skuItem.getId());
                productSkuStockService.save(stockItem);
            });
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(ProductInfoModel productInfoModel) {
        ProductInfoModel oldProduct = productInfoMapper.selectById(productInfoModel.getId());
        if (null == oldProduct) {
            throw new DubboException("商品信息不存在");
        }
        List<ProductSkuModel> skuList = productInfoModel.getSkuList();
        if (CollectionUtil.isEmpty(skuList)) {
            throw new DubboException("规格不能为空");
        }
        //BigDecimal mallProductShareBenefitPercentage = bizIdentityService.getById(pusherId).getMallProductShareBenefitPercentage();
        productInfoMapper.updateById(productInfoModel);
        skuList.forEach(skuItem -> {
            log.info("skuItem:{}", JSON.toJSONString(skuItem));
            String skuName = this.genSkuName(productInfoModel.getProductName(), skuItem);
            if (ObjectUtil.isNull(skuItem.getId())) {
                //不存在SKU项
                //新增SKU
                skuItem.setSkuName(skuName);
                skuItem.setShopId(productInfoModel.getShopId());
                productSkuService.save(skuItem);
                //新增SKU价格
                List<BizSoftwareTypeModel> softwareTypeModels = bizSoftwareTypeService.getOrderBySoftware();
                ProductSkuPriceModel productSkuPriceModel;
                for (BizSoftwareTypeModel bizSoftwareTypeModel : softwareTypeModels) {
                    productSkuPriceModel = new ProductSkuPriceModel();
                    productSkuPriceModel.setSoftwareTypeId(bizSoftwareTypeModel.getId());
                    productSkuPriceModel.setSoftwareTypeName(bizSoftwareTypeModel.getTypeName());
                    productSkuPriceModel.setSkuId(skuItem.getId());
                    if (softwareTypeId.equals(bizSoftwareTypeModel.getId())) {
                        productSkuPriceModel.setPrice(productInfoModel.getStandardPrice());
                        productSkuPriceModel.setExchangeIntegral(productSkuPriceModel.getPrice());
                    } else {
                        ShopInfoModel shopInfoModel = shopInfoService.getById(productInfoModel.getShopId());
                        BigDecimal exclusiveRate = BigDecimal.ZERO;
                        if (!"0".equals(productInfoModel.getExclusiveMemberPhone())) {
                            exclusiveRate = productInfoModel.getExclusiveRate();
                        }
                        //productSkuPriceModel.setPrice(productInfoModel.getStandardPrice().subtract(productInfoModel.getStandardPrice().multiply(shopInfoModel.getAgentProfit().add(shopInfoModel.getFloorPriceProportion()).add(shopInfoModel.getPlatformProfit()).subtract(exclusiveRate)).multiply(mallProductShareBenefitPercentage)));
                        productSkuPriceModel.setExchangeIntegral(productSkuPriceModel.getPrice());
                    }
                    productSkuPriceService.save(productSkuPriceModel);
                }
                //新增SKU库存
                skuItem.getStockList().forEach(stockItem -> {
                    stockItem.setSkuId(skuItem.getId());
                    productSkuStockService.save(stockItem);
                });
            } else {
                //存在SKU项 一般情况下是不更新SKU表的 要更新只能更新skuName 只更新价格和库存
                ProductSkuModel skuModel = new ProductSkuModel();
                skuModel.setId(skuItem.getId());
                skuModel.setSkuName(skuName);
                skuModel.setSkuName(skuItem.getSkuName());
                skuModel.setSpecName1(skuItem.getSpecName1());
                skuModel.setSpecValue1(skuItem.getSpecValue1());
                productSkuService.updateById(skuModel);
                //更新SKU价格
                skuItem.getPriceList().forEach(priceItem -> {
                    if (ObjectUtil.isNull(priceItem.getId())) {
                        if (softwareTypeId.equals(priceItem.getSoftwareTypeId())) {
                            priceItem.setPrice(productInfoModel.getStandardPrice());
                        } else {
                            ShopInfoModel shopInfoModel = shopInfoService.getById(productInfoModel.getShopId());
                            BigDecimal exclusiveRate = BigDecimal.ZERO;
                            if (!"0".equals(productInfoModel.getExclusiveMemberPhone())) {
                                exclusiveRate = productInfoModel.getExclusiveRate();
                            }
                            //priceItem.setPrice(productInfoModel.getStandardPrice().subtract(productInfoModel.getStandardPrice().multiply(shopInfoModel.getAgentProfit().add(shopInfoModel.getFloorPriceProportion()).add(shopInfoModel.getPlatformProfit()).subtract(exclusiveRate)).multiply(mallProductShareBenefitPercentage)));
                            priceItem.setExchangeIntegral(priceItem.getPrice());
                        }
                        priceItem.setSkuId(skuItem.getId());
                        productSkuPriceService.save(priceItem);
                    } else {
                        ProductSkuPriceModel update = new ProductSkuPriceModel();
                        update.setId(priceItem.getId());
                        update.setExchangeIntegral(priceItem.getExchangeIntegral());
                        if (softwareTypeId.equals(priceItem.getSoftwareTypeId())) {
                            update.setPrice(productInfoModel.getStandardPrice());
                        } else {
                            ShopInfoModel shopInfoModel = shopInfoService.getById(productInfoModel.getShopId());
                            BigDecimal exclusiveRate = BigDecimal.ZERO;
                            if (!"0".equals(productInfoModel.getExclusiveMemberPhone())) {
                                exclusiveRate = productInfoModel.getExclusiveRate();
                            }
                            //update.setPrice(productInfoModel.getStandardPrice().subtract(productInfoModel.getStandardPrice().multiply(shopInfoModel.getAgentProfit().add(shopInfoModel.getFloorPriceProportion()).add(shopInfoModel.getPlatformProfit()).subtract(exclusiveRate)).multiply(mallProductShareBenefitPercentage)));
                            update.setExchangeIntegral(priceItem.getPrice());
                        }
                        if (!productSkuPriceService.updateById(update)) {
                            throw new DubboException("更新SKU价格失败");
                        }
                    }
                });
                //更新SKU库存
                skuItem.getStockList().forEach(stockItem -> {
                    stockItem.setSkuId(skuItem.getId());
                    //有可能对已存在的sku新增仓库库存 会导致stockItem的ID为空 需要新增
                    if (ObjectUtil.isNull(stockItem.getId())) {
                        productSkuStockService.save(stockItem);
                    } else {
                        if (!productSkuStockService.updateById(stockItem)) {
                            throw new DubboException("更新SKU库存失败");
                        }
                    }

                });
            }
        });
        merchantProductInfoService.syncUpdateMchProduct(productInfoModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateState(Long id, Integer state, Long merchantId) {
        ProductInfoModel pro = new ProductInfoModel();
        pro.setId(id);
        pro.setState(state);
        pro.setUpdateBy(merchantId);
        productInfoMapper.updateById(pro);
    }

    /**
     * 生成SKU NAME
     *
     * @param productName
     * @param skuModel
     * @return
     */
    private String genSkuName(String productName, ProductSkuModel skuModel) {

        List<String> nameList = new ArrayList<>();
        nameList.add(productName);
        if (StrUtil.isNotBlank(skuModel.getSpecValue1())) {
            nameList.add(skuModel.getSpecValue1());
        }
        return CollectionUtil.join(nameList, StrUtil.SPACE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStateByIdBatch(List<Long> idList, Integer state, Long userId) {
        ProductInfoModel productInfoModel = new ProductInfoModel();
        productInfoModel.setState(state);
        productInfoModel.setUpdateBy(userId);
        productInfoMapper.update(productInfoModel, new LambdaQueryWrapper<ProductInfoModel>().in(ProductInfoModel::getId, idList));
        // todo 更新商户的兑换商品状态  删除
//        if (state == BizConstant.ProductState.OFF_AWAY.value()) {
//            merchantProductInfoService.updateProductStateBatch(idList, BizConstant.STATE_DISNORMAL);
//        }
    }

    @Override
    public void logicDeleteById(Long id, Long userId) {
        ProductInfoModel productInfoModel = new ProductInfoModel();
        productInfoModel.setId(id);
        productInfoModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        productInfoModel.setUpdateBy(userId);
        productInfoMapper.updateById(productInfoModel);

        //todo 兑换商品 删除
//        ArrayList<Long> idList = new ArrayList<>();
//        idList.add(id);
//        merchantProductInfoService.updateProductStateBatch(idList, BizConstant.STATE_DISNORMAL);
    }

    @Override
    public List<ProductInfoDTO> getProductInfoBySku(Map<String, Object> params) {
        return productInfoMapper.getProductBySku(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateDiscountById(List<Long> idList, BigDecimal discount, Long userId) {
        ProductInfoModel productInfoModel = new ProductInfoModel();
        productInfoModel.setDiscount(discount);
        productInfoModel.setUpdateBy(userId);
        productInfoMapper.update(productInfoModel, new LambdaQueryWrapper<ProductInfoModel>().in(ProductInfoModel::getId, idList));
    }

    @Override
    public List<RepositoryDetailVO> getProductRepositoryStock(Long productId, Long skuId) {
        return productInfoMapper.getProductRepositoryStock(productId, skuId);
    }

    @Override
    public List<TransportInfoVO> getProductTransportList(Long productId, List<Integer> transportTypeIds) {
        ProductInfoModel productInfo = productInfoMapper.selectById(productId);
        return transportInfoService.getTransportVOList(productInfo.getTransportIds(), transportTypeIds);
    }

    @Override
    public Map<String, Object> countRangeNum() {
        return productInfoMapper.countRangeNum();
    }

    @Override
    public Integer countProductInfo(ProductInfoQueryDTO dto) {
        QueryWrapper<ProductInfoModel> qw = new QueryWrapper<>();
        qw.eq(null != dto.getBrandId(), "brand_id", dto.getBrandId());
        qw.in(null != dto.getCategoryIds(), "category_id", dto.getCategoryIds());
        return productInfoMapper.selectCount(qw);
    }

    @Override
    public List<MchProductResultDTO> apiListPage(List<MchProductResultDTO> list, Long softTypeId) {
        List<Long> ids = new ArrayList<>();
        for (MchProductResultDTO item : list) {
            MchProductResultExtendDTO extendDTO = (MchProductResultExtendDTO) item;
            ids.add(extendDTO.getSkuId());
        }
        List<MchProductResultDTO> newList = productInfoMapper.apiList(ids, softTypeId);
        List<MchProductResultDTO> resList = new ArrayList<>();
        for (MchProductResultDTO oldObj : list) {
            MchProductResultExtendDTO oldExt = (MchProductResultExtendDTO) oldObj;
            MchProductResultDTO res = new MchProductResultDTO();
            for (MchProductResultDTO newObj : newList) {
                MchProductResultExtendDTO newExt = (MchProductResultExtendDTO) newObj;
                if (oldExt.getSkuId().equals(newExt.getSkuId())) {
                    oldExt.setPrice(newExt.getPrice());
                    oldExt.setWeight(newExt.getWeight());
                    oldExt.setVolume(newExt.getVolume());
                    oldExt.setProductHtml(newExt.getProductHtml());
                    oldExt.setServiceRate(newExt.getServiceRate());
                    List<String> thumbUrls = new ArrayList<>();
                    if (StringUtils.isNotBlank(newExt.getThumbUrlStr())) {
                        List<String> strings = Arrays.asList(newExt.getThumbUrlStr().split(","));
                        for (String item : strings) {
                            thumbUrls.add(imgDomain + item);
                        }
                    }
                    oldExt.setThumbUrls(thumbUrls);
                }
            }
            BeanUtil.copyProperties(oldExt, res);
            resList.add(res);
        }
        return resList;
    }

    @Override
    public MchProductResultDTO apiGetById(MchProductResultDTO dto, Long softTypeId) {
        MchProductResultExtendDTO oldExt = (MchProductResultExtendDTO) dto;
        MchProductResultExtendDTO newExt = (MchProductResultExtendDTO) productInfoMapper.apiGetById(oldExt.getSkuId(), softTypeId);
        oldExt.setPrice(newExt.getPrice());
        oldExt.setWeight(newExt.getWeight());
        oldExt.setVolume(newExt.getVolume());
        oldExt.setProductHtml(newExt.getProductHtml());
        oldExt.setServiceRate(newExt.getServiceRate());
        List<String> thumbUrls = new ArrayList<>();
        if (StringUtils.isNotBlank(newExt.getThumbUrlStr())) {
            List<String> strings = Arrays.asList(newExt.getThumbUrlStr().split(","));
            for (String item : strings) {
                thumbUrls.add(imgDomain + item);
            }
        }
        oldExt.setThumbUrls(thumbUrls);
        MchProductResultDTO res = new MchProductResultDTO();
        BeanUtil.copyProperties(oldExt, res);
        return res;
    }

    @Override
    public List<ProductInfoModel> getByIds(List<Long> ids) {
        return productInfoMapper.getByIds(ids);
    }

    @Override
    public ProductInfoModel getSingleTableById(Long productId) {
        return productInfoMapper.selectById(productId);
    }

    @Override
    public void batchUpdateTagById(List<Long> idList, String tag, Integer flag, Long userId) {
        ProductInfoModel productInfoModel = new ProductInfoModel();
        try {
            Field field = ReflectUtil.getField(productInfoModel.getClass(), tag);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), productInfoModel.getClass());
            Method setMethod = pd.getWriteMethod();
            setMethod.invoke(productInfoModel, flag);
        } catch (Exception e) {
            throw new DubboException("参数错误");
        }
        productInfoModel.setUpdateBy(userId);
        productInfoMapper.update(productInfoModel, new LambdaQueryWrapper<ProductInfoModel>().in(ProductInfoModel::getId, idList));
    }

    @Override
    public PageResult getListPageByShopId(Map<String, Object> params) {
        String searchCategoryIds = (String) params.get("searchCategoryIds");
        params.put("softwareTypeId", softwareTypeId);
        if (StrUtil.isNotBlank(searchCategoryIds)) {
            params.put("searchCatelogIds", Arrays.asList(searchCategoryIds.split(",")));
        }
        IPage<ProductInfoModel> page = productInfoMapper.getListPageByShopId(new PageWrapper<ProductInfoModel>(params).getPage(), params);
        return new PageResult(page);
    }

    @Override
    public ProductInfoModel getByShareId(Long productId, String phone, Long shareId) {
        ProductInfoModel productInfoModel = getById(productId);
        List<ProductSkuModel> skuList = productInfoModel.getSkuList();
        for (ProductSkuModel productSkuModel : skuList) {
            Long skuId = productSkuModel.getId();
            BigDecimal addPrice = productShareService.getProductShare(shareId).getAddPrice();
            List<ProductSkuPriceModel> priceList = productSkuModel.getPriceList();
            productSkuModel.setAddPrice(addPrice);
            productSkuModel.setPriceList(priceList);
        }
        return productInfoModel;
    }

    @Override
    public ProductInfoModel getBySkuIdAndUserId(Long userId, Long skuId) {
        Long productId = productSkuMapper.getSkuById(skuId, softwareTypeId).getProductId();
        ProductInfoModel productInfoModel = getById(productId);
        if (productShareService.checkSkuId(userId, skuId)) {
            Map<String, BigDecimal> priceMap = productShareService.getPriceMap(userId, skuId);
            productInfoModel.setPrice(priceMap.get("price"));
        }
        return productInfoModel;
    }

    @Override
    public List<ProductInfoModel> getByShopId(Map<String, Object> params) {
        List<ProductInfoModel> list = productInfoMapper.getByShopId(params);
        for (ProductInfoModel productInfoModel : list) {
            productInfoModel.getSkuList().forEach(sku -> {
                BigDecimal bigPrice = BigDecimal.ZERO;
                BigDecimal smallPrice = BigDecimal.ZERO;
                for (ProductSkuPriceModel productSkuPriceModel : sku.getPriceList()) {
                    if (softwareTypeId.equals(productSkuPriceModel.getSoftwareTypeId())) {
                        bigPrice = productSkuPriceModel.getPrice();
                    }
                    if (softwareTypeIdZS.equals(productSkuPriceModel.getSoftwareTypeId())) {
                        smallPrice = productSkuPriceModel.getPrice();
                    }
                }
                productInfoModel.setPriceBZ(bigPrice);
                productInfoModel.setPriceDifference(bigPrice.subtract(smallPrice));
            });
        }
        return list;
    }

    @Override
    public PageResult getProductByShopId(Map<String, Object> params) {
        params.put("state", BizConstant.STATE_DISNORMAL);
        params.put("exclusiveMemberPhone", "0");
        IPage<ProductInfoModel> page = productInfoMapper.getListPageByShopId(
                new PageWrapper<ProductInfoModel>(params).getPage(), params);
        return new PageResult(page);
    }

    @Override
    public List<Map> getExclusiveProduct(String phone, Long memberId) {

        List<Map> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
        calendar.set(Calendar.DAY_OF_MONTH, -1); //当前时间减去一天，即一天前的时间
        List<ProductInfoModel> productInfoModels = productInfoMapper.getExclusiveProduct(phone);
        for (ProductInfoModel productInfoModel : productInfoModels) {
            Map map = new HashMap();
            map.put("productInfoModel", productInfoModel);
            map.put("todaySalesVolume", orderDetailMapper.getCountByProductId(productInfoModel.getId(), new Date()));
            map.put("salesVolume", orderDetailMapper.getCountByProductId(productInfoModel.getId(), null));
            //map.put("todayProfit",shopOrderShareBenefitRecordService.getExclusiveProductShare(productInfoModel.getId(),memberId,calendar.getTime()));
            //map.put("outstandingAccountProfit",shopOrderShareBenefitRecordService.getExclusiveProductShare(productInfoModel.getId(),memberId,new Date()));
            //map.put("totalSum",shopOrderShareBenefitRecordService.getExclusiveProductShare(productInfoModel.getId(),memberId,null));
            list.add(map);
        }
        return list;
    }

    @Override
    public List<ProductInfoModel> getExclusiveProductCount(String phone) {
        return productInfoMapper.getExclusiveProduct(phone);
    }

    @Override
    public void updateExclusiveRate(ProductInfoModel productInfoModel) {
        // BigDecimal mallProductShareBenefitPercentage=bizIdentityService.getById(pusherId).getMallProductShareBenefitPercentage();
        productInfoModel.getSkuList().forEach(skuItem -> {
            for (ProductSkuPriceModel priceModel : skuItem.getPriceList()) {
                if (softwareTypeId.equals(priceModel.getSoftwareTypeId())) {
                    productInfoModel.setStandardPrice(priceModel.getPrice());
                }
            }
            skuItem.getPriceList().forEach(priceItem -> {
                ProductSkuPriceModel productSkuPriceModel = new ProductSkuPriceModel();
                if (!softwareTypeId.equals(priceItem.getSoftwareTypeId())) {
                    ShopInfoModel shopInfoModel = shopInfoService.getById(productInfoModel.getShopId());
                    BigDecimal exclusiveRate = BigDecimal.ZERO;
                    if (!"0".equals(productInfoModel.getExclusiveMemberPhone())) {
                        exclusiveRate = productInfoModel.getExclusiveRate();
                    }
                    //priceItem.setPrice(productInfoModel.getStandardPrice().subtract(productInfoModel.getStandardPrice().multiply(shopInfoModel.getAgentProfit().add(shopInfoModel.getFloorPriceProportion()).add(shopInfoModel.getPlatformProfit()).subtract(exclusiveRate)).multiply(mallProductShareBenefitPercentage)));
                    priceItem.setExchangeIntegral(priceItem.getPrice());
                    if (!productSkuPriceService.updateById(priceItem)) {
                        throw new DubboException("更新SKU价格失败");
                    }
                }

            });

        });
        productInfoMapper.updateById(productInfoModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProductSave(ProductInfoModel productInfoModel) {
        {
            List<ProductSkuModel> skuList = productInfoModel.getSkuList();
            if (CollectionUtil.isEmpty(skuList)) {
                throw new DubboException("无法保存SKU信息");
            }
            productInfoMapper.insert(productInfoModel);
            skuList.forEach(skuItem -> {
                //新增SKU
                skuItem.setProductId(productInfoModel.getId());
                skuItem.setSkuName(this.genSkuName(productInfoModel.getProductName(), skuItem));
                skuItem.setShopId(productInfoModel.getShopId());
                productSkuService.save(skuItem);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductInfoModel productInfoModel) {
        ProductInfoModel product = productInfoMapper.selectById(productInfoModel.getId());
        if (null == product) {
            throw new DubboException("商品信息不存在");
        }
        List<ProductSkuModel> skuList = productInfoModel.getSkuList();
        if (CollectionUtil.isEmpty(skuList)) {
            throw new DubboException("规格不能为空");
        }
        //更新商品表
        productInfoMapper.updateById(product);
        skuList.forEach(skuItem -> {
            log.info("skuItem:{}", JSON.toJSONString(skuItem));
            String skuName = this.genSkuName(productInfoModel.getProductName(), skuItem);
            if (ObjectUtil.isNull(skuItem.getId())) {
                //不存在SKU项
                //新增SKU
                skuItem.setSkuName(skuName);
                skuItem.setShopId(productInfoModel.getShopId());
                productSkuService.save(skuItem);
            } else {
                //存在SKU项 一般情况下是不更新SKU表的 要更新只能更新skuName 只更新价格和库存
                ProductSkuModel sku = new ProductSkuModel();
                sku.setId(skuItem.getId());
                sku.setSkuName(skuName);
                sku.setSkuName(skuItem.getSkuName());
                sku.setSpecName1(skuItem.getSpecName1());
                sku.setSpecValue1(skuItem.getSpecValue1());
                productSkuService.updateById(sku);
            }
        });
    }

    @Override
    public boolean subStock(OrderSubStockDTO productInfoModel) {
        Integer result=productInfoMapper.subStock(productInfoModel.getId(),productInfoModel.getCurrentStock(),productInfoModel.getSubNum());
        return ObjectUtil.isNotNull(result) && result > 0;
    }

}
