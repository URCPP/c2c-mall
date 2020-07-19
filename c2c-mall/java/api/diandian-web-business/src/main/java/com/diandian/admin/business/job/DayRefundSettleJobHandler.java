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
import java.util.List;


/**
 * 每日退款结算
 *
 * @author x
 * @date 2018/09/29
 */
@JobHandler(value="dayRefundSettleJobHandler")
@Component
@Slf4j
public class DayRefundSettleJobHandler extends IJobHandler {

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
		// 获取已分润并已退货订单
		OrderDetailQueryDTO dto = new OrderDetailQueryDTO();
		dto.setShareFlag(BizConstant.OrderShareFlag.SHARE.value());
		dto.setAfterSaleFlag(BizConstant.OrderAfterSaleFlag.AFTER_SALE.value());
		List<OrderDetailDTO> orderDetailList = orderDetailService.listByState(dto);
		BizConfigModel config = bizConfigService.getOne();
		for (OrderDetailDTO orderDetailDTO : orderDetailList) {
			OrderInfoModel orderInfo = orderInfoService.getByOrderNo(orderDetailDTO.getOrderNo());
			if(null == orderInfo || !BizConstant.OrderPayState.PAY_SUCCESS.value().equals(orderInfo.getPayState())){
				log.error("订单数据异常，异常订单：{}", orderDetailDTO);
				continue;
			}
			OrderDetailModel orderDetailModel = orderDetailService.getById(orderDetailDTO.getId());
            //处理退款，佣金返还
			bizCommissionDetailService.updateReturnCommissionReward(orderDetailModel, config.getTaxPoint());
		}
		return SUCCESS;
	}




}
