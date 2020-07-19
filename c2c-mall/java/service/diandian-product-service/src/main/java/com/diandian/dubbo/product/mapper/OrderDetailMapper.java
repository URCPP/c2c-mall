package com.diandian.dubbo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.merchant.MerchantOrderTradeDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailNumDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailQueryDTO;
import com.diandian.dubbo.facade.dto.order.OrderNumDTO;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.vo.ProductStateNumberVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: yqingyuan
 * @Date: 2019/2/28 14:33
 * @Version 1.0
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetailModel> {

    /**
     * 获取订单总量
     *
     * @param params
     * @return
     */
    OrderDetailNumDTO getOrderDetailNum(@Param("params") Map<String, Object> params);

    /**
     * 获取订单总量
     *
     * @param params
     * @return
     */
    OrderNumDTO getOrderNum(@Param("params") Map<String, Object> params);

    /**
     * 批量修改状态
     *
     * @param ids
     */
    void updateStateByIdBatch(@Param("ids") List<Long> ids, @Param("oldState") Integer oldState, @Param("newState") Integer newState,@Param("transmitTime") Date transmitTime);


    /**
     * 批量修改状态
     *
     * @param orderNo
     * @param oldState
     * @param newState
     */
    Integer updateStateByOrderNo(@Param("orderNo") String orderNo, @Param("oldState") Integer oldState, @Param("newState") Integer newState,@Param("payTime")Date payTime );

    /**
     * @return java.lang.Integer
     * @Author wubc
     * @Description // 确认收货
     * @Date 10:42 2019/3/25
     * @Param [id, oldState, newState, now]
     **/
    Integer updateStateById(@Param("id") Long id, @Param("oldState") Integer oldState, @Param("newState") Integer newState, @Param("confirmRecvTime") Date now);

    BigDecimal totalTransportFee(@Param("orderNo") String orderNo);

    /**
     *
     * 功能描述: 查询订单商品列表
     *
     * @param orderNo
     * @param state
     * @return
     * @author cjunyuan
     * @date 2019/4/16 10:27
     */
    List<OrderDetailModel> selectOrderDetailList(@Param("orderNo") String orderNo, @Param("state") Integer state);

    /**
     *
     * 功能描述: 统计订单中的商品状态对应的数量
     *
     * @param orderNo
     * @return
     * @author cjunyuan
     * @date 2019/4/28 10:01
     */
    ProductStateNumberVO statisticsProductStateInOrder(@Param("orderNo") String orderNo);

    /**
     *
     * 功能描述: 获取订单中的商品ID列表
     *
     * @param orderNo
     * @return
     * @author cjunyuan
     * @date 2019/4/28 10:13
     */
    List<Long> getProductIdListByOrderNo(@Param("orderNo") String orderNo);

    /**
     *
     * 功能描述: 查询商户交易列表
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/5/16 16:31
     */
    IPage<MerchantOrderTradeDTO> listMerchantTradePage(Page<MerchantOrderTradeDTO> page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 查询订单明细列表
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/6/5 15:17
     */
    List<OrderDetailDTO> getOrderDetailDTOList(@Param("dto") OrderDetailQueryDTO dto);

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<OrderDetailDTO> listPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询已发货 id list
     * @author linshuofeng/1275339166@qq.com
     * @date 2019/9/6 0006
     **/
    List<Long> selectIds();

    /**
    * @Description: 根据店铺id获取
    * @Param:  createTime，shopId
    * @return:
    * @Author: wsk
    * @Date: 2019/9/12
    */
    List<OrderDetailModel> getByShopId(@Param("createTime")Date createTime,@Param("shopId")Long shopId);

    List<OrderDetailModel> automaticDelivery();

    Integer getCountByProductId(@Param("productId")Long productId,@Param("createTime")Date createTime);
}
