package com.diandian.admin.merchant.modules.api.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.common.config.AliPayConfig;
import com.diandian.dubbo.facade.common.constant.TradeWayConstant;
import com.diandian.dubbo.facade.model.merchant.MerchantPayfeeRecordModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantPayfeeRecordService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.pay.WeixinPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/pay/callBack")
@Slf4j
public class PayCallBackController extends BaseController {

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    MerchantInfoService merchantInfoService;
    @Autowired
    MerchantPayfeeRecordService merchantPayfeeRecordService;

    @Autowired
    WeixinPayService weixinPayService;

    /**
     * 支付宝回调
     * @return
     */
    @RequestMapping("/order")
    public void order(HttpServletRequest request,HttpServletResponse response)throws Exception{
        response.setContentType("text/html;charset=" + AliPayConfig.charset);
        // 将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = convertRequestParamsToMap(request);
        String paramsJson = JSON.toJSONString(params);
        log.info("支付宝回调，{}", paramsJson);
        try {
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipayPublicKey, AliPayConfig.charset, AliPayConfig.signType);
            if (signVerified) {
                log.info("支付宝回调签名认证成功");
                // 校验 out_trade_no订单号 是否正确
                String outTradeNo = params.get("out_trade_no");
                OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(outTradeNo);
                if (orderInfoModel == null) {
                    log.info("订单号{}不存在",outTradeNo);
                    throw new AlipayApiException("out_trade_no错误");
                }
                // 判断total_amount是否确实为该订单的实际金额
                BigDecimal totalAmount = new BigDecimal(params.get("total_amount"));
                if (orderInfoModel.getActualAmount().compareTo(totalAmount) != 0) {
                    log.info("订单{}，金额{}不一致",outTradeNo,totalAmount);
                    throw new AlipayApiException("error total_amount,订单金额不一致!");
                }
                //验证app_id是否为该商户本身
                if (!params.get("app_id").equals(AliPayConfig.appid)) {
                    log.info("app_id{}不一致",params.get("app_id"));
                    throw new AlipayApiException("app_id不一致");
                }
                // 验证交易状态 是否成功
                String tradeStatus = params.get("trade_status");
                if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")){
                    try {
                        // 处理支付成功逻辑
                        orderInfoService.paySuccess(orderInfoModel,params.get("trade_no"),TradeWayConstant.PC_ALIPAY);
                    } catch (Exception e) {
                        log.error("支付宝回调业务处理报错,params:" + paramsJson, e);
                        response.getWriter().println("fail");
                    }
                } else {
                    log.error("没有处理支付宝回调业务，支付宝交易状态：{},params:{}",tradeStatus,paramsJson);
                    response.getWriter().println("fail");
                }
                response.getWriter().println("success");
            } else {
                log.info("支付宝回调签名认证失败，signVerified=false, paramsJson:{}", paramsJson);
                response.getWriter().println("fail");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名认证失败,paramsJson:{},errorMsg:{}", paramsJson, e.getMessage());
            response.getWriter().println("fail");
        }
    }


    /*@RequestMapping("/wxNotify")
    public void productGiftPackageWxPayNotify(@RequestBody String xmlData, HttpServletResponse response) {
        PrintWriter printWriter = null;
        try {
            log.info("【微信支付回调】回调数据={}", xmlData);
            printWriter = response.getWriter();
            String result = weixinPayService.wxPayNotify(xmlData);
            log.info("回调结束 result->{}",result);
            printWriter.write(result);
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != printWriter){
                printWriter.close();
            }
        }
    }*/

    /**
     * 支付宝(商户)回调
     * @return
     */
    /*@RequestMapping("/merchant")
    public void merchant(HttpServletRequest request,HttpServletResponse response)throws Exception{
        response.setContentType("text/html;charset=" + AliPayConfig.charset);
        // 将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = convertRequestParamsToMap(request);
        String paramsJson = JSON.toJSONString(params);
        log.info("支付宝回调，{}", paramsJson);
        try {
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipayPublicKey, AliPayConfig.charset, AliPayConfig.signType);
            if (signVerified) {
                log.info("支付宝回调签名认证成功");
                // 校验 out_trade_no订单号 是否正确
                String outTradeNo = params.get("out_trade_no");
                MerchantPayfeeRecordModel merchantPayfeeRecordModel = merchantPayfeeRecordService.getByPayNo(outTradeNo);
                if (merchantPayfeeRecordModel == null) {
                    log.info("订单号{}不存在",outTradeNo);
                    throw new AlipayApiException("out_trade_no错误");
                }
                // 判断total_amount是否确实为该订单的实际金额
                BigDecimal totalAmount = new BigDecimal(params.get("total_amount"));
                if (merchantPayfeeRecordModel.getTradeAmount().compareTo(totalAmount) != 0) {
                    log.info("订单{}，金额{}不一致",outTradeNo,totalAmount);
                    throw new AlipayApiException("error total_amount,订单金额不一致!");
                }
                //验证app_id是否为该商户本身
                if (!params.get("app_id").equals(AliPayConfig.appid)) {
                    log.info("app_id{}不一致",params.get("app_id"));
                    throw new AlipayApiException("app_id不一致");
                }
                // 验证交易状态 是否成功
                String tradeStatus = params.get("trade_status");
                if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")){
                    try {
                        // 处理支付成功逻辑
                        merchantPayfeeRecordModel.setTradeOrderNo(params.get("trade_no"));
                        merchantPayfeeRecordService.paySuccess(merchantPayfeeRecordModel);
                    } catch (Exception e) {
                        log.error("支付宝回调业务处理报错,params:" + paramsJson, e);
                        response.getWriter().println("fail");
                    }
                } else {
                    log.error("没有处理支付宝回调业务，支付宝交易状态：{},params:{}",tradeStatus,paramsJson);
                    response.getWriter().println("fail");
                }
                response.getWriter().println("success");
            } else {
                log.info("支付宝回调签名认证失败，signVerified=false, paramsJson:{}", paramsJson);
                response.getWriter().println("fail");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名认证失败,paramsJson:{},errorMsg:{}", paramsJson, e.getMessage());
            response.getWriter().println("fail");
        }
    }*/

    /**
     * 将request中的参数转换成Map
     * @param request
     * @return
     */
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<>();
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;
            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }
        return retMap;
    }

}
