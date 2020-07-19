package com.diandian.dubbo.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.common.ons.AliyunOnsUtil;
import com.diandian.dubbo.common.redis.lock.LockInfo;
import com.diandian.dubbo.common.redis.lock.LockTemplate;
import com.diandian.dubbo.common.util.DateToolUtil;
import com.diandian.dubbo.facade.common.constant.*;
import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.api.data.UnifiedOrderDTO;
import com.diandian.dubbo.facade.dto.api.query.OrderQueryDTO;
import com.diandian.dubbo.facade.dto.api.result.MchIntegralOrderAmountResultDTO;
import com.diandian.dubbo.facade.dto.api.result.OrderDetailResultDTO;
import com.diandian.dubbo.facade.dto.api.result.UnifiedOrderResultDTO;
import com.diandian.dubbo.facade.dto.biz.ProductInfoDTO;
import com.diandian.dubbo.facade.dto.order.*;
import com.diandian.dubbo.facade.model.member.*;
import com.diandian.dubbo.facade.model.merchant.MerchantFreightSetModel;
import com.diandian.dubbo.facade.model.merchant.MerchantProductInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantRecipientsSetModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.model.order.OrderPayModel;
import com.diandian.dubbo.facade.model.product.*;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.model.transport.TransportFeeRuleModel;
import com.diandian.dubbo.facade.model.transport.TransportInfoModel;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.member.MemberInfoService;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.member.MemberRecipientsSetService;
import com.diandian.dubbo.facade.service.merchant.*;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.order.OrderPayService;
import com.diandian.dubbo.facade.service.product.*;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.service.sys.SysPlaceRegionService;
import com.diandian.dubbo.facade.service.transport.TransportInfoService;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import com.diandian.dubbo.facade.vo.ProductStateNumberVO;
import com.diandian.dubbo.facade.vo.StatisticsByDayVO;
import com.diandian.dubbo.facade.vo.StatisticsMerchantSalesVO;
import com.diandian.dubbo.facade.vo.StatisticsSalesOverviewVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantPurchaseOrderVO;
import com.diandian.dubbo.facade.vo.order.OrderDetailExpressInfoVO;
import com.diandian.dubbo.facade.vo.order.OrderExchangeVO;
import com.diandian.dubbo.product.mapper.OrderDetailMapper;
import com.diandian.dubbo.product.mapper.OrderExpressInfoMapper;
import com.diandian.dubbo.product.mapper.OrderInfoMapper;
import com.diandian.dubbo.product.mapper.ProductInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zweize
 * @date 2019/02/21
 */
@Service("orderInfoService")
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderExpressInfoMapper orderExpressInfoMapper;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSkuPriceService productSkuPriceService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private TransportInfoService transportInfoService;
    @Autowired
    private MerchantRecipientsSetService merchantRecipientsSetService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private NoGenerator noGenerator;
    @Autowired
    private LockTemplate lockTemplate;
    @Autowired
    private AliyunOnsUtil aliyunOnsUtil;
    @Autowired
    private MerchantShoppingCartService merchantShoppingCartService;
    @Autowired
    private MemberRecipientsSetService memberRecipientsSetService;
    @Autowired
    private MerchantFreightSetService merchantFreightSetService;
    @Autowired
    private MerchantProductInfoService merchantProductInfoService;
    @Autowired
    private MemberOrderOptLogService memberOrderOptLogService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private ProductShareService productShareService;
    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;
    @Autowired
    private UserConfigurationService userConfigurationService;
    @Value("${softwareTypeId}")
    private Long softwareTypeId;
    @Value("${pusherId}")
    private Long pusherId;


    @Override
    public void updateById(OrderInfoModel orderInfoModel) {
        orderInfoMapper.updateById(orderInfoModel);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String createTime = (String) params.get("createTime");
        if (StrUtil.isNotBlank(createTime) && createTime.contains(StrUtil.COMMA)) {
            String[] createTimeArr = createTime.split(",");
            params.put("createStartTime", createTimeArr[0]);
            params.put("createEndTime", createTimeArr[1]);
        }
        IPage<OrderInfoModel> page = orderInfoMapper.listPage(new PageWrapper<OrderInfoModel>(params).getPage(), params);
        return new PageResult(page);
    }

    @Override
    public OrderInfoModel getById(Long id) {
        AssertUtil.notNull(id, "订单id为空");
        return orderInfoMapper.selectById(id);
    }

    @Override
    public PageResult listMerchantPage(Map<String, Object> params) {
        Page<OrderInfoModel> page = new PageWrapper<OrderInfoModel>(params).getPage();
        Long merchantId = (Long) params.get("merchantId");
        MerchantInfoModel merchant = merchantInfoService.getMerchantInfoById(merchantId);
        if (null == merchant) {
            return new PageResult(page);
        }
        String orderState = (String) params.get("orderState");
        params.put("orderType", BizConstant.OrderType.AMOUNT_ORDER.value());
        params.put("afterSaleFlag", BizConstant.OrderAfterSaleFlag.NORMAL.value());
        IPage<OrderInfoModel> ipage = orderInfoMapper.listOrderPage(page, params);
        List<OrderInfoModel> records = ipage.getRecords();
        if (records.size() > 0) {
            for (int i = 0; i < records.size(); i++) {
                OrderInfoModel orderInfoModel = records.get(i);
                String orderNo = orderInfoModel.getOrderNo();
//                QueryWrapper<OrderDetailModel> qw = new QueryWrapper<>();
//                qw.eq("order_no", orderNo);
//                if (StrUtil.isNotBlank(orderState)) {
//                    qw.eq(ObjectUtil.isNotNull(orderState), "state", Integer.parseInt(orderState));
//                }
//                qw.orderByAsc("state");
                List<OrderDetailModel> orderDetailModels = orderDetailMapper.selectOrderDetailList(orderNo, StrUtil.isNotBlank(orderState) ? Integer.parseInt(orderState) : null);
                orderInfoModel.setDetailList(orderDetailModels);
            }
        }
        return new PageResult(ipage);
    }

    @Override
    public MerchantPurchaseOrderVO countOrder(MerchantInfoModel merchantInfo) {

        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        params.put("orderType", BizConstant.OrderType.AMOUNT_ORDER.value());
        params.put("afterSaleFlag", BizConstant.OrderAfterSaleFlag.NORMAL.value());
        MerchantPurchaseOrderVO vo = orderInfoMapper.countOrder(params);
        return vo;
    }

    @Override
    public MerchantPurchaseOrderVO countOrderByMember(MemberMerchantRelationModel member) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", member.getMerchantId());
        params.put("memberId", member.getMemberId());
        params.put("orderType", BizConstant.OrderType.INTERGRAL_ORDER.value());
        params.put("afterSaleFlag", BizConstant.OrderAfterSaleFlag.NORMAL.value());
        MerchantPurchaseOrderVO vo = orderInfoMapper.countOrder(params);
        return vo;
    }

    @Override
    public PageResult listExchangeOrderPage(Map<String, Object> params) {
        params.put("orderType", BizConstant.OrderType.INTERGRAL_ORDER.value());
        Page<OrderExchangeVO> page = new PageWrapper<OrderExchangeVO>(params).getPage();
        IPage<OrderExchangeVO> iPage = orderInfoMapper.listExchangeOrderPage(page, params);
        List<OrderExchangeVO> records = iPage.getRecords();
        if (null != records) {
            for (OrderExchangeVO record : records) {
                Long memberId = record.getMemberId();
                MemberInfoModel member = memberInfoService.getMemberById(memberId);
                if (null != member) {
                    record.setMemberName(member.getAccountNo());
                }
            }
        }
        return new PageResult(iPage);
    }

    @Override
    public OrderInfoModel getByOrderNo(String orderNo) {
        QueryWrapper<OrderInfoModel> qw = new QueryWrapper<>();
        qw.eq("order_no", orderNo);
        return orderInfoMapper.selectOne(qw);
    }

    @Override
    public OrderInfoModel getByMchOrderNo(String mchOrderNo) {
        QueryWrapper<OrderInfoModel> qw = new QueryWrapper<>();
        qw.eq("mch_order_no", mchOrderNo);
        return orderInfoMapper.selectOne(qw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(OrderInfoModel orderInfoModel, String tradeNo, String tradeWay) {
        log.info("进入支付成功方法 - > {},tradeNo->{},tradeWay{}",orderInfoModel.toString(),tradeNo,tradeWay);
        // 查询支付订单是否存在
        OrderPayModel orderPayModel = orderPayService.getByTradeOrderNo(tradeNo);
        if (null == orderPayModel) {
            log.info("新增支付记录表");
            // 新增支付订单表
            OrderPayModel orderPay = new OrderPayModel();
            orderPay.setOrderId(orderInfoModel.getId());
            orderPay.setOrderNo(orderInfoModel.getOrderNo());
            orderPay.setTradeOrderNo(tradeNo);
            orderPay.setTradeAmount(orderInfoModel.getActualAmount());
            orderPay.setTradeWay(tradeWay);
            orderPay.setState(BizConstant.OrderPayState.PAY_SUCCESS.value());
            orderPay.setTradeTime(new Date());
            orderPayService.save(orderPay);
            log.info("新增支付记录表成功 {}",orderPay.toString());
            // 更新订单状态
            OrderInfoModel updateOrderInfo = new OrderInfoModel();
            updateOrderInfo.setPayState(BizConstant.OrderPayState.PAY_SUCCESS.value());
            UpdateWrapper<OrderInfoModel> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("order_no", orderInfoModel.getOrderNo());
            orderInfoMapper.update(updateOrderInfo, updateWrapper);
            //更新订单详情状态
            orderDetailService.updateStateByOrderNo(orderInfoModel.getOrderNo(), BizConstant.OrderState.NOT_PAY.value(), BizConstant.OrderState.PAYMENT.value());
            this.calculateOrderShare(orderInfoModel.getOrderNo());
            this.addPurchaseRecord(orderInfoModel.getOrderNo());
        }
    }

    /**
     * 计算订单收益、销售返佣和消费返佣
     *
     * @param orderNo
     * @return R
     */
    private void calculateOrderShare(String orderNo){
        UserConfiguration userConfiguration=userConfigurationService.findAll();
        List<OrderDetailModel> list=orderDetailMapper.selectList(new QueryWrapper<OrderDetailModel>().eq("order_no",orderNo));
        for (OrderDetailModel orderDetailModel:list){
            MerchantInfoModel merchantInfoModel=merchantInfoService.getByShopId(orderDetailModel.getShopId());//订单所属商户
            MerchantInfoModel purchaser=merchantInfoService.getByOrderNo(orderNo);//购买者
            BigDecimal totalPrice=orderDetailModel.getPrice().multiply(BigDecimal.valueOf(orderDetailModel.getNum()));
            BigDecimal profit=totalPrice.subtract(totalPrice.multiply(userConfiguration.getSalesServiceMoney()));
            //新增商品收益记录
            merchantAccountInfoService.saveAccount(merchantInfoModel.getId(),profit,1,1,1,RandomUtil.randomNumbers(18),orderDetailModel.getId());
            //新增销售返佣记录
            if(null!=merchantInfoModel.getParentId()){
                MerchantInfoModel parentMerchant=merchantInfoService.getMerchantInfoById(merchantInfoModel.getParentId());
                //上级是商户及以上的新增销售返佣记录
                if(parentMerchant.getLevel()>1){
                    merchantAccountInfoService.saveAccount(parentMerchant.getId(),totalPrice.multiply(userConfiguration.getImmediateSales()),6,1,1,RandomUtil.randomNumbers(18),merchantInfoModel.getId());
                    //上级是商户继续向上分润
                    if (parentMerchant.getLevel()==2){
                        merchantAccountInfoService.subordinateIncome(parentMerchant.getId(),totalPrice.multiply(userConfiguration.getImmediateSales()));
                    }
                }
            }
            //记录消费返佣记录
            if(null!=purchaser.getParentId()){
                MerchantInfoModel purchaserParentMerchant=merchantInfoService.getMerchantInfoById(purchaser.getParentId());
                //上级是商户及以上的新增消费返佣记录
                if(purchaserParentMerchant.getLevel()>1){
                    merchantAccountInfoService.saveAccount(purchaserParentMerchant.getId(),totalPrice.multiply(userConfiguration.getImmediateConsumption()),6,1,1,RandomUtil.randomNumbers(18),merchantInfoModel.getId());
                    //上级是商户继续向上分润
                    if (purchaserParentMerchant.getLevel()==2){
                        merchantAccountInfoService.subordinateIncome(purchaserParentMerchant.getId(),totalPrice.multiply(userConfiguration.getImmediateConsumption()));
                    }
                }
            }
        }
    }

    /**
     * 购买记录和增加冻结金额
     *
     * @param orderNo
     * @return R
     */
    private void addPurchaseRecord(String orderNo){
        List<OrderDetailModel> list=orderDetailMapper.selectList(new QueryWrapper<OrderDetailModel>().eq("order_no",orderNo));
        for (OrderDetailModel orderDetailModel:list){
            MerchantInfoModel purchaser=merchantInfoService.getByOrderNo(orderNo);//购买者
            //购物记录
            merchantAccountInfoService.saveAccount(purchaser.getId(),orderDetailModel.getPrice().multiply(BigDecimal.valueOf(orderDetailModel.getNum())),10,1,2,RandomUtil.randomNumbers(18),orderDetailModel.getId());
            //冻结金额记录
            merchantAccountInfoService.saveAccount(purchaser.getId(),orderDetailModel.getPrice().multiply(BigDecimal.valueOf(orderDetailModel.getNum())),12,2,1,RandomUtil.randomNumbers(18),orderDetailModel.getId());
        }

    }

    /**
     * 计算最优运费
     *
     * @return
     */
    public OrderFreightDTO computeOptimumFreight(ProductInfoModel productInfo, String transportIdStr, Integer areaCode, Integer productNum, Long skuId) {
        OrderFreightDTO orderFreightDTO = new OrderFreightDTO();
        if (StrUtil.isBlank(transportIdStr)) {
            return null;
        }
        List<String> transportStrIds = Arrays.asList(transportIdStr.split(","));
        List<Long> transportIds = transportStrIds.stream().map(v -> Long.valueOf(v)).collect(Collectors.toList());
        List<TransportInfoModel> transportList = transportInfoService.listDetail(transportIds);
        //过滤掉自提和到付
        List<TransportInfoModel> expressList = transportList.stream().filter(el -> el.getTransportType().equals(TransportTypeConstant.EXPRESS.getValue()) || el.getTransportType().equals(TransportTypeConstant.PINKAGE.getValue())).collect(Collectors.toList());
        //检验运输方式
        if (expressList.size() == 0) {
            return null;
        }
        for (TransportInfoModel transport : expressList) {
            ProductSkuStockModel skuStock = productSkuStockService.getBySkuAndRepoId(skuId, transport.getRepositoryId());
            //判断库存是否足够
//            if (ObjectUtil.isNotNull(skuStock) && skuStock.getStock() >= orderInfoDTO.getNum()) {
            //物流快递
            if (transport.getTransportType().equals(TransportTypeConstant.EXPRESS.getValue())) {
                for (TransportFeeRuleModel transportFeeRuleModel : transport.getTransportFeeRuleList()) {
                    boolean isInArea = transportFeeRuleModel.getTransportFeeRuleAreaList().contains(areaCode);
                    if (isInArea) {
                        BigDecimal curFreight = genFreightByFeeType(transportFeeRuleModel, transport.getFeeType(), productNum, productInfo);
                        if ((ObjectUtil.isNull(orderFreightDTO.getFreight()) || orderFreightDTO.getFreight().compareTo(curFreight) > 0)) {
                            orderFreightDTO.setStock(skuStock.getStock());
                            orderFreightDTO.setRepositoryId(skuStock.getRepositoryId());
                            orderFreightDTO.setFreight(curFreight);
                            orderFreightDTO.setTransport(transport);
                        }
                    }
                }
                //包邮
            } else if (transport.getTransportType().equals(TransportTypeConstant.PINKAGE.getValue())) {
                orderFreightDTO.setFreight(new BigDecimal(0));
                orderFreightDTO.setStock(skuStock.getStock());
                orderFreightDTO.setRepositoryId(skuStock.getRepositoryId());
                orderFreightDTO.setTransport(transport);
            }
        }
//        }
        return orderFreightDTO;
    }


    /**
     * 创建金额订单事务
     *
     * @param list
     * @param lockInfoList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderNoDTO createOrderTransaction(List<OrderInfoDTO> list, List<LockInfo> lockInfoList) {

        BigDecimal totalOrderAmount = new BigDecimal(0);
        List<Long> skuIdList = new ArrayList<>();
        MerchantInfoModel merchant = new MerchantInfoModel();
        //生成订单号
        String publicOrderNo=noGenerator.getPublicOrderNo();
        List<Long> productIds = list.stream().map(i -> i.getProductId()).distinct().collect(Collectors.toList());
        List<ProductInfoModel> productInfoModels = productInfoMapper.selectBatchIds(productIds);
        List<Long> shopIds = productInfoModels.stream().map(i -> i.getShopId()).distinct().collect(Collectors.toList());
        for (Long shopId : shopIds) {
            List<OrderInfoDTO> shopList=new ArrayList<>();
            for (OrderInfoDTO orderInfoDTO : list) {
                ProductInfoModel productInfoModel = productInfoMapper.selectById(orderInfoDTO.getProductId());
                if(productInfoModel.getShopId().equals(shopId)){
                    shopList.add(orderInfoDTO);
                }
            }
            //生成其中一个
            String orderNo = noGenerator.getShopOrderNo();
            BigDecimal orderAmount = new BigDecimal(0);
            for (OrderInfoDTO order : shopList) {
                //校验参数有效性
                boolean validParam = ObjectUtil.isNotNull(order.getProductId()) && ObjectUtil.isNotNull(order.getSkuId())
                        && ObjectUtil.isNotNull(order.getRepositoryId()) && ObjectUtil.isNotNull(order.getNum()) && ObjectUtil.isNotNull(order.getMerchantId());
                if (!validParam) {
                    throw new DubboException("提交参数错误");
                }
                //校验购买数量必须大于0
                if (!(order.getNum() > 0)) {
                    throw new DubboException("商品购买数量填写错误");
                }
                //校验商品是否有效
                ProductInfoModel productInfo = productInfoService.getById(order.getProductId());
                if (ObjectUtil.isNull(productInfo) || !BizConstant.STATE_NORMAL.equals(productInfo.getDelFlag())
                        || !BizConstant.PRODUCT_ONSALE.equals(productInfo.getState())) {
                    throw new DubboException("商品已失效");
                }

                //校验收货地址
                Map<String, Object> mchRecvParams = new HashMap<>(1);
                mchRecvParams.put("merchantId", order.getMerchantId());
                List<MerchantRecipientsSetModel> mchRecvList = merchantRecipientsSetService.list(mchRecvParams);
                if (CollectionUtil.isEmpty(mchRecvList)) {
                    throw new DubboException("请先添加收货地址");
                }
                MerchantRecipientsSetModel mchRecv = mchRecvList.get(0);
            /*if (!this.checkTransport(transport, mchRecv)) {
                throw new DubboException("该运输方式无法到达您所在地址");
            }*/
                //校验SKU
                ProductSkuModel sku = productSkuService.getById(order.getSkuId());
                sku.setSkuName(productInfo.getProductName()+"--->"+sku.getSpecName1()+":"+order.getSkuValue());
                if (ObjectUtil.isNull(sku)) {
                    throw new DubboException("商品规格已失效");
                }
                //校验商户是否有效
                merchant = merchantInfoService.getMerchantInfoById(order.getMerchantId());
            /*if (ObjectUtil.isNull(merchant) || !merchant.getApproveFlag().equals(2)) {
                throw new DubboException("商户未认证");
            }
            if (BizConstant.STATE_DISNORMAL.equals(merchant.getDisabledFlag())) {
                throw new DubboException("商户已被禁用");
            }*/
                //redis锁+数据库乐观锁扣减库存
                try {
                    LockInfo stockLock = lockTemplate.lock(String.format("ORDER_STOCK_%s_%s",
                            order.getSkuId(), order.getRepositoryId()), 15000, 8000);
                    log.info("stockLock={}", JSON.toJSONString(stockLock));
                    if (ObjectUtil.isNull(stockLock)) {
                        throw new DubboException("系统繁忙,请稍后再试");
                    }
                    //将lock放入lockList，出现异常统一释放锁
                    lockInfoList.add(stockLock);
                    //判断库存是否足够
                    //ProductSkuStockModel skuStock = productSkuStockService.getBySkuAndRepoId(order.getSkuId(), order.getRepositoryId());
                    if (ObjectUtil.isNull(productInfo) || productInfo.getProductStock() < order.getNum()) {
                        throw new DubboException("部分商品库存不足");
                    }

                    //构建并保存订单详情实体

                    OrderDetailModel orderDetailModel = this.buildOrderDetailModel(orderNo, productInfo, sku, productInfo.getSalesPrice(), order.getNum(), new BigDecimal(0));
                    orderDetailModel.setRecvName(mchRecv.getConcactName());
                    orderDetailModel.setRecvPhone(mchRecv.getConcactPhone());
                    StringBuilder addrSb = new StringBuilder();
                    addrSb.append(mchRecv.getProvinceName()).append(StrUtil.SPACE)
                            .append(mchRecv.getCityName()).append(StrUtil.SPACE)
                            .append(mchRecv.getAreaName()).append(StrUtil.SPACE)
                            .append(mchRecv.getAddress());
                    orderDetailModel.setRecvAddress(addrSb.toString());
                    //计算订单总价
                    orderAmount = (orderAmount.add(productInfo.getSalesPrice().multiply(
                            new BigDecimal(order.getNum())).add(new BigDecimal(0)))
                            .add(productInfo.getFreight().multiply(BigDecimal.valueOf(order.getNum())))).setScale(2, BigDecimal.ROUND_UP);
                    orderDetailModel.setTransportFee(productInfo.getFreight().multiply(BigDecimal.valueOf(order.getNum())));
                    orderDetailMapper.insert(orderDetailModel);
                    //扣减库存
                    OrderSubStockDTO subStockDTO = new OrderSubStockDTO();
                    subStockDTO.setId(productInfo.getId());
                    subStockDTO.setCurrentStock(productInfo.getProductStock());
                    subStockDTO.setSubNum(order.getNum());
                    boolean subResult = productInfoService.subStock(subStockDTO);
                    if (!subResult) {
                        throw new DubboException("库存更新异常,请稍后再试");
                    }
                    //更新商品销量
                    ProductInfoModel updateProductInfo = new ProductInfoModel();
                    updateProductInfo.setId(productInfo.getId());
                    updateProductInfo.setSaleVolume(ObjectUtil.isNotNull(productInfo.getSaleVolume()) ? productInfo.getSaleVolume() + order.getNum() : order.getNum());
                    productInfoMapper.updateById(updateProductInfo);
                } catch (Exception e) {
                    log.error("下单库存锁异常", e);
                    //异常释放锁
                    lockTemplate.releaseLock(lockInfoList);
                    throw new DubboException("库存异常,请稍后再试");
                }
                skuIdList.add(order.getSkuId());
            }
            //构建并保存订单
            OrderInfoModel orderInfoModel = new OrderInfoModel();
            orderInfoModel.setOrderNo(orderNo);
            orderInfoModel.setOrderAmount(orderAmount);
            orderInfoModel.setActualAmount(orderAmount);
            orderInfoModel.setMerchantId(merchant.getId());
            orderInfoModel.setTreeStr(merchant.getTreeStr());
            orderInfoModel.setMerchantName(merchant.getName());
            orderInfoModel.setMerchantSoftwareTypeId(merchant.getSoftTypeId());
            orderInfoModel.setMerchantSoftwareTypeName(merchant.getSoftTypeName());
            orderInfoModel.setOrderType(0);
            orderInfoModel.setPublicOrderNo(publicOrderNo);
            orderInfoMapper.insert(orderInfoModel);

            totalOrderAmount= totalOrderAmount.add(orderAmount);
        }


/*

        List<OrderInfoDTO> publicList=new ArrayList<>();
        List<OrderInfoDTO> shopList=new ArrayList<>();
        if(list.size()>1){
            for (int i = 0; i < list.size(); i++) {
                for (int j = 1; j < list.size() ; j++) {
                    if(!list.get(i).getShopId().equals(list.get(j).getShopId())) {
                        shopList.add(list.get(i));
                        list=shopList;
                    }else {
                        publicList.add(list.get(i));
                    }
                }
            }
        }
*/


        OrderNoDTO orderNoDTO = new OrderNoDTO();
        orderNoDTO.setOrderAmount(totalOrderAmount);
        orderNoDTO.setOrderNo(publicOrderNo);
        orderNoDTO.setCreateTime(System.currentTimeMillis());
        //删除已下单的购物车商品
        this.deleteShoppingCartGoods(merchant.getId(), skuIdList);
        //发送延迟消息 到期自定关闭订单恢复库存
        /*aliyunOnsUtil.doSendOrderDelayAsync(String.format("orderExpire_%s", orderInfoModel.getId()),
                String.valueOf(orderInfoModel.getId()), RedisConstant.ORDER_DELAY_LOCK_EXPIRE);*/
        return orderNoDTO;
    }

    /**
     * 下单
     *
     * @param list
     */
    @Override
    public OrderNoDTO createOrder(List<OrderInfoDTO> list) {
        log.info("下单请求：{}", JSON.toJSONString(list));
        //提交商品列表不能为空
        if (CollectionUtil.isEmpty(list)) {
            throw new DubboException("请选择结算商品");
        }
        List<LockInfo> lockInfoList = new ArrayList<>();
        OrderNoDTO orderNoDTO = this.createOrderTransaction(list, lockInfoList);
        lockTemplate.releaseLock(lockInfoList);
        return orderNoDTO;
    }

    @Override
    public List<OrderInfoModel> countOrderWeek(Map<String, Object> params) {
        return orderInfoMapper.getExchangeOrderWeek(params);
    }

    @Override
    public PageResult listMerchantOrderPage(Map<String, Object> params) {
        Long merchantId = (Long) params.get("merchantId");
        Long merchantSoftTypeId = (Long) params.get("merchantSoftTypeId");

        return null;
    }

    @Override
    public PageResult listMemberOrderPage(Map<String, Object> params) {
        Page<OrderInfoModel> page = new PageWrapper<OrderInfoModel>(params).getPage();
        Long merchantId = (Long) params.get("merchantId");
        MerchantInfoModel merchant = merchantInfoService.getMerchantInfoById(merchantId);
        if (null == merchant) {
            return new PageResult(page);
        }

        params.put("merchantSoftTypeId", merchant.getSoftTypeId());
        params.put("orderType", BizConstant.OrderType.INTERGRAL_ORDER.value());
        params.put("afterSaleFlag", BizConstant.OrderAfterSaleFlag.NORMAL.value());
        String orderState = (String) params.get("orderState");
        IPage<OrderInfoModel> ipage = orderInfoMapper.listOrderPage(page, params);
        List<OrderInfoModel> records = ipage.getRecords();
        if (records.size() > 0) {
            for (int i = 0; i < records.size(); i++) {
                OrderInfoModel orderInfoModel = records.get(i);
                String orderNo = orderInfoModel.getOrderNo();
                QueryWrapper<OrderDetailModel> qw = new QueryWrapper<>();
                qw.eq("order_no", orderNo);
                if (StrUtil.isNotBlank(orderState)) {
                    qw.eq(ObjectUtil.isNotNull(orderState), "state", Integer.parseInt(orderState));
                }
                qw.orderByAsc("state");
                List<OrderDetailModel> orderDetailModels = orderDetailMapper.selectList(qw);
                if (orderDetailModels.size() > 0) {
                    for (OrderDetailModel orderDetailModel : orderDetailModels) {
                        Long skuId = orderDetailModel.getSkuId();
                        MemberOrderOptLogModel orderOptLog = memberOrderOptLogService.getOrderOptLogByOrderNo(orderNo);
                        orderDetailModel.setMemberOrderOptLog(orderOptLog);
//                        MerchantProductInfoModel productInfoModel =  merchantProductInfoService.getProductByMidAndSid(merchantId,skuId);
//                        orderDetailModel.setMerchantProductInfoModel(productInfoModel);
                    }
                }
                orderInfoModel.setDetailList(orderDetailModels);
            }
        }
        return new PageResult(ipage);
    }

    /**
     * 构建订单详情实体
     *
     * @param orderNo
     * @param productInfo
     * @param sku
     * @param //transport
     * @param price
     * @param productNum
     * @param freight
     * @return
     */
    public OrderDetailModel buildOrderDetailModel(String orderNo,
                                                  ProductInfoModel productInfo,
                                                  ProductSkuModel sku,
                                                  BigDecimal price,
                                                  Integer productNum,
                                                  BigDecimal freight) {
        //构建并保存订单详情实体

        OrderDetailModel orderDetailModel = new OrderDetailModel();
        orderDetailModel.setOrderNo(orderNo);
        orderDetailModel.setShopId(productInfo.getShopId());
        orderDetailModel.setShopName(productInfo.getShopName());
        orderDetailModel.setProductId(productInfo.getId());
        if (StrUtil.isNotBlank(productInfo.getImageUrls())) {
            String[] imgArr = productInfo.getImageUrls().split(",");
            orderDetailModel.setProductImageUrl(imgArr[0]);
        }
        orderDetailModel.setSkuId(sku.getId());
        orderDetailModel.setSkuName(sku.getSkuName());
        ArrayList<String> sukArr = new ArrayList<>();
        orderDetailModel.setSpecInfo(CollectionUtil.join(sukArr, ","));
        orderDetailModel.setPrice(price);
        orderDetailModel.setNum(productNum);
        orderDetailModel.setTransportFee(freight);
        return orderDetailModel;
    }

    /**
     * 删除购物车商品
     *
     * @param merchantId
     * @param skuIdList
     */
    public void deleteShoppingCartGoods(Long merchantId, List<Long> skuIdList) {
        merchantShoppingCartService.deleteByMchIdAndSkuIdList(merchantId, skuIdList);
    }

    /**
     * 填充规格
     *
     * @param list
     * @param key
     * @param value
     */
    public void buildSpec(List list, String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            list.add(key + ":" + value);
        }
    }

    /**
     * 检查运输方式
     *
     * @param transport
     * @param merchantRecipientsSetModel
     * @return
     */
    public boolean checkTransport(TransportInfoModel transport, MerchantRecipientsSetModel merchantRecipientsSetModel) {
        if (transport == null) {
            return false;
        }
        Integer transportType = transport.getTransportType();
        if (transportType.equals(TransportTypeConstant.EXPRESS.getValue())) {
            return checkTransportByExpress(transport, merchantRecipientsSetModel);
        } else {
            return true;
        }
    }

    /**
     * 检查物流快递是否能到达商户所在地
     *
     * @param transport
     * @param merchantRecipientsSetModel
     * @return
     */
    public boolean checkTransportByExpress(TransportInfoModel transport, MerchantRecipientsSetModel merchantRecipientsSetModel) {
        int len = transport.getTransportFeeRuleList().size();
        for (int i = 0; i < len; i++) {
            TransportFeeRuleModel transportFeeRuleModel = transport.getTransportFeeRuleList().get(i);
            boolean isInArea = transportFeeRuleModel.getTransportFeeRuleAreaList().contains(merchantRecipientsSetModel.getAreaCode());
            if (isInArea) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算运费
     *
     * @param transport
     * @param merchantRecipientsSetModel
     * @param productNum
     * @param productInfoModel
     * @return
     */
    public BigDecimal genFreight(TransportInfoModel transport, MerchantRecipientsSetModel merchantRecipientsSetModel, Integer productNum, ProductInfoModel productInfoModel) {
        Integer transportType = transport.getTransportType();
        List<String> transportIdList = Arrays.asList(productInfoModel.getTransportIds().split(","));
        if (transportIdList.indexOf(transport.getId().toString()) == -1) {
            throw new DubboException("商品【" + productInfoModel.getProductName() + "】不支持【" + transport.getTransportName() + "】运输方式，请重新选择");
        }
        List<String> freeShippingNumList = Arrays.asList(productInfoModel.getFreeShippingNum().split(","));
        if (transportIdList.size() != freeShippingNumList.size()) {
            throw new DubboException("商品信息配置错误");
        }
        String freeShippingNumStr = freeShippingNumList.get(transportIdList.indexOf(transport.getId().toString()));
        if (!NumberUtil.isNumber(freeShippingNumStr)) {
            throw new DubboException("商品信息配置错误");
        }
        Integer freeShippingNum = Integer.valueOf(freeShippingNumStr);
        if (freeShippingNum > 0 && productNum >= freeShippingNum) {
            return new BigDecimal(0);
        }
        if (transportType.equals(TransportTypeConstant.EXPRESS.getValue())) {
            int len = transport.getTransportFeeRuleList().size();
            for (int i = 0; i < len; i++) {
                TransportFeeRuleModel transportFeeRuleModel = transport.getTransportFeeRuleList().get(i);
                boolean isInArea = transportFeeRuleModel.getTransportFeeRuleAreaList().contains(merchantRecipientsSetModel.getAreaCode());
                if (isInArea) {
                    return genFreightByFeeType(transportFeeRuleModel, transport.getFeeType(), productNum, productInfoModel);
                }
            }
            return null;
        } else {
            return new BigDecimal(0);
        }
    }

    /**
     * 按计费类型计算运费
     *
     * @param rule
     * @param feeType
     * @param productNum
     * @param productInfoModel
     * @return
     */
    public BigDecimal genFreightByFeeType(TransportFeeRuleModel rule, Integer feeType, Integer productNum, ProductInfoModel productInfoModel) {
        //'计费类型 0按件 1按重量 2按体积',
        switch (feeType) {
            case 0:
                return genFreightByUnit(rule, new BigDecimal(1), productNum, feeType);
            case 1:
                return genFreightByUnit(rule, productInfoModel.getWeight(), productNum, feeType);
            case 2:
                return genFreightByUnit(rule, productInfoModel.getVolume(), productNum, feeType);
            default:
                return new BigDecimal(0);
        }
    }

    /**
     * 计费模版
     *
     * @param rule
     * @param unit
     * @param productNum
     * @return
     */
    public BigDecimal genFreightByUnit(TransportFeeRuleModel rule, BigDecimal unit, Integer productNum, Integer feeType) {
        BigDecimal num = new BigDecimal(productNum);
        BigDecimal totalValue = unit.multiply(num);
        BigDecimal freight;
        if (FeeTypeConstant.VOLUME.getValue().equals(feeType)) {
            if (unit.compareTo(rule.getFirstValue()) == -1) {
                freight = rule.getFirstFee();
            } else {
                freight = rule.getExtFee().divide(rule.getExtValue()).multiply(totalValue).setScale(0, BigDecimal.ROUND_UP);
            }
        } else if (totalValue.compareTo(rule.getFirstValue()) <= 0) {
            freight = rule.getFirstFee();
        } else {
            BigDecimal extValue = (totalValue.subtract(rule.getFirstValue())).divide(rule.getExtValue(), 0, BigDecimal.ROUND_UP);
            BigDecimal extFee = rule.getExtFee().multiply(extValue);
            freight = rule.getFirstFee().add(extFee);
        }
        return freight.add(rule.getInsurance());
    }

    @Override
    public OrderExchangeVO getExchangeOrderInfo(Map<String, Object> params) {
        OrderExchangeVO exchangeOrderInfo = orderInfoMapper.getExchangeOrderInfo(params);
        if(ObjectUtil.isNotNull(exchangeOrderInfo.getAddPrice())){
            BigDecimal price = exchangeOrderInfo.getPrice();
            BigDecimal addPrice = new BigDecimal(0);
            String orderNo = exchangeOrderInfo.getOrderNo();
            List<OrderDetailModel> orderDetailModels = orderDetailMapper.selectOrderDetailList(orderNo, 0);
            for (OrderDetailModel orderDetailModel : orderDetailModels) {
                if(orderDetailModel.getSkuId().equals(exchangeOrderInfo.getSkuId())&&ObjectUtil.isNotNull(orderDetailModel.getAddPrice())){
                    addPrice = orderDetailModel.getAddPrice();
                }
            }
            price = price.add(addPrice);
            exchangeOrderInfo.setPrice(price);
        }
        exchangeOrderInfo.setExpressInfoList(orderExpressInfoMapper.getOrderDetailExpressInfo(exchangeOrderInfo.getId()));
        return exchangeOrderInfo;
    }

    /**
     * 更新订单状态
     *
     * @param orderNo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderState(String orderNo) {
        OrderInfoModel orderInfoModel = new OrderInfoModel();
        orderInfoModel.setPayState(BizConstant.OrderPayState.PAY_SUCCESS.value());
        int i = orderInfoMapper.update(orderInfoModel, new QueryWrapper<OrderInfoModel>().eq("order_no", orderNo));
        OrderDetailModel orderDetailModel = new OrderDetailModel();
        orderDetailModel.setState(BizConstant.OrderState.PAYMENT.value());
        orderDetailModel.setPayTime(new Date());
        int j = orderDetailMapper.update(orderDetailModel, new QueryWrapper<OrderDetailModel>().eq("order_no", orderNo));
        if (i <= 0 || j <= 0) {
            throw new DubboException("订单状态更新失败,orderNo={}", orderNo);
        }
    }

    @Override
    public List<StatisticsMerchantSalesVO> listMchSalesByMchIds(List<StatisticsMerchantSalesVO> list, String startTime, String endTime) {
        List<Long> mchIds = new ArrayList<>();
        for (StatisticsMerchantSalesVO vo : list) {
            mchIds.add(vo.getMerchantId());
        }
        List<StatisticsMerchantSalesVO> resList = orderInfoMapper.listMchSalesByMchIds(mchIds, startTime, endTime);

        for (StatisticsMerchantSalesVO mch : list) {
            for (StatisticsMerchantSalesVO statistics : resList) {
                if (mch.getMerchantId().equals(statistics.getMerchantId())) {
                    mch.setTradeNum(statistics.getTradeNum());
                    mch.setTradeAmount(statistics.getTradeAmount());
                    resList.remove(statistics);
                    break;
                }
            }
        }
        return list;
    }

    @Override
    public StatisticsSalesOverviewVO getMchSalesOverview(Map<String, Object> params) {
        return orderInfoMapper.getMchSalesOverview(params);
    }

    @Override
    public List<StatisticsByDayVO> statisticsDailySalesAmount(OrderInfoQueryDTO dto) {
        AssertUtil.notBlank(dto.getStartTime(), "参数错误");
        AssertUtil.notBlank(dto.getEndTime(), "参数错误");
        List<String> dateList = DateToolUtil.getBetweenDate(DateToolUtil.parseDate(dto.getStartTime()), DateToolUtil.parseDate(dto.getEndTime()));
        List<StatisticsByDayVO> list = new ArrayList<>();
        List<StatisticsByDayVO> statisticsList = orderInfoMapper.statisticsDailySalesAmount(dto);
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            StatisticsByDayVO vo = new StatisticsByDayVO();
            vo.setDate(date);
            vo.setAmount(BigDecimal.ZERO);
            for (StatisticsByDayVO old : statisticsList) {
                if (null != old && date.equals(old.getDate())) {
                    vo.setAmount(old.getAmount());
                    statisticsList.remove(old);
                    break;
                }
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public boolean canPlaceOrder(String orderNo) {
        OrderInfoModel oldOrderInfo = this.getByOrderNo(orderNo);
        if (null == oldOrderInfo) {
            log.info("订单编号为【{}】的订单信息不存在：" + orderNo);
            return false;
        }
        ProductStateNumberVO vo = orderDetailMapper.statisticsProductStateInOrder(orderNo);
        return vo.getNormalCnt().equals(vo.getTotal());
    }

    @Override
    public boolean canPlaceExchangeOrder(String orderNo) {
        OrderInfoModel oldOrderInfo = this.getByOrderNo(orderNo);
        if (null == oldOrderInfo) {
            log.info("订单编号为【{}】的订单信息不存在：" + orderNo);
            return false;
        }
        ProductStateNumberVO vo = orderDetailMapper.statisticsProductStateInOrder(orderNo);
        if (!vo.getNormalCnt().equals(vo.getTotal())) {
            return false;
        }
        List<Long> productIds = orderDetailMapper.getProductIdListByOrderNo(orderNo);
        AssertUtil.isTrue(null != productIds && productIds.size() > 0, "订单信息异常");
        ProductStateNumberVO mch = merchantProductInfoService.statisticsProductState(oldOrderInfo.getMerchantId(), productIds);
        if (!mch.getTotal().equals(productIds.size())) {
            return false;
        }
        if (!mch.getTotal().equals(mch.getNormalCnt())) {
            return false;
        }
        return true;
    }

    @Override
    public OrderDetailResultDTO apiGetByMchOrderNo(OrderQueryDTO dto) {
        return orderInfoMapper.apiGetByMchOrderNo(dto.getMchOrderNo());
    }

    @Override
    public BigDecimal apiGetIntegralOrderAmountByMch(Long merchantId) {
        List<MchIntegralOrderAmountResultDTO> list = orderInfoMapper.apiGetIntegralOrderAmountByMch(merchantId);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (MchIntegralOrderAmountResultDTO item : list) {
            totalAmount = totalAmount.add(item.getPrice().multiply(new BigDecimal(item.getNum())).add(item.getServiceFee()).add(item.getTransportFee()));
        }
        return totalAmount;
    }

}
