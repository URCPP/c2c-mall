package com.diandian.admin.business.modules.biz.controller;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.business.modules.biz.service.BizWithdrawalApplyService;
import com.diandian.admin.common.constant.BisinessConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.biz.BizWithdrawalApplyModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/biz/withdrawalAudit")
public class BizWithdrawalApplyController extends BaseController {

    @Autowired
    BizWithdrawalApplyService bizWithdrawalApplyService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:withdrawalAudit:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizWithdrawalApplyService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取对象
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:orgStrategy:list")
    public RespData getById(@PathVariable Long id) {
        Map<String,Object> data = new HashMap<>();
        BizWithdrawalApplyModel bizWithdrawalApplyModel = bizWithdrawalApplyService.getById(id);
        data.put("bizWithdrawalApply",bizWithdrawalApplyModel);
        return RespData.ok(data);
    }


    /**
     * 提现申请审核
     * @param params
     * @return
     */
    @PostMapping("/audit")
    @RequiresPermissions("biz:withdrawalAudit:audit")
    public RespData audit(@RequestBody Map<String,Object> params) {
        BizWithdrawalApplyModel bizWithdrawalApplyModel = bizWithdrawalApplyService.getById(Long.parseLong(params.get("id").toString()));
        bizWithdrawalApplyModel.setAuditState(Integer.parseInt(params.get("auditState").toString()));
        bizWithdrawalApplyModel.setAuditUserId(getUserId());
        if (bizWithdrawalApplyModel.getAuditState() == BizConstant.AuditState.AUDIT_PASSED.value())
            bizWithdrawalApplyModel.setPaymentState(BisinessConstant.PaymentState.WAIT.value());
        bizWithdrawalApplyModel.setRemark(params.get("remark").toString());
        bizWithdrawalApplyService.auditBizWithdrawalApply(bizWithdrawalApplyModel);
        return RespData.ok();
    }

    /**
     * 付款
     * @param params
     * @return
     */
    @PostMapping("/payment")
    @RequiresPermissions("biz:withdrawalAudit:payment")
    public RespData payment(@RequestBody Map<String,Object> params) {
        AssertUtil.notNull(params.get("id"),"提现申请ID不能为空");
        AssertUtil.notNull(params.get("paymentState"),"打款状态不能为空");
        BizWithdrawalApplyModel bizWithdrawalApplyModel = bizWithdrawalApplyService.getById(Long.parseLong(params.get("id").toString()));
        bizWithdrawalApplyModel.setPaymentState(Integer.parseInt(params.get("paymentState").toString()));
        bizWithdrawalApplyModel.setRemark(params.get("remark").toString());
        bizWithdrawalApplyModel.setPaymentUserId(getUserId());
        bizWithdrawalApplyService.paymentBizWithdrawalApply(bizWithdrawalApplyModel,params.get("pic").toString());
        return RespData.ok();
    }

}
