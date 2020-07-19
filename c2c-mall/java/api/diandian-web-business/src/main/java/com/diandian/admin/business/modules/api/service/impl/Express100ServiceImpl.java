package com.diandian.admin.business.modules.api.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.diandian.admin.business.common.config.Express100Properties;
import com.diandian.admin.business.modules.api.dto.ResponseExpress100DTO;
import com.diandian.admin.business.modules.api.dto.SubscriptionRequestParamDTO;
import com.diandian.admin.business.modules.api.service.Express100Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 快递100接口service实现类
 * @author cjunyuan
 * @date 2019/5/15 20:36
 */
@Service("express100Service")
@Slf4j
public class Express100ServiceImpl implements Express100Service {

    @Autowired
    private Express100Properties express100Properties;

    @Override
    public boolean subscription(String company, String transportNo, String phone) {
        SubscriptionRequestParamDTO dto = new SubscriptionRequestParamDTO();
        dto.setCompany(company);
        dto.setNumber(transportNo);
        dto.setKey(express100Properties.getKey());
        SubscriptionRequestParamDTO.SubscriptionRequestParam param = dto.new SubscriptionRequestParam();
        param.setCallbackurl(express100Properties.getCallbackUrl());
        param.setResultv2("1");
        param.setPhone(phone);
        dto.setParameters(param);
        Map<String, Object> params = new HashMap<>();
        params.put("schema", "json");
        params.put("param", JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty));
        String postResultStr = HttpUtil.post(express100Properties.getSubscriptionUrl(), params);
        log.info("【快递100订阅请求结果】：" + postResultStr);
        ResponseExpress100DTO postResult = JSONUtil.toBean(postResultStr, ResponseExpress100DTO.class);
        if("501".equals(postResult.getReturnCode()) || "200".equals(postResult.getReturnCode())){
            return true;
        }
        return false;
    }
}
