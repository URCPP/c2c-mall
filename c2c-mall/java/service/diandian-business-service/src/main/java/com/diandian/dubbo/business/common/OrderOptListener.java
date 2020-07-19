package com.diandian.dubbo.business.common;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author x
 * @date 2019-03-11
 */
@Component
@Slf4j
public class OrderOptListener implements MessageListener {

    @Autowired
    private MemberOrderOptLogService memberOrderOptLogService;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        MemberOrderOptLogModel memberOrderOptLogModel = JSONObject.parseObject(message.getBody(), MemberOrderOptLogModel.class);
        log.info("收到订单操作消息order={}", memberOrderOptLogModel.toString());
        try {
            memberOrderOptLogService.insertOrderOptLog(memberOrderOptLogModel);
        } catch (Exception e) {
            log.error("添加会员订单操作记录异常", e);
            return Action.ReconsumeLater;
        }
        return Action.CommitMessage;
    }
}
