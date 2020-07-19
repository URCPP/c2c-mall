package com.diandian.dubbo.business.service.impl.merchant;

import com.diandian.dubbo.business.mapper.merchant.MerchantSoftHistoryLogMapper;
import com.diandian.dubbo.facade.model.merchant.MerchantSoftHistoryLogModel;
import com.diandian.dubbo.facade.service.merchant.MerchantSoftHistoryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商户软件变动历史表
 *
 * @author wbc
 * @date 2019/02/19
 */
@Service("merchantSoftHistoryLogService")
@Slf4j
public class MerchantSoftHistoryLogServiceImpl implements MerchantSoftHistoryLogService {

    @Autowired
    private MerchantSoftHistoryLogMapper merchantSoftHistoryLogMapper;


    @Override
    public boolean save(MerchantSoftHistoryLogModel merchantSoftHistoryLogModel) {
        merchantSoftHistoryLogMapper.insert(merchantSoftHistoryLogModel);
        return true;
    }
}
