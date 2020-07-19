package com.diandian.admin.business.modules.api.controller;

import cn.hutool.json.JSONUtil;
import com.diandian.admin.business.modules.api.dto.Express100SubscriptionPushDTO;
import com.diandian.admin.business.modules.api.dto.ResponseExpress100DTO;
import com.diandian.admin.common.util.EnumUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.service.order.OrderExpressInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快递100接口
 * @author cjunyuan
 * @date 2019/5/15 16:59
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class Express100Controller {

    @Autowired
    private OrderExpressInfoService orderExpressInfoService;

    @PostMapping("/listen/expressInfo")
    public ResponseExpress100DTO receivePush(@RequestParam String param){
        log.info("【接收快递100的订阅推送】：" + param);
        try{
            Express100SubscriptionPushDTO dto = JSONUtil.toBean(param, Express100SubscriptionPushDTO.class);
            BizConstant.TransportStatus status = EnumUtil.getLabelByValue(BizConstant.TransportStatus.class, "value", dto.getStatus());
            Integer state = Integer.valueOf(dto.getLastResult().getState());
            orderExpressInfoService.batchSave(dto.getLastResult().getNu(), dto.getLastResult().getCom(), state, status.value(), dto.getLastResult().getData());
        }catch (Exception e){
            log.info("【快递100的订阅推送执行失败】：" + e);
            return ResponseExpress100DTO.fail();
        }
        return ResponseExpress100DTO.success();
    }
}
