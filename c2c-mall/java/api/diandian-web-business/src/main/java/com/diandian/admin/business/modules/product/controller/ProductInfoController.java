package com.diandian.admin.business.modules.product.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author x
 * @date 2019-02-15
 */
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController extends BaseController {

    @Autowired
    private ProductInfoService productInfoService;


    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @RequestMapping("/listPage")
    @RequiresPermissions("productInfo:list")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(productInfoService.listPageBackend(params));
    }

    /**
     * 分页查询(商户)
     *
     * @param params
     * @return
     */
    @RequestMapping("/listPageByShopId")
    public RespData listPageByShopId(@RequestParam Map<String, Object> params) {
        return RespData.ok(productInfoService.listPageByShopId(params));
    }

    /**
     * 通过ID获取商品
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    @RequiresPermissions("productInfo:list")
    public RespData getById(Long id) {
        return RespData.ok(productInfoService.getById(id));
    }

    /**
     * 通过ID更新商品
     *
     * @param productInfoModel
     * @return
     */
    @PostMapping("/updateById")
    @RequiresPermissions("productInfo:update")
    public RespData updateById(@RequestBody ProductInfoModel productInfoModel) {
        productInfoModel.setUpdateBy(super.getUserId());
        productInfoService.updateById(productInfoModel);
        return RespData.ok();
    }

    /**
     * 通过ID批量更新状态 上下架
     *
     * @param idList
     * @param state
     * @return
     */
    @PostMapping("/updateStateByIdBatch")
    @RequiresPermissions("productInfo:update")
    public RespData updateStateByIdBatch(@RequestParam("idList") List<Long> idList, Integer state) {
        productInfoService.updateStateByIdBatch(idList, state, super.getUserId());
        return RespData.ok();
    }

    /**
     * 新增保存商品
     *
     * @param productInfoModel
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("productInfo:add")
    public RespData save(@RequestBody ProductInfoModel productInfoModel) {
        productInfoModel.setCreateBy(super.getUserId());
        productInfoModel.setUpdateBy(super.getUserId());
        productInfoService.save(productInfoModel);
        return RespData.ok();
    }

    /**
     * 逻辑删除商品
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    @RequiresPermissions("productInfo:delete")
    public RespData deleteById(Long id) {
        productInfoService.logicDeleteById(id, super.getUserId());
        return RespData.ok();
    }

    /**
     * 通过ID批量修改商品的折扣
     *
     * @param idList
     * @param discount
     * @return
     */
    @PostMapping("/batchUpdateDiscountById")
    @RequiresPermissions("productInfo:update")
    public RespData batchUpdateDiscountById(@RequestParam("idList") List<Long> idList, BigDecimal discount) {
        productInfoService.batchUpdateDiscountById(idList, discount, super.getUserId());
        return RespData.ok();
    }

    /**
     *
     * 功能描述: 批量修改商品标签
     *
     * @param idList
     * @param tag
     * @param flag
     * @return
     * @author cjunyuan
     * @date 2019/5/29 16:05
     */
    @PostMapping("/batchUpdateTagById")
    @RequiresPermissions("productInfo:update")
    public RespData batchUpdateTagById(@RequestParam("idList") List<Long> idList, @RequestParam String tag, @RequestParam Integer flag) {
        productInfoService.batchUpdateTagById(idList, tag, flag, super.getUserId());
        return RespData.ok();
    }

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @RequestMapping("/listPageByShop")
    public RespData listPageByShop(@RequestParam Map<String, Object> params) {
        return RespData.ok(productInfoService.getListPageByShopId(params));
    }

}
