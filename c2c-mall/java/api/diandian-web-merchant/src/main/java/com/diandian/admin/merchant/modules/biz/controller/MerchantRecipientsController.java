package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.common.util.WebUtil;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.model.merchant.MerchantRecipientsSetModel;
import com.diandian.dubbo.facade.service.merchant.MerchantRecipientsSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户收货地址管理
 *
 * @author wubc
 * @date 2019/2/14 18:20
 */
@RestController
@RequestMapping("/merRecipients")
@Slf4j
public class MerchantRecipientsController extends BaseController {

    @Autowired
    private MerchantRecipientsSetService merchantRecipientsSetService;

    /**
     * 添加收货地址
     *
     * @param model
     * @return
     */
    @PostMapping("/save")
    public RespData save(@RequestBody MerchantRecipientsSetModel model, HttpServletRequest request) {
        Map<String,Object> params=new HashMap<>();
        params.put("merchantId", getMerchantInfoId());
        model.setIpAddress(WebUtil.getIpAddress(request));
        model.setMerchantId(this.getMerchantInfoId());
        merchantRecipientsSetService.save(model);
        return RespData.ok();
    }

    /**
     * 收货地址列表
     *
     * @return
     */
    @GetMapping("/list")
    public RespData list(@RequestParam Map<String, Object> params) {
        params.put("merchantId", getMerchantInfoId());
        List<MerchantRecipientsSetModel> setList = merchantRecipientsSetService.list(params);
        return RespData.ok(setList);
    }


    /**
     * 设置默认收货地址
     *
     * @param id
     * @return
     */
    @GetMapping("/setDefault/{id}")
    public RespData setDefault(@PathVariable Long id) {
        merchantRecipientsSetService.setDefault(id);
        return RespData.ok();
    }

    /**
     * 修改收货地址
     *
     * @param model
     * @return
     */
    @PostMapping("/update")
    public RespData update(@RequestBody MerchantRecipientsSetModel model) {
        model.setMerchantId(getMerchantInfoId());
        merchantRecipientsSetService.update(model);
        return RespData.ok();
    }

    @PostMapping("delete")
    public RespData delete(@RequestParam Long id){
        merchantRecipientsSetService.delete(id);
        return RespData.ok();
    }


}

