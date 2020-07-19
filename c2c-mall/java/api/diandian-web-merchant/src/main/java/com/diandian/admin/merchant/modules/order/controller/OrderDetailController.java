package com.diandian.admin.merchant.modules.order.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.order.OrderDetailNumDTO;
import com.diandian.dubbo.facade.dto.order.OrderNumDTO;
import com.diandian.dubbo.facade.service.merchant.MerchantProductInfoService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.vo.order.OrderNumVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/3/6 13:55
 */
@RestController
@RequestMapping("/orderDetail")
@Slf4j
public class OrderDetailController extends BaseController {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private MerchantProductInfoService merchantProductInfoService;

    /**
     * 根据订单的状态获取对应的数量
     *
     * @param
     * @author: jbuhuan
     * @date: 2019/3/26 11:45
     * @return: R
     */
    @GetMapping("/orderNum")
    public RespData getOrderNum() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", this.getMerchantInfoId());
        OrderNumDTO orderNum = orderDetailService.getOrderNum(params);
        OrderNumVO vo = new OrderNumVO();
        vo.setExchangeNum(orderNum.getExchangeNum() == null ? 0 : orderNum.getExchangeNum());
        vo.setOrderNum(orderNum.getOrderNum() == null ? 0 : orderNum.getOrderNum());
        OrderDetailNumDTO orderDetailNum = orderDetailService.getOrderDetailNum(params);
        vo.setUnSendOutNum(orderDetailNum.getUnSendOutNum() == null ? 0 : orderDetailNum.getUnSendOutNum());
        vo.setUnReceivedNum(orderDetailNum.getUnReceivedNum() == null ? 0 : orderDetailNum.getUnReceivedNum());

       /* //已加入预售区的商品总数
        MerchantProductInfoDTO infoDTO = merchantProductInfoService.getProductTotal(params);
        vo.setProductTotal(infoDTO.getProductTotal());*/
        Date now = new Date();
        String date = DateUtil.format(now, "yyyy-MM-dd");
        params.put("date", date);
        OrderNumDTO curOrderNumDTO = orderDetailService.getOrderNum(params);
        vo.setCurExchangeNum(curOrderNumDTO.getExchangeNum() == null ? 0 : curOrderNumDTO.getExchangeNum());
        vo.setCurOrderNum(curOrderNumDTO.getOrderNum() == null ? 0 : curOrderNumDTO.getOrderNum());
        return RespData.ok(vo);
    }

    /**
     * 确认单件商品收货
     *
     * @param id
     * @author: jbuhuan
     * @date: 2019/3/26 11:44
     * @return: R
     */
    @GetMapping("/update/{id}")
    public RespData confirmOrderById(@PathVariable Long id) {
        orderDetailService.confirmTake(id);
        return RespData.ok();
    }

    /**
     * 确认整个订单收货
     *
     * @param ids
     * @author: jbuhuan
     * @date: 2019/3/26 11:44
     * @return: R
     */
    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public RespData confirmOrderByIds(@RequestBody Long[] ids) {
        AssertUtil.isTrue(ArrayUtil.isNotEmpty(ids), "订单不存在");
        for (Long id : ids) {
            orderDetailService.confirmTake(id);
        }
        return RespData.ok();
    }
}
