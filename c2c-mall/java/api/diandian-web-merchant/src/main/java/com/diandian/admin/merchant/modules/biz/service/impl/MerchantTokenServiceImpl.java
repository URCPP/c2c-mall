package com.diandian.admin.merchant.modules.biz.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.merchant.common.oauth2.TokenGenerator;
import com.diandian.admin.merchant.common.util.CodeGen;
import com.diandian.admin.merchant.modules.biz.mapper.MerchantTokenMapper;
import com.diandian.admin.merchant.modules.biz.service.MerchantTokenService;
import com.diandian.admin.merchant.modules.sys.vo.TokenVO;
import com.diandian.dubbo.facade.common.constant.DubboConstant;
import com.diandian.dubbo.facade.model.member.MerchantTokenModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 商户TOKEN信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
@Service("merchantTokenService")
@Slf4j
public class MerchantTokenServiceImpl extends ServiceImpl<MerchantTokenMapper, MerchantTokenModel> implements MerchantTokenService {

    //12小时后过期
    private final static int EXPIRE = 3600 * 168;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public TokenVO createToken(String phone) {
        String newToken = TokenGenerator.generateValue();
        DateTime expireTime = DateUtil.offsetSecond(new Date(), DubboConstant.MEMBER_LOGIN_EXPIRE_TIME);
        String value = stringRedisTemplate.opsForValue().get(CodeGen.genTokenRedisKey(phone));
        if (StringUtils.isNotBlank(value)){
            stringRedisTemplate.delete(CodeGen.genTokenRedisKey(value));
        }
        stringRedisTemplate.opsForValue().set(CodeGen.genTokenRedisKey(phone),
                newToken,DubboConstant.MEMBER_LOGIN_EXPIRE_TIME, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(CodeGen.genTokenRedisKey(newToken),phone,
                DubboConstant.MEMBER_LOGIN_EXPIRE_TIME, TimeUnit.SECONDS);
        return new TokenVO(newToken, expireTime);
    }

    @Override
    public void logout(String phone) {
        String value = stringRedisTemplate.opsForValue().get(CodeGen.genTokenRedisKey(phone));
        if (StringUtils.isNotBlank(value)){
            stringRedisTemplate.delete(CodeGen.genTokenRedisKey(value));
        }
        stringRedisTemplate.delete(phone);
    }


}
