package com.diandian.admin.merchant.modules.order.controller;

import cn.hutool.core.util.StrUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.order.OrderDeliveryDTO;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.vo.order.OrderExchangeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/3/6 20:11
 */
@RestController
@RequestMapping("/orderInfo")
@Slf4j
public class OrderInfoController extends BaseController {
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderDetailService orderDetailService;


    /**
     * 兑换订单的列表、分页
     * @author: jbuhuan
     * @date: 2019/3/26 11:47
     * @param params
     * @return: R
     */
    @GetMapping("/exchangeListPage")
    public RespData exchangeListPage(@RequestParam Map<String, Object> params){
        params.put("merchantId", getMerchantInfoId());
        PageResult pageResult = orderInfoService.listExchangeOrderPage(params);
        return RespData.ok(pageResult);
    }

    /**
     * 获取兑换订单信息
     * @author: jbuhuan
     * @date: 2019/3/26 11:46
     * @param params
     * @return: R
     */
    @GetMapping("/info")
    public RespData getExchangeOrderInfo(@RequestParam Map<String, Object> params){
        OrderExchangeVO vo = orderInfoService.getExchangeOrderInfo(params);
        return RespData.ok(vo);
    }

    /**
     * 发货
     *
     * @return
     */
    @PostMapping("/updateStateSend2")
    public RespData updateStateSend2(@RequestBody @Valid OrderDeliveryDTO dto) {
        OrderDetailModel detailModel = orderDetailService.getById(dto.getOrderDetailId());
        AssertUtil.notNull(detailModel,"订单不存在");
        orderDetailService.updateStateSend2(dto);
        return RespData.ok();
    }

}
