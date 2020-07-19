package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.product.ProductAdModel;
import com.diandian.dubbo.facade.service.product.ProductAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productAd")
public class ProductAdController {
    @Autowired
    ProductAdService productAdService;

    @GetMapping("/list")
    public RespData list(@RequestParam Map<String, Object> params) {
        return RespData.ok(productAdService.list(params));
    }

    @GetMapping("listAd")
    public RespData listAd(String type){
        return RespData.ok(productAdService.listAll(type));
    }

}
