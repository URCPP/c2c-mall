package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductSkuService;
import com.diandian.dubbo.facade.vo.SkuVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantProductInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jbuhuan
 * @date 2019/2/21 15:22
 */
@RestController
@RequestMapping("/biz/merchantProductInfo")
@Slf4j
public class MerchantProductInfoController extends BaseController {
    @Autowired
    private MerchantProductInfoService merchantProductInfoService;

    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private ProductInfoService productInfoService;


    /**
     * @Author wubc
     * @Description //TODO 商家 预售商品列表、分页
     * @Date 21:47 2019/4/3
     * @Param [params]
     * @return com.diandian.admin.common.util.RespData
     **/
    @GetMapping("/listPage")
    public RespData listProductInfo(@RequestParam Map<String, Object> params) {
        params.put("merchantId", this.getMerchantInfoId());
        PageResult pageResult = merchantProductInfoService.listProductInfo(params);
        return RespData.ok(pageResult);
    }

    /**
     * 商户的兑换商品
     *
     * @param params
     * @return
     */
    @GetMapping("/listSkuPage")
    public RespData listSkuPage(@RequestParam Map<String, Object> params) {
        Long merchantId = getMerchantInfoId();
        params.put("merchantId", merchantId);
        PageResult pageResult = productSkuService.listSkuPage(params);
        return RespData.ok(pageResult);
    }

    /**
     * 单个SKU
     *
     * @param id
     * @return
     */
    @GetMapping("/getBySkuId/{id}")
    public RespData getBySkuId(@PathVariable Long id) {
        SkuVO vo = productSkuService.getBySkuId(id);
        return RespData.ok(vo);
    }

    /**
     * 批量下架
     *
     * @param ids
     * @return
     */
    @PostMapping("/updateState")
    @Transactional(rollbackFor = Exception.class)
    public RespData updateProductState(@RequestBody Long[] ids) {
        if ((ids != null && ids.length == 0) || ids == null) {
            return RespData.fail("没有选中下架的商品!");
        }
        for (Long id : ids) {
            MerchantProductInfoModel product = merchantProductInfoService.getProduct(id);
            product.setProductStateFlag(0);
            merchantProductInfoService.updateProduct(product);
        }
        return RespData.ok();
    }

    /**
     * 上下架
     *
     * @param id
     * @return
     */
    @RequestMapping("/update/{id}")
    public RespData updateProduct(@PathVariable Long id) {
        MerchantProductInfoModel product = merchantProductInfoService.getProduct(id);
        AssertUtil.notBlank(product.getTransportIds(), "无法上架运输方式为空的商品");
        Integer stateFlag = product.getProductStateFlag();
        product.setProductStateFlag(stateFlag == 0 ? 1 : 0);
        merchantProductInfoService.updateProduct(product);
        return RespData.ok();
    }

    /**
     * 修改排序和兑换价
     *
     * @param productInfoVO
     * @return
     */
    @PostMapping("/update")
    public RespData update(@RequestBody MerchantProductInfoVO productInfoVO) {
        Long id = productInfoVO.getId();
        MerchantProductInfoModel product = merchantProductInfoService.getProduct(id);
        AssertUtil.notNull(product, "商品信息不存在");
        product = new MerchantProductInfoModel();
        product.setId(id);
        product.setExchangePrice(productInfoVO.getExchangePrice());
        product.setSort(productInfoVO.getSort());
        product.setTransportIds(productInfoVO.getTransportIds());
        merchantProductInfoService.updateProduct(product);
        return RespData.ok();
    }

   /**
    * @Author wubc
    * @Description //TODO 添加商户预售商品
    * @Date 20:57 2019/4/3
    * @Param [sku]
    * @return com.diandian.admin.common.util.RespData
    **/
    @PostMapping("/saveMerProduct")
    public RespData saveMerProduct(@RequestBody SkuVO sku) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        merchantProductInfoService.saveMerProduct(merchantInfo, sku);
        return RespData.ok();
    }

    /**
     * 批量添加商户预售商品
     *
     * @return
     */
    @PostMapping("/saveProducts")
    @Transactional(rollbackFor = Exception.class)
    public RespData saveProducts(@RequestBody List<SkuVO> products) {
        AssertUtil.isTrue(products != null && products.size() > 0, "未选中商品");
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        merchantProductInfoService.batchSaveMerProduct(merchantInfo, products);
        return RespData.ok();
    }

}
