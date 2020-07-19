package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizFinancialRecordsDetailMapper;
import com.diandian.dubbo.business.mapper.BizFinancialRecordsMapper;
import com.diandian.dubbo.business.mapper.OrderDetailMapper;
import com.diandian.dubbo.business.mapper.ShopInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.biz.BizFinancialRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:
 * @author: wsk
 * @create: 2019-08-31 16:17
 */
@Service("bizFinancialRecordsService")
@Slf4j
public class BizFinancialRecordsServiceImpl implements BizFinancialRecordsService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShopInfoMapper shopInfoMapper;
    @Autowired
    private BizFinancialRecordsMapper bizFinancialRecordsMapper;
    @Autowired
    private BizFinancialRecordsDetailMapper bizFinancialRecordsDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordShopProfit() throws ParseException {
        log.info("财务记录任务执行");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Map<String,Object> incomeMap;
        Map<String,Object> expenditureMap;
        BizFinancialRecordsModel bizFinancialRecordsModel;
        List<ShopInfoModel> list=shopInfoMapper.selectList(
                new QueryWrapper<ShopInfoModel>()
                        .eq("del_flag", BizConstant.STATE_NORMAL)
                        .eq("state",1)
        );
        for (ShopInfoModel shopInfoModel:list){
            bizFinancialRecordsModel=new BizFinancialRecordsModel();
            incomeMap=bizFinancialRecordsDetailMapper.getByShopId(shopInfoModel.getId(),calendar.getTime(),1);
            expenditureMap=bizFinancialRecordsDetailMapper.getByShopId(shopInfoModel.getId(),calendar.getTime(),2);
            BigDecimal income=BigDecimal.ZERO;
            Integer incomeCount=0;
            BigDecimal expenditure=BigDecimal.ZERO;
            Integer expenditureCount=0;
            Date date=new Date();
            if(null!=incomeMap){
                if (StrUtil.isNotBlank(incomeMap.get("money").toString())){
                    income=new BigDecimal(incomeMap.get("money").toString());
                    incomeCount=Integer.valueOf(incomeMap.get("count").toString());
                    date=sdf.parse(incomeMap.get("createTime").toString());
                }
            }
            if(null!=expenditureMap){
                if(StrUtil.isNotBlank(expenditureMap.get("money").toString())){
                    expenditure=new BigDecimal(expenditureMap.get("money").toString());
                    expenditureCount=Integer.valueOf(expenditureMap.get("count").toString());
                    date=sdf.parse(expenditureMap.get("createTime").toString());
                }
            }
            bizFinancialRecordsModel.setShopId(shopInfoModel.getId());
            bizFinancialRecordsModel.setIncome(income);
            bizFinancialRecordsModel.setExpenditure(expenditure);
            bizFinancialRecordsModel.setCreateTime(date);
            bizFinancialRecordsModel.setIncomeCount(incomeCount);
            bizFinancialRecordsModel.setExpenditureCount(expenditureCount);
            bizFinancialRecordsMapper.insert(bizFinancialRecordsModel);
        }
    }


    /**
    * @Description:  获取分页列表
    * @Param:  params
    * @return:  PageResult
    * @Author: wsk
    * @Date: 2019/8/31
    */
    @Override
    public PageResult listPage(Map<String, Object> params) {
        String createTime = null == params.get("createTime") ? "" : params.get("createTime").toString();
        IPage<BizFinancialRecordsModel> page=bizFinancialRecordsMapper.selectPage(
                new PageWrapper<BizFinancialRecordsModel>(params).getPage(),
                new QueryWrapper<BizFinancialRecordsModel>()
                        .eq("del_flag",BizConstant.STATE_NORMAL)
                        .eq(StrUtil.isNotBlank(createTime),"DATE_FORMAT( create_time, '%Y-%m-%d' )",createTime)
                        .eq("shop_id",params.get("shopId"))
                        .orderByDesc("create_time")
        );
        return new PageResult(page);
    }

    @Override
    public PageResult listPageByMonth(Map<String, Object> params) {
        IPage<BizFinancialRecordsModel> page=bizFinancialRecordsMapper.getByMonth(
                new PageWrapper<BizFinancialRecordsModel>(params).getPage(),params);
        return new PageResult(page);
    }


    /**
    * @Description: 根据店铺id和时间获取交易记录
    * @Param:  shopId，date
    * @return:  Map
    * @Author: wsk
    * @Date: 2019/8/31
    */
    /*@Override
    public Map<String, String> getProfitByShopId(Long shopId, Date date) {
        return bizFinancialRecordsDetailMapper.getByShopId(shopId,date,1);
    }*/
}