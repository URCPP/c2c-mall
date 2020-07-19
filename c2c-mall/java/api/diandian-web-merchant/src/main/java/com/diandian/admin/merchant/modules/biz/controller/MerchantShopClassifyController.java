package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.service.merchant.MerchantShopClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 19:37 2019/9/26
 * @Modified By:
 */
@RestController
@RequestMapping("/biz/classify")
@Slf4j
public class MerchantShopClassifyController {
    @Autowired
    private MerchantShopClassifyService merchantShopClassifyService;

    /**
     * app前端获取一级类目
     */

    @GetMapping("/findOneCategory")
    public RespData findOneCategory(){
        return RespData.ok(merchantShopClassifyService.listOne());
    }

    /**
     * app前端获取二级类目
     */
    @GetMapping("/findTwoCategory")
    public  RespData findTwoCategory(Long id){
        return RespData.ok(merchantShopClassifyService.listTwo(id));
    }


}
