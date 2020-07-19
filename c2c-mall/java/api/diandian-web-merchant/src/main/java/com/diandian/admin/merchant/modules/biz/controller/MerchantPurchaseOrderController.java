package com.diandian.admin.merchant.modules.biz.controller;

import cn.hutool.core.util.StrUtil;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.order.OrderTransactionService;
import com.diandian.dubbo.facade.vo.merchant.MerchantPurchaseOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 商户采购订单管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/purchaseOrder")
@Slf4j
public class MerchantPurchaseOrderController extends BaseController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderTransactionService orderTransactionService;



    /**
     * 统计商户订单信息
     *
     * @return
     */
    @GetMapping("/countOrder")
    public RespData countOrder() {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        MerchantPurchaseOrderVO vo = orderInfoService.countOrder(merchantInfo);
        return RespData.ok(vo);
    }

    /**
     * 商户信息分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        params.put("merchantId", merchantInfo.getId());
        PageResult page = orderInfoService.listMerchantPage(params);
        page.getList();
        return RespData.ok(page);
    }

    /**
     * 订单取消
     *
     * @param orderNo
     * @return
     */
    @GetMapping("/cancelOrder/{orderNo}")
    public RespData cancelOrder(@PathVariable String orderNo) {
        // todo 取消订单接口
        AssertUtil.notBlank(orderNo,"订单不存在");
        orderTransactionService.closeOrderAndRecoverStock(orderNo);
        return RespData.ok();
    }

//    /**
//     * 订单付款
//     *
//     * @param id
//     * @return R
//     */
//    @PostMapping("/pay/{id}")
//    public RespData pay(@PathVariable Long id) {
//        // todo 订单付款 直接跳转支付页面
//        return RespData.ok();
//    }


}
