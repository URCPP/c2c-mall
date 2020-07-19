package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.BizBankCardInformationMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.model.biz.BizBankCardInformationModel;
import com.diandian.dubbo.facade.service.biz.BizBankCardInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-08 11:24
 */
@Service("bizBankCardInformationService")
@Slf4j
public class BizBankCardInformationServiceImpl implements BizBankCardInformationService {

    @Autowired
    private BizBankCardInformationMapper bizBankCardInformationMapper;

    @Override
    public BizBankCardInformationModel getById(Long id) {
        return bizBankCardInformationMapper.selectById(id);
    }

    @Override
    public List<BizBankCardInformationModel> getByShopId(Long shopId) {
        return bizBankCardInformationMapper.selectList(
                new QueryWrapper<BizBankCardInformationModel>()
                        .eq("del_flag", BizConstant.STATE_NORMAL)
                        .eq("shop_id",shopId)
        );
    }

    @Override
    public void insert(BizBankCardInformationModel bizBankCardInformationModel) {
        bizBankCardInformationMapper.insert(bizBankCardInformationModel);
    }

    @Override
    public void updateById(BizBankCardInformationModel bizBankCardInformationModel) {
        bizBankCardInformationMapper.updateById(bizBankCardInformationModel);
    }
}