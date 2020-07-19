package com.diandian.admin.business.modules;

import com.diandian.admin.model.sys.SysUserModel;
import org.apache.shiro.SecurityUtils;

/**
 * @author x
 * @date 2018/9/14 9:49
 */
public class BaseController {


    protected SysUserModel getUser() {
        return (SysUserModel) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getId();
    }

}
