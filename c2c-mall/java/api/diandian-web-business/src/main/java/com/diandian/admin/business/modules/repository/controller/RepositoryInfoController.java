package com.diandian.admin.business.modules.repository.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.repository.RepositoryInfoModel;
import com.diandian.dubbo.facade.service.repository.RepositoryInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author x
 * @date 2019-02-22
 */
@RestController
@RequestMapping("/repositoryInfo")
public class RepositoryInfoController {

    @Autowired
    private RepositoryInfoService repositoryInfoService;

    @GetMapping("/list/{shopId}")
    @RequiresPermissions("repositoryInfo:list")
    public RespData list(@PathVariable Long shopId) {
        return RespData.ok(repositoryInfoService.list(shopId));
    }

    @RequestMapping("/listPage")
    @RequiresPermissions("repositoryInfo:list")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(repositoryInfoService.listPage(params));
    }

    @GetMapping("/getById")
    @RequiresPermissions("repositoryInfo:list")
    public RespData getById(Long id) {
        return RespData.ok(repositoryInfoService.getById(id));
    }

    @PostMapping("/updateById")
    @RequiresPermissions("repositoryInfo:update")
    public RespData updateById(@RequestBody RepositoryInfoModel repositoryInfoModel) {
        repositoryInfoService.updateById(repositoryInfoModel);
        return RespData.ok();
    }

    @PostMapping("/save")
    @RequiresPermissions("repositoryInfo:add")
    public RespData save(@RequestBody RepositoryInfoModel repositoryInfoModel) {
        repositoryInfoService.save(repositoryInfoModel);
        return RespData.ok();
    }

    @GetMapping("/deleteById")
    @RequiresPermissions("repositoryInfo:delete")
    public RespData deleteById(Long id) {
        repositoryInfoService.logicDeleteById(id);
        return RespData.ok();
    }
}
