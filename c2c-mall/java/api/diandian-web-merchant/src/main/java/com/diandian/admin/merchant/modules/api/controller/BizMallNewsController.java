package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.service.biz.BizMallNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class BizMallNewsController {
    @Autowired
    BizMallNewsService bizMallNewsService;
    @GetMapping("/list")
    public RespData list(@RequestParam Map<String,Object> params){
        params.put("is_show",BizConstant.STATE_DISNORMAL);
        PageResult pageResult = bizMallNewsService.listPage(params);
        return RespData.ok(pageResult);
    }

    @GetMapping("/getById/{id}")
    public RespData getById(@PathVariable Long id){
        return RespData.ok(bizMallNewsService.getById(id));
    }

}
