package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantIntegralMallBannerMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantOpenPlatformMapper;
import com.diandian.dubbo.facade.dto.api.BaseDTO;
import com.diandian.dubbo.facade.dto.api.result.MchIntegralMallBannerListResultDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantIntegralMallBannerModel;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantIntegralMallBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 商户积分商城banner表 服务实现类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-30
 */
@Service("merchantIntegralMallBannerService")
@Slf4j
public class MerchantIntegralMallBannerServiceImpl implements MerchantIntegralMallBannerService {

    @Autowired
    private MerchantIntegralMallBannerMapper merchantIntegralMallBannerMapper;

    @Autowired
    private MerchantOpenPlatformMapper merchantOpenPlatformMapper;

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Value("${aliyun.oss.domain}")
    private String imgDomain;

    @Override
    public List<MerchantIntegralMallBannerModel> listByMchId(Long merchantId) {
        QueryWrapper<MerchantIntegralMallBannerModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        return merchantIntegralMallBannerMapper.selectList(qw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(Long merchantId, List<MerchantIntegralMallBannerModel> list) {
        QueryWrapper<MerchantIntegralMallBannerModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        List<MerchantIntegralMallBannerModel> oldList = merchantIntegralMallBannerMapper.selectList(qw);
        List<MerchantIntegralMallBannerModel> tmp = new ArrayList<>(oldList);
        tmp.removeAll(list);
        Iterator<MerchantIntegralMallBannerModel> del = tmp.iterator();
        while (del.hasNext()){
            MerchantIntegralMallBannerModel next = del.next();
            merchantIntegralMallBannerMapper.deleteById(next.getId());
        }
        for (MerchantIntegralMallBannerModel merchantIntegralMallBanner : list){
            if(null != merchantIntegralMallBanner.getId()){
                merchantIntegralMallBannerMapper.updateById(merchantIntegralMallBanner);
            }else {
                merchantIntegralMallBanner.setMerchantId(merchantId);
                merchantIntegralMallBannerMapper.insert(merchantIntegralMallBanner);
            }
        }
    }

    @Override
    public List<MchIntegralMallBannerListResultDTO> apiGetList(BaseDTO dto){
        Long merchantId = merchantOpenPlatformMapper.getMerchantIdByAppId(dto.getAppId());
       // merchantInfoService.apiCheckMchIsNormal(merchantId);
        return merchantIntegralMallBannerMapper.apiGetList(merchantId, imgDomain);
    }
}
