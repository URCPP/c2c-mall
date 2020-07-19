package com.diandian.admin.merchant.modules.sys.service.impl;

import com.diandian.admin.merchant.modules.biz.service.MerchantTokenService;
import com.diandian.admin.merchant.modules.sys.service.ShiroService;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.member.MerchantTokenModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantWalletInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author x
 * @date 2018-11-08
 */
@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {


    @Autowired
    private MerchantTokenService merchantTokenService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;
    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;

    @Override
    public Set<String> listUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
//        if (userId == SysConstant.USER_SUPPER_ADMIN) {
//            List<SysMenuModel> menuList = sysMenuMapper.selectList(null);
//            permsList = new ArrayList<>(menuList.size());
//            for (SysMenuModel menu : menuList) {
//                permsList.add(menu.getPerms());
//            }
//        } else {
//            permsList = sysUserMapper.listAllPermsByUserId(userId);
//        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
//        for (String perms : permsList) {
//            if (StringUtils.isBlank(perms)) {
//                continue;
//            }
//            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
//        }
        return permsSet;
    }

    @Override
    public MerchantInfoModel getMerchantByPhone(String phone) {
        MerchantInfoModel merchant = merchantInfoService.getOneByPhone(phone);
        return merchant;
    }
}
