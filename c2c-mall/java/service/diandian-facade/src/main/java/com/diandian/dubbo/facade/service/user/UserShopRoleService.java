package com.diandian.dubbo.facade.service.user;

import com.diandian.dubbo.facade.model.user.UserShopRoleModel;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-05 20:53
 */
public interface UserShopRoleService {

    UserShopRoleModel getById(Long id);

    void insert(UserShopRoleModel userShopRoleModel);

    void updateById(UserShopRoleModel userShopRoleModel);

    void deleteById(Long id);

    UserShopRoleModel getByUserId(Long userId);
}
