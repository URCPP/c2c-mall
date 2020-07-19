package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.model.biz.BizConfigModel;
import com.diandian.dubbo.facade.service.biz.BizConfigService;
import com.diandian.dubbo.facade.vo.BizConfigVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jbuhuan
 * @Date 2019/3/14 14:06
 */
@RestController
@RequestMapping("/biz/bizConfig")
public class BizConfigController {
    @Autowired
    private BizConfigService bizConfigService;

    @RequestMapping("/config")
    public RespData getBizConfig(){
        BizConfigModel model = bizConfigService.getOne();
        return RespData.ok(model);
    }

    @PostMapping("/save")
    @RequiresPermissions("biz:bizConfig:save")
    public RespData saveBizConfig(@RequestBody BizConfigVO vo){
        bizConfigService.save(vo);
        return RespData.ok();
    }
}
