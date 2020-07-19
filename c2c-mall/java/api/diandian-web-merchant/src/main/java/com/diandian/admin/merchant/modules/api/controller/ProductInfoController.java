package com.diandian.admin.merchant.modules.api.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.ObjectUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.common.oss.AliyunStorageFactory;
import com.diandian.dubbo.facade.common.constant.TransportTypeConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductSkuService;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.vo.TransportInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productInfo")
public class ProductInfoController extends BaseController {
    @Autowired
    ProductInfoService productInfoService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ShopInfoService shopInfoService;

    /**
     * 产品类型
     *
     * @return
     */
    @GetMapping("/listPage")
    public RespData list(@RequestParam Map<String, Object> params) {
        //状态 99=>下架,1=>上架,2=>预售,0=>未上架
        params.put("state", 1);
        PageResult pageResult = productInfoService.listPage(params);
        return RespData.ok(pageResult);
    }

    @GetMapping("/getById")
    public RespData getById(Long id,Long shareId) {
        if(ObjectUtil.isNotNull(shareId)){
            return getByShareId(id,shareId);
        }
        return RespData.ok(productInfoService.getById(id));
    }

    @GetMapping("/getTransportList")
    public RespData getTransportList(Long productId) {
        List<Integer> transportTypeIds = new ArrayList<>();
        transportTypeIds.add(TransportTypeConstant.EXPRESS.getValue());
        transportTypeIds.add(TransportTypeConstant.TAKE_THEIR.getValue());
        transportTypeIds.add(TransportTypeConstant.PINKAGE.getValue());
        List<TransportInfoVO> list = productInfoService.getProductTransportList(productId, transportTypeIds);
        return RespData.ok(list);
    }

    @GetMapping("/getSkuById")
    public RespData getSkuById(Long id) {
        return RespData.ok(productSkuService.getBySkuId(id));
    }

    @GetMapping("/getByShareId")
    public RespData getByShareId(Long id,Long shareId) {
        return RespData.ok(productInfoService.getByShareId(id,getMerchantInfo().getPhone(), shareId));
    }

    /*@GetMapping("/getSkuById")
    public RespData getSkuByIdAndUserId(Long id) {
        Long merchantId = getMerchantInfoId();
        return RespData.ok(productSkuService.getBySkuIdAndUserId(merchantId, id));
    }*/

    @GetMapping("getImage")
    public RespData getImageByUri(String uri, HttpServletResponse response) {
        InputStream inputStream = AliyunStorageFactory.build().getObject(uri);
        byte[] bytes = null;
        try {
            bytes = FileCopyUtils.copyToByteArray(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespData.ok(Base64Utils.encodeToString(bytes));
    }

    @GetMapping("getByShopId")
    public RespData getByShopId(@RequestParam Map<String, Object> params){
        Map map=new HashMap();
        List<ProductInfoModel> list=productInfoService.getByShopId(params);
        ShopInfoModel shopInfoModel=shopInfoService.getById(Long.valueOf(params.get("shopId").toString()));
        map.put("list",list);
        map.put("shop",shopInfoModel);
        return RespData.ok(map);
    }

}
