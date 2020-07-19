package com.diandian.admin.business.modules.product.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.product.ProductAttrNameModel;
import com.diandian.dubbo.facade.service.product.ProductAttrNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author x
 * @date 2019-02-15
 */
@RestController
@RequestMapping("/productAttrName")
public class ProductAttrNameController {

    @Autowired
    private ProductAttrNameService productAttrNameService;


    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(productAttrNameService.listPage(params));
    }

    @GetMapping("/getById")
    public RespData getById(Long id) {
        return RespData.ok(productAttrNameService.getById(id));
    }

    @PostMapping("/updateById")
    public RespData updateById(@RequestBody ProductAttrNameModel productAttrNameModel) {
        productAttrNameService.updateById(productAttrNameModel);
        return RespData.ok();
    }

    @PostMapping("/save")
    public RespData save(@RequestBody ProductAttrNameModel productAttrNameModel) {
        productAttrNameService.save(productAttrNameModel);
        return RespData.ok();
    }

    @GetMapping("/deleteById")
    public RespData deleteById(Long id) {
        productAttrNameService.logicDeleteById(id);
        return RespData.ok();
    }

}
