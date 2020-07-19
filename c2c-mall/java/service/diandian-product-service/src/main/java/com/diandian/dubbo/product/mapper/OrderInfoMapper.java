package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.api.query.OrderQueryDTO;
import com.diandian.dubbo.facade.dto.api.result.MchIntegralOrderAmountResultDTO;
import com.diandian.dubbo.facade.dto.api.result.OrderDetailResultDTO;
import com.diandian.dubbo.facade.dto.order.OrderInfoQueryDTO;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.vo.StatisticsByDayVO;
import com.diandian.dubbo.facade.vo.StatisticsMerchantSalesVO;
import com.diandian.dubbo.facade.vo.StatisticsSalesOverviewVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantPurchaseOrderVO;
import com.diandian.dubbo.facade.vo.order.OrderExchangeVO;
import com.diandian.dubbo.facade.vo.order.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author zweize
 * @date 2019/02/21
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfoModel> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<OrderInfoModel> listPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 商户采购订单信息
     *
     * @param params
     * @return
     */
    MerchantPurchaseOrderVO countOrder(Map<String, Object> params);

    /**
     * 商户订单分页
     *
     * @param page
     * @param params
     * @return
     */
    IPage<OrderVO> listOrderVOPage(Page<OrderVO> page, @Param("params") Map<String, Object> params);

    /**
     * 商户的兑换订单、分页
     *
     * @param page
     * @param params
     * @return
     */
    IPage<OrderExchangeVO> listExchangeOrderPage(Page<OrderExchangeVO> page, @Param("params") Map<String, Object> params);

    /**
     * 获取订单的详细信息
     *
     * @param params
     * @return
     */
    OrderExchangeVO getExchangeOrderInfo(@Param("params") Map<String, Object> params);

    /**
     * 兑换订单周数据
     *
     * @param params
     * @author: jbuhuan
     * @date: 2019/3/13 22:05
     * @return: R
     */
    List<OrderInfoModel> getExchangeOrderWeek(@Param("params") Map<String, Object> params);

    /**
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.diandian.dubbo.facade.model.order.OrderInfoModel>
     * @Author wubc
     * @Description // 商户采购订单分页列表
     * @Date 21:04 2019/3/18
     * @Param [page, params]
     **/
    IPage<OrderInfoModel> listOrderPage(Page<OrderInfoModel> page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 查询商户销售情况列表
     *
     * @param mchIds
     * @return
     * @author cjunyuan
     * @date 2019/4/12 15:53
     */
    List<StatisticsMerchantSalesVO> listMchSalesByMchIds(@Param("ids") List<Long> mchIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
     * 功能描述: 查询商户的销售概况
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/12 15:53
     */
    StatisticsSalesOverviewVO getMchSalesOverview(@Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 按时间范围统计每天的销售金额
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/4/15 18:24
     */
    List<StatisticsByDayVO> statisticsDailySalesAmount(@Param("dto") OrderInfoQueryDTO dto);

    /**
     *
     * 功能描述: api计算商户订单号数量接口
     *
     * @param merchantId
     * @param mchOrderNo
     * @return
     * @author cjunyuan
     * @date 2019/5/14 20:13
     */
    Integer apiCountByMchOrderNo(@Param("mchId") Long merchantId, @Param("mchOrderNo") String mchOrderNo);

    /**
     *
     * 功能描述: api获取订单详情接口
     *
     * @param mchOrderNo
     * @return
     * @author cjunyuan
     * @date 2019/5/15 15:31
     */
    OrderDetailResultDTO apiGetByMchOrderNo(@Param("mchOrderNo") String mchOrderNo);

    /**
     *
     * 功能描述: 统计商户待付款的积分订单金额
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/5/17 9:38
     */
    List<MchIntegralOrderAmountResultDTO> apiGetIntegralOrderAmountByMch(@Param("merchantId") Long merchantId);

    /**
     * 根据订单号查询下单商户id 去重
     * @author linshuofeng/1275339166@qq.com
     * @date 2019/9/9 0009
     * @param [orderNo 订单号]
     **/
    Long selectByOrderNoDistinct(String orderNo);
}
