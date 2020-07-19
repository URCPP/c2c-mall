package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.order.OrderDetailTransportDTO;
import com.diandian.dubbo.facade.model.order.OrderDetailTransportModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单和快递信息关系表 Mapper 接口
 * </p>
 *
 * @author cjunyuan
 * @since 2019-06-04
 */
public interface OrderDetailTransportMapper extends BaseMapper<OrderDetailTransportModel> {

    /**
     *
     * 功能描述: 根据快递编号和快递公司编码查询
     *
     * @param transportNo
     * @param transportCode
     * @return
     * @author cjunyuan
     * @date 2019/6/4 11:54
     */
    OrderDetailTransportModel getByNoAndCode(@Param("transportNo") String transportNo, @Param("transportCode") String transportCode);

    /**
     *
     * 功能描述: 查询订单的运输信息
     *
     * @param orderDetailId
     * @return
     * @author cjunyuan
     * @date 2019/6/4 15:10
     */
    List<OrderDetailTransportDTO> getOrderTransportList(Long orderDetailId);


}
