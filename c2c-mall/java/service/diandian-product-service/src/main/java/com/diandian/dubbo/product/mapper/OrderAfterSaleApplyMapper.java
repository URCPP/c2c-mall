package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.order.OrderAfterSaleApplyDTO;
import com.diandian.dubbo.facade.model.order.OrderAfterSaleApplyModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 订单售后
 *
 * @author yqingyuan
 * @date 2019/03/05
 */
public interface OrderAfterSaleApplyMapper extends BaseMapper<OrderAfterSaleApplyModel> {
    /**
     * 售后订单列表、分页
     * @param page
     * @param params
     * @return
     */
    IPage<OrderAfterSaleApplyDTO> listOrderAfterSale(Page page, @Param("params") Map<String, Object> params);
}
