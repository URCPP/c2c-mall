package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantAddPriceMapper;
import com.diandian.dubbo.facade.model.merchant.MerchantAddPriceModel;
import com.diandian.dubbo.facade.service.merchant.MerchantAddPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("merchantAddPriceService")
public class MerchantAddPriceServiceImpl implements MerchantAddPriceService {

    @Autowired
    private MerchantAddPriceMapper merchantAddPriceMapper;

    @Override
    public List<MerchantAddPriceModel> selectOrderComplete() {
        return merchantAddPriceMapper.selectList(new QueryWrapper<MerchantAddPriceModel>().eq("flag", 2));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(MerchantAddPriceModel merchantAddPriceModel) {
        return merchantAddPriceMapper.updateById(merchantAddPriceModel);
    }

    @Override
    public void insert(MerchantAddPriceModel merchantAddPriceModel) {
        merchantAddPriceMapper.insert(merchantAddPriceModel);
    }


}
