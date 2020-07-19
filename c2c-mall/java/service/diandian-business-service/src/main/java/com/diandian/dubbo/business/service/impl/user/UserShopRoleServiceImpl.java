package com.diandian.dubbo.business.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.user.UserShopRoleMapper;
import com.diandian.dubbo.facade.model.user.UserShopRoleModel;
import com.diandian.dubbo.facade.service.user.UserShopRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-05 20:55
 */
@Service("userShopRoleService")
@Slf4j
public class UserShopRoleServiceImpl implements UserShopRoleService {

    @Autowired
    private UserShopRoleMapper userShopRoleMapper;

    @Override
    public UserShopRoleModel getById(Long id) {
        return userShopRoleMapper.selectById(id);
    }

    @Override
    public void insert(UserShopRoleModel userShopRoleModel) {
        userShopRoleMapper.insert(userShopRoleModel);
    }

    @Override
    public void updateById(UserShopRoleModel userShopRoleModel) {
        userShopRoleMapper.updateById(userShopRoleModel);
    }

    @Override
    public void deleteById(Long id) {
        userShopRoleMapper.deleteById(id);
    }

    @Override
    public UserShopRoleModel getByUserId(Long userId) {
        return userShopRoleMapper.selectOne(new QueryWrapper<UserShopRoleModel>().eq("user_id",userId));
    }
}