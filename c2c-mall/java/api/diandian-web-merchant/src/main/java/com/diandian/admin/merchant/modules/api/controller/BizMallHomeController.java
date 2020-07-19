package com.diandian.admin.merchant.modules.api.controller;

import cn.hutool.core.util.ObjectUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.sys.service.ShiroService;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.member.MerchantTokenModel;
import com.diandian.dubbo.facade.model.product.ProductCategoryModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.service.biz.BizMallNewsService;
import com.diandian.dubbo.facade.service.biz.BizNotifyInfoService;
import com.diandian.dubbo.facade.service.biz.HotKeywordService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.product.ProductAdService;
import com.diandian.dubbo.facade.service.product.ProductBrandService;
import com.diandian.dubbo.facade.service.product.ProductCategoryService;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mall")
public class BizMallHomeController extends BaseController {

    @Autowired
    BizMallNewsService bizMallNewsService;
    @Autowired
    BizNotifyInfoService bizNotifyInfoService;
    @Autowired
    ProductAdService productAdService;
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ProductInfoService productInfoService;
    @Autowired
    HotKeywordService hotKeywordService;

    @Autowired
    private ProductBrandService productBrandService;
    @Autowired
    private ShiroService shiroService;



    @GetMapping("/news/list")
    public RespData listNews(@RequestParam Map<String, Object> params) {
        params.put("is_show",BizConstant.STATE_DISNORMAL);
        PageResult pageResult = bizMallNewsService.listPage(params);
        return RespData.ok(pageResult);
    }

    @GetMapping("/notify/list")
    public RespData listNotify(@RequestParam Map<String, Object> params) {
        PageResult pageResult = bizNotifyInfoService.listPage(params);
        return RespData.ok(pageResult);
    }


    @GetMapping("/productAd/list")
    public RespData list(@RequestParam Map<String, Object> params) {
        return RespData.ok(productAdService.list(params));
    }


    /**
     * 产品类型
     *
     * @return
     */
    @GetMapping("/productCategory/list")
    public RespData listProductCategory() {
        List<ProductCategoryModel> list = productCategoryService.list();
        return RespData.ok(list);
    }

    /**
     * 商品列表
     *
     * @return
     */
    @GetMapping("/productInfo/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        //状态 99=>下架,1=>上架,2=>预售,0=>未上架
        params.put("state",1);
        PageResult pageResult = productInfoService.listPage(params);
        return RespData.ok(pageResult);
    }

    /**
     * 热搜关键字
     */
    @GetMapping("/hotKeyword")
    public RespData hotKeyword(@RequestParam Map<String, Object> params){
        params.put("state",1);
        PageResult pageResult = hotKeywordService.listPage(params);
        return RespData.ok(pageResult);
    }

    /**
     * 排行数量统计
     * @return
     */
    @GetMapping("/countRangeNum")
    public RespData countRangeNum() {
        return RespData.ok(productInfoService.countRangeNum());
    }

    /**
     *
     * 功能描述: 分页品牌列表
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/5/7 20:50
     */
    @GetMapping("/productBrand/listPage")
    public RespData listProductBrandPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(productBrandService.listPage(params));
    }

    /**
     *
     * 功能描述: 品牌列表
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/5/7 20:51
     */
    @GetMapping("/productBrand/list")
    public RespData listProductBrand(Map<String, Object> params) {
        return RespData.ok(productBrandService.list(params));
    }

    @RequestMapping("/getProductByIds")
    public RespData getProductByIds(@RequestBody List<Long> ids){
        return RespData.ok(productInfoService.getByIds(ids));
    }

}
