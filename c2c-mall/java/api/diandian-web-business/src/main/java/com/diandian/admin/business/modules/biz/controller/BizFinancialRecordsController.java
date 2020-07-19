package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.service.biz.BizFinancialRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-10 15:16
 */
@RestController
@RequestMapping("/biz/financialRecords")
public class BizFinancialRecordsController {

    @Autowired
    private BizFinancialRecordsService bizFinancialRecordsService;

    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        PageResult page = bizFinancialRecordsService.listPage(params);
        return RespData.ok(page);
    }

    @RequestMapping("/listPageByMonth")
    public RespData listPageByMonth(@RequestParam Map<String, Object> params) {
        PageResult page = bizFinancialRecordsService.listPageByMonth(params);
        return RespData.ok(page);
    }
}