package com.diandian.admin.merchant.modules.biz.controller;

import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.common.constant.RedisKeyConstant;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.biz.service.MerchantDebitCardInfoService;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantDebitCardInfoVO;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantDebitCardPartInfoVO;
import com.diandian.admin.model.merchant.MerchantDebitCardInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商户银行卡信息管理
 *
 * @author jbuhuan
 * @date 2019/2/14 18:20
 */
@RestController
@RequestMapping("/merchantDebitCardInfo")
@Slf4j
public class MerchantDebitCardInfoController extends BaseController {
    @Autowired
    private MerchantDebitCardInfoService merchantDebitCardInfoService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 添加银行卡信息
     *
     * @param cardInfoVO
     * @return
     */
    @PostMapping("/insert")
    public RespData insertCardInfo(@RequestBody MerchantDebitCardInfoVO cardInfoVO) {
        String code = redisTemplate.opsForValue().get(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + cardInfoVO.getCardholderPhone());
        AssertUtil.isTrue(cardInfoVO.getCode().equals(code), "验证码输入有误或过期!");
        redisTemplate.delete(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + cardInfoVO.getCardholderPhone());

        String cardNumber = cardInfoVO.getCardNumber();
        MerchantDebitCardInfoModel cardInfo = merchantDebitCardInfoService.getDebitCardInfo(cardNumber);
        AssertUtil.isNull(cardInfo, "一张银行卡只能绑定一次");
        Long id = getMerchantInfo().getId();
        cardInfoVO.setMerchantId(id);
        merchantDebitCardInfoService.insertCardInfo(cardInfoVO);
        return RespData.ok();
    }

    /**
     * 银行卡列表
     *
     * @return
     */
    @GetMapping("/list")
    public RespData listCardInfo() {
        Long id = getMerchantInfo().getId();
        List<MerchantDebitCardPartInfoVO> list = merchantDebitCardInfoService.listCardInfo(id);
        return RespData.ok(list);
    }

    /**
     * 删除银行卡
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public RespData deleteCardInfo(@PathVariable Long id) {
        merchantDebitCardInfoService.deleteCardInfo(id);
        return RespData.ok();
    }

    /**
     * 修改银行卡为默认银行卡
     *
     * @param id
     * @return
     */
    @GetMapping("/update/{id}")
    public RespData updateCardInfo(@PathVariable Long id) {
        Long merchantId = getMerchantInfo().getId();
        merchantDebitCardInfoService.updateCardInfo(id, merchantId);
        return RespData.ok();
    }


}
