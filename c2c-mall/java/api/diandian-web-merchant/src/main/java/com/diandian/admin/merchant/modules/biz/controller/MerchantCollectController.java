package com.diandian.admin.merchant.modules.biz.controller;


import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.material.MaterialDetailModel;
import com.diandian.dubbo.facade.service.merchant.MerchantCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collect")
@Slf4j
public class MerchantCollectController extends BaseController {

    @Autowired
    private MerchantCollectService merchantCollectService;
    @GetMapping("/listCollect")
    public RespData listCollect(Long productId){
        Map map=new HashMap();
        Long merchantId=getMerchantInfoId();
        List<MaterialDetailModel> list=merchantCollectService.listCollect(merchantId,productId);
        map.put("list",list);
        map.put("collect",list.size());
        return  RespData.ok(map);
    }

}
