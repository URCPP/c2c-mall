package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


/**
 * 商户钱包帐户信息表
 *
 * @author jbh
 * @date 2019/02/21
 */
public interface MerchantWalletInfoMapper extends BaseMapper<MerchantWalletInfoModel> {

    int updateAmount(@Param("merchantId") Long merchantId,@Param("oldAmount") BigDecimal oldAmount, @Param("newAmount") BigDecimal newAmount);

    int updateMarginAmount(@Param("merchantId") Long merchantId,@Param("oldAmount") BigDecimal oldAmount,
                           @Param("newAmount") BigDecimal newAmount,@Param("marginAmount") BigDecimal marginAmount);
}
