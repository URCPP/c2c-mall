package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantFreightSetMapper;
import com.diandian.dubbo.facade.model.merchant.MerchantFreightSetModel;
import com.diandian.dubbo.facade.model.merchant.MerchantIntegralMallBannerModel;
import com.diandian.dubbo.facade.service.merchant.MerchantFreightSetService;
import com.diandian.dubbo.facade.service.merchant.MerchantIntegralMallBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 商户运费设置表
 *
 * @author wbc
 * @date 2019/02/20
 */
@Service("merchantFreightSetService")
@Slf4j
public class MerchantFreightSetServiceImpl implements MerchantFreightSetService {
    @Autowired
    private MerchantFreightSetMapper freightSetMapper;
    @Autowired
    private MerchantIntegralMallBannerService merchantIntegralMallBannerService;
    @Override
    public MerchantFreightSetModel getByMerchantId(Long id) {
        QueryWrapper<MerchantFreightSetModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", id);
        return freightSetMapper.selectOne(wrapper);
    }

    @Override
    public void saveFreightSet(MerchantFreightSetModel freightSetModel) {
         freightSetMapper.insert(freightSetModel);
    }

    @Override
    public void updateFreightSet(MerchantFreightSetModel freightSetModel) {
        freightSetMapper.updateById(freightSetModel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFreightSet(MerchantFreightSetModel freightSetModel, List<MerchantIntegralMallBannerModel> list) {
        if(null != freightSetModel.getId()){
            freightSetMapper.updateById(freightSetModel);
        }else {
            freightSetMapper.insert(freightSetModel);
        }
        merchantIntegralMallBannerService.batchSave(freightSetModel.getMerchantId(), list);
    }
}
