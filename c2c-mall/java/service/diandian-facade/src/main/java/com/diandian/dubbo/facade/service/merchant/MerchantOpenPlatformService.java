package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.api.query.GetTokenQueryDTO;
import com.diandian.dubbo.facade.dto.api.result.TokenResultDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantOpenPlatformModel;
import com.diandian.dubbo.facade.tuple.BinaryTuple;

/**
 * <p>
 * 商户积分商城开放平台信息 服务类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-10
 */
public interface MerchantOpenPlatformService {

    /**
     *
     * 功能描述: token查询接口
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/13 14:16
     */
    TokenResultDTO apiGetToken(GetTokenQueryDTO dto);

    /**
     *
     * 功能描述: token校验
     *
     * @param appId
     * @param ipAddress
     * @return
     * @author cjunyuan
     * @date 2019/5/13 15:21
     */
    BinaryTuple<Long, String> apiCheckToken(String appId, String ipAddress);

    /**
     *
     * 功能描述: 校验IP是否在白名单中
     *
     * @param appId
     * @param ipAddress
     * @return
     * @author cjunyuan
     * @date 2019/5/20 15:27
     */
    Long apiCheckIPInWhitelist(String appId, String ipAddress);

    /**
     *
     * 功能描述: 获取商户ID
     *
     * @param appId
     * @return
     * @author cjunyuan
     * @date 2019/5/14 13:39
     */
    Long getMerchantIdByAppId(String appId);

    /**
     *
     * 功能描述: 获取商户开放平台信息
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/5/17 17:53
     */
    MerchantOpenPlatformModel getByMchId(Long merchantId);

    /**
     *
     * 功能描述: 开通商户开放平台
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/5/17 18:52
     */
    String generateOpenPlatformInfo(Long merchantId);

    /**
     *
     * 功能描述: 修改
     *
     * @param model
     * @return
     * @author cjunyuan
     * @date 2019/5/17 17:55
     */
    void update(MerchantOpenPlatformModel model);

    /**
     *
     * 功能描述: 重新生成appSecret
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/5/17 19:05
     */
    String regenerate(Long merchantId);
}
