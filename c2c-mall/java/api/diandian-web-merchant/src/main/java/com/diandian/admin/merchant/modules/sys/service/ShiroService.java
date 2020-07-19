package com.diandian.admin.merchant.modules.sys.service;


import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.member.MerchantTokenModel;

import java.util.Set;

/**
 * @author x
 * @date 2018-11-08
 */
public interface ShiroService {


    /**
     * 获取用户权限列表
     */
    Set<String> listUserPermissions(long userId);

    /**
     * 根据用户ID获取
     *
     * @param merchantId
     */
    MerchantInfoModel getMerchantByPhone(String phone);
}
