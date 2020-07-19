package com.diandian.admin.merchant.modules.material.controller;

import cn.hutool.core.util.ObjectUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductShareService;
import com.diandian.dubbo.facade.service.product.ProductSkuPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @Auther chensong
 * @Date 2019/9/3
 */
@RestController
@Slf4j
@RequestMapping("/share")
public class ProductShareController extends BaseController {

    @Autowired
    private ProductShareService productShareService;

    /**
     * 商品加价
     */
    @GetMapping("/addPrice")
    public RespData addPrice(@RequestParam Map<String, Object> params){
        return RespData.ok(productShareService.addPrice(params));
    }

    /**
     * 绑定用户并查询商品详情
     */
    @GetMapping("/bind/{shareId}/{userId}")
    public RespData bind(@PathVariable Long shareId,@PathVariable Long userId){
        productShareService.bind(shareId, userId);
        return RespData.ok();
    }

}
