package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallNewsModel;
import com.diandian.dubbo.facade.service.biz.BizMallNewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/biz/mallNews")
public class BizMallNewsController extends BaseController {

    @Autowired
    BizMallNewsService bizMallNewsService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:mallNews:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizMallNewsService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 根据id获取对象
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:mallNews:list")
    public RespData getById(@PathVariable Long id){
        return RespData.ok(bizMallNewsService.getById(id));
    }

    /**
     * 新增
     * bizCatalogModel
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("biz:mallNews:add")
    public RespData save(@RequestBody BizMallNewsModel bizMallNewsModel){
        bizMallNewsService.save(bizMallNewsModel);
        return RespData.ok();
    }

    /**
     * 根据id删除
     * @return
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("biz:mallNews:delete")
    public RespData delete(@PathVariable Long id){
        bizMallNewsService.deleteById(id);
        return RespData.ok();
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("biz:mallNews:update")
    public RespData update(@RequestBody BizMallNewsModel bizMallNewsModel){
        bizMallNewsModel.setUpdateTime(new Date());
        bizMallNewsService.updateById(bizMallNewsModel);
        return RespData.ok();
    }
}
