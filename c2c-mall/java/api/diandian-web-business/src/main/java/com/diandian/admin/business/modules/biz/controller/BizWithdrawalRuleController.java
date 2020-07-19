package com.diandian.admin.business.modules.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.dubbo.facade.service.biz.BizWithdrawalRuleService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalRuleModel;
import com.diandian.dubbo.facade.dto.PageResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 提现规则
 */
@RestController
@RequestMapping("/biz/withdrawalRule")
public class BizWithdrawalRuleController extends BaseController {

    @Autowired
    BizWithdrawalRuleService bizWithdrawalRuleService;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:withdrawalRule:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizWithdrawalRuleService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取对象
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:withdrawalRule:list")
    public RespData getById(@PathVariable Long id) {
        return RespData.ok(bizWithdrawalRuleService.getById(id));
    }


    /**
     * 更新
     * @param bizWithdrawalRuleModel
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("biz:withdrawalRule:update")
    public RespData updateById(@RequestBody BizWithdrawalRuleModel bizWithdrawalRuleModel) {
        BizWithdrawalRuleModel ruleModel = bizWithdrawalRuleService.getByName(bizWithdrawalRuleModel.getName());
        if (ruleModel.getId().compareTo(bizWithdrawalRuleModel.getId()) != 0){
            AssertUtil.isNull(ruleModel,"提现规则名称已存在!");
        }
        // 启动状态为启动，更新所有状态为禁用
        if (bizWithdrawalRuleModel.getState() == SysConstant.IS_YES){
            BizWithdrawalRuleModel withdrawalRuleModel = new BizWithdrawalRuleModel();
            withdrawalRuleModel.setState(SysConstant.IS_NO);
            bizWithdrawalRuleService.update(withdrawalRuleModel,new QueryWrapper<>());
        }
        bizWithdrawalRuleService.updateById(bizWithdrawalRuleModel);
        return RespData.ok();
    }

    /**
     * 新增
     * @param bizWithdrawalRuleModel
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("biz:withdrawalRule:add")
    public RespData save(@RequestBody BizWithdrawalRuleModel bizWithdrawalRuleModel) {
        BizWithdrawalRuleModel ruleModel = bizWithdrawalRuleService.getByName(bizWithdrawalRuleModel.getName());
        AssertUtil.isNull(ruleModel,"提现规则名称已存在!");
        //启动状态为启动，更新所有状态为禁用
        if (bizWithdrawalRuleModel.getState() == SysConstant.IS_YES){
            BizWithdrawalRuleModel withdrawalRuleModel = new BizWithdrawalRuleModel();
            withdrawalRuleModel.setState(SysConstant.IS_NO);
            bizWithdrawalRuleService.update(withdrawalRuleModel,new QueryWrapper<>());
        }
        bizWithdrawalRuleService.save(bizWithdrawalRuleModel);
        return RespData.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    @RequiresPermissions("biz:withdrawalRule:delete")
    public RespData deleteById(@PathVariable  Long id) {
        bizWithdrawalRuleService.removeById(id);
        return RespData.ok();
    }

}
