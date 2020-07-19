package com.diandian.admin.business.modules.sys.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sys/userConfiguration")
public class UserConfigurationController {
    @Autowired
    private UserConfigurationService userConfigurationService;

    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public RespData userConfigurationList(){
        UserConfiguration userConfiguration=userConfigurationService.findAll();
        return RespData.ok(userConfiguration);
    }

//    /**
//     * 新增
//     * bizCatalogModel
//     * @return
//     */
//    @PostMapping("/add")
//    @RequiresPermissions("sys:user:add")
//    public RespData save(@RequestBody UserConfiguration userConfiguration){
//        userConfigurationService.insert(userConfiguration);
//        return RespData.ok();
//    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public RespData update(@RequestBody UserConfiguration userConfiguration){
        userConfigurationService.updateByPrimaryKey(userConfiguration);
        return RespData.ok();
    }
}
