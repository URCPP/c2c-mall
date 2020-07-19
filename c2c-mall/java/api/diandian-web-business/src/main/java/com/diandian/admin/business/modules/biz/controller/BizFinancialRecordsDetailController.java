package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.service.biz.BizFinancialRecordsDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-03 17:16
 */
@RestController
@RequestMapping("/biz/financialRecordsDetail")
public class BizFinancialRecordsDetailController extends BaseController {

    @Autowired
    private BizFinancialRecordsDetailService bizFinancialRecordsDetailService;

    @RequestMapping("/listPage")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizFinancialRecordsDetailService.listPage(params);
        return RespData.ok(page);
    }

}