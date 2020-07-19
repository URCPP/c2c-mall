package com.diandian.dubbo.facade.service.order;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.api.data.UnifiedOrderDTO;
import com.diandian.dubbo.facade.dto.api.query.OrderQueryDTO;
import com.diandian.dubbo.facade.dto.api.result.OrderDetailResultDTO;
import com.diandian.dubbo.facade.dto.api.result.UnifiedOrderResultDTO;
import com.diandian.dubbo.facade.dto.order.OrderInfoDTO;
import com.diandian.dubbo.facade.dto.order.OrderInfoQueryDTO;
import com.diandian.dubbo.facade.dto.order.OrderNoDTO;
import com.diandian.dubbo.facade.model.member.MemberMerchantRelationModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.vo.StatisticsByDayVO;
import com.diandian.dubbo.facade.vo.StatisticsMerchantSalesVO;
import com.diandian.dubbo.facade.vo.StatisticsSalesOverviewVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantPurchaseOrderVO;
import com.diandian.dubbo.facade.vo.order.OrderExchangeVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zweize
 * @date 2019/02/21
 */
public interface OrderInfoService {

    void updateById(OrderInfoModel orderInfoModel);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    OrderInfoModel getById(Long id);

    /**
     * 商户的订单分页
     * @param params
     * @return
     */
    PageResult listMerchantPage(Map<String,Object> params);

    /**
     * 商户订单统计信息
     * @param merchantInfo
     * @return
     */
    MerchantPurchaseOrderVO countOrder(MerchantInfoModel merchantInfo);

    /**
     * 会员订单统计信息
     * @param member
     * @return
     */
    MerchantPurchaseOrderVO countOrderByMember(MemberMerchantRelationModel member);

    /**
     * 商户的兑换订单、分页
     * @param params
     * @return
     */
    PageResult listExchangeOrderPage(Map<String, Object> params);

    /**
     * 根据订单编号获取订单详情
     * @param orderNo
     * @return
     */
    OrderInfoModel getByOrderNo(String orderNo);

    /**
     *
     * 功能描述: 根据商户订单编号查询订单信息
     *
     * @param mchOrderNo
     * @return
     * @author cjunyuan
     * @date 2019/5/15 15:18
     */
    OrderInfoModel getByMchOrderNo(String mchOrderNo);

    /**
     * 支付成功更新订单状态,增加支付订单表
     * @param orderInfoModel
     * @param tradeNo   支付宝交易号
     */
    void paySuccess(OrderInfoModel orderInfoModel,String tradeNo,String tradeWay);


    /**
     * 获取订单的详细信息
     * @param params
     * @return
     */
    OrderExchangeVO getExchangeOrderInfo(Map<String,Object> params);

    /**
     * 下单
     * @param list
     */
    OrderNoDTO createOrder(List<OrderInfoDTO> list);

    /**
     * 兑换订单周数量
     * @author: jbuhuan
     * @date: 2019/3/13 21:52
     * @param params
     * @return: R
     */
    List<OrderInfoModel> countOrderWeek(Map<String, Object> params);

    /**
     * 更新订单状态
     * @return
     */
    void updateOrderState(String orderNo);

    /**
     * @return com.diandian.dubbo.facade.dto.PageResult
     * @Author wubc
     * @Description // 商户采购订单
     * @Date 19:50 2019/3/18
     * @Param [params]
     **/
    PageResult listMerchantOrderPage(Map<String, Object> params);

    /**
     * @Author wubc
     * @Description // 会员兑换订单列表
     * @Date 15:18 2019/3/22
     * @Param [params]
     * @return com.diandian.dubbo.facade.dto.PageResult
     **/
    PageResult listMemberOrderPage(Map<String,Object> params);

    /**
     *
     * 功能描述: 查询商户销售情况列表
     *
     * @param mchIds
     * @return
     * @author cjunyuan
     * @date 2019/4/12 15:52
     */
    List<StatisticsMerchantSalesVO> listMchSalesByMchIds(List<StatisticsMerchantSalesVO> mchIds, String startTime, String endTime);

    /**
     *
     * 功能描述: 查询商户的销售概况
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/12 15:52
     */
    StatisticsSalesOverviewVO getMchSalesOverview(Map<String, Object> params);

    /**
     *
     * 功能描述: 按时间范围统计每天的销售金额
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/4/15 18:19
     */
    List<StatisticsByDayVO> statisticsDailySalesAmount(OrderInfoQueryDTO dto);

    /**
     *
     * 功能描述: 是否可以下单支付
     *
     * @param orderNo
     * @return
     * @author cjunyuan
     * @date 2019/4/24 18:01
     */
    boolean canPlaceOrder(String orderNo);

    /**
     *
     * 功能描述: 是否可以下兑换订单
     *
     * @param orderNo
     * @return
     * @author cjunyuan
     * @date 2019/4/28 9:47
     */
    boolean canPlaceExchangeOrder(String orderNo);

    /**
     *
     * 功能描述: api统一下单接口
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/14 17:27
     */
//    UnifiedOrderResultDTO apiCreateOrder(UnifiedOrderDTO dto);

    /**
     *
     * 功能描述: 根据商户订单号查询订单
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/15 13:58
     */
    OrderDetailResultDTO apiGetByMchOrderNo(OrderQueryDTO dto);

    /**
     *
     * 功能描述: 统计商户待付款的积分订单金额
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/5/17 9:38
     */
    BigDecimal apiGetIntegralOrderAmountByMch(Long merchantId);
}
