package com.diandian.admin.merchant.modules.biz.controller;


import cn.hutool.core.util.ObjectUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.biz.service.MerchantDebitCardInfoService;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantAccountInfoVO;
import com.diandian.admin.model.merchant.MerchantDebitCardInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户账户管理表
 *
 * @author jbuhuan
 * @date 2019/2/25 21:13
 */
@RestController
@RequestMapping("/biz/merchantAccountInfo")
@Slf4j
public class MerchantAccountInfoController extends BaseController {
    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;

    @GetMapping("getAccount")
    public RespData getByMerchantId(){
        return RespData.ok(merchantAccountInfoService.getByMerchantId(getMerchantInfoId()));
    }

    @PostMapping("releaseFrozenAmount")
    public RespData releaseFrozenAmount(){
        merchantAccountInfoService.releaseFrozenAmount(getMerchantInfoId());
        return RespData.ok();
    }
}
