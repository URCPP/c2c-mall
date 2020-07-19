package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizNotifyInfoModel;
import com.diandian.dubbo.facade.service.biz.BizNotifyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/2/26 21:51
 */
@RestController
@RequestMapping("/biz/bizNotifyInfo")
@Slf4j
public class BizNotifyInfoController {
    @Autowired
    private BizNotifyInfoService notifyInfoService;

    @RequestMapping("/list")
    public RespData listByQuery() {
        List<BizNotifyInfoModel> list = notifyInfoService.listNotifyByQuery();
        return RespData.ok(list);
    }

    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        params.put("stateFlag", "0");
        params.put("typeFlag", "0");
        PageResult pageResult = notifyInfoService.listPage(params);
        return RespData.ok(pageResult);
    }

    /**
     * 前后数据列表
     * @author: jbuhuan
     * @date: 2019/3/8 14:55
     * @param id
     * @return: R
     */
    @GetMapping("/notify/{id}")
    public RespData listAround(@PathVariable Long id) {
        List<BizNotifyInfoModel> list = notifyInfoService.listAround(id);
        return RespData.ok(list);
    }
}
