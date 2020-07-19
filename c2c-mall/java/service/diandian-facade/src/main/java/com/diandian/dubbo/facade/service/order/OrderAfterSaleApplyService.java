package com.diandian.dubbo.facade.service.order;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.order.OrderAfterSaleApplyModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单售后
 *
 * @author yqingyuan
 * @date 2019/03/05
 */
public interface OrderAfterSaleApplyService {
    /**
     * 售后订单列表、分页
     *
     * @param params
     * @return
     */
    PageResult listPage(@Param("params") Map<String, Object> params);

    /**
     * 根据订单详情ID获取订单售后
     *
     * @param orderDetailId
     * @return
     */
    List<OrderAfterSaleApplyModel> listByOrderDetailId(Long orderDetailId);


    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    OrderAfterSaleApplyModel getById(Long id);


    /**
     * 根据ID更新
     *
     * @param orderAfterSaleApplyModel
     */
    void updateById(OrderAfterSaleApplyModel orderAfterSaleApplyModel);

}
