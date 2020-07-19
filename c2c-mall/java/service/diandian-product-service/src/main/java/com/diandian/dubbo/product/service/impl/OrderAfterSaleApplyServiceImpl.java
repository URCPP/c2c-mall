package com.diandian.dubbo.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.order.OrderAfterSaleApplyDTO;
import com.diandian.dubbo.facade.model.order.OrderAfterSaleApplyModel;
import com.diandian.dubbo.facade.service.order.OrderAfterSaleApplyService;
import com.diandian.dubbo.product.mapper.OrderAfterSaleApplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 订单售后
 *
 * @author yqingyuan
 * @date 2019/03/05
 */
@Service("orderAfterSaleApplyService")
@Slf4j
public class OrderAfterSaleApplyServiceImpl implements OrderAfterSaleApplyService {

    @Autowired
    private OrderAfterSaleApplyMapper orderAfterSaleApplyMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Page<OrderAfterSaleApplyDTO> page = new PageWrapper<OrderAfterSaleApplyDTO>(params).getPage();
        IPage<OrderAfterSaleApplyDTO> iPage = orderAfterSaleApplyMapper.listOrderAfterSale(page, params);
        return new PageResult(iPage);
    }

    @Override
    public List<OrderAfterSaleApplyModel> listByOrderDetailId(Long orderDetailId) {
        List<OrderAfterSaleApplyModel> list = orderAfterSaleApplyMapper.selectList(new LambdaQueryWrapper<OrderAfterSaleApplyModel>().eq(OrderAfterSaleApplyModel::getOrderDetailId, orderDetailId).orderByDesc(OrderAfterSaleApplyModel::getCreateTime));
        return list;
    }

    @Override
    public OrderAfterSaleApplyModel getById(Long id) {
        return orderAfterSaleApplyMapper.selectById(id);
    }

    @Override
    public void updateById(OrderAfterSaleApplyModel orderAfterSaleApplyModel) {
        orderAfterSaleApplyMapper.updateById(orderAfterSaleApplyModel);
    }
}
