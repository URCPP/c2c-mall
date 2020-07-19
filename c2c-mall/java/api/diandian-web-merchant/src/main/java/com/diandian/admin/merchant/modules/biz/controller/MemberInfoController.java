package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.member.MemberInfoDTO;
import com.diandian.dubbo.facade.model.member.MemberInfoModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.member.MemberInfoService;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoTotalVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 商户管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/member")
@Slf4j
public class MemberInfoController extends BaseController {

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private ShopInfoService shopInfoService;


    /**
     * 通过ID查询
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public RespData getById(@PathVariable Long id) {
//        MerchantInfoModel bizMerchantInfoModel = memberInfoService.getById(id);
        return RespData.ok();
    }

    /**
     * 添加
     *
     * @param dto
     * @return R
     */
    @PostMapping("/add")
    public RespData insert(@RequestBody MemberInfoDTO dto) {
        dto.setType(1);
        dto.setMerchantId(getMerchantInfoId());
        memberInfoService.memberRegister(dto);
        return RespData.ok();
    }

    /**
     * 修改
     *
     * @param memberInfoModel
     * @return R
     */
    @PostMapping("/update")
    public RespData updateById(@RequestBody MemberInfoModel memberInfoModel) {
        return RespData.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public RespData deleteById(@PathVariable Long id) {
        return RespData.ok();
    }

    /**
     * 分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        params.put("merchantInfo", merchantInfo);
        PageResult page = memberInfoService.listPage(params);
        return RespData.ok(page);
    }

    @GetMapping("/getMerchnatList")
    public RespData getMerchantList(){
        MerchantInfoTotalVO merchantInfoTotalVO=new MerchantInfoTotalVO();
        ShopInfoModel shopInfoModel=shopInfoService.getShopInfoBymerId(this.getMerchantInfoId());
        if (shopInfoModel==null){
            return RespData.fail("该用户没有商户信息");
        }
        merchantInfoTotalVO.setTodayMember(0);
        merchantInfoTotalVO.setTodayOrder(0);
        merchantInfoTotalVO.setTodayTurnover(new BigDecimal(0));
        merchantInfoTotalVO.setTotalMember(0);
        merchantInfoTotalVO.setTotalOrder(0);
        merchantInfoTotalVO.setTotalIncome(new BigDecimal(0));
        merchantInfoTotalVO.setShopName(shopInfoModel.getShopName());
        merchantInfoTotalVO.setShopAvatar(shopInfoModel.getShopAvatar());
        return RespData.ok(merchantInfoTotalVO);
    }

    @PostMapping("/updateMerchant")
    public RespData updateMerchant(@RequestBody ShopInfoModel shopInfoModel) {
        ShopInfoModel shopInfo=shopInfoService.getShopInfoBymerId(this.getMerchantInfoId());
        shopInfoModel.setId(shopInfo.getId());
        shopInfoService.updateById(shopInfoModel);
        return RespData.ok();
    }



}
