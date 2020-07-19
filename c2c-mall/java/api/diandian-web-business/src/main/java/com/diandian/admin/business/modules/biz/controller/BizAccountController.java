package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.AccountQueryDTO;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.model.biz.BizBonusDetailModel;
import com.diandian.dubbo.facade.service.biz.BizAccountDetailService;
import com.diandian.dubbo.facade.service.biz.BizAccountService;
import com.diandian.dubbo.facade.service.biz.BizBonusDetailService;
import com.diandian.dubbo.facade.service.biz.BizCommissionDetailService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/biz/account")
public class BizAccountController extends BaseController {

    @Autowired
    BizAccountService bizAccountService;
    @Autowired
    NoGenerator noGenerator;
    @Autowired
    BizBonusDetailService bizBonusDetailService;
    @Autowired
    private BizAccountDetailService bizAccountDetailService;
    @Autowired
    private BizCommissionDetailService bizCommissionDetailService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:account:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        params.put("orgId",getUser().getOrgId());
        PageResult page = bizAccountService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 发放奖金
     * @param params
     * @return
     */
    @PostMapping("/paymentBonus")
    @RequiresPermissions("biz:account:bonus")
    public RespData paymentBonus(@RequestBody Map<String,Object> params) {
        AssertUtil.notNull(params.get("orgId"),"机构ID不能为空");
        AssertUtil.notNull(params.get("amount"),"发放金额不能为空");
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        AssertUtil.isTrue(amount.compareTo(BigDecimal.ZERO) >0,"发放金额必须大于零");
        // 获取机构账号
        Long orgId = Long.parseLong(params.get("orgId").toString());
        AccountQueryDTO accountQueryDTO = new AccountQueryDTO();
        accountQueryDTO.setOrgId(orgId);
        BizAccountModel bizAccountModel = bizAccountService.getOne(accountQueryDTO);
        AssertUtil.isTrue(bizAccountModel.getBonus().compareTo(amount) > 0,"账号奖金余额不足");
        // 记录奖金发放明细
        BizBonusDetailModel bizBonusDetailModel = new BizBonusDetailModel();
        bizBonusDetailModel.setTradeNo(noGenerator.getTradeNo());
        bizBonusDetailModel.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
        bizBonusDetailModel.setBusType(BizConstant.BonusBusType.ISSUE.value());
        bizBonusDetailModel.setTradeAmount(amount);
        bizBonusDetailModel.setTradeStart(bizAccountModel.getBonus());
        bizBonusDetailModel.setTradeEnd(bizAccountModel.getBonus().subtract(amount));
        bizBonusDetailModel.setOrgId(orgId);
        bizBonusDetailModel.setIssueUserId(getUserId());
        bizBonusDetailModel.setIssueTime(new Date());
        bizBonusDetailService.issueBonus(bizBonusDetailModel,bizAccountModel,Integer.parseInt(params.get("bonusType").toString()));
        return RespData.ok();
    }

    /**
     * 列表
     * @param query
     * @return
     */
    @RequestMapping("/detailListPage")
    @RequiresPermissions("biz:accountDetail:list")
    public RespData detailListPage(@RequestBody OrgAccountQueryDTO query) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            query.setOrgId(orgId);
        }
        if(null != query.getAccountType()){
            if(query.getAccountType() == 1){
                return RespData.ok(bizAccountDetailService.listPage(query));
            }else if(query.getAccountType() == 2){
                return RespData.ok(bizBonusDetailService.listPage(query));
            }else if (query.getAccountType() == 3){
                return RespData.ok(bizCommissionDetailService.listPage(query));
            }
        }
        return RespData.ok();
    }

}
