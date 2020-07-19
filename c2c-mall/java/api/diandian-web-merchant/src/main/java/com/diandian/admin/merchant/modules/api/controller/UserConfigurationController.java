package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/userConfiguration")
public class UserConfigurationController {
    @Autowired
    private UserConfigurationService userConfigurationService;

    @GetMapping("/list")
    public RespData userConfigurationList(){
        UserConfiguration userConfiguration=userConfigurationService.findAll();
        return RespData.ok(userConfiguration);
    }



    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    public RespData update(@RequestBody UserConfiguration userConfiguration){
        userConfigurationService.updateByPrimaryKey(userConfiguration);
        return RespData.ok();
    }
}
