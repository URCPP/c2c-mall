package com.diandian.admin.merchant.modules.api.controller;

import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.common.util.WebUtil;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.common.config.AliPayConfig;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.TradeWayConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.dto.wx.WeixinPayMpOrderResultDTO;
import com.diandian.dubbo.facade.model.biz.BizPayConfigModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.biz.BizPayConfigService;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.pay.AliPayService;
import com.diandian.dubbo.facade.service.pay.WeixinPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orderPay")
public class OrderPayController extends BaseController {

    @Autowired
    BizPayConfigService bizPayConfigService;
    @Autowired
    AliPayService aliPayService;
    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    WeixinPayService weixinPayService;

    @Value("${spring.profiles.active}")
    private String profilesActive;


    @Value("${weixin.pay.notifyUrl}")
    private String wxPayNotifyUrl;

    /**
     * 支付产品列表
     * @return
     */
    @GetMapping("/list")
    public RespData list(){
        List<BizPayConfigModel> list = bizPayConfigService.list();
        return RespData.ok(list);
    }

    /**
     * 商品下单支付
     * @return
     */
    @PostMapping("/placeOrder")
    public void placeOrder(@RequestParam String orderNo,@RequestParam String payCode, HttpServletResponse httpResponse) throws IOException {
        //判断订单时间是否过期
        OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
        Long minutes = (new Date().getTime() - orderInfoModel.getCreateTime().getTime())/(60*1000);
        if (minutes >= BizConstant.TIMEOUT_EXPRESS) {
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            httpResponse.getWriter().write("订单超时");
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
        minutes = BizConstant.TIMEOUT_EXPRESS - minutes;
        if (TradeWayConstant.PC_ALIPAY.equals(payCode)){
            // 支付宝PC支付
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            //直接将完整的表单html输出到页面
            String html = aliPayService.placeOrder(orderNo,minutes);
            httpResponse.getWriter().write(html);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
        boolean canPlaceOrder = orderInfoService.canPlaceOrder(orderNo);
        if(!canPlaceOrder){
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            httpResponse.getWriter().write("订单存在已下架商品无法进行支付");
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
    }

    /**
     * 手机商品下单支付
     * @return
     */
    @PostMapping("/phoneOrder")
    public void phoneOrder(@RequestParam String orderNo,@RequestParam String payCode, HttpServletResponse httpResponse) throws IOException {
        //判断订单时间是否过期
        OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
        Long minutes = (new Date().getTime() - orderInfoModel.getCreateTime().getTime())/(60*1000);
        if (minutes >= BizConstant.TIMEOUT_EXPRESS) {
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            httpResponse.getWriter().write("订单超时");
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
        minutes = BizConstant.TIMEOUT_EXPRESS - minutes;
        if (TradeWayConstant.PC_ALIPAY.equals(payCode)){
            // 支付宝PC支付
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            //直接将完整的表单html输出到页面
            String html = aliPayService.phoneOrder(orderNo,minutes);
            httpResponse.getWriter().write(html);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
    }

    /**
     * 商户（充值，续费） 支付
     * @param merchantId
     * @param amount
     * @param payCode
     * @param httpResponse
     * @throws IOException
     */
    @PostMapping("/merchantPayFee")
    public void merchantPayFee(@RequestParam Long merchantId, @RequestParam BigDecimal amount,@RequestParam Integer payType, @RequestParam String payCode, HttpServletResponse httpResponse) throws IOException {
        if (TradeWayConstant.PC_ALIPAY.equals(payCode)){
            // 支付宝PC支付
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            //直接将完整的表单html输出到页面
            String html = aliPayService.merchantPayFee(merchantId,amount,payType,payCode);
            httpResponse.getWriter().write(html);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
    }

    /**
     * 手机端 - 商户（充值，续费） 支付
     * @param amount
     * @param payCode
     * @param httpResponse
     * @throws IOException
     */
    @PostMapping("/phoneMerchantPayFee")
    public void phoneMerchantPayFee(@RequestParam BigDecimal amount,@RequestParam Integer payType, @RequestParam String payCode, HttpServletResponse httpResponse) throws IOException {
        if (TradeWayConstant.PC_ALIPAY.equals(payCode)){
            // 支付宝PC支付
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            //直接将完整的表单html输出到页面
                String html = aliPayService.phoneMerchantPayFee(getMerchantInfoId(),amount,payType,payCode);
            httpResponse.getWriter().write(html);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
    }


    /**
     * 商城支付（微信H5支付）
     * @return
     */
    @PostMapping("/wxH5Pay/orderPay")
    public RespData wxH5PayProductGiftPackageOrder(@RequestParam String orderNo, HttpServletRequest request) {
        if ("test".equals(profilesActive)||"dev".equals(profilesActive)){
            OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
            orderInfoService.paySuccess(orderInfoModel, orderNo, TradeWayConstant.PC_WXPAY);
        }
        String ipAddress = WebUtil.getIpAddress(request);
        String result = weixinPayService.h5OrderPay(orderNo, wxPayNotifyUrl, ipAddress);
        return RespData.ok(result);
    }

    /**
     * 积分商城运输费用支付（微信公众号支付）
     * @return
     */
    @PostMapping("/wxMpPay/orderPay")
    public RespData wxMpPayProductGiftPackageOrder(@RequestParam(value = "orderNo") String orderNo, @RequestParam(value = "openId") String openId, HttpServletRequest request) {
        if ("test".equals(profilesActive)||"dev".equals(profilesActive)){
            OrderInfoModel orderInfoModel = orderInfoService.getByOrderNo(orderNo);
            orderInfoService.paySuccess(orderInfoModel, orderNo, TradeWayConstant.PC_WXPAY);
        }
        String ipAddress = WebUtil.getIpAddress(request);
        WeixinPayMpOrderResultDTO resultDTO = weixinPayService.mpOrderPay(orderNo, openId, wxPayNotifyUrl, ipAddress);
        return RespData.ok(resultDTO);
    }

    /**
     * 手机端商户入驻支付保证金
     * @param amount
     * @param payType
     * @param payCode
     * @param httpResponse
     * @throws IOException
     */

    @PostMapping("/paySecurityDeposit")
    public void paySecurityDeposit(@RequestParam BigDecimal amount,@RequestParam Integer payType, @RequestParam String payCode,HttpServletResponse httpResponse)  throws IOException {
       if("test".equals(profilesActive)||"dev".equals(profilesActive)){
           System.out.println("单品质保金:"+amount);
       }
        if (TradeWayConstant.PC_ALIPAY.equals(payCode)){
            // 支付宝PC支付
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            //直接将完整的表单html输出到页面
            String html = aliPayService.phonePaySecurityDeposit(getMerchantInfoId(),amount,payType,payCode);
            httpResponse.getWriter().write(html);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
    }

    @PostMapping("/payMargin")
    public void  payMargin(@RequestParam BigDecimal amount,@RequestParam Integer payType, @RequestParam String payCode,HttpServletResponse httpResponse ) throws IOException{
        if (TradeWayConstant.PC_ALIPAY.equals(payCode)){
            // 支付宝PC支付
            httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
            //直接将完整的表单html输出到页面
            String html = aliPayService.phonePayMargin(getMerchantInfoId(),amount,payType,payCode);
            httpResponse.getWriter().write(html);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        }
    }

}
