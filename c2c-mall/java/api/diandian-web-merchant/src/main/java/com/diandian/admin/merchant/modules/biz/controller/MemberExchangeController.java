package com.diandian.admin.merchant.modules.biz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.biz.vo.member.MemberExchangeRuleVO;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.member.MemberExchangeDTO;
import com.diandian.dubbo.facade.model.member.MemberExchangeSetModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.service.member.MemberExchangeLogService;
import com.diandian.dubbo.facade.service.member.MemberExchangeSetService;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
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
@RequestMapping("/biz/memberExchange")
@Slf4j
public class MemberExchangeController extends BaseController {

    @Autowired
    private MemberExchangeSetService memberExchangeSetService;

    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;

    @Autowired
    private MemberExchangeLogService memberExchangeLogService;


    /**
     * 获取兑换规则
     *
     * @return
     */
    /*@GetMapping("/getExchangeRule")
    public RespData getExchangeRule() {

        MemberExchangeRuleVO vo = new MemberExchangeRuleVO();
        Long mId = this.getMerchantInfo().getId();

        MemberExchangeSetModel rule = memberExchangeSetService.getSetByMerchantId(mId);
        AssertUtil.notNull(rule, "未配置兑换规则");
        vo.setAmountBase(rule.getAmountBase());
        vo.setExchangeRate(rule.getExchangeRate());
        vo.setShoppingRate(rule.getShoppingRate());

        MerchantAccountInfoModel mc = merchantAccountInfoService.getByMerchantId(mId);
        if (null == mc) {
            vo.setMerchantExchangeSum(0);
            vo.setMerchantShoppingSum(BigDecimal.ZERO);
        } else {
            vo.setMerchantExchangeSum(mc.getExchangeCouponSum());
            vo.setMerchantShoppingSum(mc.getShoppingCouponSum());
        }
        return RespData.ok(vo);
    }*/


    /**
     * 会员兑换记录分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        params.put("merchantId", merchantInfo.getId());
        PageResult page = memberExchangeLogService.listPage(params);
        return RespData.ok(page);
    }


   /**
    * @Author wubc
    * @Description //TODO 会员兑换券充值
    * @Date 14:17 2019/4/2
    * @Param [dto]
    * @return com.diandian.admin.common.util.RespData
    **/
    /*@PostMapping("/exchange")
    public RespData exchange(@RequestBody MemberExchangeDTO dto) {
        MerchantInfoModel mer = this.getMerchantInfo();
        dto.setMerchantId(mer.getId());
        dto.setMerchantName(mer.getName());
        dto.setMechantAcc(mer.getLoginName());
        //会员操作
        String quoate = memberExchangeLogService.exchange(dto);

        //商户帐操作
        JSONObject parse = JSON.parseObject(quoate);
        int exchangeQuoate = parse.getIntValue("exchange");
        BigDecimal shoppingQuoate = parse.getBigDecimal("shopping");
        merchantAccountInfoService.exchange(this.getMerchantInfo().getId(), exchangeQuoate, shoppingQuoate);
        return RespData.ok(parse);
    }*/

}
