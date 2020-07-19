package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizFinancialRecordsDetailMapper;
import com.diandian.dubbo.business.mapper.BizFinancialRecordsMapper;
import com.diandian.dubbo.business.mapper.BizWithdrawalsRecordMapper;
import com.diandian.dubbo.business.mapper.user.UserAccountHistoryLogMapper;
import com.diandian.dubbo.business.mapper.user.UserAccountInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsDetailModel;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalsRecordModel;
import com.diandian.dubbo.facade.model.user.UserAccountHistoryLogModel;
import com.diandian.dubbo.facade.model.user.UserAccountInfoModel;
import com.diandian.dubbo.facade.service.biz.BizWithdrawalsRecordService;
import com.diandian.dubbo.facade.service.user.UserAccountHistoryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-10 21:03
 */
@Service("bizWithdrawalsRecordService")
@Slf4j
public class BizWithdrawalsRecordServiceImpl implements BizWithdrawalsRecordService {

    @Autowired
    private BizWithdrawalsRecordMapper bizWithdrawalsRecordMapper;

    @Autowired
    private BizFinancialRecordsDetailMapper bizFinancialRecordsDetailMapper;

    @Autowired
    private UserAccountInfoMapper userAccountInfoMapper;

    @Autowired
    private UserAccountHistoryLogMapper userAccountHistoryLogMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        IPage<BizWithdrawalsRecordModel> page=bizWithdrawalsRecordMapper.listPage(
                new PageWrapper<BizWithdrawalsRecordModel>(params).getPage(), params);
        return new PageResult(page);
    }

    @Override
    public BizWithdrawalsRecordModel getById(Long id) {
        return bizWithdrawalsRecordMapper.selectById(id);
    }

    @Override
    public void insert(BizWithdrawalsRecordModel bizWithdrawalsRecordModel) {
        bizWithdrawalsRecordMapper.insert(bizWithdrawalsRecordModel);
    }

    @Override
    public void updateById(BizWithdrawalsRecordModel bizWithdrawalsRecordModel) {
        bizWithdrawalsRecordMapper.updateById(bizWithdrawalsRecordModel);
    }

    @Override
    public void delete(BizWithdrawalsRecordModel bizWithdrawalsRecordModel) {
        bizWithdrawalsRecordModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        bizWithdrawalsRecordMapper.updateById(bizWithdrawalsRecordModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawal(BizWithdrawalsRecordModel bizWithdrawalsRecordModel)  {
        UserAccountInfoModel userAccountInfoModel=userAccountInfoMapper.getByShopId(bizWithdrawalsRecordModel.getShopId());
        if (userAccountInfoModel.getAvailableBalance().compareTo(bizWithdrawalsRecordModel.getCashWithdrawalAmount())==-1){
            throw new DubboException("余额不足");
        }
        bizWithdrawalsRecordModel.setState(0);
        this.insert(bizWithdrawalsRecordModel);
        BizFinancialRecordsDetailModel bizFinancialRecordsDetailModel=new BizFinancialRecordsDetailModel();
        bizFinancialRecordsDetailModel.setShopId(bizWithdrawalsRecordModel.getShopId());
        bizFinancialRecordsDetailModel.setMoney(bizWithdrawalsRecordModel.getCashWithdrawalAmount());
        bizFinancialRecordsDetailModel.setState(1);
        bizFinancialRecordsDetailModel.setType(2);
        bizFinancialRecordsDetailMapper.insert(bizFinancialRecordsDetailModel);
        UserAccountHistoryLogModel userAccountHistoryLogModel=new UserAccountHistoryLogModel();
        userAccountHistoryLogModel.setUserId(userAccountInfoModel.getUserId());
        userAccountHistoryLogModel.setAmount(bizWithdrawalsRecordModel.getCashWithdrawalAmount());
        userAccountHistoryLogModel.setPreAmount(userAccountInfoModel.getAvailableBalance());
        userAccountHistoryLogModel.setPostAmount(userAccountInfoModel.getAvailableBalance().subtract(bizWithdrawalsRecordModel.getCashWithdrawalAmount()));
        userAccountHistoryLogModel.setTypeFlag(2);
        userAccountHistoryLogMapper.insert(userAccountHistoryLogModel);
        userAccountInfoModel.setAvailableBalance(userAccountHistoryLogModel.getPostAmount());
        userAccountInfoMapper.updateById(userAccountInfoModel);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void examine(BizWithdrawalsRecordModel bizWithdrawalsRecordModel) {
        bizWithdrawalsRecordMapper.updateById(bizWithdrawalsRecordModel);
        if(1==bizWithdrawalsRecordModel.getState()){

        }else if(2==bizWithdrawalsRecordModel.getState()){
            bizWithdrawalsRecordModel=bizWithdrawalsRecordMapper.selectById(bizWithdrawalsRecordModel);
            UserAccountInfoModel userAccountInfoModel=userAccountInfoMapper.getByShopId(bizWithdrawalsRecordModel.getShopId());
            UserAccountHistoryLogModel userAccountHistoryLogModel=new UserAccountHistoryLogModel();
            userAccountHistoryLogModel.setUserId(userAccountInfoModel.getUserId());
            userAccountHistoryLogModel.setAmount(bizWithdrawalsRecordModel.getCashWithdrawalAmount());
            userAccountHistoryLogModel.setPreAmount(userAccountInfoModel.getAvailableBalance());
            userAccountHistoryLogModel.setPostAmount(userAccountInfoModel.getAvailableBalance().add(bizWithdrawalsRecordModel.getCashWithdrawalAmount()));
            userAccountHistoryLogModel.setTypeFlag(3);
            userAccountHistoryLogMapper.insert(userAccountHistoryLogModel);
            userAccountInfoModel.setAvailableBalance(userAccountHistoryLogModel.getPostAmount());
            userAccountInfoMapper.updateById(userAccountInfoModel);
        }
    }


}