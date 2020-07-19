package com.diandian.dubbo.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderDetailTransportModel;
import com.diandian.dubbo.facade.service.order.OrderDetailTransportService;
import com.diandian.dubbo.product.mapper.OrderDetailMapper;
import com.diandian.dubbo.product.mapper.OrderDetailTransportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.List;

/**
 * <p>
 * 订单和快递信息关系表 服务实现类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-06-04
 */
@Service("orderDetailTransportService")
public class OrderDetailTransportServiceImpl implements OrderDetailTransportService {
}
