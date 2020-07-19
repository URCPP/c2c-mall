package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.service.biz.BizNotifyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notify")
public class BizNotifyController {
    @Autowired
    BizNotifyInfoService bizNotifyInfoService;
    @GetMapping("/list")
    public RespData list(@RequestParam Map<String,Object> params){
        PageResult pageResult = bizNotifyInfoService.listPage(params);
        return RespData.ok(pageResult);
    }
}
