package com.diandian.dubbo.facade.service.order;

import com.diandian.dubbo.facade.dto.api.query.GetExpressInfoListDTO;
import com.diandian.dubbo.facade.dto.api.result.ExpressInfoListResultDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailTransportDTO;
import com.diandian.dubbo.facade.dto.order.OrderExpressInfoDTO;

import java.util.List;

/**
 * <p>
 * 订单快递信息表 服务类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-15
 */
public interface OrderExpressInfoService {

    /**
     *
     * 功能描述: 批量保存快递信息
     *
     * @param transportNo
     * @param transportCode
     * @param state
     * @param status
     * @param list
     * @return
     * @author cjunyuan
     * @date 2019/5/15 18:35
     */
    void batchSave(String transportNo, String transportCode, Integer state, String status, List<OrderExpressInfoDTO> list);

    /**
     *
     * 功能描述: api物流查询接口
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/16 9:08
     */
    ExpressInfoListResultDTO apiGetExpressInfo(GetExpressInfoListDTO dto);

    /**
     *
     * 功能描述: 查询订单的运输信息
     *
     * @param orderDetailId
     * @return
     * @author cjunyuan
     * @date 2019/5/21 17:49
     */
    List<OrderDetailTransportDTO> list(Long orderDetailId);
}
