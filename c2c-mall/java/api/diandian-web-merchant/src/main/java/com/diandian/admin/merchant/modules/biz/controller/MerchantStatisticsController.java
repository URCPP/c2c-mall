package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.vo.member.MemberExchangeHistoryLogVO;
import com.diandian.dubbo.facade.vo.member.MemberExchangeOderWeekVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantCountMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/2/20 17:05
 */
@RestController
@RequestMapping("/biz/statistics")
@Slf4j
public class MerchantStatisticsController extends BaseController {
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 获取商户会员统计
     *
     * @return
     */
    @GetMapping("/countMember")
    public RespData countMember(@RequestParam String type) {
        MerchantCountMemberVO vo = merchantInfoService.countMember(getMerchantInfoId(),type);
        return RespData.ok(vo);
    }

    /**
     * 商户兑换券统计
     *
     * @return
     */
    @GetMapping("/countIntegral")
    public RespData countIntegral(@RequestParam String type) {
        MemberExchangeHistoryLogVO vo = merchantInfoService.countIntegral(getMerchantInfoId(), type);
        return RespData.ok(vo);
    }

    @GetMapping("/countWeek")
    public RespData countWeek() {
        Map<String, Object> params =new HashMap<>();
        params.put("merchantId", getMerchantInfoId());
        List<OrderInfoModel> list = orderInfoService.countOrderWeek(params);
        MemberExchangeOderWeekVO weekVO = new MemberExchangeOderWeekVO();
        BigDecimal total = BigDecimal.valueOf(0);
        if(list!=null && list.size()>0){
            for (OrderInfoModel model : list) {
                total= total.add(model.getOrderAmount());
            }
        }
        weekVO.setExchangeOrderWeekTotal(total);
        weekVO.setExchangeOrderWeekNum(list!=null &&list.size()>0?list.size():0);
        return RespData.ok(weekVO);
    }
}
