package com.diandian.dubbo.business.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.user.UserAccountInfoMapper;
import com.diandian.dubbo.facade.model.user.UserAccountInfoModel;
import com.diandian.dubbo.facade.service.user.UserAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商户帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Service("userAccountInfoService")
@Slf4j
public class UserAccountInfoServiceImpl implements UserAccountInfoService {

    @Autowired
    private UserAccountInfoMapper userAccountInfoMapper;

    @Override
    public UserAccountInfoModel getById(Long id) {
        return userAccountInfoMapper.selectById(id);
    }

    @Override
    public void insert(UserAccountInfoModel userAccountInfoModel) {
        userAccountInfoMapper.insert(userAccountInfoModel);
    }

    @Override
    public void updateById(UserAccountInfoModel userAccountInfoModel) {
        userAccountInfoMapper.updateById(userAccountInfoModel);
    }

    @Override
    public UserAccountInfoModel getByShopId(Long shopId) {
        return userAccountInfoMapper.getByShopId(shopId);
    }

    @Override
    public UserAccountInfoModel selectBalanceByUserId(Long userId) {
        return userAccountInfoMapper.selectOne( new QueryWrapper<UserAccountInfoModel>()
                .eq("user_id",userId)
        );
    }
}
