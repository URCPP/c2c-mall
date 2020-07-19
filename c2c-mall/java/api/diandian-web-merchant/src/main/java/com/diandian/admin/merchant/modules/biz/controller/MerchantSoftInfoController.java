package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.service.merchant.MerchantSoftInfoService;
import com.diandian.dubbo.facade.vo.merchant.MerchantSoftInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jbuhuan
 * @date 2019/2/20 17:05
 */
@RestController
@RequestMapping("/biz/merchantSoftInfo")
@Slf4j
public class MerchantSoftInfoController extends BaseController {
    @Autowired
    private MerchantSoftInfoService softInfoService;

    /**
     * 获取商户的软件信息列表
     *
     * @return
     */
    @RequestMapping("/list")
    public RespData listByMerchantId() {
        List<MerchantSoftInfoVO> list = softInfoService.listByMerchantId(getMerchantInfoId());
        return RespData.ok(list);
    }
}
