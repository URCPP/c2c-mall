package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.RemitAuditDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantRemitLogModel;
import com.diandian.dubbo.facade.service.merchant.MerchantRemitLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/biz/remitAudit")
public class BizRemitController extends BaseController {

    @Autowired
    MerchantRemitLogService merchantRemitLogService;

    /**
     * 列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:remitAudit:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = merchantRemitLogService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取对象
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:remitAudit:list")
    public RespData getById(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();
        MerchantRemitLogModel model = merchantRemitLogService.getById(id);
//        data.put("bizWithdrawalApply",bizWithdrawalApplyModel);
        return RespData.ok(model);
    }


    /**
     * @Author wubc
     * @Description //TODO  商家线下支付审核
     * @Date 12:09 2019/4/1
     * @Param [dto]
     * @return com.diandian.admin.common.util.RespData
     **/
    /*@PostMapping("/audit")
    @RequiresPermissions("biz:remitAudit:audit")
    public RespData audit(@RequestBody RemitAuditDTO dto) {
        dto.setAuditor(this.getUser().getUsername());
        merchantRemitLogService.audit(dto);
        return RespData.ok();
    }*/


}
