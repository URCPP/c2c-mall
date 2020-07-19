package com.diandian.admin.business.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.common.oauth2.TokenGenerator;
import com.diandian.admin.business.modules.sys.mapper.SysUserTokenMapper;
import com.diandian.admin.business.modules.sys.service.SysUserTokenService;
import com.diandian.admin.business.modules.sys.vo.TokenVO;
import com.diandian.admin.model.sys.SysUserTokenModel;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author x
 * @date 2018-11-08
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserTokenModel> implements SysUserTokenService {

    //12小时后过期
    private final static int EXPIRE = 3600 * 168;

    @Override
    public TokenVO createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenModel tokenEntity = this.getById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenModel();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            this.updateById(tokenEntity);
        }
        return new TokenVO(token,expireTime);
    }

    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();
        //修改token
        SysUserTokenModel tokenEntity = new SysUserTokenModel();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }
}
