package com.diandian.admin.merchant.modules.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 软件类型管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/softType")
@Slf4j
public class SoftTypeController extends BaseController {

    @Autowired
    private BizSoftwareTypeService bizSoftwareTypeService;

    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/list")
    public RespData list() {
        List<BizSoftwareTypeModel> list = bizSoftwareTypeService.listSoftType();
        return RespData.ok(list);
    }

    @GetMapping("/{id}")
    public RespData getById(@PathVariable Long id) {
        return RespData.ok(bizSoftwareTypeService.getSoftTypeById(id));
    }


}
