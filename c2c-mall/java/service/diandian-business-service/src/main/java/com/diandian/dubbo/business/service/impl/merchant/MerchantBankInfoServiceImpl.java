package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantBankInfoMapper;
import com.diandian.dubbo.facade.model.merchant.MerchantBankInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 11:59 2019/10/28
 * @Modified By:
 */
@Service("merchantBankInfoService")
public class MerchantBankInfoServiceImpl implements MerchantBankInfoService {

    @Autowired
    private MerchantBankInfoMapper merchantBankInfoMapper;


    @Override
    public List<MerchantBankInfoModel> list(Long merchantId) {
        return merchantBankInfoMapper.selectList(new QueryWrapper<MerchantBankInfoModel>()
                .eq("merchant_id",merchantId)) ;
    }

    @Override
    public void deleteBankById(Long id) {
        merchantBankInfoMapper.deleteById(id);
    }
}
