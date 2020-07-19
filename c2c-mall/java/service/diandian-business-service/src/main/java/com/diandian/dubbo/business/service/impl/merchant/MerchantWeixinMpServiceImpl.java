package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantWeixinMpMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWeixinMpModel;
import com.diandian.dubbo.facade.service.merchant.MerchantWeixinMpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商户微信公众号信息表 服务实现类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-11
 */
@Service("merchantWeixinMpService")
@Slf4j
public class MerchantWeixinMpServiceImpl implements MerchantWeixinMpService {

    @Autowired
    private MerchantWeixinMpMapper merchantWeixinMpMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Override
    public MerchantWeixinMpModel getByAppId(String appId){
        QueryWrapper<MerchantWeixinMpModel> qw = new QueryWrapper<>();
        qw.eq("app_id", appId);
        return merchantWeixinMpMapper.selectOne(qw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindWeixinMp(MerchantWeixinMpModel wechatMp){
        AssertUtil.isTrue(null != wechatMp.getMerchantId() && wechatMp.getMerchantId() > 0, "商户信息不存在");
        QueryWrapper<MerchantInfoModel> mchInfoQuery = new QueryWrapper<>();
        mchInfoQuery.eq("id", wechatMp.getMerchantId());
        MerchantInfoModel oldMchInfo = merchantInfoMapper.selectOne(mchInfoQuery);
        AssertUtil.notNull(oldMchInfo, "商户信息不存在");
        MerchantInfoModel updateMchInfo = new MerchantInfoModel();
        updateMchInfo.setId(oldMchInfo.getId());
        //updateMchInfo.setHasBindWeixinMp(BizConstant.STATE_DISNORMAL);
        merchantInfoMapper.updateById(updateMchInfo);

        QueryWrapper<MerchantWeixinMpModel> mchWeixinMp = new QueryWrapper<>();
        mchWeixinMp.eq("merchant_id", wechatMp.getMerchantId());
        mchWeixinMp.eq("app_id", wechatMp.getAppId());
        MerchantWeixinMpModel oldWeixinMp = merchantWeixinMpMapper.selectOne(mchWeixinMp);
        if(null != oldWeixinMp){
            wechatMp.setId(oldWeixinMp.getId());
            merchantWeixinMpMapper.updateById(wechatMp);
        }else{
            merchantWeixinMpMapper.insert(wechatMp);
        }
    }

    @Override
    public MerchantWeixinMpModel getByMerchantId(Long merchantId){
        return merchantWeixinMpMapper.getByMerchantId(merchantId);
    }
}
