package com.diandian.admin.merchant.modules.order.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.service.order.OrderAfterSaleApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/3/5 15:40
 */
@RestController
@RequestMapping("/orderAfterSaleApply")
@Slf4j
public class OrderAfterSaleApplyController extends BaseController {
    @Autowired
    private OrderAfterSaleApplyService orderAfterSaleApplyService;

    @GetMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params){
        params.put("afterSaleFlag", 1);
        params.put("merchantId", getMerchantInfoId());
        PageResult pageResult = orderAfterSaleApplyService.listPage(params);
        return RespData.ok(pageResult);
    }
}
