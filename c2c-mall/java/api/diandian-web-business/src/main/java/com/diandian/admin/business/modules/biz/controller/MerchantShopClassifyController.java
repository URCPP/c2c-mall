package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.merchant.MerchantShopClassifyModel;
import com.diandian.dubbo.facade.service.merchant.MerchantShopClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 17:57 2019/9/24
 * @Modified By:
 */

@RestController
@RequestMapping("/classify")
@Slf4j
public class MerchantShopClassifyController extends BaseController {

    @Autowired
    private MerchantShopClassifyService merchantShopClassifyService;


    @GetMapping("/listOneCategory")
    public RespData ShopClassifyListOne(){
        List<MerchantShopClassifyModel> list=merchantShopClassifyService.listOne();
        return RespData.ok(list);
    }

    @GetMapping("/listTwoCategory")
    public RespData ShopClassifyListTwo(Long id){
        List<MerchantShopClassifyModel> list=merchantShopClassifyService.listTwo(id);
        return RespData.ok(list);
    }

    @GetMapping("/listPage")
    public RespData ShopClassifyPage(@RequestParam Map<String, Object> params){
        PageResult pageResult = merchantShopClassifyService.listPage(params);
        return RespData.ok(pageResult);
    }

    @GetMapping("/getById")
    public RespData getClassifyById(Long id){
        return RespData.ok(merchantShopClassifyService.getCategoryById(id));
    }

    @PostMapping("/addClassify")
    public RespData addClassify(@RequestBody MerchantShopClassifyModel mer){
        if(null!=mer.getCategoryType() && mer.getCategoryType()==0){
            mer.setCategoryParent(0L);
        }else if(null!=mer.getCategoryType() &&mer.getCategoryType()==1){
            mer.setPlatformUseFee(mer.getPlatformUseFee().divide(BigDecimal.valueOf(100)));
            mer.setAnnualPromotionFee(mer.getAnnualPromotionFee().divide(BigDecimal.valueOf(100)));
            mer.setTechnologyServiceFee(mer.getTechnologyServiceFee().divide(BigDecimal.valueOf(100)));
        }
        merchantShopClassifyService.save(mer);
        return RespData.ok();
    }

    @PostMapping("/deleteById/{id}")
    public RespData deleteById(@PathVariable Long id){
        merchantShopClassifyService.deleteById(id);
        return RespData.ok();
    }


    @PostMapping("updateById")
    public RespData updateById(@RequestBody MerchantShopClassifyModel mer){
        merchantShopClassifyService.updateById(mer);
        return  RespData.ok();
    }
}
