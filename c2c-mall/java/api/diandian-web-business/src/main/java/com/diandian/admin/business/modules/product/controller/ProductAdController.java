package com.diandian.admin.business.modules.product.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.product.ProductAdModel;
import com.diandian.dubbo.facade.model.res.ResGroupModel;
import com.diandian.dubbo.facade.service.product.ProductAdService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productAd")
public class ProductAdController extends BaseController {
    @Autowired
    ProductAdService productAdService;

    @GetMapping("/list")
    @RequiresPermissions("productAd:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        return RespData.ok(productAdService.list(params));
    }

    @PostMapping("/update")
    @RequiresPermissions("productAd:update")
    public RespData updateById(@RequestBody List<ProductAdModel> productAdList) {
        productAdService.update(productAdList);
        return RespData.ok();
    }

    @PostMapping("/save")
    @RequiresPermissions("productAd:save")
    public RespData save(@RequestBody List<ProductAdModel> productAdList) {
        productAdService.save(productAdList);
        return RespData.ok();
    }
    @PostMapping("/deleteById/{id}")
    @RequiresPermissions("productAd:deleteById")
    public RespData deleteById(@PathVariable Long id) {
        productAdService.deleteById(id);
        return RespData.ok();
    }


    @GetMapping("/listAd")
    @RequiresPermissions("productAd:listAd")
    public RespData listAd(String type){
        return RespData.ok(productAdService.listAd(type));
    }

    @PostMapping("/add")
    @RequiresPermissions("productAd:add")
    public RespData add(@RequestBody ProductAdModel productAdModel){
        String createBy=getUser().getUsername();
        productAdService.add(productAdModel,createBy);
        return  RespData.ok();
    }

    @PostMapping("/amend")
    @RequiresPermissions("productAd:amend")
    public RespData amend(@RequestBody ProductAdModel productAdModel){
        String createBy=getUser().getUsername();
        productAdService.amend(productAdModel,createBy);
        return RespData.ok();
    }

    @PostMapping("/show")
    @RequiresPermissions("productAd:show")
    public RespData show(@RequestBody ProductAdModel productAdModel){
        productAdService.show(productAdModel);
        return RespData.ok();
    }

}
