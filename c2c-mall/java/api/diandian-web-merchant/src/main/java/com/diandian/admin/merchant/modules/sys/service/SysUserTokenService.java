package com.diandian.admin.merchant.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.merchant.modules.sys.vo.TokenVO;
import com.diandian.admin.model.sys.SysUserTokenModel;

/**
 * @author x
 * @date 2018-11-08
 */
public interface SysUserTokenService extends IService<SysUserTokenModel> {

    /**
     * 生成token
     * @param userId  用户ID
     */
    TokenVO createToken(long userId);

    /**
     * 退出，修改token值
     * @param userId  用户ID
     */
    void logout(long userId);
}
