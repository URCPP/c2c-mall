package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.merchant.MerchantWalletHistoryLogMapper;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.merchant.ExchangeDTO;
import com.diandian.dubbo.facade.dto.order.OrderInfoDTO;
import com.diandian.dubbo.facade.dto.order.OrderNoDTO;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;
import com.diandian.dubbo.facade.model.member.*;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletHistoryLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import com.diandian.dubbo.facade.service.member.MemberExchangeHistoryLogService;
import com.diandian.dubbo.facade.service.member.MemberInfoService;
import com.diandian.dubbo.facade.service.member.MemberMerchantRelationService;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletHistoryLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商户钱包变动历史表
 *
 * @author jbh
 * @date 2019/02/21
 */
@Service("merchantWalletHistoryLogService")
@Slf4j
public class MerchantWalletHistoryLogServiceImpl implements MerchantWalletHistoryLogService {

    @Autowired
    private MerchantWalletHistoryLogMapper merchantWalletHistoryLogMapper;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private MemberMerchantRelationService memberMerchantRelationService;
    @Autowired
    private NoGenerator noGenerator;
    @Autowired
    private MemberExchangeHistoryLogService memberExchangeHistoryLogService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private MemberOrderOptLogService memberOrderOptLogService;
    @Autowired
    private BizMallConfigService bizMallConfigService;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Page<MerchantWalletHistoryLogModel> page = new PageWrapper<MerchantWalletHistoryLogModel>(params).getPage();
        IPage<MerchantWalletHistoryLogModel> iPage = merchantWalletHistoryLogMapper.listPage(page, params);
        return new PageResult(iPage);
    }

    @Override
    public void save(MerchantWalletHistoryLogModel merchantWalletHistoryLogModel) {
        merchantWalletHistoryLogMapper.insert(merchantWalletHistoryLogModel);
    }

    @Override
    public BigDecimal getMerchantMinRechargeAmount(Long merchantId){
        BigDecimal rechargedAmount = merchantWalletHistoryLogMapper.getMchRechargedAmount(merchantId);
        if(null != rechargedAmount && rechargedAmount.compareTo(BigDecimal.ZERO) == 1){
            return BigDecimal.ONE;
        }
        BizMallConfigModel firstRechargeAmount = bizMallConfigService.getByEngName("first_recharge_amount");
        if(null == firstRechargeAmount){
            return BigDecimal.ONE;
        }
        return new BigDecimal(firstRechargeAmount.getMallValue());
    }
}
