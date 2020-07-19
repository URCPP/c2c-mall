package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.member.MemberMerchantRelationService;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/2/19 15:45
 */
@RestController
@RequestMapping("/biz/memberOrderOptLog")
@Slf4j
public class MemberOrderOptLogController extends BaseController {

    @Autowired
    private MemberOrderOptLogService orderOptLogService;
    @Autowired
    private MemberMerchantRelationService memberMerchantRelationService;
    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 操作记录列表、分页
     *
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        Long merchantInfoId = getMerchantInfoId();
        params.put("merchantId", merchantInfoId);
        PageResult pageResult = orderOptLogService.listPage(params);
        return RespData.ok(pageResult);
    }

    /**
     * 会员异常订单列表
     * @param params
     * @return
     */
    @GetMapping("/listMemberAbnormalOrder")
    public RespData listMemberAbnormalOrder(@RequestParam Map<String, Object> params){
        params.put("merchantId",getMerchantInfoId());
        PageResult pageResult = orderOptLogService.listMemberAbnormalOrder(params);
        return RespData.ok(pageResult);
    }

    /**
     * 恢复异常订单
     * @param params
     * @return
     */
    @PostMapping("/recoverAbnormalOrder")
    public RespData recoverAbnormalOrder(@RequestBody Map<String, Object> params){
        String orderNo = (String)params.get("orderNo");
        MemberOrderOptLogModel orderOptLog = orderOptLogService.getOrderOptLogByOrderNo(orderNo);
        OrderInfoModel orderInfo = orderInfoService.getByOrderNo(orderNo);
        Integer orderState = orderOptLog.getOrderState();
        if(orderState.equals(MemberConstant.OrderState.ORDER_PAID_FREIGHT.getValue())&&orderInfo.getPayState().equals(BizConstant.OrderPayState.PAY_FAIL.value())){
            memberMerchantRelationService.updateMemberAccount(orderNo);
        }else if(orderState.equals(MemberConstant.OrderState.ORDER_PAID_COUPON.getValue())&&orderInfo.getPayState().equals(BizConstant.OrderPayState.PAY_FAIL.value())){
            merchantWalletInfoService.updateMerchantWallet(orderNo);
        }else{
            throw new BusinessException("订单状态异常");
        }
        return RespData.ok();
    }

}
