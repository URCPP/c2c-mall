package com.diandian.admin.merchant.modules.api.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.ProductInfoDTO;
import com.diandian.dubbo.facade.dto.biz.ShoppingCartDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantShoppingCartModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductShareModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.service.merchant.MerchantShoppingCartService;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author zzj
 * @since 2019-02-26
 */
@RestController
@RequestMapping("/api/merchantShoppingCart")
public class MerchantShoppingCartController extends BaseController {
    @Autowired
    private MerchantShoppingCartService merchantShoppingCartServer;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductShareService productShareService;

    List setProductInfo(List<MerchantShoppingCartModel> shoppingCartList, List<ProductInfoDTO> productInfoBySku) {
        productInfoBySku.forEach(p -> {
            shoppingCartList.forEach(s -> {
                if (s.getSkuId().equals(p.getProductSku().getId())) {
                    p.setId(s.getId());
                    p.setSkuId(p.getProductSku().getId());
                    p.setShareId(s.getShareId());
                    p.setMerchantId(s.getMerchantId());
                    p.setNum(s.getNum());
                    p.setProductId(s.getProductId());
                    p.setRepositoryId(s.getRepositoryId());
                    p.setTransportId(s.getTransportId());
                    p.setSpecName(s.getSpecName());
                    p.setSpecValue(s.getSpecValue());
                }
            });
        });
        return productInfoBySku;
    }

    @GetMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        params.put("merchantId", getMerchantInfoId());
        params.put("softwareTypeId", getMerchantInfo().getSoftTypeId());
        PageResult pageResult = merchantShoppingCartServer.listPage(params);
        List<MerchantShoppingCartModel> shoppingCartList = (List<MerchantShoppingCartModel>) pageResult.getList();
        if (CollectionUtil.isEmpty(shoppingCartList)) {
            return RespData.ok(pageResult);
        }
        List<Long> skuIds = new ArrayList<>();
        shoppingCartList.forEach(el->{
            skuIds.add(el.getSkuId());
        });
        params.put("skuIds", skuIds);
        List<ProductInfoDTO> productInfoBySku = productInfoService.getProductInfoBySku(params);
        this.setProductInfo(shoppingCartList, productInfoBySku);
        productInfoBySku.sort((a,b)-> b.getId()>a.getId()?1:-1);

        if ("1104994513978195969".equals(getMerchantInfo().getSoftTypeId().toString())||
                "1104994214865600514".equals(getMerchantInfo().getSoftTypeId().toString())){
            Map<String,Object> map = new HashMap<>(2);
            map.put("softwareTypeId",1104994625655316481L);
            map.put("skuIds", skuIds);
            List<ProductInfoDTO> infos = productInfoService.getProductInfoBySku(map);
            productInfoBySku.forEach(productInfoDTO -> {
                infos.forEach(productInfoDTO1 -> {
                    if (productInfoDTO.getProductSku().getProductId().equals(productInfoDTO1.getProductSku().getProductId())){
                        BigDecimal multiply =
                                productInfoDTO1.getProductSkuPrice().getPrice().subtract(productInfoDTO.getProductSkuPrice().getPrice());
                        productInfoDTO.setProvinceMoney(multiply);
                    }
                });
            });
        }

        List<ShoppingCartDTO> shoppingCartDTOS = new ArrayList<>();
        ShoppingCartDTO shoppingCartDTO =new ShoppingCartDTO();
        shoppingCartDTO.setShopName(productInfoBySku.get(0).getProductInfo().getShopName());
        List<ProductInfoDTO> productInfoDTOS = new ArrayList<>();
        productInfoDTOS.add(productInfoBySku.get(0));
        shoppingCartDTO.setProductInfoDTOList(productInfoDTOS);
        shoppingCartDTOS.add(shoppingCartDTO);
        for (int i = 1; i < productInfoBySku.size(); i++) {
            for (int j = 0; j < shoppingCartDTOS.size(); j++) {
                if (productInfoBySku.get(i).getProductInfo().getShopName().equals(
                        shoppingCartDTOS.get(j).getShopName())){
                    shoppingCartDTOS.get(j).getProductInfoDTOList().add(productInfoBySku.get(i));
                }else {
                    ShoppingCartDTO shoppingCartDTO1 =new ShoppingCartDTO();
                    shoppingCartDTO1.setShopName(productInfoBySku.get(i).getProductInfo().getShopName());
                    List<ProductInfoDTO> productInfoDTOS1 = new ArrayList<>();
                    productInfoDTOS1.add(productInfoBySku.get(i));
                    shoppingCartDTO1.setProductInfoDTOList(productInfoDTOS1);
                    shoppingCartDTOS.add(shoppingCartDTO1);
                }
                j = shoppingCartDTOS.size();
            }
        }

        for (ShoppingCartDTO cartDTO : shoppingCartDTOS) {
            List<ProductInfoDTO> productInfoDTOList = cartDTO.getProductInfoDTOList();
            for (ProductInfoDTO productInfoDTO : productInfoDTOList) {
                ProductSkuPriceModel productSkuPrice = productInfoDTO.getProductSkuPrice();
                BigDecimal price = productSkuPrice.getPrice();
                //如果有shareId就加价
                Long shareId = productInfoDTO.getShareId();
                if(ObjectUtil.isNotNull(shareId)){
                    ProductShareModel productShare = productShareService.getProductShare(shareId);
                    BigDecimal addPrice = productShare.getAddPrice();
                    price = price.add(addPrice);
                    productSkuPrice.setPrice(price);
                    productInfoDTO.setProductSkuPrice(productSkuPrice);
                }
                productInfoDTO.setProductSkuPrice(productSkuPrice);
            }
            cartDTO.setProductInfoDTOList(productInfoDTOList);
        }
        pageResult.setList(shoppingCartDTOS);
        return RespData.ok(pageResult);
    }

    @GetMapping("/getById")
    public RespData getById(Long id) {
        return RespData.ok(merchantShoppingCartServer.getById(id));
    }

    @PostMapping("/updateById")
    public RespData updateById(@RequestBody MerchantShoppingCartModel merchantShoppingCartModel) {
        merchantShoppingCartServer.updateById(merchantShoppingCartModel);
        return RespData.ok();
    }

    @PostMapping("/save")
    public RespData save(@RequestBody MerchantShoppingCartModel merchantShoppingCartModel) {
        merchantShoppingCartModel.setMerchantId(getMerchantInfoId());
        merchantShoppingCartServer.save(merchantShoppingCartModel);
        return RespData.ok();
    }

    @PostMapping("/deleteById/{id}")
    public RespData deleteById(@PathVariable Long id) {
        merchantShoppingCartServer.deleteById(id);
        return RespData.ok();
    }
}
