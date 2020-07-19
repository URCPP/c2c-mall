package com.diandian.admin.business.modules.sys.controller;


import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.service.sys.SysPlaceRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 全国区县 前端控制器
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/sys/sysPlaceRegion")
public class SysPlaceRegionController {
    @Autowired
    SysPlaceRegionService sysPlaceRegionService;

    @GetMapping("/list")
    public RespData listArea(){
        return RespData.ok(sysPlaceRegionService.list());
    }

}
