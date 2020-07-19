package com.diandian.admin.business.modules.sys.controller;

import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.business.modules.sys.service.SysRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysRoleModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.sys.OrgFormDTO;
import com.diandian.dubbo.facade.dto.sys.OrgQueryDTO;
import com.diandian.dubbo.facade.dto.sys.OrgTypeDTO;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeMenuService;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeService;
import com.diandian.dubbo.facade.vo.OrgListVO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjunyuan
 * @date 2019/2/15 16:14
 */
@RestController
@RequestMapping("/sys")
public class SysOrgController extends BaseController {

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private SysOrgTypeService sysOrgTypeService;

    @Autowired
    private SysOrgTypeMenuService sysOrgTypeMenuService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 机构树
     */
    @GetMapping("/org/tree")
    @RequiresPermissions("sys:org:list")
    public RespData orgTree(@RequestParam Map<String, Object> params) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && orgId > 0){
            if(!SysConstant.ROOT_ORG.equals(orgTypeId)){
                params.put("parentId", orgId);
            }
            List<Long> excludeList = new ArrayList<>();
            excludeList.add(orgId);
            params.put("excludeList", excludeList);
        }
        PageResult page =  sysOrgService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取
     */
    @GetMapping("/org/{id}")
    @RequiresPermissions("sys:org:list")
    public RespData getOrg(@PathVariable Long id) {
        return RespData.ok(sysOrgService.getOrgDetailVO(id));
    }

    /**
     * 保存
     */
    @PostMapping("/org/add")
    @RequiresPermissions("sys:org:add")
    public RespData addOrg(@RequestBody OrgFormDTO org) {
        if(null == org.getParentId() || org.getParentId() == 0){
            SysUserModel user = getUser();
            Long orgId = user.getOrgId();
            Long orgTypeId = user.getOrgTypeId();
            if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
                org.setParentId(orgId);
            }
        }
        sysOrgService.insertOrg(org);
        return RespData.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/org/update")
    @RequiresPermissions("sys:org:update")
    public RespData updateOrg(@RequestBody OrgFormDTO org) {
        if(null == org.getParentId() || org.getParentId() == 0){
            SysUserModel user = getUser();
            Long orgId = user.getOrgId();
            Long orgTypeId = user.getOrgTypeId();
            if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
                org.setParentId(orgId);
            }
        }
        sysOrgService.updateOrgById(org);
        return RespData.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/org/updateSelf")
    @RequiresPermissions("sys:org:updateSelf")
    public RespData updateSelfOrg(@RequestBody OrgFormDTO org) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(!SysConstant.ROOT_ORG.equals(orgTypeId)){
            AssertUtil.isTrue(org.getOrgId().equals(orgId), "您没有修改其它机构信息的权限");
        }
        SysOrgModel oldOrg = sysOrgService.getById(org.getOrgId());
        AssertUtil.notNull(oldOrg, "修改的机构信息不存在");
        org.setRecommendId(null);
        org.setParentId(null);
        org.setOrgTypeId(oldOrg.getOrgTypeId());
        org.setState(null);
        if(!MerchantConstant.MerchantApproveState.APPROVED.value().equals(oldOrg.getApproveFlag())){
            org.setApproveFlag(MerchantConstant.MerchantApproveState.APPROVEING.value());
        }else {
            org.setBusinessLicenseCode(null);
            org.setIdcard(null);
            org.setBusinessLicensePicBase64(null);
            org.setIdcardPositivePicBase64(null);
            org.setIdcardReversePicBase64(null);
        }
        sysOrgService.updateOrgById(org);
        return RespData.ok();
    }

    /**
     * 机构类型分页
     */
    @GetMapping("/orgType/listPage")
    @RequiresPermissions("sys:orgType:list")
    public RespData orgTypeListPage(@RequestParam Map<String, Object> params) {
        PageResult page = sysOrgTypeService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 机构类型列表
     */
    @PostMapping("/orgType/list")
    @RequiresPermissions("sys:orgType:list")
    public RespData orgTypeList(@RequestBody(required = false) OrgTypeDTO dto) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null == dto || null == dto.getLevel() || null == dto.getLevel().getValue() || null == dto.getLevel().getCompare()){
            dto = new OrgTypeDTO();
            OrgTypeDTO.Level level = new OrgTypeDTO.Level();
            if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
                SysOrgModel org = sysOrgService.getById(getUser().getOrgId());
                AssertUtil.notNull(org, "参数错误");
                SysOrgTypeModel orgType = sysOrgTypeService.getById(org.getOrgTypeId());
                AssertUtil.notNull(orgType, "参数错误");
                level.setValue(orgType.getLevel());
                level.setCompare(BizConstant.CompareType.GT.value());
            }
        }
        dto.setDelFlag(SysConstant.STATUS_NORMAL);
        List<SysOrgTypeModel> list = sysOrgTypeService.list(dto);
        return RespData.ok(list);
    }

    /**
     * 保存
     */
    @PostMapping("/orgType/add")
    @RequiresPermissions("sys:orgType:add")
    public RespData addOrgType(@RequestBody SysOrgTypeModel orgType) {
        sysOrgTypeService.insertOrgType(orgType);
        return RespData.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/orgType/update")
    @RequiresPermissions("sys:orgType:update")
    public RespData updateOrgType(@RequestBody SysOrgTypeModel orgType) {
        sysOrgTypeService.updateOrgType(orgType);
        return RespData.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/orgType/delete/{orgTypeId}")
    @RequiresPermissions("sys:orgType:delete")
    public RespData deleteOrgType(@PathVariable("orgTypeId") long orgTypeId) {
        //逻辑删除
        SysOrgTypeModel orgType = sysOrgTypeService.getById(orgTypeId);
        orgType.setDelFlag(BizConstant.STATE_DISNORMAL);
        sysOrgTypeService.updateOrgType(orgType);
        return RespData.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/orgType/{orgTypeId}")
    @RequiresPermissions("sys:orgType:list")
    public RespData getOrgType(@PathVariable("orgTypeId") long orgTypeId) {
        SysOrgTypeModel orgType = sysOrgTypeService.getById(orgTypeId);
        AssertUtil.notNull(orgType, "机构类型不存在");
        orgType.setMenuIdList(sysOrgTypeMenuService.queryMenuIdListByOrgTypeId(orgTypeId));
        return RespData.ok(orgType);
    }

    /**
     * 机构树
     */
    @GetMapping("/org/getRecommendOrg")
    @RequiresPermissions("sys:org:list")
    public RespData getRecommendOrg(@RequestParam Map<String, Object> params) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            params.put("recommendId", orgId);
        }
        PageResult page =  sysOrgService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 机构树
     */
    @GetMapping("/org/exportExcel")
    @RequiresPermissions("sys:org:exportExcel")
    public void exportExcelOrgInfo(@RequestParam(value = "startTime", required = false) String startTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "approveFlag", required = false) Integer approveFlag,
                                   ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        TemplateExportParams exportParams = new TemplateExportParams("doc/org.xlsx");
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("approveFlag", approveFlag);
        List<Map<String, Object>> mapList = sysOrgService.getExportOrgList(params);
        res.put("orgList",mapList);
        modelMap.put(TemplateExcelConstants.FILE_NAME, "机构信息");
        modelMap.put(TemplateExcelConstants.PARAMS, exportParams);
        modelMap.put(TemplateExcelConstants.MAP_DATA, res);
        PoiBaseView.render(modelMap, request, response, TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
    }
}
