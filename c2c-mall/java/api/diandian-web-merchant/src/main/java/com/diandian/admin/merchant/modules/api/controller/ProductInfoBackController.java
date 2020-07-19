package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 14:53 2019/11/2
 * @Modified By:
 */

@RequestMapping("productInfoBack")
@RestController
@Slf4j
public class ProductInfoBackController extends BaseController {
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 前台商品管理分页展示
     *
     * @param params
     * @return
     */
    @GetMapping("listPage")
    public RespData list(@RequestParam Map<String, Object> params) {
//        params.put("merchantId",getMerchantInfoId());
        PageResult pageResult = productInfoService.listBackend(params);
        return RespData.ok(pageResult);
    }

    /**
     * 获取单个商品
     *
     * @param id
     * @return
     */
    @GetMapping("getById")
    public RespData getById(Long id) {
        return RespData.ok(productInfoService.getProductById(id));
    }


    /**
     * 新增保存商品
     *
     * @param productInfoModel
     * @return
     */
    @PostMapping("/appProductSave")
    public RespData appProductSave(@RequestBody ProductInfoModel productInfoModel) {
        productInfoModel.setCreateBy(getMerchantInfoId());
        productInfoModel.setUpdateBy(getMerchantInfoId());
        productInfoService.addProductSave(productInfoModel);
        return RespData.ok();
    }


    /**
     * 更新商品
     *
     * @param productInfoModel
     * @return
     */

    @PostMapping("/updateProductById")
    public RespData updateProductById(@RequestBody ProductInfoModel productInfoModel) {
        productInfoModel.setUpdateBy(getMerchantInfoId());
        productInfoService.updateProduct(productInfoModel);
        return RespData.ok();
    }


    /**
     * 修改上下架状态
     *
     * @param id
     * @param state
     * @return
     */

    @PostMapping("updateState")
    public RespData updateState(Long id, Integer state) {
        Long merchantId = getMerchantInfoId();
        productInfoService.updateState(id, state, merchantId);
        return RespData.ok();
    }

    /**
     * 逻辑删除商品
     *
     * @param id
     * @return
     */
    @PostMapping("deleteById")
    public RespData deleteById(Long id) {
        Long merchantId = getMerchantInfoId();
        productInfoService.logicDeleteById(id, merchantId);
        return RespData.ok();
    }


}

