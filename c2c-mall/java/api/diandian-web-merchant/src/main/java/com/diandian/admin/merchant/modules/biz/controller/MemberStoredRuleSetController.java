package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.member.MemberStoredRuleSetModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.service.member.MemberStoredRuleSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 商户管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/memberStoredSet")
@Slf4j
public class MemberStoredRuleSetController extends BaseController {

    @Autowired
    private MemberStoredRuleSetService memberStoredRuleSetService;

    @GetMapping("/{id}")
    public RespData getById(@PathVariable Long id) {
        MemberStoredRuleSetModel memberStoredRuleSetModel = memberStoredRuleSetService.getSetById(id);
        return RespData.ok(memberStoredRuleSetModel);
    }


    /**
     * 添加
     *
     * @param model
     * @return R
     */
    @PostMapping("/add")
    public RespData insert(@RequestBody MemberStoredRuleSetModel model) {
        Long merchantInfoId = getMerchantInfoId();
        model.setMerchantId(merchantInfoId);
        memberStoredRuleSetService.insertSet(model);
        return RespData.ok();

    }

    /**
     * 修改
     *
     * @param model
     * @return R
     */
    @PostMapping("/update")
    public RespData updateById(@RequestBody MemberStoredRuleSetModel model) {
        memberStoredRuleSetService.updateSet(model);
        return RespData.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
//    @GetMapping("/delete/{id}")
//    public RespData deleteById(@PathVariable Long id) {
//        return RespData.ok();
//    }

    /**
     * 分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @RequestMapping("/list")
    public RespData list(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = getMerchantInfo();
        Long merchantInfoId = merchantInfo.getId();
        List<MemberStoredRuleSetModel> list = memberStoredRuleSetService.listSets(params, merchantInfoId);
        return RespData.ok(list);
    }

}
