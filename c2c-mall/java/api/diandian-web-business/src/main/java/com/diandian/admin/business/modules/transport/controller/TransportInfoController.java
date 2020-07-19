package com.diandian.admin.business.modules.transport.controller;


import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.transport.TransportInfoModel;
import com.diandian.dubbo.facade.service.transport.TransportInfoService;
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
@RequestMapping("/transport")
public class TransportInfoController {
    @Autowired
    private TransportInfoService transportInfoService;

    @RequestMapping("/listPage")
    @RequiresPermissions("transportInfo:list")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(transportInfoService.listPage(params));
    }

    @GetMapping("/getById/{id}")
    @RequiresPermissions("transportInfo:getById")
    public RespData getById(@PathVariable Long id) {
        return RespData.ok(transportInfoService.getById(id));
    }

    @PostMapping("/updateById")
    @RequiresPermissions("transportInfo:updateById")
    public RespData updateById(@RequestBody TransportInfoModel transportInfoModel) {
        transportInfoService.updateTransportInfo(transportInfoModel);
        return RespData.ok();
    }

    @PostMapping("/save")
    @RequiresPermissions("transportInfo:save")
    public RespData save(@RequestBody TransportInfoModel transportInfoModel) {
        transportInfoService.saveTransportInfo(transportInfoModel);
        return RespData.ok();
    }

    @PostMapping("/deleteById/{id}")
    @RequiresPermissions("transportInfo:deleteById")
    public RespData deleteById(@PathVariable Long id) {
        transportInfoService.deleteTransportInfo(id);
        return RespData.ok();
    }
}
