package com.diandian.admin.merchant.modules.biz.controller;


import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.merchant.MerchantOpenPlatformModel;
import com.diandian.dubbo.facade.service.merchant.MerchantOpenPlatformService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商户积分商城开放平台信息 前端控制器
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-10
 */
@RestController
@RequestMapping("/biz/mchOpenPlatform")
public class MerchantOpenPlatformController extends BaseController {

    @Autowired
    private MerchantOpenPlatformService merchantOpenPlatformService;

    @Autowired
    private NoGenerator noGenerator;

    @GetMapping("/get")
    public RespData get(){
        Long mchId = getMerchantInfoId();
        MerchantOpenPlatformModel mchOpenPlatform = merchantOpenPlatformService.getByMchId(mchId);
        return RespData.ok(mchOpenPlatform);
    }

    @GetMapping("/open")
    public RespData open(){
        Long mchId = getMerchantInfoId();
        MerchantOpenPlatformModel old = merchantOpenPlatformService.getByMchId(mchId);
        AssertUtil.isNull(old, "你已经开通了开放平台，请勿重复操作");
        String appSecret = merchantOpenPlatformService.generateOpenPlatformInfo(mchId);
        return RespData.ok(appSecret);
    }

    @PostMapping("/update")
    public RespData update(@RequestBody MerchantOpenPlatformModel model){
        merchantOpenPlatformService.update(model);
        return RespData.ok();
    }

    @GetMapping("/regenerate")
    public RespData regenerate(){
        Long mchId = getMerchantInfoId();
        String appSecret = merchantOpenPlatformService.regenerate(mchId);
        return RespData.ok(appSecret);
    }
}
