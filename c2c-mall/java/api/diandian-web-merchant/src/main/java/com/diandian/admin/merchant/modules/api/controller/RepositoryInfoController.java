package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.TransportTypeConstant;
import com.diandian.dubbo.facade.model.repository.RepositoryInfoModel;
import com.diandian.dubbo.facade.service.repository.RepositoryInfoService;
import com.diandian.dubbo.facade.vo.RepositoryDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/repositoryInfo")
public class RepositoryInfoController {
    @Autowired
    RepositoryInfoService repositoryInfoService;

    @GetMapping("/list")
    public RespData list(){
        List<RepositoryInfoModel> list = repositoryInfoService.list(null);
        return RespData.ok(list);
    }

    @GetMapping("/listByProduct")
    public RespData listByProduct(@RequestParam Long productId, @RequestParam Long skuId){
        List<Integer> transportTypeIds = new ArrayList<>();
        transportTypeIds.add(TransportTypeConstant.EXPRESS.getValue());
        transportTypeIds.add(TransportTypeConstant.TAKE_THEIR.getValue());
        transportTypeIds.add(TransportTypeConstant.PINKAGE.getValue());
        List<RepositoryDetailVO> list = repositoryInfoService.getRepositoryDetailByProduct(productId, skuId, transportTypeIds);
        return RespData.ok(list);
    }

    @PostMapping("/save")
    public RespData save(@RequestBody RepositoryInfoModel repositoryInfoModel) {
        repositoryInfoService.save(repositoryInfoModel);
        return RespData.ok();
    }

    @GetMapping("getRepositoryByShopId")
    public RespData getRepositoryByShopId(Long shopId){
        return RespData.ok(repositoryInfoService.getRepositoryByShopId(shopId));
    }
}
