package com.diandian.admin.merchant.modules.biz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.merchant.modules.biz.mapper.MerchantWithdrawApplyOptLogMapper;
import com.diandian.admin.merchant.modules.biz.service.MerchantWithdrawApplyOptLogService;
import com.diandian.admin.model.merchant.MerchantWithdrawApplyOptLogModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商家提现申请操用记录表
 *
 * @author jbh
 * @date 2019/02/26
 */
@Service("merchantWithdrawApplyOptLogService")
@Slf4j
public class MerchantWithdrawApplyOptLogServiceImpl extends ServiceImpl<MerchantWithdrawApplyOptLogMapper, MerchantWithdrawApplyOptLogModel> implements MerchantWithdrawApplyOptLogService {
    @Autowired
    private MerchantWithdrawApplyOptLogMapper withdrawApplyOptLogMapper;
    @Override
    public void saveWithdrawOptLog(MerchantWithdrawApplyOptLogModel optLogModel) {
        withdrawApplyOptLogMapper.insert(optLogModel);
    }

    @Override
    public PageResult ListPage(Map<String, Object> params) {
        Page<MerchantWithdrawApplyOptLogModel> page = new PageWrapper<MerchantWithdrawApplyOptLogModel>(params).getPage();
        IPage<MerchantWithdrawApplyOptLogModel> iPage = withdrawApplyOptLogMapper.listPage(page, params);
        return new PageResult(iPage);
    }
}
