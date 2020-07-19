package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cjunyuan
 * @date 2019/4/26 19:13
 */
@RestController
@RequestMapping("/biz/mallConfig")
public class BizMallConfigController extends BaseController {

    @Autowired
    BizMallConfigService bizMallConfigService;

    /**
     * 根据id获取对象
     * @return
     */
    @GetMapping("/getByKey")
    public RespData getByKey(@RequestParam String key){
        return RespData.ok(bizMallConfigService.getByEngName(key));
    }

}