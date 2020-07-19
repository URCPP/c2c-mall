package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.product.ProductCategoryModel;
import com.diandian.dubbo.facade.service.product.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productCategory")
public class ProductCategoryController extends BaseController {
    @Autowired
    ProductCategoryService productCategoryService;

    /**
     * 产品类型
     * @return
     */
    @GetMapping("/list")
    public RespData list(){
        List<ProductCategoryModel> list = productCategoryService.list();
        return RespData.ok(list);
    }

    /**
     * 商品一级类别包含子类别的idList 存redis
     * @return
     */
    @GetMapping("/listProductCategory")
    public RespData listMerProductCategory() {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        List<ProductCategoryModel> category = productCategoryService.listProductCategory();
        return RespData.ok(category);
    }
}
