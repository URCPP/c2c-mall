package com.diandian.dubbo.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAddPriceModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantAddPriceService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.task.TaskService;
import com.diandian.dubbo.product.mapper.OrderDetailMapper;
import com.diandian.dubbo.product.mapper.OrderInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("taskService")
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private MerchantAddPriceService merchantAddPriceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ordercompleteShareBenefit() {
        List<Long> orderDetailIdS = orderDetailMapper.selectIds();
        //结算分销分润
        //bizMemberAccountService.mallOrderCompletionShareProfit(orderDetailIdS);
        List<MerchantAddPriceModel> merchantAddPriceModels = merchantAddPriceService.selectOrderComplete();
        //结算分享加价分润
        for (int i = 0; i < merchantAddPriceModels.size(); i++) {
            MerchantInfoModel shareMember =
                    merchantInfoService.getMerchantInfoById(merchantAddPriceModels.get(i).getMerchantId());
            OrderInfoModel orderInfoModel = orderInfoMapper.selectOne(new QueryWrapper<OrderInfoModel>().eq("order_no"
                    , merchantAddPriceModels.get(i).getOrderNo()).groupBy("merchant_id"));
            MerchantInfoModel placeOrderMember = merchantInfoService.getMerchantInfoById(orderInfoModel.getMerchantId());
            merchantInfoService.getMerchantInfoById(merchantAddPriceModels.get(i).getMerchantId());
            //远程调用代理增加余额
//            bizMemberAccountService.shareOrderAddAccount(merchantAddPriceModels.get(i).getAddPrice(),
//                    shareMember.getPhone(), placeOrderMember.getPhone(), merchantAddPriceModels.get(i).getOrderDetailId());
            merchantAddPriceModels.get(i).setFlag(3);
            merchantAddPriceService.updateById(merchantAddPriceModels.get(i));
            log.info("商品加价订单分润成功 id {}", merchantAddPriceModels.get(i).getId());
        }
    }
}
