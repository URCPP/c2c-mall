package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.OrgPrivilegeDTO;
import com.diandian.dubbo.facade.dto.biz.OrgPrivilegeQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOrgTypeStrategyModel;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.service.biz.BizOrgPrivilegeService;
import com.diandian.dubbo.facade.service.biz.BizOrgTypeStrategyService;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeService;
import com.diandian.dubbo.facade.vo.OrgPrivilegeListVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 软件类型
 */
@RestController
@RequestMapping("/biz")
public class BizOrgStrategyController extends BaseController {

    @Autowired
    BizOrgTypeStrategyService bizOrgTypeStrategyService;
    @Autowired
    SysOrgTypeService sysOrgTypeService;
    @Autowired
    BizSoftwareTypeService bizSoftwareTypeService;
    @Autowired
    private BizOrgPrivilegeService bizOrgPrivilegeService;
    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/orgTypeStrategy/listPage")
    @RequiresPermissions("biz:orgTypeStrategy:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizOrgTypeStrategyService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取对象
     * @param id
     * @return
     */
    @GetMapping("/orgTypeStrategy/{id}")
    @RequiresPermissions("biz:orgTypeStrategy:list")
    public RespData getById(@PathVariable Long id) {
        return RespData.ok(bizOrgTypeStrategyService.getById(id));
    }


    /**
     * 更新
     * @param bizOrgTypeStrategyModel
     * @return
     */
    @PostMapping("/orgTypeStrategy/update")
    @RequiresPermissions("biz:orgTypeStrategy:update")
    public RespData updateById(@RequestBody BizOrgTypeStrategyModel bizOrgTypeStrategyModel) {
        //开通机构
        SysOrgTypeModel sysOrgTypeModel = sysOrgTypeService.getById(bizOrgTypeStrategyModel.getOrgTypeId());
        bizOrgTypeStrategyModel.setOrgTypeName(sysOrgTypeModel.getTypeName());
        //推荐奖励
        bizOrgTypeStrategyModel.setRecommendTypeName(BizConstant.RecommendType.getLabel(bizOrgTypeStrategyModel.getRecommendType()));
        //奖励类型
        bizOrgTypeStrategyModel.setRewardTypeName(BizConstant.RewardType.getLabel(bizOrgTypeStrategyModel.getRewardType()));
        bizOrgTypeStrategyService.updateById(bizOrgTypeStrategyModel);
        return RespData.ok();
    }

    /**
     * 新增
     * @param bizOrgTypeStrategyModel
     * @return
     */
    @PostMapping("/orgTypeStrategy/add")
    @RequiresPermissions("biz:orgTypeStrategy:add")
    public RespData save(@RequestBody BizOrgTypeStrategyModel bizOrgTypeStrategyModel) {
        //开通机构
        SysOrgTypeModel sysOrgTypeModel = sysOrgTypeService.getById(bizOrgTypeStrategyModel.getOrgTypeId());
        bizOrgTypeStrategyModel.setOrgTypeName(sysOrgTypeModel.getTypeName());
        //推荐奖励
        bizOrgTypeStrategyModel.setRecommendTypeName(BizConstant.RecommendType.getLabel(bizOrgTypeStrategyModel.getRecommendType()));
        //奖励类型
        bizOrgTypeStrategyModel.setRewardTypeName(BizConstant.RewardType.getLabel(bizOrgTypeStrategyModel.getRewardType()));
        bizOrgTypeStrategyService.save(bizOrgTypeStrategyModel);
        return RespData.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/orgTypeStrategy/deleteById/{id}")
    @RequiresPermissions("biz:orgTypeStrategy:delete")
    public RespData deleteById(@PathVariable  Long id) {
        bizOrgTypeStrategyService.removeById(id);
        return RespData.ok();
    }

    /**
     * 保存机构权益
     * @author cjunyuan
     * @date 2019/3/6 13:48
     */
    @PostMapping("/orgStrategy/batchSave/{orgId}")
    @RequiresPermissions("biz:orgStrategy:save")
    public RespData batchSaveOrgStrategy(@PathVariable("orgId") Long orgId, @RequestBody List<OrgPrivilegeDTO> list) {
        bizOrgPrivilegeService.saveOrgStrategy(orgId, list);
        return RespData.ok();
    }

    /**
     * 查询当前机构权益（不包括未分配的机构类型和软件类型）
     * @author cjunyuan
     * @date 2019/3/6 13:48
     */
    @PostMapping("/orgStrategy/list")
    @RequiresPermissions("biz:orgStrategy:list")
    public RespData orgStrategyList(@RequestBody OrgPrivilegeQueryDTO dto) {
        List<OrgPrivilegeListVO> list = bizOrgPrivilegeService.queryOrgPrivilegeListVO(dto);
        return RespData.ok(list);
    }

    /**
     *
     * 功能描述: 查询机构权益（包括未分配的机构类型和软件类型）
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/29 10:24
     */
    @GetMapping("/orgStrategy/typeList")
    @RequiresPermissions("biz:orgStrategy:list")
    public RespData getOrgAllTypePrivilege(@RequestParam(required = false) Long orgId){
        Map<String, Object> res = new HashMap<>();
        Integer level = null;
        if(null != orgId && orgId > 0){
            SysOrgModel org = sysOrgService.getById(orgId);
            AssertUtil.notNull(org, "机构信息不存在");
            SysOrgTypeModel orgType = sysOrgTypeService.getById(org.getOrgTypeId());
            level = orgType.getLevel();
        }else {
            SysUserModel user = getUser();
            orgId = user.getOrgId();
            level = user.getLevel();
            if(null == orgId || SysConstant.ROOT_ORG.equals(user.getOrgTypeId())){
                orgId = null;
                level = null;
            }
        }
        res.put("orgType", bizOrgPrivilegeService.queryOrgTypePrivilege(orgId, level));
        res.put("softwareType", bizOrgPrivilegeService.querySoftwareTypePrivilege(orgId));
        return RespData.ok(res);
    }

    /**
     * 回收机构权益
     * @author cjunyuan
     * @date 2019/3/6 13:48
     */
    @PostMapping("/orgStrategy/recycle/{orgId}")
    @RequiresPermissions("biz:orgStrategy:save")
    public RespData recycleOrgStrategy(@PathVariable("orgId") Long orgId, @RequestBody OrgPrivilegeDTO dto) {
        bizOrgPrivilegeService.recycleOrgPrivilege(orgId, dto);
        return RespData.ok();
    }

    /**
     * 赠送机构权益
     * @author cjunyuan
     * @date 2019/3/6 13:48
     */
    @PostMapping("/orgStrategy/gift/{orgId}")
    @RequiresPermissions("biz:orgStrategy:save")
    public RespData giftOrgStrategy(@PathVariable("orgId") Long orgId, @RequestBody OrgPrivilegeDTO dto) {
        bizOrgPrivilegeService.giftOrgPrivilege(orgId, dto);
        return RespData.ok();
    }

}
