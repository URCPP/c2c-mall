package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.biz.BizNotifyInfoModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.service.biz.BizNotifyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listPage")
    public RespData listPage(@RequestParam Map<String,Object> params){
        PageResult pageResult = notifyInfoService.listPage(params);
        return RespData.ok(pageResult);
    }

    @PostMapping("/save")
    public RespData saveNotifyInfo(@RequestBody BizNotifyInfoModel notifyInfoModel){
        notifyInfoService.saveNotify(notifyInfoModel);
        return RespData.ok();
    }

    @PostMapping("/update")
    public RespData updateNotifyInfo(@RequestBody BizNotifyInfoModel notifyInfoModel){
        notifyInfoService.updateNotify(notifyInfoModel);
        return RespData.ok();
    }
    @GetMapping("/delete/{id}")
    public RespData saveNotifyInfo(@PathVariable Long id){
        notifyInfoService.deleteNotify(id);
        return RespData.ok();
    }
}
