package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallSuccessCaseModel;
import com.diandian.dubbo.facade.service.biz.BizMallSuccessCaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/biz/successCase")
public class BizMallSuccessCaseController extends BaseController {

    @Autowired
    BizMallSuccessCaseService bizMallSuccessCaseService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:successCase:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizMallSuccessCaseService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 根据id获取对象
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:successCase:list")
    public RespData getById(@PathVariable Long id){
        return RespData.ok(bizMallSuccessCaseService.getById(id));
    }


    /**
     * 新增
     * bizCatalogModel
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("biz:successCase:add")
    public RespData save(@RequestBody BizMallSuccessCaseModel bizMallSuccessCaseModel){
        bizMallSuccessCaseService.save(bizMallSuccessCaseModel);
        return RespData.ok();
    }

    /**
     * 根据id删除
     * @return
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("biz:successCase:delete")
    public RespData delete(@PathVariable Long id){
        bizMallSuccessCaseService.deleteById(id);
        return RespData.ok();
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("biz:successCase:update")
    public RespData update(@RequestBody BizMallSuccessCaseModel bizMallSuccessCaseModel){
        bizMallSuccessCaseModel.setUpdateTime(new Date());
        bizMallSuccessCaseService.updateById(bizMallSuccessCaseModel);
        return RespData.ok();
    }

    @GetMapping("/listCatalog")
    @RequiresPermissions("biz:successCase:list")
    public RespData select() {
        List<BizMallSuccessCaseModel> list = bizMallSuccessCaseService.listCataLog();
        return RespData.ok(list);
    }

}
