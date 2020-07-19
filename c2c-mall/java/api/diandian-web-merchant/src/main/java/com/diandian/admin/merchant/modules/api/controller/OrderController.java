package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.order.OrderInfoDTO;
import com.diandian.dubbo.facade.dto.order.OrderNoDTO;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController {
    @Autowired
    OrderInfoService orderInfoService;

    @PostMapping("/createOrder")
    public RespData createOrder(@RequestBody List<OrderInfoDTO> list){
        list.forEach(el->{
            el.setMerchantId(getMerchantInfoId());
        });
        OrderNoDTO orderNo = orderInfoService.createOrder(list);
        return RespData.ok(orderNo);
    }

    @GetMapping("/canPlaceOrder")
    public RespData canPlaceOrder(@RequestParam String orderNo){
        boolean canPlaceOrder = orderInfoService.canPlaceOrder(orderNo);
        return RespData.ok(canPlaceOrder ? 1 : 0);
    }
}
