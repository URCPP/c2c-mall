package com.diandian.admin.merchant.modules.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.merchant.modules.sys.vo.TokenVO;
import com.diandian.dubbo.facade.model.member.MerchantTokenModel;

/**
 * 商户TOKEN信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
public interface MerchantTokenService extends IService<MerchantTokenModel> {

    /**
     * 生成token
     *
     * @param merchantId 用户ID
     */
    TokenVO createToken(String phone);

    /**
     * 退出，修改token值
     *
     * @param merchantId 用户ID
     */
    void logout(String phone);
}
