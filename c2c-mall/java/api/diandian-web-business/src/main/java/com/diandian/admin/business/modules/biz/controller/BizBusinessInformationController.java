package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizBusinessInformationModel;
import com.diandian.dubbo.facade.service.biz.BizBusinessInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-08 10:20
 */
@RestController
@RequestMapping("/biz/businessInformation")
public class BizBusinessInformationController {

    @Autowired
    private BizBusinessInformationService bizBusinessInformationService;

    @GetMapping("/listPage")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page=bizBusinessInformationService.listPage(params);
        return RespData.ok(page);
    }

    @GetMapping("getByShopId/{shopId}")
    public RespData getByShopId(@PathVariable Long shopId){
        BizBusinessInformationModel bizBusinessInformationModel=bizBusinessInformationService.getByShopId(shopId);
        bizBusinessInformationModel.setPlatformProfit(bizBusinessInformationModel.getShop().getPlatformProfit().add(bizBusinessInformationModel.getShop().getAgentProfit()).add(bizBusinessInformationModel.getShop().getFloorPriceProportion()));
        return RespData.ok(bizBusinessInformationModel);
    }

}