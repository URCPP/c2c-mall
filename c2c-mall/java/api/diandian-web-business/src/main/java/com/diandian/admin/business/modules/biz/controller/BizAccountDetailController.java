package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.business.modules.biz.service.BizWithdrawalApplyService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.service.biz.BizWithdrawalRuleService;
import com.diandian.admin.common.constant.BisinessConstant;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.biz.BizWithdrawalApplyModel;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalRuleModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.AccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.service.biz.BizAccountDetailService;
import com.diandian.dubbo.facade.service.biz.BizAccountService;
import com.diandian.dubbo.facade.service.biz.BizBonusDetailService;
import com.diandian.dubbo.facade.service.biz.BizCommissionDetailService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/biz/accountDetail")
public class BizAccountDetailController extends BaseController {

    @Autowired
    BizAccountDetailService bizAccountDetailService;
    @Autowired
    BizBonusDetailService bizBonusDetailService;
    @Autowired
    BizCommissionDetailService bizCommissionDetailService;
    @Autowired
    BizWithdrawalRuleService bizWithdrawalRuleService;
    @Autowired
    BizWithdrawalApplyService bizWithdrawalApplyService;
    @Autowired
    BizAccountService bizAccountService;
    @Autowired
    NoGenerator noGenerator;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:accountDetail:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            params.put("orgId",orgId);
        }
        PageResult page = bizAccountDetailService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 奖金列表
     * @param params
     * @return
     */
    @RequestMapping("/listBonusPage")
    @RequiresPermissions("biz:accountDetail:list")
    public RespData listBonusPage(@RequestParam Map<String, Object> params) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            params.put("orgId",orgId);
        }
        PageResult page = bizBonusDetailService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 销售佣金列表
     * @param params
     * @return
     */
    @RequestMapping("/listCommissionPage")
    @RequiresPermissions("biz:accountDetail:list")
    public RespData listCommissionPage(@RequestParam Map<String, Object> params) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            params.put("orgId",orgId);
        }
        PageResult page = bizCommissionDetailService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取账号余额
     * @return
     */
    @RequestMapping("/getAccount")
    @RequiresPermissions("biz:accountDetail:list")
    public RespData getAccount(@RequestParam(required = false) Long orgId) {
        Long userOrgId = getUser().getOrgId();
        if(null != orgId){
            return RespData.ok(bizAccountService.getByOrgId(orgId));
        }
        if(null != userOrgId){
            return RespData.ok(bizAccountService.getByOrgId(userOrgId));
        }
        return RespData.ok();
    }

    /**
     * 提现申请
     * @param amount
     * @return
     */
    @PostMapping("/apply")
    @RequiresPermissions("biz:accountDetail:apply")
    public RespData apply(@RequestParam BigDecimal amount) {
        Long orgId = getUser().getOrgId();
        // 获取账号信息
        AccountQueryDTO accountQueryDTO = new AccountQueryDTO();
        accountQueryDTO.setOrgId(orgId);
        BizAccountModel bizAccountModel = bizAccountService.getOne(accountQueryDTO);
        // 校验提现规则
        BizWithdrawalRuleModel bizWithdrawalRuleModel = bizWithdrawalRuleService.getBizWithdrawalRuleModel();
        if (amount.compareTo(bizWithdrawalRuleModel.getMaxAmount()) > 0){
            return RespData.fail("提现金额超过单笔最大提现金额");
        }
        if (bizWithdrawalRuleModel.getMinAmount().compareTo(amount) > 0){
            return RespData.fail("提现金额低于单笔最小提现金额");
        }
        if (amount.compareTo(bizAccountModel.getAvailableBalance()) >0 ){
            return RespData.fail("提现金额超过用户账户余额");
        }
        // 校验当日提现次数
        Integer count = bizWithdrawalApplyService.countByOrgId(orgId);
        if (count > bizWithdrawalRuleModel.getDayMaxNum()){
            return RespData.fail("超过当日最高提现次数");
        }
        BizWithdrawalApplyModel bizWithdrawalApplyModel = new BizWithdrawalApplyModel();
        bizWithdrawalApplyModel.setOrderNo(noGenerator.getWithdrawalNo());
        bizWithdrawalApplyModel.setAgentId(orgId);
        bizWithdrawalApplyModel.setWithdrawalAmount(amount);
        bizWithdrawalApplyModel.setWithdrawalStart(bizAccountModel.getAvailableBalance());
        bizWithdrawalApplyModel.setWithdrawalEnd(bizAccountModel.getAvailableBalance().subtract(amount));
        bizWithdrawalApplyModel.setWithdrawalFee(amount.multiply(bizWithdrawalRuleModel.getFee()).divide(new BigDecimal(100)));
        bizWithdrawalApplyModel.setActualAmount(amount.subtract(bizWithdrawalApplyModel.getWithdrawalFee()));
        bizWithdrawalApplyModel.setAuditState(BisinessConstant.AuditState.WAIT.value());
        bizWithdrawalApplyModel.setPaymentState(BisinessConstant.PaymentState.WAIT.value());
        bizWithdrawalApplyService.saveBizWithdrawalApply(bizWithdrawalApplyModel);
        return RespData.ok();
    }

}
