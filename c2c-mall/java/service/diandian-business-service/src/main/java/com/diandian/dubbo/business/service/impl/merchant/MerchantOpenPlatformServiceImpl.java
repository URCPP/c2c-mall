package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantOpenPlatformMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.dto.api.query.GetTokenQueryDTO;
import com.diandian.dubbo.facade.dto.api.result.TokenResultDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantOpenPlatformModel;
import com.diandian.dubbo.facade.service.merchant.MerchantOpenPlatformService;
import com.diandian.dubbo.facade.tuple.BinaryTuple;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;

/**
 * <p>
 * 商户积分商城开放平台信息 服务实现类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-10
 */
@Service("merchantOpenPlatformService")
public class MerchantOpenPlatformServiceImpl implements MerchantOpenPlatformService {

    @Autowired
    private MerchantOpenPlatformMapper merchantOpenPlatformMapper;

    @Override
    public TokenResultDTO apiGetToken(GetTokenQueryDTO dto) {
        MerchantOpenPlatformModel openPlatform = merchantOpenPlatformMapper.apiGetToken(dto.getAppId());
        if(null == openPlatform){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40004_CODE, IntegralStoreConstant.ERROR_40004_MESSAGE);
        }
        if(!dto.getAppSecret().equals(openPlatform.getAppSecret())){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40005_CODE, IntegralStoreConstant.ERROR_40005_MESSAGE);
        }
        DateTime date = DateUtil.date();
        String accessToken = RandomUtil.randomString(IntegralStoreConstant.ACCESS_TOKEN_LENGTH);
        MerchantOpenPlatformModel update = new MerchantOpenPlatformModel();
        update.setId(openPlatform.getId());
        update.setAccessToken(accessToken);
        update.setTokenExpireTime(DateUtil.offsetSecond(date, IntegralStoreConstant.ACCESS_TOKEN_EXPIRE));
        merchantOpenPlatformMapper.updateById(update);
        TokenResultDTO result = new TokenResultDTO();
        result.setAccessToken(accessToken);
        result.setExpiresIn(IntegralStoreConstant.ACCESS_TOKEN_EXPIRE);
        return result;
    }

    @Override
    public BinaryTuple<Long, String> apiCheckToken(String appId, String ipAddress){
        MerchantOpenPlatformModel openPlatform = merchantOpenPlatformMapper.apiGetToken(appId);
        if(null == openPlatform){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40004_CODE, IntegralStoreConstant.ERROR_40004_MESSAGE);
        }
        if(StrUtil.isBlank(openPlatform.getWhiteIp()) || openPlatform.getWhiteIp().indexOf(ipAddress) == -1){
            throw new DubboException("" + IntegralStoreConstant.ERROR_41012_CODE, IntegralStoreConstant.ERROR_41012_MESSAGE);
        }
        if(StringUtils.isBlank(openPlatform.getAccessToken())){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40007_CODE, IntegralStoreConstant.ERROR_40007_MESSAGE);
        }
        if(null != openPlatform.getTokenExpireTime() && DateUtil.current(false) > openPlatform.getTokenExpireTime().getTime()){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40007_CODE, IntegralStoreConstant.ERROR_40007_MESSAGE);
        }
        if(null == openPlatform.getMerchantId()){
            throw new DubboException("" + IntegralStoreConstant.ERROR_41000_CODE, IntegralStoreConstant.ERROR_41000_MESSAGE);
        }
        return new BinaryTuple<>(openPlatform.getMerchantId(), openPlatform.getAccessToken());
    }

    @Override
    public Long apiCheckIPInWhitelist(String appId, String ipAddress){
        MerchantOpenPlatformModel openPlatform = merchantOpenPlatformMapper.apiGetToken(appId);
        if(null == openPlatform){
            throw new DubboException("" + IntegralStoreConstant.ERROR_40004_CODE, IntegralStoreConstant.ERROR_40004_MESSAGE);
        }
        if(StrUtil.isBlank(openPlatform.getWhiteIp()) || openPlatform.getWhiteIp().indexOf(ipAddress) == -1){
            throw new DubboException("" + IntegralStoreConstant.ERROR_41012_CODE, IntegralStoreConstant.ERROR_41012_MESSAGE);
        }
        return openPlatform.getMerchantId();
    }

    @Override
    public Long getMerchantIdByAppId(String appId){
        return merchantOpenPlatformMapper.getMerchantIdByAppId(appId);
    }

    @Override
    public MerchantOpenPlatformModel getByMchId(Long merchantId){
        QueryWrapper<MerchantOpenPlatformModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        return merchantOpenPlatformMapper.selectOne(qw);
    }

    @Override
    public String generateOpenPlatformInfo(Long merchantId) {
        MerchantOpenPlatformModel model = new MerchantOpenPlatformModel();
        model.setMerchantId(merchantId);
        model.setAppId(String.format(BizConstant.NO_MERCHANT_APP_ID, RandomUtil.randomString(16)));
        String appSecret = RandomUtil.randomString(32);
        model.setAppSecret(appSecret);
        Integer mchIdCnt = merchantOpenPlatformMapper.count(model.getMerchantId(), null, null);
        if(null != mchIdCnt && mchIdCnt > 0){
            throw new DubboException("开通失败");
        }
        Integer appIdCnt = merchantOpenPlatformMapper.count(null, model.getAppId(), null);
        if(null != appIdCnt && appIdCnt > 0){
            throw new DubboException("开通失败");
        }
        Integer appSecretCnt = merchantOpenPlatformMapper.count(null, null, model.getAppSecret());
        if(null != appSecretCnt && appSecretCnt > 0){
            throw new DubboException("开通失败");
        }
        merchantOpenPlatformMapper.insert(model);
        return appSecret;
    }

    @Override
    public void update(MerchantOpenPlatformModel model) {
        MerchantOpenPlatformModel old = merchantOpenPlatformMapper.selectById(model.getId());
        if(null == old){
            throw new DubboException("更新失败");
        }
        merchantOpenPlatformMapper.updateById(model);
    }

    @Override
    public String regenerate(Long merchantId){
        MerchantOpenPlatformModel old = this.getByMchId(merchantId);
        if(null == old){
            throw new DubboException("AppSecret设置失败");
        }
        MerchantOpenPlatformModel update = new MerchantOpenPlatformModel();
        update.setId(old.getId());
        String appSecret = RandomUtil.randomString(32);
        update.setAppSecret(appSecret);
        Integer appSecretCnt = merchantOpenPlatformMapper.count(null, null, update.getAppSecret());
        if(null != appSecretCnt && appSecretCnt > 0){
            throw new DubboException("AppSecret设置失败");
        }
        merchantOpenPlatformMapper.updateById(update);
        return appSecret;
    }
}
