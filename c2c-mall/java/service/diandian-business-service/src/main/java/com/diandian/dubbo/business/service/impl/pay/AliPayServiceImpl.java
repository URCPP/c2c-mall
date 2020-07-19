package com.diandian.dubbo.business.service.impl.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.diandian.dubbo.facade.common.config.AliPayConfig;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantPayfeeRecordModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantPayfeeRecordService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.pay.AliPayService;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@Service("aliPayService")
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    MerchantInfoService merchantInfoService;
    @Autowired
    NoGenerator noGenerator;
    @Autowired
    MerchantPayfeeRecordService merchantPayfeeRecordService;
    @Autowired
    BizSoftwareTypeService bizSoftwareTypeService;
    @Autowired
    private BizMallConfigService bizMallConfigService;
    @Autowired
    private ShopInfoService shopInfoService;

    //支付类型 1订单支付 2 商户支付
    private static Integer orderPay = 1;
    private static Integer merchantPay = 2;

    @Override
    public String placeOrder(String orderNo,Long minutes) {
        OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(orderInfoModel,"订单不存在!");
        AssertUtil.isFalse(orderInfoModel.getPayState() == BizConstant.STATE_DISNORMAL,"订单已支付");
        return aliPay(orderInfoModel.getOrderNo(),orderInfoModel.getActualAmount(),AliPayConfig.mallUrl+AliPayConfig.mallPort + AliPayConfig.orderNotifyUrl,AliPayConfig.mallUrl+AliPayConfig.returnUrl,minutes,orderPay);
    }

    @Override
    public String phoneOrder(String orderNo,Long minutes) {
        OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(orderInfoModel,"订单不存在!");
        AssertUtil.isFalse(orderInfoModel.getPayState() == BizConstant.STATE_DISNORMAL,"订单已支付");
        return phonePay(orderNo,orderInfoModel.getActualAmount(),AliPayConfig.mallUrl+AliPayConfig.mallPort + AliPayConfig.orderNotifyUrl,AliPayConfig.phoneMallUrl+AliPayConfig.returnUrl,minutes,orderPay);
    }

    @Override
    public String payTransportFee(String orderNo) {
        OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(orderInfoModel,"订单不存在!");
        //获取运输费用
        BigDecimal transportFee = orderDetailService.totalTransportFee(orderNo);
        log.info("运输费用回调地址：" + AliPayConfig.mchUrl+AliPayConfig.mchPost + AliPayConfig.integralMallNotifyUrl);
        return phonePay(orderNo,transportFee,AliPayConfig.mchUrl+AliPayConfig.mchPost + AliPayConfig.integralMallNotifyUrl,AliPayConfig.mchUrl+AliPayConfig.returnUrl,BizConstant.TIMEOUT_EXPRESS,orderPay);
    }

    @Override
    public String merchantPayFee(Long merchantId,BigDecimal amount,Integer payType,String tradeWay) {
        MerchantInfoModel merchantInfoModel = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(merchantInfoModel,"商户不存在!");
        if (MerchantConstant.MerchantPayType.RENEW.value() == payType){
            BizSoftwareTypeModel bizSoftwareTypeModel = bizSoftwareTypeService.getSoftTypeById(merchantInfoModel.getSoftTypeId());
            amount = bizSoftwareTypeModel.getRenewCost();
        }else if (MerchantConstant.MerchantPayType.OPEN_MALL.value() == payType){
            BizMallConfigModel openAmount = bizMallConfigService.getByEngName(MerchantConstant.MALL_OPEN_AMOUNT);
            amount = new BigDecimal(openAmount.getMallValue());
        }
        //TODO 测试代码
        Long testMchId = 1105010906761068545L;
        if(testMchId.equals(merchantInfoModel.getId())){
            amount = new BigDecimal("0.1");
        }
        MerchantPayfeeRecordModel merchantPayfeeRecordModel = new MerchantPayfeeRecordModel();
        merchantPayfeeRecordModel.setMerchantId(merchantId);
        merchantPayfeeRecordModel.setPayNo(noGenerator.getMerchantPayNo());
        merchantPayfeeRecordModel.setPayType(payType);
        merchantPayfeeRecordModel.setTradeAmount(amount);
        merchantPayfeeRecordModel.setTradeWay(tradeWay);
        merchantPayfeeRecordModel.setState(MerchantConstant.MerchantPayState.WAIT.value());
        merchantPayfeeRecordModel.setTradeTime(new Date());
        merchantPayfeeRecordService.save(merchantPayfeeRecordModel);
        return aliPay(merchantPayfeeRecordModel.getPayNo(),merchantPayfeeRecordModel.getTradeAmount(),AliPayConfig.mallUrl+AliPayConfig.mallPort + AliPayConfig.merchantNotifyUrl,AliPayConfig.mallUrl+AliPayConfig.returnUrl,BizConstant.TIMEOUT_EXPRESS,merchantPay);
    }


    @Override
    public String phoneMerchantPayFee(Long merchantId, BigDecimal amount, Integer payType, String tradeWay) {
        MerchantInfoModel merchantInfoModel = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(merchantInfoModel,"商户不存在!");
        BizSoftwareTypeModel bizSoftwareTypeModel = bizSoftwareTypeService.getSoftTypeById(merchantInfoModel.getSoftTypeId());
        if (MerchantConstant.MerchantPayType.RENEW.value() == payType){
            amount = bizSoftwareTypeModel.getRenewCost();
        }else if (MerchantConstant.MerchantPayType.OPEN_MALL.value() == payType){
            amount = bizSoftwareTypeModel.getOpeningCost();
        }
        MerchantPayfeeRecordModel merchantPayfeeRecordModel = new MerchantPayfeeRecordModel();
        merchantPayfeeRecordModel.setMerchantId(merchantId);
        merchantPayfeeRecordModel.setPayNo(noGenerator.getMerchantPayNo());
        merchantPayfeeRecordModel.setPayType(payType);
        merchantPayfeeRecordModel.setTradeAmount(amount);
        merchantPayfeeRecordModel.setTradeWay(tradeWay);
        merchantPayfeeRecordModel.setState(MerchantConstant.MerchantPayState.WAIT.value());
        merchantPayfeeRecordModel.setTradeTime(new Date());
        merchantPayfeeRecordService.save(merchantPayfeeRecordModel);
        return phonePay(merchantPayfeeRecordModel.getPayNo(),merchantPayfeeRecordModel.getTradeAmount(),AliPayConfig.mallUrl+AliPayConfig.mallPort + AliPayConfig.merchantNotifyUrl,AliPayConfig.phoneMallUrl+AliPayConfig.returnUrl,BizConstant.TIMEOUT_EXPRESS,merchantPay);
    }

    /**
     * 手机支付 保证金
     * @param merchantId
     * @param amount
     * @param payType
     * @param tradeWay
     * @return
     */
    @Override
    public String phonePaySecurityDeposit(Long merchantId, BigDecimal amount, Integer payType, String tradeWay) {
        MerchantInfoModel merchantInfoModel = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(merchantInfoModel,"商户不存在!");
        List<ShopInfoModel> list=shopInfoService.getShopInfoByMerchantId(merchantId);
        MerchantPayfeeRecordModel merchantPayfeeRecordModel = new MerchantPayfeeRecordModel();
        merchantPayfeeRecordModel.setMerchantId(merchantId);
        merchantPayfeeRecordModel.setPayNo(noGenerator.getMerchantPayNo());
        merchantPayfeeRecordModel.setPayType(payType);
        merchantPayfeeRecordModel.setTradeAmount(amount);
        merchantPayfeeRecordModel.setTradeWay(tradeWay);
        merchantPayfeeRecordModel.setState(MerchantConstant.MerchantPayState.WAIT.value());
        merchantPayfeeRecordModel.setTradeTime(new Date());
        merchantPayfeeRecordService.save(merchantPayfeeRecordModel);
        return phonePay(merchantPayfeeRecordModel.getPayNo(),merchantPayfeeRecordModel.getTradeAmount(),AliPayConfig.mallUrl+AliPayConfig.mallPort + AliPayConfig.merchantNotifyUrl,AliPayConfig.phoneMallUrl+AliPayConfig.returnUrl,BizConstant.TIMEOUT_EXPRESS,merchantPay);
    }

    /**
     * 手机支付 保证金
     * @param merchantId
     * @param amount
     * @param payType
     * @param tradeWay
     * @return
     */
    @Override
    public String phonePayMargin(Long merchantId, BigDecimal amount, Integer payType, String tradeWay) {
        return null;
    }

    /**
     * 手机网页支付
     * @param orderNo
     * @param amount
     * @param notifyUrl
     * @param returnUrl
     * @return
     */
    private String phonePay(String orderNo,BigDecimal amount,String notifyUrl,String returnUrl,Long minutes,Integer orderType){
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.appid, AliPayConfig.privateKey, AliPayConfig.format, AliPayConfig.charset, AliPayConfig.alipayPublicKey, AliPayConfig.signType);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        try {
            alipayRequest.setReturnUrl(returnUrl+"?orderNo="+orderNo+"&orderAmount="+amount+"&orderType="+orderType+"&payWay="+URLEncoder.encode("支付宝","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new DubboException("支付编码异常");
        }
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
        JSONObject content = new JSONObject();
        content.put("out_trade_no", orderNo);
        content.put("total_amount",amount.setScale(2, BigDecimal.ROUND_UP));
        content.put("product_code", "QUICK_WAP_PAY");
        content.put("subject", orderNo);
        content.put("body", "");
        content.put("timeout_express",String.valueOf(minutes) + "m");
        alipayRequest.setBizContent(JSON.toJSONString(content));//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    /**
     * PC网页支付
     * @param outTradeNo
     * @param amount
     * @param notifyUrl
     * @param returnUrl
     * @return
     */
    private String aliPay(String outTradeNo,BigDecimal amount,String notifyUrl,String returnUrl,Long minutes,Integer orderType){
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.appid, AliPayConfig.privateKey, AliPayConfig.format, AliPayConfig.charset, AliPayConfig.alipayPublicKey, AliPayConfig.signType);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        try {
            alipayRequest.setReturnUrl(returnUrl+"?orderNo="+outTradeNo+"&orderAmount="+amount+"&orderType="+orderType+"&payWay="+URLEncoder.encode("支付宝","UTF-8"));
        } catch (UnsupportedEncodingException e) {
           throw new DubboException("支付编码异常");
        }
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
        JSONObject content = new JSONObject();
        content.put("out_trade_no", outTradeNo);
        content.put("total_amount",amount.setScale(2, BigDecimal.ROUND_UP));
        content.put("product_code", "FAST_INSTANT_TRADE_PAY");
        content.put("subject", outTradeNo);
        content.put("body", "");
        content.put("timeout_express",String.valueOf(minutes)+"m");
        alipayRequest.setBizContent(JSON.toJSONString(content));//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

}
