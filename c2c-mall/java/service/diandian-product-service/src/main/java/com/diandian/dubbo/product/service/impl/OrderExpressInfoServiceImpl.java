package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.facade.dto.api.query.GetExpressInfoListDTO;
import com.diandian.dubbo.facade.dto.api.result.ExpressInfoListResultDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailTransportDTO;
import com.diandian.dubbo.facade.dto.order.OrderExpressInfoDTO;
import com.diandian.dubbo.facade.model.order.OrderDetailTransportModel;
import com.diandian.dubbo.facade.model.order.OrderExpressInfoModel;
import com.diandian.dubbo.facade.service.order.OrderExpressInfoService;
import com.diandian.dubbo.product.mapper.OrderDetailTransportMapper;
import com.diandian.dubbo.product.mapper.OrderExpressInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 订单快递信息表 服务实现类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-15
 */
@Service("orderExpressInfoService")
public class OrderExpressInfoServiceImpl implements OrderExpressInfoService {

    @Autowired
    private OrderExpressInfoMapper orderExpressInfoMapper;

    @Autowired
    private OrderDetailTransportMapper orderDetailTransportMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(String transportNo, String transportCode, Integer state, String status, List<OrderExpressInfoDTO> list) {
        QueryWrapper<OrderDetailTransportModel> qw = new QueryWrapper<>();
        qw.eq("transport_no", transportNo);
        List<OrderDetailTransportModel> orderDetailTransportList = orderDetailTransportMapper.selectList(qw);
        if(CollectionUtil.isNotEmpty(orderDetailTransportList)){
            OrderDetailTransportModel update = new OrderDetailTransportModel();
            for (OrderDetailTransportModel orderDetailTransport : orderDetailTransportList){
                update.setId(orderDetailTransport.getId());
                update.setTransportStatus(status);
                orderDetailTransportMapper.updateById(update);
            }
        }

        List<OrderExpressInfoDTO> oldList = orderExpressInfoMapper.listByTransportNo(transportNo);
        for (OrderExpressInfoDTO newObj : list){
            boolean isAdd = true;
            for (OrderExpressInfoDTO oldObj : oldList){
                if(oldObj.getFtime().equals(newObj.getFtime())){
                    isAdd = false;
                }
            }
            if(isAdd){
                OrderExpressInfoModel orderExpressInfo = new OrderExpressInfoModel();
                orderExpressInfo.setTransportNo(transportNo);
                orderExpressInfo.setTransportCode(transportCode);
                orderExpressInfo.setState(state);
                orderExpressInfo.setContext(newObj.getContext());
                orderExpressInfo.setFtime(newObj.getFtime());
                orderExpressInfo.setAreaCode(newObj.getAreaCode());
                orderExpressInfo.setAreaName(newObj.getAreaName());
                orderExpressInfo.setStatus(newObj.getStatus());
                orderExpressInfo.setTime(newObj.getTime());
                orderExpressInfoMapper.insert(orderExpressInfo);
            }
        }
    }

    @Override
    public ExpressInfoListResultDTO apiGetExpressInfo(GetExpressInfoListDTO dto){
        return orderExpressInfoMapper.apiGetExpressInfo(dto);
    }

    @Override
    public List<OrderDetailTransportDTO> list(Long orderDetailId){
        List<OrderDetailTransportDTO> orderTransportList = orderDetailTransportMapper.getOrderTransportList(orderDetailId);
        for (OrderDetailTransportDTO item : orderTransportList){
            QueryWrapper<OrderExpressInfoModel> qw = new QueryWrapper<>();
            qw.eq("transport_no", item.getTransportNo());
            qw.eq("transport_code", item.getTransportCode());
            item.setExpressList(orderExpressInfoMapper.selectList(qw));
        }
        return orderTransportList;
    }
}
