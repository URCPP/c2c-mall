package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantOpenOptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 商户管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/openOptLog")
public class MerchantOpenOptLogController extends BaseController {

    @Autowired
    private MerchantOpenOptLogService merchantOpenOptLogService;


    /**
     * 分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/listPage")
    public RespData listSoftAcc(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        params.put("merchantId", merchantInfo.getId());
        PageResult page = merchantOpenOptLogService.listPage(params);
        return RespData.ok(page);
    }


}
