package com.diandian.admin.business.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/biz/merchantTean")
public class MerchantTeamController {
    @Autowired
    private MerchantInfoService merchantInfoService;

    @PostMapping("/updateMerchantInfo")
    public RespData getMerchantTreeList(MerchantInfoModel merchantInfoModel){
        merchantInfoService.updateById(merchantInfoModel);
        return RespData.ok();
    }

    /**
     * 获取某个父节点下面的所有子节点
     * @param id 上级id
     * @return
     * 获取团队管理列表
     */
    @GetMapping("/merchantTreeList")
    public RespData getMerchantTreeList(Long id,String name,String parentPhone,String phone,Integer level,Integer delFlag){
        //找出所有上级id为1173082792281092097的用户
        PageResult pageResult=new PageResult();
        if(parentPhone!=null){
            MerchantInfoModel merchantInfoModel=merchantInfoService.getListByParentPhone(parentPhone);
            id=merchantInfoModel.getParentId();
        }
        List <MerchantInfoModel>list = merchantInfoService.getMerchantInfoByIdList(id,name,phone,level,delFlag);
        Long TotalCount= Long.valueOf(list.size());
        pageResult.setList(list);
        pageResult.setTotalCount(TotalCount);
        return RespData.ok(pageResult);
    }

}
