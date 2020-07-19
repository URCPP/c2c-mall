package com.diandian.admin.business.modules.transport.controller;


import com.diandian.admin.business.modules.transport.service.TransportCompanyService;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.transport.TransportCompanyModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 1运输方式信息 前端控制器
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/transportCompany")
public class TransportCompanyController {
    @Autowired
    private TransportCompanyService transportCompanyService;

    @RequestMapping("/listPage")
    @RequiresPermissions("transportCompany:list")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(transportCompanyService.listPage(params));
    }

    @GetMapping("/getById")
    @RequiresPermissions("transportCompany:list")
    public RespData getById(Long id) {
        return RespData.ok(transportCompanyService.getById(id));
    }

    @PostMapping("/updateById")
    @RequiresPermissions("transportCompany:update")
    public RespData updateById(@RequestBody TransportCompanyModel transportCompanyModel) {
        transportCompanyService.updateById(transportCompanyModel);
        return RespData.ok();
    }

    @PostMapping("/save")
    @RequiresPermissions("transportCompany:add")
    public RespData save(@RequestBody TransportCompanyModel transportCompanyModel) {
        transportCompanyService.save(transportCompanyModel);
        return RespData.ok();
    }

    @PostMapping("/deleteById/{id}")
    @RequiresPermissions("transportCompany:delete")
    public RespData deleteById(@PathVariable Long id) {
        transportCompanyService.logicDeleteById(id);
        return RespData.ok();
    }
}
