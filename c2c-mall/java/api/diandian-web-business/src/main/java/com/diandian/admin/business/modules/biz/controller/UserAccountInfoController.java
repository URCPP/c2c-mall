package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.service.user.UserAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-11 15:00
 */
@RestController
@RequestMapping("/userAccountInfo")
public class UserAccountInfoController extends BaseController {

    @Autowired
    private UserAccountInfoService userAccountInfoService;

    @GetMapping("getByShopId/{shopId}")
    public RespData getByShopId(@PathVariable Long shopId){
        return RespData.ok(userAccountInfoService.getByShopId(shopId));
    }

    @PostMapping("/getIndexData")
    public RespData getIndexData( @RequestParam Map<String, Object> params){
        Map<String,Object> map=new HashMap<>();
        Long userId=getUserId();
        params.put("userId",userId);
        return  RespData.ok(map);


    }
}