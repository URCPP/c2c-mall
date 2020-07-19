package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.service.biz.BizOpenDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author jbuhuan
 * @Date 2019/3/20 10:39
 */
@RestController
@RequestMapping("/biz/openDetail")
@Slf4j
public class BizOpenDetailController extends BaseController {
    @Autowired
    private BizOpenDetailService bizOpenDetailService;
    /**
     * 列表
     * @param query
     * @return
     */
    @PostMapping("/listPage")
    public RespData list(@RequestBody OrgAccountQueryDTO query) {
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            query.setOrgId(orgId);
        }
        PageResult page = bizOpenDetailService.listPage(query);
        return RespData.ok(page);
    }
}
