package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.biz.BizMallHelpModel;
import com.diandian.dubbo.facade.service.biz.BizMallHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biz/help")
public class BizMallHelpController extends BaseController {

    @Autowired
    BizMallHelpService bizMallHelpService;


    /**
     * @Author wubc
     * @Description // 目录列表
     * @Date 20:55 2019/3/13
     * @Param []
     * @return com.diandian.admin.common.util.RespData
     **/
    @GetMapping("/listCatalog")
    public RespData listCatalog() {
        List<BizMallHelpModel> list = bizMallHelpService.listCataLog();
        return RespData.ok(list);
    }

    /**
     * @Author wubc
     * @Description // 内容列表
     * @Date 20:55 2019/3/13
     * @Param []
     * @return com.diandian.admin.common.util.RespData
     **/
    @GetMapping("/listContent")
    public RespData listContent() {
        List<BizMallHelpModel> list = bizMallHelpService.listContent();
        return RespData.ok(list);
    }

}
