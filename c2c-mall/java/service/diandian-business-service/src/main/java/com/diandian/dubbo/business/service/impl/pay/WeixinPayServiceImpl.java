package com.diandian.dubbo.business.service.impl.pay;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.diandian.dubbo.business.common.config.GlobalConfig;
import com.diandian.dubbo.facade.common.config.AliPayConfig;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.TradeWayConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.common.util.EnumUtil;
import com.diandian.dubbo.facade.dto.pay.WxPayNotifyAttachDTO;
import com.diandian.dubbo.facade.dto.wx.WeixinPayMpOrderResultDTO;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantOpenApplyLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantPayfeeRecordModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.service.member.MemberInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantOpenApplyLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantPayfeeRecordService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletHistoryLogService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.pay.WeixinPayService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.tuple.BinaryTuple;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 微信支付接口实现类
 * @author cjunyuan
 * @date 2019/6/5 17:35
 */
@Service("weixinPayService")
@Slf4j
public class WeixinPayServiceImpl implements WeixinPayService {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private BizSoftwareTypeService bizSoftwareTypeService;

    @Autowired
    private MerchantWalletHistoryLogService merchantWalletHistoryLogService;

    @Autowired
    private MerchantPayfeeRecordService merchantPayfeeRecordService;

    @Autowired
    private MerchantOpenApplyLogService merchantOpenApplyLogService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private NoGenerator noGenerator;

    @Autowired
    private WxPayService wxPayService;

    @Value("${weixin.pay.notifyUrl}")
    private String notifyUrl;

    @Override
    public String pcPlaceOrder(String orderNo, String host, String ipAddress) {
        OrderInfoModel oldOrderInfo = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(oldOrderInfo, "订单信息不存在");
        long minutes = DateUtil.between(oldOrderInfo.getCreateTime(), DateUtil.date(), DateUnit.MINUTE, false);
        if (minutes >= BizConstant.TIMEOUT_EXPRESS) {
            throw new DubboException("订单已超时");
        }
        int totalFee = oldOrderInfo.getActualAmount().multiply(new BigDecimal(100)).intValue();
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.PRODUCT_ORDER_PAYMENT.value());
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body("商户订单支付")
                    .tradeType(WxPayConstants.TradeType.NATIVE)
                    .notifyUrl(new StringBuilder(host).append(notifyUrl).toString())
                    .outTradeNo(orderNo)
                    .totalFee(totalFee)
                    .spbillCreateIp(ipAddress)
                    .productId(oldOrderInfo.getId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            return wxPayUnifiedOrderResult.getCodeURL();
        } catch (WxPayException e) {
            log.info("【PC订单支付—微信支付预下单失败】");
            throw new DubboException(e.getErrCodeDes());
        }
    }

    @Override
    public String h5PlaceOrder(String orderNo, String host, String ipAddress) {
        OrderInfoModel oldOrderInfo = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(oldOrderInfo, "订单信息不存在");
        long minutes = DateUtil.between(oldOrderInfo.getCreateTime(), DateUtil.date(), DateUnit.MINUTE, false);
        if (minutes >= BizConstant.TIMEOUT_EXPRESS) {
            throw new DubboException("订单已超时");
        }
        int totalFee = oldOrderInfo.getActualAmount().multiply(new BigDecimal(100)).intValue();
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.PRODUCT_ORDER_PAYMENT.value());
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body("商户订单支付")
                    .tradeType(WxPayConstants.TradeType.MWEB)
                    .notifyUrl(new StringBuilder(host).append(notifyUrl).toString())
                    .outTradeNo(orderNo)
                    .totalFee(totalFee)
                    .spbillCreateIp(ipAddress)
                    .productId(oldOrderInfo.getId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            return wxPayUnifiedOrderResult.getMwebUrl();
        } catch (WxPayException e) {
            log.info("【H5订单支付—微信支付预下单失败】");
            throw new DubboException(e.getErrCodeDes());
        }
    }

    @Override
    public String h5PayTransportFee(String orderNo, String host, String ipAddress) {
        OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(orderInfoModel,"订单不存在!");
        //获取运输费用
        BigDecimal transportFee = orderDetailService.totalTransportFee(orderNo);
        int totalFee = transportFee.multiply(GlobalConfig.percent).intValue();
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.ORDER_TRANSPORT_FEE_PAYMENT.value());
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body("积分订单运费支付")
                    .tradeType(WxPayConstants.TradeType.MWEB)
                    .notifyUrl(new StringBuilder(host).append(notifyUrl).toString())
                    .outTradeNo(orderNo)
                    .totalFee(totalFee)
                    .spbillCreateIp(ipAddress)
                    .productId(orderInfoModel.getId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            return wxPayUnifiedOrderResult.getMwebUrl();
        } catch (WxPayException e) {
            log.info("【H5积分订单运费支付—微信支付预下单失败】");
            throw new DubboException("预下单失败");
        }
    }

    @Override
    public WeixinPayMpOrderResultDTO mpPayTransportFee(String orderNo, String openId, String host, String ipAddress) {
        OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(orderInfoModel,"订单不存在!");
        //获取运输费用
        BigDecimal transportFee = orderDetailService.totalTransportFee(orderNo);
        int totalFee = transportFee.multiply(GlobalConfig.percent).intValue();
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.ORDER_TRANSPORT_FEE_PAYMENT.value());
        try{
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body("积分订单运费支付")
                    .openid(openId)
                    .tradeType(WxPayConstants.TradeType.JSAPI)
                    .notifyUrl(new StringBuilder(host).append(notifyUrl).toString())
                    .outTradeNo(orderNo)
                    .totalFee(totalFee)
                    .spbillCreateIp(ipAddress)
                    .productId(orderInfoModel.getId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayMpOrderResult order = wxPayService.createOrder(wxPayUnifiedOrderRequest);
            WeixinPayMpOrderResultDTO resultDTO = new WeixinPayMpOrderResultDTO();
            BeanUtil.copyProperties(order, resultDTO);
            return resultDTO;
        }catch (WxPayException e){
            log.info("【JSAPI积分订单运费支付—微信支付预下单失败】");
            throw new DubboException("预下单失败");
        }
    }

    @Override
    public BinaryTuple<String, String> pcMchPayFee(Long merchantId, BigDecimal amount, Integer payType, String host, String ipAddress) {
        MerchantPayfeeRecordModel mchPayFeeRecord = this.mchPayFee(merchantId, amount, payType);
        merchantPayfeeRecordService.save(mchPayFeeRecord);
        MerchantConstant.MerchantPayType mchPayType = EnumUtil.getLabelByValue(MerchantConstant.MerchantPayType.class, "value", payType);
        AssertUtil.notNull(mchPayType, "参数错误");
        int totalFee = mchPayFeeRecord.getTradeAmount().multiply(new BigDecimal(100)).intValue();
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.MERCHANT_PAYMENT.value());
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body(mchPayType.getLabel())
                    .tradeType(WxPayConstants.TradeType.NATIVE)
                    .notifyUrl(new StringBuilder(host).append(notifyUrl).toString())
                    .outTradeNo(mchPayFeeRecord.getPayNo())
                    .totalFee(totalFee)
                    .spbillCreateIp(ipAddress)
                    .productId(mchPayFeeRecord.getMerchantId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            return new BinaryTuple<String, String>(mchPayFeeRecord.getPayNo(), wxPayUnifiedOrderResult.getCodeURL());
        } catch (WxPayException e) {
            log.info("【PC{}—微信支付预下单失败】", mchPayType.getLabel());
            throw new DubboException(e.getErrCodeDes());
        }
    }

    @Override
    public BinaryTuple<String, String> h5MchPayFee(Long merchantId, BigDecimal amount, Integer payType, String host, String ipAddress) {
        MerchantPayfeeRecordModel mchPayFeeRecord = this.mchPayFee(merchantId, amount, payType);
        merchantPayfeeRecordService.save(mchPayFeeRecord);
        MerchantConstant.MerchantPayType mchPayType = EnumUtil.getLabelByValue(MerchantConstant.MerchantPayType.class, "value", payType);
        AssertUtil.notNull(mchPayType, "参数错误");
        int totalFee = mchPayFeeRecord.getTradeAmount().multiply(new BigDecimal(100)).intValue();
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.MERCHANT_PAYMENT.value());
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body(mchPayType.getLabel())
                    .tradeType(WxPayConstants.TradeType.MWEB)
                    .notifyUrl(new StringBuilder(host).append(notifyUrl).toString())
                    .outTradeNo(mchPayFeeRecord.getPayNo())
                    .totalFee(totalFee)
                    .spbillCreateIp(ipAddress)
                    .productId(mchPayFeeRecord.getMerchantId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            return new BinaryTuple<String, String>(mchPayFeeRecord.getPayNo(), wxPayUnifiedOrderResult.getMwebUrl());
        } catch (WxPayException e) {
            log.info("【H5{}—微信支付预下单失败】", mchPayType.getLabel());
            throw new DubboException(e.getErrCodeDes());
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public BinaryTuple<String, String> pcMchPayOpeningCost(Long merchantId, String ipAddress, String host){
        MerchantInfoModel oldMchInfo = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(oldMchInfo, "商户信息不存在");
        MerchantOpenApplyLogModel mchOpenApplyInfo = merchantOpenApplyLogService.getByMerIdAndSoftId(oldMchInfo.getId(), oldMchInfo.getSoftTypeId());
        AssertUtil.notNull(mchOpenApplyInfo, "商户申请信息不存在");
        AssertUtil.isTrue(BizConstant.ApplyType.APPLY.value().equals(mchOpenApplyInfo.getApplyType()), "免费开通申请无需支付费用");
        AssertUtil.isTrue(BizConstant.STATE_NORMAL.equals(mchOpenApplyInfo.getPayFlag()), "已支付申请费用，请勿重复提交");
        BizSoftwareTypeModel softwareType = bizSoftwareTypeService.getSoftTypeById(oldMchInfo.getSoftTypeId());
        AssertUtil.notNull(softwareType, "商户申请的软件信息不存在");

        BigDecimal totalAmount = mchOpenApplyInfo.getOpeningCost().add(new BigDecimal(mchOpenApplyInfo.getPurchaseVoucherNum()).multiply(mchOpenApplyInfo.getPurchaseVoucherExchangeRatio().divide(new BigDecimal(100))));

        MerchantPayfeeRecordModel mchPayFeeRecord = new MerchantPayfeeRecordModel();
        mchPayFeeRecord.setTradeAmount(softwareType.getOpeningCost());
        mchPayFeeRecord.setMerchantId(merchantId);
        mchPayFeeRecord.setPayNo(noGenerator.getMerchantPayNo());
        mchPayFeeRecord.setPayType(MerchantConstant.MerchantPayType.OPENING_COST.value());
        mchPayFeeRecord.setTradeAmount(totalAmount);
        mchPayFeeRecord.setTradeWay(TradeWayConstant.PC_WXPAY);
        mchPayFeeRecord.setState(MerchantConstant.MerchantPayState.WAIT.value());
        merchantPayfeeRecordService.save(mchPayFeeRecord);

        MerchantOpenApplyLogModel update = new MerchantOpenApplyLogModel();
        update.setId(mchOpenApplyInfo.getId());
        update.setOnlinePay(BizConstant.STATE_DISNORMAL);
        merchantOpenApplyLogService.update(update);
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.MERCHANT_PAY_OPENING_COST.value());
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body(MerchantConstant.MerchantPayType.OPENING_COST.getLabel())
                    .tradeType(WxPayConstants.TradeType.NATIVE)
                    .notifyUrl(new StringBuilder(host).append(notifyUrl).toString())
                    .outTradeNo(mchPayFeeRecord.getPayNo())
                    .totalFee(totalAmount.multiply(GlobalConfig.percent).intValue())
                    .spbillCreateIp(ipAddress)
                    .productId(mchPayFeeRecord.getMerchantId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            return new BinaryTuple<String, String>(mchPayFeeRecord.getPayNo(), wxPayUnifiedOrderResult.getCodeURL());
        } catch (WxPayException e) {
            log.info("【PC商户开通—微信支付预下单失败】");
            throw new DubboException(e.getErrCodeDes());
        }
    }

    @Override
    public BinaryTuple<String, String> h5MchPayOpeningCost(Long merchantId, String host, String ipAddress){
        MerchantInfoModel oldMchInfo = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(oldMchInfo, "商户信息不存在");
        MerchantOpenApplyLogModel mchOpenApplyInfo = merchantOpenApplyLogService.getByMerIdAndSoftId(oldMchInfo.getId(), oldMchInfo.getSoftTypeId());
        AssertUtil.notNull(mchOpenApplyInfo, "商户申请信息不存在");
        AssertUtil.isTrue(BizConstant.ApplyType.APPLY.value().equals(mchOpenApplyInfo.getApplyType()), "免费开通申请无需支付费用");
        AssertUtil.isTrue(BizConstant.STATE_NORMAL.equals(mchOpenApplyInfo.getPayFlag()), "已支付申请费用，请勿重复提交");
        BizSoftwareTypeModel softwareType = bizSoftwareTypeService.getSoftTypeById(oldMchInfo.getSoftTypeId());
        AssertUtil.notNull(softwareType, "商户申请的软件信息不存在");

        BigDecimal totalAmount = mchOpenApplyInfo.getOpeningCost().add(new BigDecimal(mchOpenApplyInfo.getPurchaseVoucherNum()).multiply(mchOpenApplyInfo.getPurchaseVoucherExchangeRatio().divide(new BigDecimal(100))));

        MerchantPayfeeRecordModel mchPayFeeRecord = new MerchantPayfeeRecordModel();
        mchPayFeeRecord.setTradeAmount(softwareType.getOpeningCost());
        mchPayFeeRecord.setMerchantId(merchantId);
        mchPayFeeRecord.setPayNo(noGenerator.getMerchantPayNo());
        mchPayFeeRecord.setPayType(MerchantConstant.MerchantPayType.OPENING_COST.value());
        mchPayFeeRecord.setTradeAmount(totalAmount);
        mchPayFeeRecord.setTradeWay(TradeWayConstant.PC_WXPAY);
        mchPayFeeRecord.setState(MerchantConstant.MerchantPayState.WAIT.value());
        merchantPayfeeRecordService.save(mchPayFeeRecord);

        MerchantOpenApplyLogModel update = new MerchantOpenApplyLogModel();
        update.setId(mchOpenApplyInfo.getId());
        update.setOnlinePay(BizConstant.STATE_DISNORMAL);
        merchantOpenApplyLogService.update(update);
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.MERCHANT_PAY_OPENING_COST.value());
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body(MerchantConstant.MerchantPayType.OPENING_COST.getLabel())
                    .tradeType(WxPayConstants.TradeType.MWEB)
                    .notifyUrl(new StringBuilder(host).append(notifyUrl).toString())
                    .outTradeNo(mchPayFeeRecord.getPayNo())
                    .totalFee(totalAmount.multiply(GlobalConfig.percent).intValue())
                    .spbillCreateIp(ipAddress)
                    .productId(mchPayFeeRecord.getMerchantId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            return new BinaryTuple<String, String>(mchPayFeeRecord.getPayNo(), wxPayUnifiedOrderResult.getMwebUrl());
        } catch (WxPayException e) {
            log.info("【H5商户开通—微信支付预下单失败】");
            throw new DubboException(e.getErrCodeDes());
        }
    }

    @Override
    public WeixinPayMpOrderResultDTO mpOrderPay(String orderNo, String openId, String notifyUrl, String ipAddress) {
        OrderInfoModel oldOrderInfo = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(oldOrderInfo,"订单不存在!");
        //获取运输费用
        long minutes = DateUtil.between(oldOrderInfo.getCreateTime(), DateUtil.date(), DateUnit.MINUTE, false);
//        if (minutes >= DubboConstant.ORDER_EXPIRE_TIMES) {
//            throw new DubboException("订单已超时");
//        }
        int totalFee = oldOrderInfo.getOrderAmount().multiply(GlobalConfig.percent).intValue();
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
//        dto.setPayType(DubboConstant.PayType.MP_WXPAY.getValue());
        dto.setBusinessType(MerchantConstant.WxPayBusinessType.PRODUCT_ORDER_PAYMENT.value());
        try{
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body("商品订单支付")
                    .openid(openId)
                    .tradeType(WxPayConstants.TradeType.JSAPI)
                    .notifyUrl(notifyUrl)
                    .outTradeNo(orderNo)
                    .totalFee(totalFee)
                    .spbillCreateIp(ipAddress)
                    .productId(oldOrderInfo.getId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayMpOrderResult order = wxPayService.createOrder(wxPayUnifiedOrderRequest);
            WeixinPayMpOrderResultDTO resultDTO = new WeixinPayMpOrderResultDTO();
            BeanUtil.copyProperties(order, resultDTO);
            return resultDTO;
        }catch (WxPayException e){
            log.info("【JSAPI商品订单支付—微信支付预下单失败】");
            throw new DubboException("预下单失败");
        }

    }

    @Override
    public String h5OrderPay(String orderNo, String notifyUrl, String ipAddress) {
        OrderInfoModel oldOrderInfo = orderInfoService.getByOrderNo(orderNo);
        AssertUtil.notNull(oldOrderInfo, "订单信息不存在");
        long minutes = DateUtil.between(oldOrderInfo.getCreateTime(), DateUtil.date(), DateUnit.MINUTE, false);
//        if (minutes >= DubboConstant.ORDER_EXPIRE_TIMES) {
//            throw new DubboException("订单已超时");
//        }
        int totalFee = oldOrderInfo.getActualAmount().multiply(GlobalConfig.percent).intValue();
        WxPayNotifyAttachDTO dto = new WxPayNotifyAttachDTO();
        //dto.setPayType(DubboConstant.PayType.H5_WXPAY.getValue());
        try {
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = WxPayUnifiedOrderRequest.newBuilder()
                    .body("商品订单支付")
                    .tradeType(WxPayConstants.TradeType.MWEB)
                    .notifyUrl(notifyUrl)
                    .outTradeNo(orderNo)
                    .totalFee(totalFee)
                    .spbillCreateIp(ipAddress)
                    .productId(oldOrderInfo.getId().toString())
                    .attach(JSON.toJSONString(dto, SerializerFeature.WriteNullStringAsEmpty))
                    .build();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
            return wxPayUnifiedOrderResult.getMwebUrl();
        } catch (WxPayException e) {
            log.info("【H5商品订单支付—微信支付预下单失败】");
            throw new DubboException(e.getErrCodeDes());
        }

    }

    /*@Override
    public String wxPayNotify(String xmlData){
        try {
            WxPayOrderNotifyResult wxPayOrderNotifyResult = wxPayService.parseOrderNotifyResult(xmlData);
            WxPayNotifyAttachDTO wxPayNotifyAttachDTO = JSON.parseObject(wxPayOrderNotifyResult.getAttach(), WxPayNotifyAttachDTO.class);
            MerchantConstant.WxPayBusinessType wxPayBusinessType = EnumUtil.getLabelByValue(MerchantConstant.WxPayBusinessType.class, "value", wxPayNotifyAttachDTO.getBusinessType());
            if(null == wxPayBusinessType){
                log.info("\n【微信回调结果处理失败】\n{}", wxPayOrderNotifyResult);
                return WxPayNotifyResponse.fail("ERROR");
            }else if(MerchantConstant.WxPayBusinessType.PRODUCT_ORDER_PAYMENT.value().equals(wxPayBusinessType.value())){
                // 处理支付成功逻辑
                OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(wxPayOrderNotifyResult.getOutTradeNo());
                if (orderInfoModel == null) {
                    log.info("订单号{}不存在",wxPayOrderNotifyResult.getOutTradeNo());
                    return WxPayNotifyResponse.fail("ERROR");
                }
                // 判断total_amount是否确实为该订单的实际金额
                if(!wxPayOrderNotifyResult.getTotalFee().equals(orderInfoModel.getActualAmount().
                        multiply(GlobalConfig.percent).intValue())){
                    log.info("订单{}，金额{}不一致 订单金额{}",wxPayOrderNotifyResult.getOutTradeNo(),wxPayOrderNotifyResult.getTotalFee(),
                            orderInfoModel.getActualAmount().multiply(GlobalConfig.percent));
                    return WxPayNotifyResponse.fail("ERROR");
                }
                orderInfoService.paySuccess(orderInfoModel, wxPayOrderNotifyResult.getTransactionId(), TradeWayConstant.PC_WXPAY);
            }else if(MerchantConstant.WxPayBusinessType.MERCHANT_PAYMENT.value().equals(wxPayBusinessType.value())){
                //商户充值/续费回调
                MerchantPayfeeRecordModel mchPayFeeRecord = merchantPayfeeRecordService.getByPayNo(wxPayOrderNotifyResult.getOutTradeNo());
                String checkResult = this.checkMchPayNotifyResult(wxPayOrderNotifyResult, mchPayFeeRecord);
                if(StrUtil.isNotBlank(checkResult)){
                    return checkResult;
                }
                merchantPayfeeRecordService.paySuccess(mchPayFeeRecord);
            }else if (MerchantConstant.WxPayBusinessType.MERCHANT_PAY_OPENING_COST.value().equals(wxPayBusinessType.value())){
                //商户支付开通费用回调
                MerchantPayfeeRecordModel mchPayFeeRecord = merchantPayfeeRecordService.getByPayNo(wxPayOrderNotifyResult.getOutTradeNo());
                String checkResult = this.checkMchPayNotifyResult(wxPayOrderNotifyResult, mchPayFeeRecord);
                if(StrUtil.isNotBlank(checkResult)){
                    return checkResult;
                }
                merchantPayfeeRecordService.paySuccess(mchPayFeeRecord);
            }else if(MerchantConstant.WxPayBusinessType.ORDER_TRANSPORT_FEE_PAYMENT.value().equals(wxPayBusinessType.value())){
                OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(wxPayOrderNotifyResult.getOutTradeNo());
                List<OrderDetailModel> orderDetailList = orderDetailService.listByOrderNo(wxPayOrderNotifyResult.getOutTradeNo());
                if (orderInfoModel == null) {
                    log.info("订单号【{}】，不存在",wxPayOrderNotifyResult.getOutTradeNo());
                    return WxPayNotifyResponse.fail("ERROR");
                }
                if (null == orderDetailList || orderDetailList.size() == 0) {
                    log.info("订单号【{}】，明细不存在",wxPayOrderNotifyResult.getOutTradeNo());
                    return WxPayNotifyResponse.fail("ERROR");
                }
                // 判断total_amount是否确实为该订单的实际金额
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (OrderDetailModel orderDetailModel : orderDetailList){
                    totalAmount = totalAmount.add(orderDetailModel.getTransportFee());
                }
                if (!wxPayOrderNotifyResult.getTotalFee().equals(totalAmount.multiply(GlobalConfig.percent).intValue())) {
                    log.info("订单【{}】，金额不一致：{}",wxPayOrderNotifyResult.getOutTradeNo(),totalAmount);
                    return WxPayNotifyResponse.fail("ERROR");
                }
                memberInfoService.exchangePaySuccess(orderInfoModel.getOrderNo(),wxPayOrderNotifyResult.getTransactionId(),TradeWayConstant.PC_WXPAY);
            }
            log.info("\n【微信【{}】回调结果】\n{}", wxPayBusinessType.getLabel(), wxPayOrderNotifyResult);
            return WxPayNotifyResponse.success("OK");
        } catch (WxPayException e) {
            log.info("【微信扫码支付回调数据解析失败】：{}"+ xmlData);
        }
        return WxPayNotifyResponse.fail("ERROR");
    }*/

    private MerchantPayfeeRecordModel mchPayFee(Long merchantId, BigDecimal amount, Integer payType){
        MerchantInfoModel merchantInfoModel = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(merchantInfoModel,"商户不存在!");
        BizSoftwareTypeModel bizSoftwareTypeModel = bizSoftwareTypeService.getSoftTypeById(merchantInfoModel.getSoftTypeId());
        if (MerchantConstant.MerchantPayType.RENEW.value().equals(payType)){
            amount = bizSoftwareTypeModel.getRenewCost();
        }else if (MerchantConstant.MerchantPayType.OPEN_MALL.value().equals(payType)){
            amount = bizSoftwareTypeModel.getOpeningCost();
        }else if(MerchantConstant.MerchantPayType.RECHARGE.value().equals(payType)){
            BigDecimal minRechargeAmount = merchantWalletHistoryLogService.getMerchantMinRechargeAmount(merchantId);
            if(amount.compareTo(minRechargeAmount) == -1){
                throw new DubboException("充值最低金额不能小于" + minRechargeAmount);
            }
        }
        MerchantPayfeeRecordModel merchantPayfeeRecordModel = new MerchantPayfeeRecordModel();
        merchantPayfeeRecordModel.setMerchantId(merchantId);
        merchantPayfeeRecordModel.setPayNo(noGenerator.getMerchantPayNo());
        merchantPayfeeRecordModel.setPayType(payType);
        merchantPayfeeRecordModel.setTradeAmount(amount);
        merchantPayfeeRecordModel.setTradeWay(TradeWayConstant.PC_WXPAY);
        merchantPayfeeRecordModel.setState(MerchantConstant.MerchantPayState.WAIT.value());
        merchantPayfeeRecordModel.setTradeTime(new Date());
        return merchantPayfeeRecordModel;
    }

    private String checkMchPayNotifyResult(WxPayOrderNotifyResult wxPayOrderNotifyResult, MerchantPayfeeRecordModel mchPayFeeRecord){
        // 校验 out_trade_no订单号 是否正确
        if (null == mchPayFeeRecord) {
            log.info("订单号{}不存在", wxPayOrderNotifyResult.getOutTradeNo());
            return WxPayNotifyResponse.fail("ERROR");
        }
        // 判断total_amount是否确实为该订单的实际金额
        BigDecimal totalAmount = new BigDecimal(wxPayOrderNotifyResult.getTotalFee()).divide(GlobalConfig.percent);
        if (mchPayFeeRecord.getTradeAmount().compareTo(totalAmount) != 0) {
            log.info("订单{}，金额{}不一致",wxPayOrderNotifyResult.getOutTradeNo(),totalAmount);
            return WxPayNotifyResponse.fail("ERROR");
        }
        // 处理支付成功逻辑
        mchPayFeeRecord.setTradeOrderNo(wxPayOrderNotifyResult.getTransactionId());
        return null;
    }
}
