package com.diandian.dubbo.product.common;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @date 2019-03-11
 */
@Component
@Slf4j
public class OrderStateListener implements MessageListener {

    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        String orderNo = new String(message.getBody());
        log.info("收到订单状态消息orderNo={}", orderNo);
        try {
            orderInfoService.updateOrderState(orderNo);
        } catch (Exception e) {
            log.error("订单状态更新异常", e);
            return Action.ReconsumeLater;
        }
        return Action.CommitMessage;
    }
}
