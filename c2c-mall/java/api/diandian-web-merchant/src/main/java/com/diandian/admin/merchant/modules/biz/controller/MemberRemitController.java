package com.diandian.admin.merchant.modules.biz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.biz.vo.member.MemberExchangeRuleVO;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.member.MemberExchangeDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantRemitDTO;
import com.diandian.dubbo.facade.model.member.MemberExchangeSetModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantRemitLogModel;
import com.diandian.dubbo.facade.service.member.MemberExchangeLogService;
import com.diandian.dubbo.facade.service.member.MemberExchangeSetService;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantRemitLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 会员兑换管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/remit")
@Slf4j
public class MemberRemitController extends BaseController {

    @Autowired
    private MerchantRemitLogService merchantRemitLogService;




   /**
    * @Author wubc
    * @Description //TODO 商户线下汇款订单添加
    * @Date 18:55 2019/3/29
    * @Param [dto]
    * @return com.diandian.admin.common.util.RespData
    **/
    @PostMapping("/addRemit")
    public RespData addRemit(@RequestBody MerchantRemitDTO dto) {
        String orderNo = merchantRemitLogService.addRemit(dto);
        return RespData.ok(orderNo);
    }

    /**
     * @Author wubc
     * @Description //TODO 商户线下汇款上传凭证
     * @Date 20:35 2019/3/29
     * @Param [dto]
     * @return com.diandian.admin.common.util.RespData
     **/
    @PostMapping("/updRemit")
    public RespData updRemit(@RequestBody MerchantRemitDTO dto) {
        merchantRemitLogService.updRemit(dto);
        return RespData.ok();
    }

    /**
     * @Author wubc
     * @Description //TODO 储备金退还
     * @Date 11:10 2019/3/30
     * @Param [dto]
     * @return com.diandian.admin.common.util.RespData
     **/
    @GetMapping("/sendBack")
    public RespData sendBack() {
        Long merchantInfoId = this.getMerchantInfoId();
        merchantRemitLogService.sendBack(merchantInfoId);
        return RespData.ok();
    }

    /**
     * @Author wubc
     * @Description //TODO 获取商户的开通或退还订单
     * @Date 12:45 2019/3/30
     * @Param []
     * @return com.diandian.admin.common.util.RespData
     **/
    @GetMapping("/getRemitOrder")
    public RespData getRemitOrder() {
        Long merchantInfoId = this.getMerchantInfoId();
        MerchantRemitLogModel log = merchantRemitLogService.getRemitOrder(merchantInfoId);
        return RespData.ok(log);
    }
    
    /**
     * @Author wubc
     * @Description //TODO 取消退还订单
     * @Date 16:41 2019/4/1
     * @Param []
     * @return com.diandian.admin.common.util.RespData
     **/
    @GetMapping("/cancelRemit")
    public RespData cancelRemit() {
        Long merchantInfoId = this.getMerchantInfoId();
        merchantRemitLogService.cancelRemit(merchantInfoId);
        return RespData.ok();
    }



}
