package com.diandian.admin.business.modules.order;

import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.diandian.admin.business.modules.api.service.Express100Service;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.EnumUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.order.OrderDeliveryDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailQueryDTO;
import com.diandian.dubbo.facade.model.order.OrderAfterSaleApplyModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.order.OrderAfterSaleApplyService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yqingyuan
 * @Date: 2019/2/28 10:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/orderInfo")
@Slf4j
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderAfterSaleApplyService orderAfterSaleApplyService;
    @Autowired
    private Express100Service express100Service;

    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        return RespData.ok(orderInfoService.listPage(params));
    }

    @RequestMapping("/listDetailPage")
    public RespData listDetailByState(@RequestParam Map<String, Object> params) {
        return RespData.ok(orderDetailService.listPage(params));
    }

    @RequestMapping("/getAfterSaleByOrderDetailId")
    public RespData getAfterSaleByOrderDetailId(Long orderDetailId) {
        if (ObjectUtil.isNull(orderDetailId)) {
            throw new BusinessException("订单详情ID不能为空");
        }
        List<OrderAfterSaleApplyModel> list = orderAfterSaleApplyService.listByOrderDetailId(orderDetailId);
        return RespData.ok(CollectionUtil.isEmpty(list) ? new OrderAfterSaleApplyModel() : list.get(0));
    }

    @RequestMapping("/updateAfterSaleState")
    public RespData updateAfterSaleState(Long id, Integer state) {
        OrderAfterSaleApplyModel afterSaleApplyModel = orderAfterSaleApplyService.getById(id);
        if (ObjectUtil.isNull(afterSaleApplyModel)) {
            throw new BusinessException("售后申请记录不存在");
        }
        if (afterSaleApplyModel.getState() > 1) {
            throw new BusinessException("该售后申请已经处理过了");
        }
        OrderAfterSaleApplyModel upModel = new OrderAfterSaleApplyModel();
        upModel.setId(afterSaleApplyModel.getId());
        upModel.setState(state);
        orderAfterSaleApplyService.updateById(upModel);
        return RespData.ok();
    }

    @GetMapping("/getDetailById")
    public RespData getDetailById(Long id) {

        Map<String, Object> resultMap = new HashMap<>();

        OrderInfoModel orderInfo = orderInfoService.getById(id);
        List<OrderDetailModel> orderDetailList = orderDetailService.listByOrderNo(orderInfo.getOrderNo());

        resultMap.put("orderInfo", orderInfo);
        resultMap.put("orderDetailList", orderDetailList);
        return RespData.ok(resultMap);
    }

    /**
     * 批量修改备注
     *
     * @param idList
     * @param remark
     * @return
     */
    @PostMapping("/updateRemarkByIdBatch")
    public RespData updateRemarkByIdBatch(@RequestParam("idList") List<Long> idList, String remark) {
        orderDetailService.updateRemarkByIdBatch(idList, remark);
        return RespData.ok();
    }

    /**
     * 批量发货
     *
     * @param idList
     * @return
     */
    @PostMapping("/updateStateSend")
    public RespData updateStateSend(@RequestParam("idList") List<Long> idList) {
        orderDetailService.updateStateSend(idList);
        return RespData.ok();
    }

    /**
     * 发货
     *
     * @return
     */
    @PostMapping("/updateStateSend2")
    public RespData updateStateSend2(@RequestBody @Valid OrderDeliveryDTO dto) {
        OrderDetailModel detailModel = orderDetailService.getById(dto.getOrderDetailId());
        AssertUtil.notNull(detailModel,"订单不存在");
        boolean subscription = true;
        for (OrderDeliveryDTO.SendInfoDTO transportInfo : dto.getList()){
            if(StrUtil.isBlank(transportInfo.getTransportCode())){
                continue;
            }
            boolean subscriptionResult = express100Service.subscription(transportInfo.getTransportCode(), transportInfo.getTransportNo(), dto.getPhone());
            if(!subscriptionResult){
                subscription = false;
                log.info("快递公司编码为【{}】的单号【{}】订阅失败", transportInfo.getTransportCode(), transportInfo.getTransportCode());
            }
        }
        orderDetailService.updateStateSend2(dto);
        if(!subscription){
            return RespData.fail("部分快递信息查询失败，请联技术人员");
        }
        return RespData.ok();
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return R
     */
    @GetMapping("/exportExcel")
    public void exportExcel(@RequestParam(value = "id") Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        TemplateExportParams params = new TemplateExportParams(
                "doc/template.xlsx");
        OrderInfoModel orderInfo = orderInfoService.getById(id);
        map.put("orderNo",orderInfo.getOrderNo());
        map.put("orderAmount",orderInfo.getOrderAmount());
        map.put("actualAmount",orderInfo.getActualAmount());
        map.put("merchantName",orderInfo.getMerchantName());
        map.put("merchantSoftwareTypeName",orderInfo.getMerchantSoftwareTypeName());
        map.put("orderType",orderInfo.getOrderType() == BizConstant.OrderType.AMOUNT_ORDER.value() ? BizConstant.OrderType.AMOUNT_ORDER.getLable() : BizConstant.OrderType.INTERGRAL_ORDER.getLable());
        map.put("createTime",DateUtil.formatDateTime(orderInfo.getCreateTime()));
        map.put("remark",StrUtil.nullToEmpty(orderInfo.getRemark()));
        List<OrderDetailModel> orderDetailList = orderDetailService.listByOrderNo(orderInfo.getOrderNo());
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(OrderDetailModel orderDetailModel : orderDetailList){
            Map<String,Object> orderDetailMap = new HashMap<>();
            orderDetailMap.put("shopName",orderDetailModel.getShopName());
            orderDetailMap.put("skuName",orderDetailModel.getSkuName());
            orderDetailMap.put("repositoryName",orderDetailModel.getRepositoryName());
            orderDetailMap.put("price",orderDetailModel.getPrice());
            orderDetailMap.put("num",orderDetailModel.getNum());
            orderDetailMap.put("serviceFee",orderDetailModel.getServiceFee());
            orderDetailMap.put("orderNo",orderDetailModel.getOrderNo());
            String state = "";
            if (orderDetailModel.getState() == BizConstant.OrderState.NOT_PAY.value()){
                state = BizConstant.OrderState.NOT_PAY.getLable();
            }else if (orderDetailModel.getState() == BizConstant.OrderState.PAYMENT.value()){
                state = BizConstant.OrderState.PAYMENT.getLable();
            }else if (orderDetailModel.getState() == BizConstant.OrderState.SEND.value()){
                state = BizConstant.OrderState.SEND.getLable();
            }else if (orderDetailModel.getState() == BizConstant.OrderState.RECEIPT.value()){
                state = BizConstant.OrderState.RECEIPT.getLable();
            }else if (orderDetailModel.getState() == BizConstant.OrderState.CLOSE_ORDER.value()){
                state = BizConstant.OrderState.CLOSE_ORDER.getLable();
            }else if (orderDetailModel.getState() == BizConstant.OrderState.ERROR_ORDER.value()){
                state = BizConstant.OrderState.ERROR_ORDER.getLable();
            }
            orderDetailMap.put("state",state);
            orderDetailMap.put("afterSaleFlag",orderDetailModel.getAfterSaleFlag() == BizConstant.OrderAfterSaleFlag.NORMAL.value() ? "无":"有");
            orderDetailMap.put("specInfo",orderDetailModel.getSpecInfo());
            orderDetailMap.put("transport", orderDetailModel.getTransportName()+ orderDetailModel.getTransportFee());
            orderDetailMap.put("total",orderDetailModel.getPrice().multiply(new BigDecimal(orderDetailModel.getNum())).add(orderDetailModel.getServiceFee()).add(orderDetailModel.getTransportFee()));
            orderDetailMap.put("recv","收货人："+orderDetailModel.getRecvName()+"电话："+orderDetailModel.getRecvPhone()+"地址："+orderDetailModel.getRecvAddress());
            orderDetailMap.put("createTime",DateUtil.formatDateTime(orderDetailModel.getCreateTime()));
            mapList.add(orderDetailMap);
        }
        map.put("orderDetailList",mapList);
        modelMap.put(TemplateExcelConstants.FILE_NAME, "订单信息_"+orderInfo.getOrderNo());
        modelMap.put(TemplateExcelConstants.PARAMS, params);
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        PoiBaseView.render(modelMap, request, response,
                TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
    }

    /**
     * 导出 代发货
     * @param modelMap
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/exportWaitSendExcel")
    public void exportWaitSendExcel(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, OrderDetailQueryDTO dto) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        TemplateExportParams params = new TemplateExportParams("doc/waitSend.xlsx");
        String state = request.getParameter("state");
        if(StrUtil.isBlank(state)){
            throw new BusinessException("参数错误");
        }
        BizConstant.OrderState value = EnumUtil.getLabelByValue(BizConstant.OrderState.class, "value", Integer.valueOf(state));
        if(null == value){
            throw new BusinessException("参数错误");
        }
        List<OrderDetailDTO> orderDetailList = orderDetailService.listByState(dto);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(OrderDetailDTO orderDetailDTO : orderDetailList){
            Map<String,Object> orderDetailMap = new HashMap<>();
            orderDetailMap.put("shopName",orderDetailDTO.getShopName());
            orderDetailMap.put("skuName",orderDetailDTO.getSkuName());
            orderDetailMap.put("repositoryName",orderDetailDTO.getRepositoryName());
            orderDetailMap.put("price",orderDetailDTO.getPrice());
            orderDetailMap.put("num",orderDetailDTO.getNum());
            orderDetailMap.put("serviceFee",orderDetailDTO.getServiceFee());
            orderDetailMap.put("orderNo",orderDetailDTO.getOrderNo());
            orderDetailMap.put("state",value.getLable());
            orderDetailMap.put("afterSaleFlag",BizConstant.OrderAfterSaleFlag.NORMAL.value().equals(orderDetailDTO.getAfterSaleFlag()) ? "无":"有");
            orderDetailMap.put("specInfo",orderDetailDTO.getSpecInfo());
            orderDetailMap.put("transport", orderDetailDTO.getTransportName()+ orderDetailDTO.getTransportFee());
            orderDetailMap.put("total",orderDetailDTO.getPrice().multiply(new BigDecimal(orderDetailDTO.getNum())).add(orderDetailDTO.getServiceFee()).add(orderDetailDTO.getTransportFee()));
            orderDetailMap.put("recv","收货人："+orderDetailDTO.getRecvName()+"电话："+orderDetailDTO.getRecvPhone()+"地址："+orderDetailDTO.getRecvAddress());
            orderDetailMap.put("createTime",DateUtil.formatDateTime(orderDetailDTO.getCreateTime()));
            orderDetailMap.put("merchantName",orderDetailDTO.getMerchantName());
            mapList.add(orderDetailMap);
        }
        map.put("stateLabel", value.getLable());
        map.put("orderDetailList",mapList);
        modelMap.put(TemplateExcelConstants.FILE_NAME, value.getLable() + "商品信息_"+ DateUtil.today());
        modelMap.put(TemplateExcelConstants.PARAMS, params);
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        PoiBaseView.render(modelMap, request, response,
                TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
    }

    /**
     * @Description: 商户端分页
     * @Param:  params
     * @return:  RespData
     * @Author: wsk
     * @Date: 2019/8/30
     */
    @RequestMapping("/listPageByMerchant")
    public RespData listPageByMerchant(@RequestParam Map<String, Object> params) {
        return RespData.ok(orderInfoService.listPage(params));
    }

    /*@GetMapping("getData/{shopId}")
    public RespData getData(@PathVariable Long shopId){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        Map map=new HashMap();
        List<OrderDetailModel> orderDetailToday=orderDetailService.getByShopId(new Date(),shopId);
        List<OrderDetailModel> orderDetailYesterday=orderDetailService.getByShopId(date,shopId);

        map.put("today",orderDetailToday.size());
        map.put("yesterday",orderDetailYesterday.size());

    }*/

}
