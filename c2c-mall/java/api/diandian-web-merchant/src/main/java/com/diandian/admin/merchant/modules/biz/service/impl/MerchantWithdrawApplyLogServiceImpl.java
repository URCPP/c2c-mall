package com.diandian.admin.merchant.modules.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.merchant.modules.biz.mapper.MerchantWithdrawApplyLogMapper;
import com.diandian.admin.merchant.modules.biz.mapper.MerchantWithdrawApplyOptLogMapper;
import com.diandian.admin.merchant.modules.biz.service.MerchantWithdrawApplyLogService;
import com.diandian.admin.model.merchant.MerchantWithdrawApplyLogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商家提现申请记录表
 *
 * @author jbh
 * @date 2019/02/26
 */
@Service("merchantWithdrawApplyLogService")
@Slf4j
public class MerchantWithdrawApplyLogServiceImpl extends ServiceImpl<MerchantWithdrawApplyLogMapper, MerchantWithdrawApplyLogModel> implements MerchantWithdrawApplyLogService {
    @Autowired
    private MerchantWithdrawApplyLogMapper withdrawApplyLogMapper;
    @Override
    public void saveWithdrawApplyLog(MerchantWithdrawApplyLogModel withdrawApplyLogModel) {
        withdrawApplyLogMapper.insert(withdrawApplyLogModel);
    }
}
