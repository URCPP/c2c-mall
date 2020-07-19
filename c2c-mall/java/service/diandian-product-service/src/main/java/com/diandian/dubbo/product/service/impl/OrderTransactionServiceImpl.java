package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.diandian.dubbo.common.redis.lock.LockInfo;
import com.diandian.dubbo.common.redis.lock.LockTemplate;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.order.OrderAddStockDTO;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuStockModel;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.order.OrderTransactionService;
import com.diandian.dubbo.facade.service.product.ProductSkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单事务
 *
 * @author zzhihong
 * @date 2019-03-11
 */
@Service("orderTransactionService")
@Slf4j
public class OrderTransactionServiceImpl implements OrderTransactionService {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private LockTemplate lockTemplate;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private MemberOrderOptLogService memberOrderOptLogService;
    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public void closeOrderAndRecoverStock(String orderNo) {
        List<LockInfo> lockInfoList = new ArrayList<>();
        this.doCloseRecover(orderNo, lockInfoList);
        lockTemplate.releaseLock(lockInfoList);
        log.info("orderNo={},库存已恢复", orderNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void doCloseRecover(String orderNo, List<LockInfo> lockInfoList) {
        List<OrderDetailModel> orderDetailList = orderDetailService.listByOrderNoAndState(orderNo, BizConstant.OrderState.NOT_PAY.value());
        if (CollectionUtil.isEmpty(orderDetailList)) {
            return;
        }
        OrderInfoModel oldOrderInfo = orderInfoService.getByOrderNo(orderNo);
        if (ObjectUtil.isNull(oldOrderInfo)) {
            throw new DubboException(String.format("恢复库存异常,订单信息不存在,orderNo=%s", orderNo));
        }
        if(oldOrderInfo.getOrderType() == 1){
            MemberOrderOptLogModel orderOptLog = memberOrderOptLogService.getOrderOptLogByOrderNo(orderNo);
            if (ObjectUtil.isNotNull(orderOptLog) && !BizConstant.OrderState.NOT_PAY.value().equals(orderOptLog.getOrderState())) {
                throw new DubboException(String.format("恢复库存异常,会员订单操作日志为空,orderNo=%s", orderNo));
            }
        }

        //关闭订单
        int updateResult = orderDetailService.updateStateByOrderNo(orderNo, BizConstant.OrderState.NOT_PAY.value(), BizConstant.OrderState.CLOSE_ORDER.value());
        //恢复库存
        if (orderDetailList.size() != updateResult) {
            throw new DubboException(String.format("恢复库存异常,更新记录数不匹配,orderNo=%s", orderNo));
        }
        //redis锁+乐观锁更新库存
        for (OrderDetailModel orderDetail : orderDetailList) {
            try {
                LockInfo stockLock = lockTemplate.lock(String.format("ORDER_STOCK_%s_%s", orderDetail.getSkuId(), orderDetail.getRepositoryId()), 15000, 5000);
                if (ObjectUtil.isNull(stockLock)) {
                    throw new DubboException(String.format("恢复库存异常,获取锁失败,orderNo=%s", orderNo));
                }
                lockInfoList.add(stockLock);
                //查询当前库存
                /*ProductSkuStockModel skuStock = productSkuStockService.getBySkuAndRepoId(orderDetail.getSkuId(), orderDetail.getRepositoryId());
                if (ObjectUtil.isNull(skuStock)) {
                    throw new DubboException(String.format("恢复库存异常,查询库存失败,orderDetail=%s", JSON.toJSONString(orderDetail)));
                }
                //增加库存
                OrderAddStockDTO addStockDTO = new OrderAddStockDTO();
                addStockDTO.setSkuId(orderDetail.getSkuId());
                addStockDTO.setRepositoryId(orderDetail.getRepositoryId());
                addStockDTO.setCurrentStock(skuStock.getStock());
                addStockDTO.setAddNum(orderDetail.getNum());
                addStockDTO.setOrderNo(orderNo);
                addStockDTO.setOrderDetailId(orderDetail.getId());
                boolean addResult = productSkuStockService.addStockBySkuIdAndRepositoryId(addStockDTO);
                if (!addResult) {
                    throw new DubboException(String.format("恢复库存异常,修改库存失败,orderNo=%s", orderNo));
                }*/
            } catch (Exception e) {
                log.error("恢复库存锁异常", e);
                lockTemplate.releaseLock(lockInfoList);
                throw new DubboException(String.format("库存异常,获取锁失败,orderNo=%s", orderNo));
            }
        }
    }
}
