package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.merchant.MerchantOrderTradeDTO;
import com.diandian.dubbo.facade.dto.order.*;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsDetailModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAddPriceModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderDetailTransportModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.biz.BizFinancialRecordsDetailService;
import com.diandian.dubbo.facade.service.merchant.MerchantAddPriceService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.product.ProductShareService;
import com.diandian.dubbo.facade.service.product.ProductSkuService;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.product.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: yqingyuan
 * @Date: 2019/2/28 15:41
 * @Version 1.0
 */
@Service("orderDetailService")
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderDetailTransportMapper orderDetailTransportMapper;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductShareService productShareService;
    @Autowired
    private MerchantAddPriceService merchantAddPriceService;
    @Autowired
    private BizFinancialRecordsDetailService bizFinancialRecordsDetailService;
    @Value("${softwareTypeId}")
    private Long softwareTypeId;


    @Override
    public PageResult listPage(Map<String, Object> params) {
        String createTime = (String) params.get("createTime");
        if (StrUtil.isNotBlank(createTime) && createTime.contains(",")) {
            String[] createTimeArr = createTime.split(",");
            params.put("startTime", createTimeArr[0]);
            params.put("endTime", createTimeArr[1]);
        }
        IPage<OrderDetailDTO> page = orderDetailMapper.listPage(new PageWrapper<OrderDetailModel>(params).getPage(), params);
        List<OrderDetailDTO> records = page.getRecords();
        for (OrderDetailDTO orderDetailDTO : records) {
            BigDecimal addPrice = orderDetailDTO.getAddPrice();
            if(ObjectUtil.isNotNull(addPrice)){
                BigDecimal price = orderDetailDTO.getPrice();
                Integer num = orderDetailDTO.getNum();
                price = price.add(addPrice.multiply(BigDecimal.valueOf(num)));
                orderDetailDTO.setPrice(price);
            }
        }
        page.setRecords(records);
        return new PageResult(page);
    }

    @Override
    public List<OrderDetailModel> listByOrderNo(String orderNo) {
        AssertUtil.notNull(orderNo, "订单号为空");
        QueryWrapper<OrderDetailModel> qw = new QueryWrapper<>();
        qw.orderByDesc("create_time");
        qw.eq("order_no", orderNo);
        return orderDetailMapper.selectList(qw);
    }

    @Override
    public List<OrderDetailModel> listByOrderNoAndState(String orderNo, Integer state) {
        LambdaQueryWrapper<OrderDetailModel> qw = new LambdaQueryWrapper<>();
        qw.eq(OrderDetailModel::getState, state);
        qw.eq(OrderDetailModel::getOrderNo, orderNo);
        return orderDetailMapper.selectList(qw);
    }

    @Override
    public OrderDetailNumDTO getOrderDetailNum(Map<String, Object> params) {
        return orderDetailMapper.getOrderDetailNum(params);
    }

    @Override
    public OrderNumDTO getOrderNum(Map<String, Object> params) {
        return orderDetailMapper.getOrderNum(params);
    }

    @Override
    public void updateRemarkByIdBatch(List<Long> ids, String remark) {
        OrderDetailModel orderDetailModel = new OrderDetailModel();
        orderDetailModel.setRemark(remark);
        orderDetailMapper.update(orderDetailModel, new LambdaQueryWrapper<OrderDetailModel>().in(OrderDetailModel::getId, ids));
    }

    @Override
    public void updateStateSend(List<Long> ids) {
        List<OrderDetailModel> orderDetailModels = orderDetailMapper.selectBatchIds(ids);
        for (OrderDetailModel model : orderDetailModels) {
            AssertUtil.isTrue(model.getState().equals(BizConstant.OrderState.PAYMENT.value()), "订单不是待发货状态，不能发货");
        }
        orderDetailMapper.updateStateByIdBatch(ids, BizConstant.OrderState.PAYMENT.value(), BizConstant.OrderState.SEND.value(),new Date());
    }

    @Override
    public int updateStateByOrderNo(String orderNo, Integer oldState, Integer newState) {
        Integer result = orderDetailMapper.updateStateByOrderNo(orderNo, oldState, newState,new Date());
        return ObjectUtil.isNull(result) ? 0 : result;
    }

    @Override
    public List<OrderDetailDTO> listByState(OrderDetailQueryDTO dto) {
        return orderDetailMapper.getOrderDetailDTOList(dto);
    }

    @Override
    public void updateById(OrderDetailModel orderDetailModel) {
        orderDetailMapper.updateById(orderDetailModel);
    }

    @Override
    public BigDecimal totalTransportFee(String orderNo) {
        return orderDetailMapper.totalTransportFee(orderNo);
    }

    @Override
    public List<OrderDetailModel> getOrderDetailListByOrderNo(String oNo) {
        QueryWrapper<OrderDetailModel> qw = new QueryWrapper<>();
        qw.eq("order_no", oNo);
        return orderDetailMapper.selectList(qw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean confirmTake(Long id) {
        if (null == id) {
            throw new DubboException("订单不存在");
        }
        OrderDetailModel detailModel = orderDetailMapper.selectById(id);
        if (null == detailModel) {
            throw new DubboException("订单不存在");
        }
        OrderInfoModel orderInfoModel=orderInfoMapper.selectOne(
                new QueryWrapper<OrderInfoModel>()
                        .eq("order_no",detailModel.getOrderNo())
        );
        //如果是分享加价的商品，则把商品加价给分享人
        if(ObjectUtil.isNotNull(orderInfoModel.getAddPrice())&&orderInfoModel.getAddPrice().compareTo(BigDecimal.ZERO)==1){
            MerchantAddPriceModel merchantAddPriceModel = new MerchantAddPriceModel();
            merchantAddPriceModel.setOrderDetailId(id);
            merchantAddPriceModel.setOrderNo(orderInfoModel.getOrderNo());
            merchantAddPriceModel.setAddPrice(orderInfoModel.getAddPrice());
            merchantAddPriceModel.setMerchantId(productShareService.getProductShare(orderInfoModel.getMerchantId(), detailModel.getSkuId()).getMerchantId());
            merchantAddPriceModel.setFlag(2);
            merchantAddPriceService.insert(merchantAddPriceModel);
        }
        ShopInfoModel shopInfoModel=shopInfoService.getById(detailModel.getShopId());
        ProductSkuModel productSkuModel;
        productSkuModel=productSkuService.getSkuById(detailModel.getSkuId(),softwareTypeId);
        Integer num=detailModel.getNum();
        Long shopId=detailModel.getShopId();
        Long productId=detailModel.getProductId();
        QueryWrapper<OrderDetailModel> qw = new QueryWrapper<>();
        qw.eq("id", detailModel.getId());
        qw.eq("state", detailModel.getState());
        detailModel = new OrderDetailModel();
        detailModel.setState(BizConstant.OrderState.RECEIPT.value());
        Date date = new Date();
        detailModel.setConfirmRecvTime(date);
        orderDetailMapper.update(detailModel, qw);
//        orderDetailMapper.updateStateById(detailModel.getId(), detailModel.getState(), BizConstant.OrderState.RECEIPT.value(), new Date());
        BigDecimal money=BigDecimal.ZERO;
        BigDecimal businessProfit=new BigDecimal(1).subtract(shopInfoModel.getAgentProfit().add(shopInfoModel.getFloorPriceProportion()).add(shopInfoModel.getPlatformProfit()));
        BizFinancialRecordsDetailModel financialRecordsDetailModel;
        for (ProductSkuPriceModel productSkuPriceModel:productSkuModel.getPriceList()){
            if(softwareTypeId.equals(productSkuPriceModel.getSoftwareTypeId())){
                money=productSkuPriceModel.getPrice().multiply(businessProfit).multiply(new BigDecimal(num));
            }
        }
        financialRecordsDetailModel=new BizFinancialRecordsDetailModel();
        financialRecordsDetailModel.setShopId(shopId);
        financialRecordsDetailModel.setMoney(money);
        financialRecordsDetailModel.setProductId(productId);
        financialRecordsDetailModel.setState(1);
        financialRecordsDetailModel.setType(1);
        bizFinancialRecordsDetailService.insert(financialRecordsDetailModel);
        return true;
    }

    @Override
    @Transactional
    public Boolean batchConfirmTake(Long[] ids) {
        for (Long id : ids) {
            confirmTake(id);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStateSend2(OrderDeliveryDTO dto) {
        OrderDetailModel upd = new OrderDetailModel();
        upd.setId(dto.getOrderDetailId());
        upd.setState(BizConstant.OrderState.SEND.value());
        upd.setTransmitTime(new Date());
        // 更新发货状态
        orderDetailMapper.updateById(upd);

        //删除所有当前订单的快递信息并重新添加
        QueryWrapper<OrderDetailTransportModel> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("order_detail_id", dto.getOrderDetailId());
        orderDetailTransportMapper.delete(deleteWrapper);
        for (OrderDeliveryDTO.SendInfoDTO transportInfo : dto.getList()){
            OrderDetailTransportModel item = new OrderDetailTransportModel();
            item.setTransportNo(transportInfo.getTransportNo());
            item.setOrderDetailId(dto.getOrderDetailId());
            item.setTransportCode(transportInfo.getTransportCode());
            orderDetailTransportMapper.insert(item);
        }
        return true;
    }

    @Override
    public OrderDetailModel getById(Long id){
        return orderDetailMapper.selectById(id);
    }

    @Override
    public PageResult listMerchantTradePage(Map<String, Object> params){
        Page<MerchantOrderTradeDTO> page = new PageWrapper<MerchantOrderTradeDTO>(params).getPage();
        IPage<MerchantOrderTradeDTO> iPage = orderDetailMapper.listMerchantTradePage(page, params);
        return new PageResult(iPage);
    }

    @Override
    public List<OrderDetailModel> getByShopId(Date createTime, Long shopId) {
        return orderDetailMapper.getByShopId(createTime, shopId);
    }
}
