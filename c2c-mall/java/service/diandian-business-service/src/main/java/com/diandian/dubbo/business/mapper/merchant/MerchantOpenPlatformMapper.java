package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.api.query.GetTokenQueryDTO;
import com.diandian.dubbo.facade.dto.api.result.TokenResultDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantOpenPlatformModel;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户积分商城开放平台信息 Mapper 接口
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-10
 */
public interface MerchantOpenPlatformMapper extends BaseMapper<MerchantOpenPlatformModel> {

    /**
     *
     * 功能描述: token查询接口
     *
     * @param appId
     * @return
     * @author cjunyuan
     * @date 2019/5/13 14:16
     */
    MerchantOpenPlatformModel apiGetToken(@Param("appId") String appId);

    /**
     *
     * 功能描述: 获取商户ID
     *
     * @param appId
     * @return
     * @author cjunyuan
     * @date 2019/5/14 13:39
     */
    Long getMerchantIdByAppId(@Param("appId") String appId);

    /**
     *
     * 功能描述: 数量统计
     *
     * @param merchantId
     * @param appId
     * @param appSecret
     * @return
     * @author cjunyuan
     * @date 2019/5/17 18:03
     */
    Integer count(@Param("mchId") Long merchantId, @Param("appId") String appId, @Param("appSecret") String appSecret);
}
