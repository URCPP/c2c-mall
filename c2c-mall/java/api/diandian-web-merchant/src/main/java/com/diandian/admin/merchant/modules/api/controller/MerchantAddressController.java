package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.merchant.MerchantRecipientsSetModel;
import com.diandian.dubbo.facade.service.merchant.MerchantRecipientsSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/merchantAddress")
public class MerchantAddressController extends BaseController {
    @Autowired
    MerchantRecipientsSetService merchantRecipientsSetService;

    @GetMapping("/list")
    public RespData list(@RequestParam Map<String,Object> params){
        params.put("merchantId",getMerchantInfoId());
        List<MerchantRecipientsSetModel> list = merchantRecipientsSetService.list(params);
        return RespData.ok(list);
    }
}
