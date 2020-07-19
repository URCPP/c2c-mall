package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.model.merchant.MerchantAttentionModel;
import com.diandian.dubbo.facade.service.merchant.MerchantAttentionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("merchantAttentionInfo")
@Slf4j
public class MerchantAttentionController extends BaseController {

    @Autowired
    private MerchantAttentionService merchantAttentionService;

    @GetMapping("/attentionList")
    public RespData attentionList(Long merchantId){
        List<MerchantAttentionModel> list= merchantAttentionService.findAttention(merchantId);
        Map<String, Object> map = new HashMap<>();
        if(list.size()>0){
            for (MerchantAttentionModel merchantAttentionModel : list) {
                MerchantAttentionModel  ma=merchantAttentionService.findOnlyOne(merchantId, merchantAttentionModel.getFocusMerchantId());
                map.put("isFollow",null == ma ? 0 : merchantAttentionModel.getState());
            }
        }
        map.put("list",list);
        return  RespData.ok(map);
    }


    @GetMapping("/fansList")
    public RespData fansList(Long focusId){
        List<MerchantAttentionModel> list= merchantAttentionService.findFans(focusId);
        Map<String, Object> map = new HashMap<>();
        if(list.size()>0){
            for (MerchantAttentionModel merchantAttentionModel : list) {
                MerchantAttentionModel  mam=merchantAttentionService.findOnlyOne(getMerchantInfoId(),merchantAttentionModel.getMerchantId());
               if(mam!=null){
                   merchantAttentionModel.setIsFollow(1);
               }else {
                   merchantAttentionModel.setIsFollow(0);
               }
            }
        }
        map.put("list",list);
        return RespData.ok(map);
    }


    @PostMapping("/clickAttention")
    public RespData clickAttention(Long focusId,Integer flag){
        MerchantAttentionModel me=new MerchantAttentionModel();
        if(flag==0){
            if(merchantAttentionService.findOnlyOne(getMerchantInfoId(),focusId)==null && !(getMerchantInfoId().equals(focusId))){
                merchantAttentionService.updateFlag(focusId);
                me.setMerchantId(getMerchantInfoId());
                me.setFocusMerchantId(focusId);
                merchantAttentionService.saveAttention(me);
                return RespData.ok("关注成功!");
            }
            return RespData.ok("已关注");
        }else{
            merchantAttentionService.updateFlag(focusId);
            merchantAttentionService.delAttention(getMerchantInfoId(),focusId);
            return RespData.ok("取消关注成功!");
        }
    }

}
