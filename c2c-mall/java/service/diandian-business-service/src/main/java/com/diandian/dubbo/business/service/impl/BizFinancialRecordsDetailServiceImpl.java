package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizFinancialRecordsDetailMapper;
import com.diandian.dubbo.business.mapper.user.UserAccountHistoryLogMapper;
import com.diandian.dubbo.business.mapper.user.UserAccountInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsDetailModel;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsModel;
import com.diandian.dubbo.facade.model.user.UserAccountHistoryLogModel;
import com.diandian.dubbo.facade.model.user.UserAccountInfoModel;
import com.diandian.dubbo.facade.service.biz.BizFinancialRecordsDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-03 15:10
 */
@Service("bizFinancialRecordsDetailService")
@Slf4j
public class BizFinancialRecordsDetailServiceImpl implements BizFinancialRecordsDetailService {

    @Autowired
    private BizFinancialRecordsDetailMapper bizFinancialRecordsDetailMapper;
    @Autowired
    private UserAccountInfoMapper userAccountInfoMapper;
    @Autowired
    private UserAccountHistoryLogMapper userAccountHistoryLogMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantFreezingBalance(){
        log.info("结算余额任务执行");
        List<BizFinancialRecordsDetailModel> list=bizFinancialRecordsDetailMapper.getUnissuedByCreateTime();
        UserAccountInfoModel userAccountInfoModel;
        UserAccountHistoryLogModel userAccountHistoryLogModel;
        for (BizFinancialRecordsDetailModel bizFinancialRecordsDetailModel:list){
            if(!Long.valueOf(1).equals(bizFinancialRecordsDetailModel.getShopId())){
                userAccountInfoModel=userAccountInfoMapper.getByShopId(bizFinancialRecordsDetailModel.getShopId());
                userAccountHistoryLogModel=new UserAccountHistoryLogModel();
                userAccountHistoryLogModel.setUserId(userAccountInfoModel.getUserId());
                userAccountHistoryLogModel.setTypeFlag(1);
                userAccountHistoryLogModel.setPreAmount(userAccountInfoModel.getAvailableBalance());
                userAccountHistoryLogModel.setAmount(bizFinancialRecordsDetailModel.getMoney());
                userAccountHistoryLogModel.setPostAmount(userAccountInfoModel.getAvailableBalance().add(bizFinancialRecordsDetailModel.getMoney()));
                userAccountHistoryLogMapper.insert(userAccountHistoryLogModel);
                userAccountInfoModel.setAvailableBalance(userAccountHistoryLogModel.getPostAmount());
                userAccountInfoMapper.updateById(userAccountInfoModel);
                bizFinancialRecordsDetailModel.setState(2);
                bizFinancialRecordsDetailMapper.updateById(bizFinancialRecordsDetailModel);
            }
        }
    }

    @Override
    public List<BizFinancialRecordsDetailModel> getByShopId(Long shopId) {
        return bizFinancialRecordsDetailMapper.selectList(
                new QueryWrapper<BizFinancialRecordsDetailModel>()
                        .eq("del_flag", BizConstant.STATE_NORMAL)
                        .eq("type",1)
                        .eq("shopId",shopId)
        );
    }

    @Override
    public void insert(BizFinancialRecordsDetailModel bizFinancialRecordsDetailModel) {
        bizFinancialRecordsDetailMapper.insert(bizFinancialRecordsDetailModel);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String createTime = null == params.get("createTime") ? "" : params.get("createTime").toString();
        String shopId = null == params.get("shopId") ? "" : params.get("shopId").toString();
        String state = null == params.get("state") ? "" : params.get("state").toString();
        String type = null == params.get("type") ? "" : params.get("type").toString();
        IPage<BizFinancialRecordsDetailModel> page=bizFinancialRecordsDetailMapper.selectPage(
                new PageWrapper<BizFinancialRecordsDetailModel>(params).getPage(),
                new QueryWrapper<BizFinancialRecordsDetailModel>()
                        .eq(StrUtil.isNotBlank(createTime),"create_time",createTime)
                        .eq(StrUtil.isNotBlank(shopId),"shop_id",shopId)
                        .eq(StrUtil.isNotBlank(state),"state",state)
                        .eq(StrUtil.isNotBlank(type),"type",type)
        );
        return new PageResult(page);
    }
}