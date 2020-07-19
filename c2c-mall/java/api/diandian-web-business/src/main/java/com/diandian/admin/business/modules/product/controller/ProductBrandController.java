package com.diandian.admin.business.modules.product.controller;


import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.product.ProductBrandModel;
import com.diandian.dubbo.facade.service.product.ProductBrandService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-07
 */
@RestController
@RequestMapping("/productBrand")
public class ProductBrandController extends BaseController {

    @Autowired
    private ProductBrandService productBrandService;

    @GetMapping("/getById/{id}")
    @RequiresPermissions("productBrand:list")
    public RespData getById(@PathVariable Long id) {
        return RespData.ok(productBrandService.getById(id));
    }

    @GetMapping("/listPage")
    @RequiresPermissions("productBrand:list")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(productBrandService.listPage(params));
    }

    @GetMapping("/list")
    //@RequiresPermissions("productBrand:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        return RespData.ok(productBrandService.list(params));
    }

    @PostMapping("/add")
    @RequiresPermissions("productBrand:add")
    public RespData save(@RequestBody ProductBrandModel productBrand) {
        productBrandService.save(productBrand);
        return RespData.ok();
    }

    @PostMapping("/update")
    @RequiresPermissions("productBrand:update")
    public RespData update(@RequestBody ProductBrandModel productBrand) {
        productBrandService.updateById(productBrand);
        return RespData.ok();
    }

    @GetMapping("/delete/{id}")
    @RequiresPermissions("productBrand:delete")
    public RespData delete(@PathVariable Long id) {
        productBrandService.deleteById(id);
        return RespData.ok();
    }
}
