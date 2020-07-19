package com.diandian.admin.merchant.modules.sys.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.sys.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/area")
public class SysAreaController extends BaseController {
    @Autowired
    SysAreaService sysAreaService;

    @GetMapping("/list")
    public RespData listArea(){
        return RespData.ok(sysAreaService.list());
    }
}
