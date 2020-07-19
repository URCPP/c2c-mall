package com.diandian.admin.merchant.modules.biz.controller;

import cn.hutool.core.util.ObjectUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.member.MemberExchangeSetModel;
import com.diandian.dubbo.facade.service.member.MemberExchangeSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 兑换积分充值设置管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/memberExchangeSet")
@Slf4j
public class MemberExchangeSetController extends BaseController {

    @Autowired
    private MemberExchangeSetService memberExchangeSetService;

    @GetMapping("/getOne")
    public RespData getOne() {
        MemberExchangeSetModel memberExchangeSetModel = memberExchangeSetService.getSetByMerchantId(this.getMerchantInfo().getId());
        return RespData.ok(memberExchangeSetModel);
    }


    /**
     * 修改
     *
     * @param model
     * @return R
     */
    @PostMapping("/update")
    public RespData updateById(@RequestBody MemberExchangeSetModel model) {
        Long merchantId = model.getMerchantId();
        if (ObjectUtil.isNull(merchantId)) {
            model.setMerchantId(this.getMerchantInfo().getId());
        }
        memberExchangeSetService.updateSet(model);
        return RespData.ok();
    }

}
