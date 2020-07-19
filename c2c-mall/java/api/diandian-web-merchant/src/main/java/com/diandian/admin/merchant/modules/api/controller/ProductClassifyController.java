package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.product.ProductClassifyModel;
import com.diandian.dubbo.facade.service.product.ProductClassifyService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/productClassify")
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


}
