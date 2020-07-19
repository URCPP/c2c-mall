package com.diandian.dubbo.facade.service.biz;



import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizCommissionDetailModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.vo.OrgAccountStatementVO;
import com.diandian.dubbo.facade.vo.StatisticsSalesOverviewVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 销售结算明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizCommissionDetailService {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 订单销售分润
     * @param orderDetailModel
     */
    void updateSaleCommissionReward(OrderDetailModel orderDetailModel, BigDecimal taxPoint);

    /**
     * 返还订单销售分润
     * @param orderDetailModel
     */
    void updateReturnCommissionReward(OrderDetailModel orderDetailModel, BigDecimal taxPoint);

    void save(BizCommissionDetailModel bizCommissionDetailModel);

    /**
     *
     * 功能描述: 统计销售佣金流水
     *
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 11:24
     */
    List<OrgAccountStatementVO> statisticsEveryDay(OrgAccountQueryDTO query);

    /**
     *
     * 功能描述: 自定义对象分页查询
     *
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 19:54
     */
    PageResult listPage(OrgAccountQueryDTO query);

    /**
     *
     * 功能描述: 统计机构获得的销售佣金
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/15 14:56
     */
    StatisticsSalesOverviewVO statisticsTotalAmount(Map<String, Object> params);
}
