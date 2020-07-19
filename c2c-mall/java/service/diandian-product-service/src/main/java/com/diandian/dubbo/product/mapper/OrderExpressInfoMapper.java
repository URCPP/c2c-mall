package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.api.query.GetExpressInfoListDTO;
import com.diandian.dubbo.facade.dto.api.result.ExpressInfoListResultDTO;
import com.diandian.dubbo.facade.dto.order.OrderExpressInfoDTO;
import com.diandian.dubbo.facade.model.order.OrderExpressInfoModel;
import com.diandian.dubbo.facade.vo.order.OrderDetailExpressInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单快递信息表 Mapper 接口
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-15
 */
public interface OrderExpressInfoMapper extends BaseMapper<OrderExpressInfoModel> {

    /**
     *
     * 功能描述: 查询快递信息
     *
     * @param transportNo
     * @return
     * @author cjunyuan
     * @date 2019/5/15 18:48
     */
    List<OrderExpressInfoDTO> listByTransportNo(@Param("transportNo") String transportNo);

    /**
     *
     * 功能描述: api物流查询接口
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/16 9:09
     */
    ExpressInfoListResultDTO apiGetExpressInfo(@Param("dto") GetExpressInfoListDTO dto);

    /**
     *
     * 功能描述: 查询快递信息（商户平台）
     *
     * @param orderDetailId
     * @return
     * @author cjunyuan
     * @date 2019/5/15 18:48
     */
    List<OrderDetailExpressInfoVO> getOrderDetailExpressInfo(@Param("orderDetailId") Long orderDetailId);
}
