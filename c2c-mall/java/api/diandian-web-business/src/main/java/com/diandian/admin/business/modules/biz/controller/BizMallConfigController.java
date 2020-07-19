package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/biz/mallConfig")
public class BizMallConfigController extends BaseController {

    @Autowired
    BizMallConfigService bizMallConfigService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:mallConfig:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizMallConfigService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 根据id获取对象
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:mallConfig:list")
    public RespData getById(@PathVariable Long id){
        return RespData.ok(bizMallConfigService.getById(id));
    }

    /**
     * 新增
     * bizCatalogModel
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("biz:mallConfig:add")
    public RespData save(@RequestBody BizMallConfigModel bizMallConfigModel){
        bizMallConfigService.save(bizMallConfigModel);
        return RespData.ok();
    }

    /**
     * 根据id删除
     * @return
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("biz:mallConfig:delete")
    public RespData delete(@PathVariable Long id){
        bizMallConfigService.deleteById(id);
        return RespData.ok();
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("biz:mallConfig:update")
    public RespData update(@RequestBody BizMallConfigModel bizMallConfigModel){
        bizMallConfigModel.setUpdateTime(new Date());
        bizMallConfigService.updateById(bizMallConfigModel);
        return RespData.ok();
    }

}
