package com.diandian.admin.business.modules.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.business.modules.sys.service.SysUserService;
import com.diandian.admin.business.modules.sys.vo.OrgApplyUserForm;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.ApplyCheckResultDTO;
import com.diandian.dubbo.facade.dto.biz.OrgApplyInfoDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantOpenApplyDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantOpenApplyLogQueryDTO;
import com.diandian.dubbo.facade.dto.sys.OrgQueryDTO;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.service.biz.BizOrgApplyService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantOpenApplyLogService;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeService;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请审核管理
 * @author cjunyuan
 * @date 2019/2/19 18:23
 */
@RequestMapping("/biz")
@RestController
public class BizApplyController extends BaseController {

    @Autowired
    private BizOrgApplyService bizOrgApplyService;

    @Autowired
    private MerchantOpenApplyLogService merchantOpenApplyLogService;

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysOrgTypeService sysOrgTypeService;

    @RequestMapping("/orgApply/listPage")
    @RequiresPermissions("biz:orgApply:list")
    public RespData orgApplyListPage(@RequestParam Map<String, Object> params) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            params.put("parentId", orgId);
            params.put("recommendId", orgId);
        }
        PageResult pageResult = bizOrgApplyService.orgApplyInfoListPage(params);
        return RespData.ok(pageResult);
    }

    @GetMapping("/orgApply/get")
    @RequiresPermissions("biz:orgApply:list")
    public RespData getOrgApply(@RequestParam Long id) {
        AssertUtil.isTrue(null != id, "参数错误");
        return RespData.ok(bizOrgApplyService.getOrgApplyInfoDetail(id));
    }

    @PostMapping("/orgApply/check")
    @RequiresPermissions("biz:orgApply:check")
    public RespData checkOrgApply(@RequestBody ApplyCheckResultDTO checkResult) {
        checkResult.setAuditUserId(getUserId());
        bizOrgApplyService.checkOrgApplyInfo(checkResult);
        return RespData.ok();
    }

    @PostMapping("/orgApply/updateById")
    @RequiresPermissions("biz:orgApply:update")
    public RespData updateOrgApplyById(@RequestBody OrgApplyInfoDTO dto) {
        SysUserModel curUser = getUser();
        if(null == dto.getParentId() || dto.getParentId() == 0){
            Long orgId = getUser().getOrgId();
            AssertUtil.isTrue(null != orgId && orgId > 0, "请选择商户的上级对象");
            dto.setParentId(orgId);
            dto.setParentType(BizConstant.ObjectType.ORG.value());
        }
        AssertUtil.isTrue(null != dto.getOrgTypeId() && dto.getOrgTypeId() > 0, "机构类型不能为空");
        if(dto.getOrgTypeId().equals(curUser.getOrgTypeId())){
            SysOrgModel recommendOrg = sysOrgService.getById(curUser.getOrgId());
            AssertUtil.notNull(recommendOrg, "机构信息不存在");
            dto.setRecommendId(recommendOrg.getId());
            dto.setRecommendType(BizConstant.ObjectType.ORG.value());
            dto.setParentId(recommendOrg.getParentId());
            dto.setParentType(BizConstant.ObjectType.ORG.value());
        }
        bizOrgApplyService.updateById(dto);
        return RespData.ok();
    }

    @PostMapping("/orgApply/save")
    @RequiresPermissions("biz:orgApply:add")
    public RespData saveOrgApply(@RequestBody OrgApplyInfoDTO dto) {
        SysUserModel curUser = getUser();
        if(null == dto.getParentId() || dto.getParentId() == 0){
            Long orgId = getUser().getOrgId();
            AssertUtil.isTrue(null != orgId && orgId > 0, "请选择商户的上级对象");
            dto.setParentId(orgId);
            dto.setParentType(BizConstant.ObjectType.ORG.value());
        }
        AssertUtil.isTrue(null != dto.getOrgTypeId() && dto.getOrgTypeId() > 0, "机构类型不能为空");
        if(dto.getOrgTypeId().equals(curUser.getOrgTypeId())){
            SysOrgModel recommendOrg = sysOrgService.getById(curUser.getOrgId());
            AssertUtil.notNull(recommendOrg, "机构信息不存在");
            dto.setRecommendId(curUser.getOrgId());
            dto.setRecommendType(BizConstant.ObjectType.ORG.value());
            dto.setParentId(recommendOrg.getParentId());
            dto.setParentType(BizConstant.ObjectType.ORG.value());
        }
        bizOrgApplyService.save(dto);
        return RespData.ok();
    }

    @GetMapping("/orgApply/deleteById")
    @RequiresPermissions("biz:orgApply:delete")
    public RespData deleteOrgApplyById(@RequestParam Long id) {
        bizOrgApplyService.removeById(id);
        return RespData.ok();
    }

    @PostMapping("/orgApply/saveOrUpdateUser")
    public RespData saveOrUpdateUser(@RequestBody OrgApplyUserForm user) {
        sysUserService.saveOrUpdateUserForOrgApply(user);
        return RespData.ok();
    }

    @GetMapping("/orgApply/getOrgUser")
    public RespData getOrgUser(@RequestParam Long orgId) {
        AssertUtil.isTrue(null != orgId && orgId > 0, "机构信息不存在");
        OrgQueryDTO queryDTO = new OrgQueryDTO();
        queryDTO.setId(orgId);
        AssertUtil.isTrue(sysOrgService.count(queryDTO) > 0, "机构信息不存在");
        QueryWrapper<SysUserModel> userWrapper = new QueryWrapper<>();
        userWrapper.eq("org_id", orgId);
        userWrapper.eq("type", 0);
        userWrapper.eq("del_flag", 0);
        SysUserModel user = sysUserService.getOne(userWrapper);
        return RespData.ok(user);
    }

    @GetMapping("/orgApply/close")
    public RespData closeOrgApply(@RequestParam Long id) {
        bizOrgApplyService.closeApply(id);
        return RespData.ok();
    }

    @GetMapping("/orgApply/undo")
    public RespData undoOrgApply(@RequestParam Long id) {
        OrgQueryDTO orgQueryDTO = new OrgQueryDTO();
        orgQueryDTO.setApplyId(id);
        SysOrgModel org = sysOrgService.getOne(orgQueryDTO);
        AssertUtil.notNull(org, "该申请信息无法撤消");
        boolean res = bizOrgApplyService.undoApply(id);
        if(res){
            sysUserService.undoApply(org.getId());
        }
        return RespData.ok();
    }

    @RequestMapping("/softwareApply/listPage")
    @RequiresPermissions("biz:softwareApply:list")
    public RespData softApplyListPage(@RequestBody MerchantOpenApplyLogQueryDTO params) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            params.setParentId(orgId);
        }
        PageResult page = merchantOpenApplyLogService.softApplyInfoListPage(params);
        return RespData.ok(page);
    }

    /*@PostMapping("/softwareApply/save")
    @RequiresPermissions("biz:softwareApply:add")
    public RespData saveSoftApply(@RequestBody @Valid MerchantOpenApplyDTO dto) {
        if(null == dto.getParentId() || dto.getParentId() == 0){
            Long orgId = getUser().getOrgId();
            AssertUtil.isTrue(null != orgId && orgId > 0, "请选择商户的上级对象");
            dto.setParentId(orgId);
            dto.setParentTypeFlag(BizConstant.ObjectType.ORG.value());
        }
        merchantOpenApplyLogService.addMerchantOpenApply(dto);
        return RespData.ok();
    }*/

    @PostMapping("/softwareApply/updateById")
    @RequiresPermissions("biz:softwareApply:update")
    public RespData updateSoftApplyById(@RequestBody MerchantOpenApplyDTO dto) {
        if(null == dto.getParentId() || dto.getParentId() == 0){
            Long orgId = getUser().getOrgId();
            AssertUtil.isTrue(null != orgId && orgId > 0, "请选择商户的上级对象");
            dto.setParentId(orgId);
            dto.setParentTypeFlag(BizConstant.ObjectType.ORG.value());
        }
        merchantOpenApplyLogService.updateById(dto);
        return RespData.ok();
    }

    @GetMapping("/softwareApply/get")
    @RequiresPermissions("biz:softwareApply:list")
    public RespData softApplyGet(@RequestParam Long id) {
        MerchantOpenApplyLogQueryDTO dto = new MerchantOpenApplyLogQueryDTO();
        dto.setId(id);
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            dto.setParentId(orgId);
        }
        return RespData.ok(merchantOpenApplyLogService.getMerchantApplyDetail(dto));
    }

    /*@PostMapping("/softwareApply/check")
    @RequiresPermissions("biz:softwareApply:check")
    public RespData softApplyCheck(@RequestBody ApplyCheckResultDTO checkResult) {
        checkResult.setAuditUserId(getUserId());
        merchantOpenApplyLogService.checkSoftApplyInfo(checkResult);
        return RespData.ok();
    }*/

    @GetMapping("/softwareApply/close")
    public RespData closeSoftwareApply(@RequestParam Long id) {
        merchantOpenApplyLogService.closeApply(id);
        return RespData.ok();
    }

    /*@GetMapping("/softwareApply/undo")
    public RespData undoSoftwareApply(@RequestParam Long id) {
        merchantOpenApplyLogService.undoApply(id);
        return RespData.ok();
    }*/

    /**
     *
     * 功能描述: 获取当前用户权限下的组织树
     *
     * @param type 获取树包含的对象类型，1-机构
     * @return
     * @author cjunyuan
     * @date 2019/2/26 21:29
     */
    @GetMapping("/apply/tree")
    public RespData getIntactTree(@RequestParam(required = false) Integer type) {
        Long orgId = getUser().getOrgId();
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", orgId);
        List<IntactTreeVO> intactTreeOrgPart = sysOrgService.getIntactTreeOrgPart(params);
        if(BizConstant.ObjectType.ORG.value().equals(type)){
            return RespData.ok(intactTreeOrgPart);
        }
        if(BizConstant.ObjectType.SOFTWARE.value().equals(type)){
            return RespData.ok(merchantInfoService.getIntactTreeMerchantPart(null, orgId));
        }
        List<IntactTreeVO> intactTree = merchantInfoService.getIntactTreeMerchantPart(intactTreeOrgPart, null);
        return RespData.ok(intactTree);
    }


}
