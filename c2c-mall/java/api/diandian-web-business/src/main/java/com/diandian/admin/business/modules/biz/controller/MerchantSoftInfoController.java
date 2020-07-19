package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.merchant.MerchantSoftInfoDTO;
import com.diandian.dubbo.facade.service.merchant.MerchantSoftInfoService;
import com.diandian.dubbo.facade.vo.merchant.MerchantSoftInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/3/6 16:26
 */
@RestController
@RequestMapping("/biz/merchantSoftInfo")
@Slf4j
public class MerchantSoftInfoController extends BaseController {

    @Autowired
    private MerchantSoftInfoService merchantSoftInfoService;

    /**
     * 获取商户的软件信息列表
     *
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("biz:merchantSoftInfo:list")
    public RespData listByMerchantId(Long mchId) {
        List<MerchantSoftInfoVO> list = merchantSoftInfoService.listByMerchantId(mchId);
        return RespData.ok(list);
    }

    /**
     * 保存商户软件信息
     * @author cjunyuan
     * @date 2019/3/6 13:48
     */
    @PostMapping("/batchSave/{mchId}")
    @RequiresPermissions("biz:merchantSoftInfo:save")
    public RespData batchSaveMerchantSoft(@PathVariable("mchId") Long mchId, @RequestBody List<MerchantSoftInfoDTO> list) {
        merchantSoftInfoService.batchSaveMerchantSoftInfo(mchId, list);
        return RespData.ok();
    }

    /**
     * 回收商户软件权益
     * @author cjunyuan
     * @date 2019/3/6 13:48
     */
    @PostMapping("/recycle/{mchId}")
    @RequiresPermissions("biz:merchantSoftInfo:save")
    public RespData recycleOrgStrategy(@PathVariable("mchId") Long mchId, @RequestBody MerchantSoftInfoDTO dto) {
        merchantSoftInfoService.recycleSoftStrategy(mchId, dto);
        return RespData.ok();
    }

    /**
     * 赠送商户软件权益
     * @author cjunyuan
     * @date 2019/3/6 13:48
     */
    @PostMapping("/gift/{mchId}")
    @RequiresPermissions("biz:merchantSoftInfo:save")
    public RespData giftOrgStrategy(@PathVariable("mchId") Long mchId, @RequestBody MerchantSoftInfoDTO dto) {
        merchantSoftInfoService.giftSoftStrategy(mchId, dto);
        return RespData.ok();
    }
}
