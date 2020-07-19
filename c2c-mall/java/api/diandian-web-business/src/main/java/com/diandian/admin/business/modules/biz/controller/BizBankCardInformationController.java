package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.model.biz.BizBankCardInformationModel;
import com.diandian.dubbo.facade.service.biz.BizBankCardInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-08 11:35
 */
@RestController
@RequestMapping("/biz/bankCardInformation")
public class BizBankCardInformationController {

    @Autowired
    private BizBankCardInformationService bizBankCardInformationService;

    @GetMapping("get/{shopId}")
    public RespData getByShopId(@PathVariable Long shopId){
        return RespData.ok(bizBankCardInformationService.getByShopId(shopId));
    }

    @PostMapping("add")
    public RespData add(@RequestBody BizBankCardInformationModel bizBankCardInformationModel){
        bizBankCardInformationService.insert(bizBankCardInformationModel);
        return RespData.ok();
    }

    @PostMapping("update")
    public RespData updateById(@RequestBody BizBankCardInformationModel bizBankCardInformationModel){
        bizBankCardInformationService.updateById(bizBankCardInformationModel);
        return RespData.ok();
    }

    @PostMapping("delete")
    public RespData delete(@RequestBody BizBankCardInformationModel bizBankCardInformationModel){
        bizBankCardInformationModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        bizBankCardInformationService.updateById(bizBankCardInformationModel);
        return RespData.ok();
    }

}