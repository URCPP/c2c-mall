package com.diandian.admin.business.modules.res.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.res.ResGroupModel;
import com.diandian.dubbo.facade.service.res.ResGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author x
 * @date 2019-02-15
 */
@RestController
@RequestMapping("/resGroup")
public class ResGroupController {

    @Autowired
    private ResGroupService resGroupService;

    @RequestMapping("/list")
    public RespData list(@RequestParam Map<String, Object> params) {
        return RespData.ok(resGroupService.list(params));
    }

    @GetMapping("/getById")
    public RespData getById(Long id) {
        return RespData.ok(resGroupService.getById(id));
    }

    @PostMapping("/updateById")
    public RespData updateById(@RequestBody ResGroupModel resGroupModel) {
        resGroupService.updateById(resGroupModel);
        return RespData.ok();
    }

    @PostMapping("/save")
    public RespData save(@RequestBody ResGroupModel resGroupModel) {
        resGroupService.save(resGroupModel);
        return RespData.ok();
    }

    @PostMapping("/deleteById/{id}")
    public RespData deleteById(@PathVariable Long id) {
        resGroupService.deleteById(id);
        return RespData.ok();
    }

}
