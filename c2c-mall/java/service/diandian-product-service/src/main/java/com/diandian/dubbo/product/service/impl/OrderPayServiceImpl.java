package com.diandian.dubbo.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.model.order.OrderPayModel;
import com.diandian.dubbo.facade.service.order.OrderPayService;
import com.diandian.dubbo.product.mapper.OrderPayMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单支付
 *
 * @author zweize
 * @date 2019/03/06
 */
@Service("orderPayService")
@Slf4j
public class OrderPayServiceImpl  implements OrderPayService {

	@Autowired
	private OrderPayMapper orderPayMapper;

    @Override
    public OrderPayModel getByTradeOrderNo(String tradeOrderNo) {
        QueryWrapper<OrderPayModel> qw = new QueryWrapper<>();
        qw.eq("trade_order_no",tradeOrderNo);
        return orderPayMapper.selectOne(qw);
    }

    @Override
    public void save(OrderPayModel orderPayModel) {
        int insert = orderPayMapper.insert(orderPayModel);
        if(insert < 1){
            throw new DubboException("订单支付信息添加失败");
        }
    }
}
