package com.diandian.admin.business.modules.product.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.product.ProductClassifyModel;
import com.diandian.dubbo.facade.service.product.ProductClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 16:42 2019/11/1
 * @Modified By:
 */
@RestController
@Slf4j
@RequestMapping("/productClassify")
public class ProductClassifyController extends BaseController {

    @Autowired
    private ProductClassifyService productClassifyService;

    @GetMapping("list")
    public RespData list(){
        List<ProductClassifyModel> list=productClassifyService.list();
        Map<String,Object> map=new HashMap<>(2);
        map.put("list",list);
        ProductClassifyModel pc=new ProductClassifyModel();
        pc.setId(0L);
        pc.setCategoryName("顶级类目");
        pc.setOpen(true);
        pc.setCategoryParent(-1L);
        List<ProductClassifyModel> formList=new ArrayList<>();
        formList.addAll(list);
        formList.add(pc);
        map.put("form",formList);
        return  RespData.ok(map);
    }

    @GetMapping("getById")
//    @RequiresPermissions("productClassify:list")
    public RespData getById(Long id){
        return  RespData.ok(productClassifyService.getById(id));
    }

    @PostMapping("updateById")
//    @RequiresPermissions("productClassify:update")
    public RespData updateById(@RequestBody ProductClassifyModel productClassifyModel){
        productClassifyService.updateById(productClassifyModel);
        return RespData.ok();
    }


    @PostMapping("save")
//    @RequiresPermissions("productClassify:save")
    public RespData save(@RequestBody ProductClassifyModel productClassifyModel){
        if(productClassifyModel.getCategoryType()!=null && productClassifyModel.getCategoryType()==0){
            productClassifyModel.setCategoryParent(0L);
        }
        productClassifyService.save(productClassifyModel);
        return RespData.ok();
    }

    @PostMapping("deleteById")
//    @RequiresPermissions("productClassify:delete")
    public RespData deleteById(Long id){
        productClassifyService.DeleteById(id);
        return RespData.ok();
    }
}
