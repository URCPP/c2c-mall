package com.diandian.dubbo.product.common;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.order.OrderTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @date 2019-03-11
 */
@Component
@Slf4j
public class OrderExpireListener implements MessageListener {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderTransactionService orderTransactionService;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        Long orderId = Long.valueOf(new String(message.getBody()));
        log.info("收到延迟消息orderId={}", orderId);
        OrderInfoModel existOrderInfo = orderInfoService.getById(orderId);
        if (ObjectUtil.isNull(existOrderInfo)) {
            log.info("订单ID={},不存在", orderId);
            return Action.CommitMessage;
        }
        try {
            orderTransactionService.closeOrderAndRecoverStock(existOrderInfo.getOrderNo());
        } catch (Exception e) {
            log.error("恢复库存异常,消息重新投递订单ID={}", orderId, e);
            return Action.ReconsumeLater;
        }
        return Action.CommitMessage;
    }
}
