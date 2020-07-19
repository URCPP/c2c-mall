package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeStrategyModel;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeStrategyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 软件类型
 */
@RestController
@RequestMapping("/biz")
public class BizSoftwareStrategyController extends BaseController {

    @Autowired
    BizSoftwareTypeStrategyService bizSoftwareTypeStrategyService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/softwareTypeStrategy/listPage")
    @RequiresPermissions("biz:softwareStrategy:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizSoftwareTypeStrategyService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取对象
     * @param id
     * @return
     */
    @GetMapping("/softwareTypeStrategy/{id}")
    @RequiresPermissions("biz:softwareStrategy:list")
    public RespData getById(@PathVariable Long id) {

        return RespData.ok(bizSoftwareTypeStrategyService.getById(id));
    }


    /**
     * 更新
     * @param bizSoftwareTypeStrategyModel
     * @return
     */
    @PostMapping("/softwareTypeStrategy/update")
    @RequiresPermissions("biz:softwareStrategy:update")
    public RespData updateById(@RequestBody BizSoftwareTypeStrategyModel bizSoftwareTypeStrategyModel) {
        bizSoftwareTypeStrategyService.updateById(bizSoftwareTypeStrategyModel);
        return RespData.ok();
    }

    /**
     * 新增
     * @param bizSoftwareTypeStrategyModel
     * @return
     */
    @PostMapping("/softwareTypeStrategy/add")
    @RequiresPermissions("biz:softwareStrategy:add")
    public RespData save(@RequestBody BizSoftwareTypeStrategyModel bizSoftwareTypeStrategyModel) {
        bizSoftwareTypeStrategyService.save(bizSoftwareTypeStrategyModel);
        return RespData.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/softwareTypeStrategy/deleteById/{id}")
    @RequiresPermissions("biz:softwareStrategy:delete")
    public RespData deleteById(@PathVariable  Long id) {
        bizSoftwareTypeStrategyService.removeById(id);
        return RespData.ok();
    }


}
