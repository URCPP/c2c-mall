package com.diandian.admin.business.modules.product.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.product.ProductCategoryModel;
import com.diandian.dubbo.facade.service.product.ProductCategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author x
 * @date 2019-02-15
 */
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;


    @RequestMapping("/list")
    //@RequiresPermissions("productCategory:list")
    public RespData list() {
        List<ProductCategoryModel> list = productCategoryService.list();
        Map<String, Object> result = new HashMap<>(2);
        result.put("list", list);
        ProductCategoryModel rootCategory = new ProductCategoryModel();
        rootCategory.setId(0L);
        rootCategory.setCategoryName("顶级类目");
        rootCategory.setParentId(-1L);
        rootCategory.setOpen(true);
        List<ProductCategoryModel> formList = new ArrayList<>();
        formList.addAll(list);
        formList.add(rootCategory);
        result.put("form", formList);
        return RespData.ok(result);
    }

    @GetMapping("/getById")
    @RequiresPermissions("productCategory:list")
    public RespData getById(Long id) {
        return RespData.ok(productCategoryService.getById(id));
    }

    @PostMapping("/updateById")
    @RequiresPermissions("productCategory:update")
    public RespData updateById(@RequestBody ProductCategoryModel productCategoryModel) {
        if (productCategoryModel.getProductCommission()!=null){
            productCategoryModel.setProductCommission(productCategoryModel.getProductCommission().divide(BigDecimal.valueOf(100)));
        }
        productCategoryService.updateById(productCategoryModel);
        return RespData.ok();
    }


    @PostMapping("/save")
    @RequiresPermissions("productCategory:add")
    public RespData save(@RequestBody ProductCategoryModel productCategoryModel) {
        if(0!=productCategoryModel.getParentId()){
            if (productCategoryModel.getProductCommission()==null){
                productCategoryModel.setProductCommission(productCategoryService.getById(productCategoryModel.getParentId()).getProductCommission());
            }else {
                productCategoryModel.setProductCommission(productCategoryModel.getProductCommission().divide(BigDecimal.valueOf(100)));
            }
        }else{
            if (productCategoryModel.getProductCommission()==null){
                return RespData.fail("顶级类目必须配置商品抽点");
            }
        }

        productCategoryService.save(productCategoryModel);
        return RespData.ok();
    }

    @GetMapping("/deleteById")
    @RequiresPermissions("productCategory:delete")
    public RespData deleteById(Long id) {
        productCategoryService.logicDeleteById(id);
        return RespData.ok();
    }

}
