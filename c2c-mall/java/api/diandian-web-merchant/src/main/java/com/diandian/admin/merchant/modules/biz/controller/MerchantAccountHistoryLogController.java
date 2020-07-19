package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountHistoryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author jbuhuan
 * @Date 2019/3/27 14:08
 */
@RestController
@RequestMapping("/biz/merchantAccountHistoryLog")
@Slf4j
public class MerchantAccountHistoryLogController extends BaseController {
    @Autowired
    private MerchantAccountHistoryLogService merchantAccountHistoryLogService;

    @GetMapping("listPage")
    public RespData listPage(@RequestParam Map<String, Object> params){
        params.put("merchantId", getMerchantInfoId());
        PageResult pageResult = merchantAccountHistoryLogService.listPage(params);
        return RespData.ok(pageResult);
    }

    @GetMapping("getById/{id}")
    public RespData getById(@PathVariable Long id){
        return RespData.ok(merchantAccountHistoryLogService.getById(id));
    }
}
