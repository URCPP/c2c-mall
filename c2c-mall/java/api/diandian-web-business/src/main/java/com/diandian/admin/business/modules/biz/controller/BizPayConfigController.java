package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizPayConfigModel;
import com.diandian.dubbo.facade.service.biz.BizPayConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 支付配置
 */
@RestController
@RequestMapping("/biz/payConfig")
public class BizPayConfigController extends BaseController {

    @Autowired
    BizPayConfigService bizPayConfigService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:payConfig:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizPayConfigService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取对象
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:payConfig:list")
    public RespData getById(@PathVariable Long id) {
        return RespData.ok(bizPayConfigService.getById(id));
    }


    /**
     * 更新
     * @param bizPayConfigModel
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("biz:payConfig:update")
    public RespData updateById(@RequestBody BizPayConfigModel bizPayConfigModel) {
        bizPayConfigService.updateById(bizPayConfigModel);
        return RespData.ok();
    }

    /**
     * 新增
     * @param bizPayConfigModel
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("biz:payConfig:add")
    @Transactional(rollbackFor = Exception.class)
    public RespData save(@RequestBody BizPayConfigModel bizPayConfigModel) {
        bizPayConfigService.save(bizPayConfigModel);
        return RespData.ok();
    }

    @PostMapping("/updateStateByIdBatch")
    public RespData updateStateByIdBatch(@RequestParam("idList") List<Long> idList, Integer state) {
        bizPayConfigService.updateStateByIdBatch(idList, state);
        return RespData.ok();
    }

}
