package com.diandian.admin.business.modules.order;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.order.OrderDetailTransportDTO;
import com.diandian.dubbo.facade.model.order.OrderExpressInfoModel;
import com.diandian.dubbo.facade.service.order.OrderExpressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/5/21 17:48
 */
@RestController
@RequestMapping("/order/expressInfo")
public class OrderExpressInfoController {

    @Autowired
    private OrderExpressInfoService orderExpressInfoService;

    @GetMapping("/list")
    public RespData list(@RequestParam Long id){
        List<OrderDetailTransportDTO> list = orderExpressInfoService.list(id);
        return RespData.ok(list);
    }
}
