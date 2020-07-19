package com.diandian.admin.merchant.modules.api.controller;


import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.service.transport.TransportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 * 1运输方式信息 前端控制器
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/api/transport")
public class TransportInfoController {
    @Autowired
    private TransportInfoService transportInfoService;

    @RequestMapping("/listDetail")
    public RespData listDetail() {
        return RespData.ok(transportInfoService.listDetail(new ArrayList()));
    }

    @RequestMapping("/checkRuleIncludeRegion")
    public RespData checkRuleIncludeRegion(Long ruleId, String regionCode) {
        Integer cnt = transportInfoService.checkRuleIncludeRegionCode(ruleId, regionCode);
        return RespData.ok(cnt);
    }

}
