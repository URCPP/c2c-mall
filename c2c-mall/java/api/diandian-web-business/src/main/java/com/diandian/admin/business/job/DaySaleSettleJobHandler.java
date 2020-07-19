package com.diandian.admin.business.job;

import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.order.OrderDetailDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizConfigModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.service.biz.BizCommissionDetailService;
import com.diandian.dubbo.facade.service.biz.BizConfigService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 每日销售结算
 *
 * @author x
 * @date 2018/09/29
 */
@JobHandler(value="daySaleSettleJobHandler")
@Component
@Slf4j
public class DaySaleSettleJobHandler extends IJobHandler {

	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	BizCommissionDetailService bizCommissionDetailService;

	@Autowired
	OrderInfoService orderInfoService;

	@Autowired
	private BizConfigService bizConfigService;
	@Override
	public ReturnT<String> execute(String param) throws Exception {
        //获取订单完成，未分润的订单详情
		OrderDetailQueryDTO dto = new OrderDetailQueryDTO();
		dto.setState(BizConstant.OrderState.RECEIPT.value());
		dto.setShareFlag(BizConstant.OrderShareFlag.NOT_SHARE.value());
		dto.setAfterSaleFlag(BizConstant.OrderAfterSaleFlag.NORMAL.value());
		List<OrderDetailDTO> orderDetailModelList = orderDetailService.listByState(dto);
		BizConfigModel config = bizConfigService.getOne();
		for (OrderDetailDTO orderDetailDTO : orderDetailModelList){
            OrderInfoModel orderInfo = orderInfoService.getByOrderNo(orderDetailDTO.getOrderNo());
            if(null == orderInfo || !BizConstant.OrderPayState.PAY_SUCCESS.value().equals(orderInfo.getPayState())){
                log.error("订单数据异常，异常订单：{}", orderDetailDTO);
                continue;
            }
            OrderDetailModel orderDetailModel = orderDetailService.getById(orderDetailDTO.getId());
            //处理销售分润
			bizCommissionDetailService.updateSaleCommissionReward(orderDetailModel, config.getTaxPoint());
		}
		return SUCCESS;
	}

}
