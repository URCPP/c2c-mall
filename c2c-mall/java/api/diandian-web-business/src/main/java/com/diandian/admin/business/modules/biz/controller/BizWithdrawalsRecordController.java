package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalsRecordModel;
import com.diandian.dubbo.facade.service.biz.BizWithdrawalsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-10 21:22
 */
@RestController
@RequestMapping("/biz/withdrawalsRecord")
public class BizWithdrawalsRecordController {

    @Autowired
    private BizWithdrawalsRecordService bizWithdrawalsRecordService;

    /** 
    * @Description: 提现
    * @Param:  
    * @return:  
    * @Author: wsk
    * @Date: 2019/9/11 
    */ 
    @PostMapping("withdrawal")
    public RespData add(@RequestBody BizWithdrawalsRecordModel bizWithdrawalsRecordModel){
        bizWithdrawalsRecordService.withdrawal(bizWithdrawalsRecordModel);
        return RespData.ok();
    }
    
    /** 
    * @Description: 分页 
    * @Param:  
    * @return:  
    * @Author: wsk
    * @Date: 2019/9/11 
    */ 
    @GetMapping("listPage")
    public RespData listPage(@RequestParam Map<String, Object> params){
        return RespData.ok(bizWithdrawalsRecordService.listPage(params));
    }
    
    /** 
    * @Description: 修改
    * @Param:  
    * @return:  
    * @Author: wsk
    * @Date: 2019/9/11 
    */ 
    @PostMapping("examine")
    public RespData examine(@RequestBody BizWithdrawalsRecordModel bizWithdrawalsRecordModel){
        bizWithdrawalsRecordService.examine(bizWithdrawalsRecordModel);
        return RespData.ok();
    }
}