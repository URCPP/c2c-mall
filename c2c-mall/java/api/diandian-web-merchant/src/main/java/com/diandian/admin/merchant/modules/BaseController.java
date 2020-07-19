package com.diandian.admin.merchant.modules;

import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import org.apache.shiro.SecurityUtils;

/**
 * @author x
 * @date 2018/9/14 9:49
 */
public class BaseController {


    protected MerchantInfoModel getMerchantInfo() {
        return (MerchantInfoModel) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getMerchantInfoId() {
        return getMerchantInfo().getId();
    }


    protected SysUserModel getUser() {
        return (SysUserModel) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getId();
    }

}
