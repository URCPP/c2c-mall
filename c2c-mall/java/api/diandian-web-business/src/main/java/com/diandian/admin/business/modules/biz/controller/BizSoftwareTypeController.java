package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.model.merchant.MerchantSoftInfoModel;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantSoftInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 软件类型
 */
@RestController
@RequestMapping("/biz/softwareType")
public class BizSoftwareTypeController extends BaseController {

    @Autowired
    BizSoftwareTypeService bizSoftwareTypeService;
    @Autowired
    MerchantSoftInfoService merchantSoftInfoService;
    @Autowired
    MerchantInfoService merchantInfoService;
    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("biz:softwareType:list")
    public RespData list(@RequestParam Map<String, Object> params) {
        PageResult page = bizSoftwareTypeService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 获取对象
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RequiresPermissions("biz:softwareType:list")
    public RespData getById(@PathVariable Long id) {
        return RespData.ok(bizSoftwareTypeService.getSoftTypeById(id));
    }


    /**
     * 更新
     * @param bizSoftwareTypeModel
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("biz:softwareType:update")
    public RespData updateById(@RequestBody BizSoftwareTypeModel bizSoftwareTypeModel) {
        bizSoftwareTypeService.updateById(bizSoftwareTypeModel);
        return RespData.ok();
    }

    /**
     * 新增
     * @param bizSoftwareTypeModel
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("biz:softwareType:add")
    @Transactional(rollbackFor = Exception.class)
    public RespData save(@RequestBody BizSoftwareTypeModel bizSoftwareTypeModel) {
        bizSoftwareTypeService.save(bizSoftwareTypeModel);
        Long id = bizSoftwareTypeModel.getId();
        List<Long> list = merchantInfoService.queryMerchantIdList(new MerchantInfoQueryDTO());
        if (list != null && list.size()>0) {
            for (Long merchantId : list) {
                MerchantSoftInfoModel softInfo = merchantSoftInfoService.getSoftTypeByIdAndMerchantId(merchantId, id);
                if (softInfo == null) {
                    softInfo = new MerchantSoftInfoModel();
                    softInfo.setMerchantId(merchantId);
                    softInfo.setSoftTypeId(id);
                    softInfo.setAvailableOpenNum(0);
                    softInfo.setOpenNum(0);
                    merchantSoftInfoService.saveSoftAcc(softInfo);
                }
            }
        }
        return RespData.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    @RequiresPermissions("biz:softwareType:delete")
    public RespData deleteById(@PathVariable  Long id) {
        //逻辑删除
        BizSoftwareTypeModel bizSoftwareTypeModel = bizSoftwareTypeService.getSoftTypeById(id);
        bizSoftwareTypeModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        bizSoftwareTypeService.updateById(bizSoftwareTypeModel);
        return RespData.ok();
    }

    /**
     * 列表
     * @return
     */
    @RequestMapping("/list")
    //@RequiresPermissions("biz:softwareType:list")
    public RespData list() {
        List<BizSoftwareTypeModel> dataList = bizSoftwareTypeService.listSoftType();
        return RespData.ok(dataList);
    }

}
