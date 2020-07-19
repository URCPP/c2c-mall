package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizWithdrawalsRecordMapper;
import com.diandian.dubbo.business.mapper.OrderDetailMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantAccountHistoryLogMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantAccountInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantBankInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalsRecordModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountHistoryLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantBankInfoModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountHistoryLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 商户资金账户变动明细表
 *
 * @author jbh
 * @date 2019/02/26
 */
@Service("merchantAccountHistoryLogService")
@Slf4j
public class MerchantAccountHistoryLogServiceImpl implements MerchantAccountHistoryLogService {
    @Autowired
    private MerchantAccountHistoryLogMapper merchantAccountHistoryLogMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private BizWithdrawalsRecordMapper bizWithdrawalsRecordMapper;
    @Autowired
    private MerchantBankInfoMapper merchantBankInfoMapper;
    @Autowired
    private MerchantInfoMapper merchantInfoMapper;
    @Autowired
    private MerchantAccountInfoMapper merchantAccountInfoMapper;

    @Override
    public void saveAccountHistoryLog(MerchantAccountHistoryLogModel accountHistoryLogModel) {
        merchantAccountHistoryLogMapper.insert(accountHistoryLogModel);
    }

    @Override
    public PageResult listPage(Map params) {
        IPage page=merchantAccountHistoryLogMapper.listPage(new PageWrapper<MerchantAccountHistoryLogModel>(params).getPage(),params);
        return new PageResult(page);
    }

    @Override
    public Map getById(Long id) {
        Map map=new HashMap();
        MerchantAccountHistoryLogModel merchantAccountHistoryLogModel=merchantAccountHistoryLogMapper.selectById(id);
        OrderDetailModel orderDetailModel=orderDetailMapper.selectById(merchantAccountHistoryLogModel.getSource());
        MerchantInfoModel merchantInfoModel=merchantInfoMapper.selectById(merchantAccountHistoryLogModel.getSource());
        String transactionMode = "";
        if(merchantAccountHistoryLogModel.getTransactionMode()==1){
            transactionMode="余额";
        }else if(merchantAccountHistoryLogModel.getTransactionMode()==2){
            transactionMode="冻结余额";
        }
        switch (merchantAccountHistoryLogModel.getBusinessType()){
            case 1:
                map.put("收款方式",transactionMode);
                map.put("收款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("收款类型","销售商品");
                map.put("商品名称",orderDetailModel.getSkuName().substring(0, orderDetailModel.getSkuName().indexOf("[")));
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                break;
            case 2:
                break;
            case 3:
                map.put("付款方式",transactionMode);
                map.put("付款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("付款类型","角色升级");
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",merchantAccountHistoryLogModel.getTransactionNumber());
                break;
            case 4:
                map.put("收款方式",transactionMode);
                map.put("收款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("收款类型","分润 - 推荐奖");
                map.put("来源账号",merchantInfoModel.getPhone());
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                break;
            case 5:
                map.put("收款方式",transactionMode);
                map.put("收款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("收款类型","分润 - 直属会员消费");
                map.put("直属账号",merchantInfoModel.getPhone());
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                break;
            case 6:
                map.put("收款方式",transactionMode);
                map.put("收款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("收款类型","分润 - 直属会员销售");
                map.put("直属账号",merchantInfoModel.getPhone());
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                break;
            case 7:
                map.put("收款方式",transactionMode);
                map.put("收款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("收款类型","分润 - 下级收益");
                map.put("来源账号",merchantInfoModel.getPhone());
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                break;
            case 8:
                map.put("收款方式",transactionMode);
                map.put("收款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("收款类型","分润 - 直属会员升级");
                map.put("直属账号",merchantInfoModel.getPhone());
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                break;
            case 9:
                MerchantAccountInfoModel merchantAccountInfoModel=merchantAccountInfoMapper.selectOne(
                        new QueryWrapper<MerchantAccountInfoModel>()
                                .eq("merchant_id",merchantAccountHistoryLogModel.getMerchantId())
                );
                map.put("收款方式",transactionMode);
                map.put("收款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("收款类型","分润 - 冻结返还");
                map.put("剩余冻结金额",merchantAccountInfoModel.getFreezeBalance());
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                break;
            case 10:
                map.put("付款方式",transactionMode);
                map.put("付款金额","￥"+merchantAccountHistoryLogModel.getTransactionAmount());
                map.put("付款类型","购物消费");
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                break;
            case 11:
                BizWithdrawalsRecordModel bizWithdrawalsRecordModel=bizWithdrawalsRecordMapper.selectOne(
                        new QueryWrapper<BizWithdrawalsRecordModel>()
                                .eq("transaction_number",merchantAccountHistoryLogModel.getTransactionNumber())
                );
                MerchantBankInfoModel merchantBankInfoModel=merchantBankInfoMapper.selectById(bizWithdrawalsRecordModel.getBankId());
                map.put("付款方式",transactionMode);
                map.put("提现金额","￥"+bizWithdrawalsRecordModel.getCashWithdrawalAmount());
                map.put("手续费","￥"+bizWithdrawalsRecordModel.getServiceCharge());
                map.put("实到金额",bizWithdrawalsRecordModel.getCashWithdrawalAmount().subtract(bizWithdrawalsRecordModel.getServiceCharge()));
                map.put("付款类型","提现");
                map.put("创建时间",merchantAccountHistoryLogModel.getCreateTime());
                map.put("订单号",orderDetailModel.getOrderNo());
                map.put("银行名称",merchantBankInfoModel.getBankName());
                map.put("银行账号",merchantBankInfoModel.getBankCardNo());
                map.put("账户名",merchantBankInfoModel.getRealName());
                break;
        }
        return map;
    }

}
