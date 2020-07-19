package com.diandian.admin.business.modules.biz.controller;

import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoFormDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoQueryDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantPayFeeRecordQueryDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantRecipientsSetModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.service.merchant.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjunyuan
 * @date 2019/2/15 16:14
 */
@RestController
@RequestMapping("/biz")
public class MerchantInfoController extends BaseController {

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;

    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;

    @Autowired
    private MerchantRecipientsSetService merchantRecipientsSetService;

    @Autowired
    private MerchantPayfeeRecordService merchantPayfeeRecordService;

    @Autowired
    private MerchantRemitLogService merchantRemitLogService;

    /**
     * 机构树
     */
    @GetMapping("/merchant/list")
    @RequiresPermissions("biz:merchant:list")
    public RespData merchantTree(@RequestParam Map<String, Object> params) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            params.put("parentId", orgId);
        }
        PageResult page = merchantInfoService.queryMerchantListVO(params);
        return RespData.ok(page);
    }

    /**
     * 获取
     */
    @GetMapping("/merchant/{id}")
    @RequiresPermissions("biz:merchant:list")
    public RespData getMerchant(@PathVariable Long id) {
        return RespData.ok(merchantInfoService.getMerchantDetailVO(id));
    }

    /**
     * 保存
     */
    /*@PostMapping("/merchant/add")
    @RequiresPermissions("biz:merchant:add")
    public RespData addMerchant(@RequestBody @Valid MerchantInfoFormDTO dto) {
        if(null == dto.getParentId() || dto.getParentId() == 0){
            Long orgId = getUser().getOrgId();
            AssertUtil.isTrue(null != orgId && orgId > 0, "请选择商户的上级对象");
            dto.setParentId(orgId);
            dto.setParentTypeFlag(BizConstant.ObjectType.ORG.value());
        }
        dto.setCreateBy(getUserId());
        //merchantInfoService.insertMerchant(dto);
        return RespData.ok();
    }*/

    /**
     * 修改
     */
    @PostMapping("/merchant/update")
    @RequiresPermissions("biz:merchant:update")
    public RespData updateMerchant(@RequestBody MerchantInfoFormDTO dto) {
        if(null == dto.getParentId() || dto.getParentId() == 0){
            Long orgId = getUser().getOrgId();
            AssertUtil.isTrue(null != orgId && orgId > 0, "请选择商户的上级对象");
            dto.setParentId(orgId);
            dto.setParentTypeFlag(BizConstant.ObjectType.ORG.value());
        }
        //merchantInfoService.updateMerchant(dto);
        return RespData.ok();
    }

    /**
     *
     * 功能描述: 查询商户账户信息
     *
     * @param mchId
     * @return
     * @author cjunyuan
     * @date 2019/3/19 11:10
     */
    /*@GetMapping("/merchant/accountInfo")
    @RequiresPermissions("biz:merchant:list")
    public RespData getMerchantAccountInfo(Long mchId) {
        Map<String, Object> res = new HashMap<>();
        MerchantAccountInfoModel mchAccount = merchantAccountInfoService.getByMerchantId(mchId);
        MerchantWalletInfoModel walletInfo = merchantWalletInfoService.getWalletInfo(mchId);
        res.put("availableBalance", null == mchAccount ? 0 : mchAccount.getAvailableBalance());
        res.put("amount", null == walletInfo ? 0 : walletInfo.getAmount());
        res.put("marginAmount", null == walletInfo ? 0 : walletInfo.getMarginAmount());
        return RespData.ok(res);
    }*/

    /**
     *
     * 功能描述: 查询商户账户信息
     *
     * @param mchId
     * @return
     * @author cjunyuan
     * @date 2019/3/19 11:10
     */
    @GetMapping("/merchant/resetPwd")
    @RequiresPermissions("biz:merchant:resetPwd")
    public RespData resetPassword(@RequestParam(value = "mchId") Long mchId) {
        SysUserModel user = getUser();
        Long orgTypeId = user.getOrgTypeId();
        if (!SysConstant.USER_SUPPER_ADMIN.equals(user.getId()) && !SysConstant.ROOT_ORG.equals(orgTypeId)) {
            throw new BusinessException("您暂时没有重置商户密码权限");
        }
        merchantInfoService.resetPassword(mchId);
        return RespData.ok();
    }

    /**
     *
     * 功能描述: 修改商户收货地址
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/31 15:59
     */
    @GetMapping("/merchant/getShippingAddress")
    @RequiresPermissions("biz:merchant:getShippingAddress")
    public RespData getShippingAddress(@RequestParam(value = "mchId") Long mchId) {
        AssertUtil.isTrue(null != mchId && mchId > 0, "商户信息不存在");
        MerchantInfoQueryDTO dto = new MerchantInfoQueryDTO();
        dto.setMerchantId(mchId);
        AssertUtil.isTrue(merchantInfoService.count(dto) > 0, "商户信息不存在");
        MerchantRecipientsSetModel mchDefaultShippingAddress = merchantRecipientsSetService.getMchDefaultShippingAddress(mchId);
        return RespData.ok(mchDefaultShippingAddress);
    }

    /**
     *
     * 功能描述: 修改商户收货地址
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/31 15:59
     */
    @PostMapping("/merchant/editShippingAddress")
    @RequiresPermissions("biz:merchant:editShippingAddress")
    public RespData editShippingAddress(@RequestBody MerchantRecipientsSetModel address) {
        AssertUtil.isTrue(null != address.getMerchantId() && address.getMerchantId() > 0, "商户信息不存在");
        MerchantInfoQueryDTO dto = new MerchantInfoQueryDTO();
        dto.setMerchantId(address.getMerchantId());
        AssertUtil.isTrue(merchantInfoService.count(dto) > 0, "商户信息不存在");
        if(null != address.getId() && address.getId() > 0){
            merchantRecipientsSetService.update(address);
        }else {
            merchantRecipientsSetService.save(address);
        }
        return RespData.ok();
    }

    /**
     * 机构树
     */
    @GetMapping("/merchant/exportExcel")
    @RequiresPermissions("biz:merchant:exportExcel")
    public void exportExcelOrgInfo(@RequestParam(value = "startTime", required = false) String startTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "approveFlag", required = false) Integer approveFlag,
                                   ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        TemplateExportParams exportParams = new TemplateExportParams("doc/mch.xlsx");
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("approveFlag", approveFlag);
        List<Map<String, Object>> mapList = merchantInfoService.getExportMchList(params);
        res.put("mchList",mapList);
        modelMap.put(TemplateExcelConstants.FILE_NAME, "商户信息");
        modelMap.put(TemplateExcelConstants.PARAMS, exportParams);
        modelMap.put(TemplateExcelConstants.MAP_DATA, res);
        PoiBaseView.render(modelMap, request, response, TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
    }

    /**
     *
     * 功能描述: 分页查询商户支付明细
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/4/22 16:31
     */
    @PostMapping("/merchant/listPayDetailForPage")
    @RequiresPermissions("biz:merchant:listPayDetailForPage")
    public RespData listPayDetailForPage(@RequestBody MerchantPayFeeRecordQueryDTO dto) {
        PageResult page = merchantPayfeeRecordService.listForPage(dto);
        return RespData.ok(page);
    }

    /**
     *
     * 功能描述: 分页查询积分商城线上开通列表
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/4/22 16:31
     */
    @PostMapping("/merchant/listPointsMallByOnline")
    @RequiresPermissions("biz:merchant:listPayDetailForPage")
    public RespData listPointsMallByOnline(@RequestBody MerchantPayFeeRecordQueryDTO dto) {
        dto.setPayType(MerchantConstant.MerchantPayType.OPEN_MALL.value());
        PageResult page = merchantPayfeeRecordService.listForPage(dto);
        return RespData.ok(page);
    }

    /**
     *
     * 功能描述: 分页查询积分商城线下开通列表
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/4/22 18:48
     */
    @RequestMapping("/merchant/listPointsMallByOffline")
    @RequiresPermissions("biz:remitAudit:list")
    public RespData listPointsMallByOffline(@RequestParam Map<String, Object> params) {
        params.put("type", 1);
        List<Integer> auditFlag = new ArrayList<>();
        String auditState = (String) params.get("auditFlag");
        if(StringUtils.isNotBlank(auditState)){
            auditFlag.add(Integer.valueOf(auditState));
        }else {
            auditFlag.add(MerchantConstant.RemitAuditState.WAIT_AUDIT.value());
            auditFlag.add(MerchantConstant.RemitAuditState.AUDIT_PASSED.value());
        }
        params.put("auditFlag", auditFlag);
        PageResult page = merchantRemitLogService.listMallOpenPage(params);
        return RespData.ok(page);
    }
}
