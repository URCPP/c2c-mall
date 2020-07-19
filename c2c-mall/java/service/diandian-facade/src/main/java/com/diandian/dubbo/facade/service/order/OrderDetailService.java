package com.diandian.dubbo.facade.service.order;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.order.*;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: yqingyuan
 * @Date: 2019/2/28 15:32
 * @Version 1.0
 */
public interface OrderDetailService {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 根据订单号获取订单详情列表
     *
     * @param orderNo
     * @return
     */
    List<OrderDetailModel> listByOrderNo(String orderNo);

    /**
     * 根据订单号和状态获取订单详情列表
     *
     * @param orderNo
     * @param state
     * @return
     */
    List<OrderDetailModel> listByOrderNoAndState(String orderNo, Integer state);


    /**
     * 获取订单待发货及待付款数量
     *
     * @param params
     * @return
     */
    OrderDetailNumDTO getOrderDetailNum(Map<String, Object> params);

    /**
     * 获取兑换订单、采购订单数量
     *
     * @param params
     * @return
     */
    OrderNumDTO getOrderNum(Map<String, Object> params);

    /**
     * 批量修改备注
     *
     * @param ids
     * @param remark
     */
    void updateRemarkByIdBatch(List<Long> ids, String remark);

    /**
     * 批量修改状态为已发货
     *
     * @param ids
     */
    void updateStateSend(List<Long> ids);

    /**
     * 根据订单编号
     *
     * @param orderNo
     */
    int updateStateByOrderNo(String orderNo, Integer oldState, Integer newState);

    /**
     * 根据订单状态，分润标识获取列表
     *
     * @param dto
     * @return
     */
    List<OrderDetailDTO> listByState(OrderDetailQueryDTO dto);

    void updateById(OrderDetailModel orderDetailModel);

    /**
     * 获取运输费用总和
     *
     * @param orderNo
     * @return
     */
    BigDecimal totalTransportFee(String orderNo);

    /**
     * @return com.diandian.dubbo.facade.model.order.OrderDetailModel
     * @Author wubc
     * @Description // 根据orderNo 查询订单
     * @Date 9:59 2019/3/25
     * @Param [oNo]
     **/
    List<OrderDetailModel> getOrderDetailListByOrderNo(String oNo);

    /**
     * @return java.lang.Boolean
     * @Author wubc
     * @Description // 确认收货 单个
     * @Date 10:13 2019/3/25
     * @Param [id 订单商品ID]
     **/
    Boolean confirmTake(Long id);

    /**
     * @return java.lang.Boolean
     * @Author wubc
     * @Description // 批量确认收货
     * @Date 11:29 2019/3/25
     * @Param [ids]
     **/
    Boolean batchConfirmTake(Long[] ids);

    /**
     * @Author wubc
     * @Description // 单个发货
     * @Date 21:33 2019/5/15 0015
     * @Param [dto]
     * @return java.lang.Boolean
     **/
    Boolean updateStateSend2(OrderDeliveryDTO dto);

    /**
     *
     * 功能描述: 查询单个订单详情
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/5/16 10:53
     */
    OrderDetailModel getById(Long id);

    /**
     *
     * 功能描述: 查询商户交易列表
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/5/16 16:30
     */
    PageResult listMerchantTradePage(Map<String, Object> params);

    /**
    * @Description:  根据店铺id获取
    * @Param:  createTime，shopId
    * @return:
    * @Author: wsk
    * @Date: 2019/9/12
    */
    List<OrderDetailModel> getByShopId(Date createTime,Long shopId);
}
