package com.diandian.admin.merchant.modules.biz.controller;

import cn.hutool.core.util.ObjectUtil;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantFreightSetVO;
import com.diandian.dubbo.facade.model.merchant.MerchantFreightSetModel;
import com.diandian.dubbo.facade.model.merchant.MerchantIntegralMallBannerModel;
import com.diandian.dubbo.facade.service.merchant.MerchantFreightSetService;
import com.diandian.dubbo.facade.service.merchant.MerchantIntegralMallBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/2/20 11:48
 */
@RequestMapping("/biz/merchantFreightSet")
@RestController
@Slf4j
public class MerchantFreightSetController extends BaseController {
    @Autowired
    private MerchantFreightSetService freightSetService;

    @Autowired
    private MerchantIntegralMallBannerService merchantIntegralMallBannerService;

    /**
     * 添加运费设置
     * @param freightSetVO
     * @return
     */
    @PostMapping("/save")
    public RespData saveFreightSet(@RequestBody MerchantFreightSetVO freightSetVO){
        String freightTemplate = freightSetVO.getFreightTemplate();
        Integer assumeFlag = freightSetVO.getAssumeFlag();
        AssertUtil.notNull(assumeFlag, "运费承担不能为空");
        Long merchantInfoId = getMerchantInfoId();
        MerchantFreightSetModel freightSetModel = freightSetService.getByMerchantId(merchantInfoId);
        if (ObjectUtil.isNull(freightSetModel)) {
            freightSetModel = new MerchantFreightSetModel();
            freightSetModel.setMerchantId(merchantInfoId);
            freightSetModel.setFreightStateFlag(0);
            freightSetModel.setFreightTemplate(freightTemplate);
            freightSetModel.setAssumeFlag(assumeFlag);
            freightSetModel.setProductShowStyle(freightSetVO.getProductShowStyle());
        }else{
            freightSetModel.setFreightTemplate(freightTemplate);
            freightSetModel.setAssumeFlag(assumeFlag);
            freightSetModel.setProductShowStyle(freightSetVO.getProductShowStyle());
        }
        freightSetService.saveFreightSet(freightSetModel, freightSetVO.getBannerList());
        return RespData.ok();
    }

    @GetMapping("/get")
    public RespData FreightSet(){
        Map<String, Object> res = new HashMap<>();
        MerchantFreightSetModel freightSetModel = freightSetService.getByMerchantId(getMerchantInfoId());
        List<MerchantIntegralMallBannerModel> list = merchantIntegralMallBannerService.listByMchId(super.getMerchantInfoId());
        if (ObjectUtil.isNull(freightSetModel)) {
            res.put("freight", null);
            return RespData.ok(res);
        }
        MerchantFreightSetVO freightSetVO = new MerchantFreightSetVO();
        freightSetVO.setAssumeFlag(freightSetModel.getAssumeFlag());
        freightSetVO.setFreightTemplate(freightSetModel.getFreightTemplate());
        freightSetVO.setProductShowStyle(freightSetModel.getProductShowStyle());
        freightSetVO.setBannerList(list);
        res.put("freight", freightSetVO);
        return RespData.ok(res);
    }
}
