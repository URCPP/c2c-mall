package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.merchant.MerchantBankInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantBankInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 15:16 2019/10/28
 * @Modified By:
 *
 */

@RequestMapping("merchantBankInfo")
@RestController
@Slf4j
public class MerchantBankInfoController extends BaseController {

    @Autowired
    private MerchantBankInfoService merchantBankInfoService;

    @GetMapping("list")
    public RespData listBank(Long merchantId){
        List<MerchantBankInfoModel> bankModels=merchantBankInfoService.list(merchantId);
        return  RespData.ok(bankModels);
    }

    @PostMapping("deleteById/{id}")
    public RespData delBank(@PathVariable @Valid Long id){
        merchantBankInfoService.deleteBankById(id);
        return  RespData.ok();
    }

}
