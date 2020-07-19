package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallHelpModel;
import com.diandian.dubbo.facade.service.biz.BizMallHelpService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/biz/help")
public class BizMallHelpController extends BaseController {

    @Autowired
    BizMallHelpService bizMallHelpService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:help:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizMallHelpService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 根据id获取对象
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:help:list")
    public RespData getById(@PathVariable Long id){
        return RespData.ok(bizMallHelpService.getById(id));
    }


    /**
     * 新增
     * bizCatalogModel
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("biz:help:add")
    public RespData save(@RequestBody BizMallHelpModel bizMallHelpModel){
        bizMallHelpService.save(bizMallHelpModel);
        return RespData.ok();
    }

    /**
     * 根据id删除
     * @return
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("biz:help:delete")
    public RespData delete(@PathVariable Long id){
        bizMallHelpService.deleteById(id);
        return RespData.ok();
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("biz:help:update")
    public RespData update(@RequestBody BizMallHelpModel bizMallHelpModel){
        bizMallHelpModel.setUpdateTime(new Date());
        bizMallHelpService.updateById(bizMallHelpModel);
        return RespData.ok();
    }

    @GetMapping("/listCatalog")
    @RequiresPermissions("biz:help:list")
    public RespData select() {
        List<BizMallHelpModel> list = bizMallHelpService.listCataLog();
        return RespData.ok(list);
    }

}
