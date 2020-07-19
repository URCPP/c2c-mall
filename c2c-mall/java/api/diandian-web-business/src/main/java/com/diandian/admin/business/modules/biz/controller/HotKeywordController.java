package com.diandian.admin.business.modules.biz.controller;


import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.HotKeywordModel;
import com.diandian.dubbo.facade.service.biz.HotKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 商品热搜关键字 前端控制器
 * </p>
 *
 * @author zzj
 * @since 2019-03-15
 */
@RestController
@RequestMapping("/hotKeyword")
public class HotKeywordController extends BaseController {
    @Autowired
    HotKeywordService hotKeywordService;

    @GetMapping("/listPage")
    public RespData listPage(@RequestParam Map<String,Object> params){
        PageResult pageResult = hotKeywordService.listPage(params);
        return RespData.ok(pageResult);
    }
    @PostMapping("/save")
    public RespData saveHotKey(@RequestBody HotKeywordModel hotKeywordModel){
        hotKeywordService.saveHotKey(hotKeywordModel);
        return RespData.ok();
    }
    @PostMapping("/updateById")
    public RespData updateById(@RequestBody HotKeywordModel hotKeywordModel){
        hotKeywordService.updateHotKeyById(hotKeywordModel);
        return RespData.ok();
    }
    @PostMapping("/deleteById/{id}")
    public RespData deleteById(@PathVariable Long id){
        hotKeywordService.deleteHotKeyById(id);
        return RespData.ok();
    }
}
