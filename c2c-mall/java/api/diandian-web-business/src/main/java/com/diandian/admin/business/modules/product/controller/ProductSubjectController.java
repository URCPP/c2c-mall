package com.diandian.admin.business.modules.product.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.product.ProductSubjectModel;
import com.diandian.dubbo.facade.service.product.ProductSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author x
 * @date 2019-02-15
 */
@RestController
@RequestMapping("/productSubject")
public class ProductSubjectController {

    @Autowired
    private ProductSubjectService productSubjectService;


    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(productSubjectService.listPage(params));
    }

    @GetMapping("/listAll")
    public RespData listAll(){
        return RespData.ok(productSubjectService.listAll());
    }

    @GetMapping("/getById")
    public RespData getById(Long id) {
        return RespData.ok(productSubjectService.getById(id));
    }

    @PostMapping("/updateById")
    public RespData updateById(@RequestBody ProductSubjectModel productSubjectModel) {
        productSubjectService.updateById(productSubjectModel);
        return RespData.ok();
    }

    @PostMapping("/save")
    public RespData save(@RequestBody ProductSubjectModel productSubjectModel) {
        productSubjectService.save(productSubjectModel);
        return RespData.ok();
    }

    @GetMapping("/deleteById")
    public RespData deleteById(Long id) {
        productSubjectService.logicDeleteById(id);
        return RespData.ok();
    }

}
