package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.member.MemberInfoModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 商户管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/wallet")
@Slf4j
public class MerchantWalletInfoController extends BaseController {

    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;


    /**
     * 通过ID查询
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public RespData getById(@PathVariable Long id) {
        MerchantWalletInfoModel info = merchantWalletInfoService.getWalletInfo(id);
        return RespData.ok(info);
    }

    /**
     * 添加
     *
     * @param memberInfoModel
     * @return R
     */
    @PostMapping("/add")
    public RespData insert(@RequestBody MemberInfoModel memberInfoModel) {

        return RespData.ok();
    }

    /**
     * 修改
     *
     * @param memberInfoModel
     * @return R
     */
    @PostMapping("/update")
    public RespData updateById(@RequestBody MemberInfoModel memberInfoModel) {
        return RespData.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public RespData deleteById(@PathVariable Long id) {
        return RespData.ok();
    }

    /**
     * 分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        params.put("merchantInfo", merchantInfo);
        return RespData.ok(null);
    }

    /**
     * 获取商户钱包帐户信息
     *
     * @return
     */
    @GetMapping("/getWalletInfo")
    public RespData getWalletInfo() {
        Long mId = this.getMerchantInfo().getId();
        MerchantWalletInfoModel info = merchantWalletInfoService.getWalletInfo(mId);
        return RespData.ok(info);
    }


}
