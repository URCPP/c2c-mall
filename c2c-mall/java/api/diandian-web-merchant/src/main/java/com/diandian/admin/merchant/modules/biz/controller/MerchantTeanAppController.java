package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.vo.merchant.MerchantTeamAppVo;
import com.diandian.dubbo.facade.vo.merchant.MerchantTeamCountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/biz/merchantTeanApp")
@Slf4j
public class MerchantTeanAppController extends BaseController {

    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * 团队列表分页
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        params.put("merchantId", merchantInfo.getId());
        PageResult page=new PageResult();
        if (params.get("phone")!=null){
             page = merchantInfoService.listMerchantPage(params);
        }
        if(page.getList().size()>0){
            return RespData.ok(page);
        }else{
            page = merchantInfoService.listMerchantPageName(params);
        }
        page.getList();
        return RespData.ok(page);
    }

    @GetMapping("/conutMenber")
    public RespData getCountNumber(){
        MerchantTeamCountVo merchantTeamCountVo=new MerchantTeamCountVo();
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        Integer CountDirectTeamNumber=merchantInfoService.getByParentId(merchantInfo.getId());
        List <MerchantInfoModel>list = merchantInfoService.getMerchantInfoByIdList(merchantInfo.getId(),null,null,null,null);
        Integer TotalCount= list.size();
        merchantTeamCountVo.setCountAllTeamNumber(TotalCount);
        merchantTeamCountVo.setCountDirectTeamNumber(CountDirectTeamNumber);
        return RespData.ok(merchantTeamCountVo);
    }
}
