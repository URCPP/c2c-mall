package com.diandian.dubbo.product.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.diandian.dubbo.facade.model.member.MemberInfoModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductShareModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.service.member.MemberInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.product.ProductShareService;
import com.diandian.dubbo.facade.service.product.ProductSkuPriceService;
import com.diandian.dubbo.product.mapper.ProductInfoMapper;
import com.diandian.dubbo.product.mapper.ProductShareMapper;
import com.diandian.dubbo.product.mapper.ProductSkuMapper;
import com.diandian.dubbo.product.mapper.ProductSkuPriceMapper;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品加价
 *
 * @author chensong
 * @date 2019/09/03
 */
@Service("productShareService")
@Slf4j
public class ProductShareServiceImpl implements ProductShareService {

    @Autowired
    private ProductShareMapper productShareMapper;
    @Autowired
    private ProductSkuPriceService productSkuPriceService;
//    @Autowired
//    private BizMemberService bizMemberService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Value("${softwareTypeId}")
    private Long softwareTypeId;

    @Override
    public Map<String, Object> addPrice(Map<String, Object> params) {
        ProductShareModel productShareModel = new ProductShareModel();
        //设置商户id
        Long merchantId = Long.parseLong(params.get("merchantId").toString()) ;
        productShareModel.setMerchantId(merchantId);
        //设置商品id
        Long skuId = Long.parseLong(params.get("productSkuId").toString());
        productShareModel.setSkuId(skuId);
        //获取价格类型
        Integer priceStyle = Integer.parseInt(params.get("priceStyle").toString());
        productShareModel.setPriceStyle(priceStyle);
        //设置加价
        BigDecimal addPrice = BigDecimal.valueOf(Double.valueOf(params.get("addPrice").toString()));
        //获取商品原价
        BigDecimal prePrice = productSkuPriceService.getBySkuAndSoftTypeId(skuId, 1104994625655316481L).getPrice();
        Map<String,Object> map = new HashMap<>();
        //设置分享价格
        if(priceStyle==1){
            addPrice = addPrice.divide(BigDecimal.valueOf(100));
            addPrice = prePrice.multiply(addPrice);
        }
        productShareModel.setAddPrice(addPrice);
        //持久化
        productShareMapper.insert(productShareModel);
        //设置分享Id
        map.put("shareId",productShareModel.getId());
        map.put("addPrice",addPrice);
        BigDecimal price = prePrice.add(addPrice);
        map.put("price",price);
        String phone = merchantInfoService.getMerchantInfoById(merchantId).getPhone();
//        BizMemberModel bizMemberModel = bizMemberService.getOneByPhone(phone);
//        if(ObjectUtil.isNotNull(bizMemberModel)){
//            String invitationCode = bizMemberModel.getInvitationCode();
//            map.put("invitationCode",invitationCode);
//        }
        return map;
    }

    @Override
    public void bind(Long id, Long userId) {
        //根据分享ID获取分享的商品数据
        ProductShareModel preProductShareModel = productShareMapper.selectById(id);
        //获取商户ID
        Long merchantId = preProductShareModel.getMerchantId();
        Long skuId = preProductShareModel.getSkuId();
        if(!merchantId.equals(userId)) {
            if (!checkSkuId(userId, skuId)) {
                //新建一条记录
                ProductShareModel productShareModel = new ProductShareModel();
                //设置商户Id
                productShareModel.setMerchantId(preProductShareModel.getMerchantId());
                //设置增加价格
                BigDecimal addPrice = preProductShareModel.getAddPrice();
                productShareModel.setAddPrice(addPrice);
                //设置用户Id
                productShareModel.setUserId(userId);
                //设置分享标记
                productShareModel.setFlag(1);
                //设置产品Id
                Long productSkuId = preProductShareModel.getSkuId();
                productShareModel.setSkuId(productSkuId);
                //设置价格类型
                Integer priceStyle = preProductShareModel.getPriceStyle();
                productShareModel.setPriceStyle(priceStyle);
                //绑定用户ID
                productShareMapper.insert(productShareModel);
            } else {
                ProductShareModel productShareModel = productShareMapper.selectOne(new QueryWrapper<ProductShareModel>().eq("sku_id", skuId).eq("user_id", userId));
                productShareModel.setUserId(userId);
                //设置增加价格
                BigDecimal addPrice = preProductShareModel.getAddPrice();
                productShareModel.setAddPrice(addPrice);
                //设置价格类型
                Integer priceStyle = preProductShareModel.getPriceStyle();
                productShareModel.setPriceStyle(priceStyle);
                productShareMapper.updateById(productShareModel);
            }
        }
    }

    @Override
    public boolean checkSkuId(Long userId, Long skuId) {
        List<ProductShareModel> productShareModels = productShareMapper.selectList(
                new QueryWrapper<ProductShareModel>()
                        .eq("sku_id", skuId)
                        .eq("user_id",userId)
        );
        if(productShareModels.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public Map<String,BigDecimal> getPriceMap(Long userId, Long skuId) {
        Map<String,BigDecimal> map = new HashMap<>();
        ProductShareModel productShareModel = productShareMapper.selectOne(
                new QueryWrapper<ProductShareModel>()
                        .eq("sku_id", skuId)
                        .eq("user_id",userId)
        );
        if(ObjectUtil.isNotNull(productShareModel)){
            //获取原价
            BigDecimal prePrice = productSkuPriceService.getBySkuAndSoftTypeId(skuId, 1104994625655316481L).getPrice();
            map.put("prePrice",prePrice);
            //获取加价
            Integer priceStyle = productShareModel.getPriceStyle();
            BigDecimal addPrice = productShareModel.getAddPrice();
            if(priceStyle==1){
                addPrice = addPrice.divide(BigDecimal.valueOf(100));
                addPrice = prePrice.multiply(addPrice);
            }
            map.put("addPrice",addPrice);
            //获取现价
            BigDecimal price = prePrice.add(addPrice);
            map.put("price",price);
        }
        return map;
    }

    @Override
    public ProductShareModel getProductShare(Long userId, Long skuId) {
        ProductShareModel productShareModel = productShareMapper.selectOne(
                new QueryWrapper<ProductShareModel>()
                        .eq("sku_id", skuId)
                        .eq("user_id",userId)
        );
        return productShareModel;
    }

    @Override
    public ProductShareModel getProductShare(Long shareId) {
        return productShareMapper.selectOne(new QueryWrapper<ProductShareModel>().eq("id", shareId));
    }

    @Override
    public void setFlagById(ProductShareModel productShareModel) {
        productShareMapper.updateById(productShareModel);
    }

    @Override
    public void deleteById(ProductShareModel productShareModel) {
        productShareMapper.deleteById(productShareModel);
    }

}
