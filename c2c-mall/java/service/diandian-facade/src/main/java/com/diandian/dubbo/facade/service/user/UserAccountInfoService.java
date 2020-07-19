package com.diandian.dubbo.facade.service.user;

import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.model.user.UserAccountInfoModel;

import java.math.BigDecimal;

/**
 * 商户帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface UserAccountInfoService {

    UserAccountInfoModel getById(Long id);

    void insert(UserAccountInfoModel userAccountInfoModel);

    void updateById(UserAccountInfoModel userAccountInfoModel);

    UserAccountInfoModel getByShopId(Long shopId);

    UserAccountInfoModel selectBalanceByUserId(Long userId);
}
