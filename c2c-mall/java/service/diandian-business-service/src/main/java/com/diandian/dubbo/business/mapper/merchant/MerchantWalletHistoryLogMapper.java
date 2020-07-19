package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletHistoryLogModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 商户软件变动历史表
 *
 * @author jbh
 * @date 2019/02/21
 */
public interface MerchantWalletHistoryLogMapper extends BaseMapper<MerchantWalletHistoryLogModel> {

    /**
     * 钱包变更记录列表 分页
     * @param page
     * @param params
     * @return
     */
    IPage<MerchantWalletHistoryLogModel> listPage(Page page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 获取商户已充值金额
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/5/27 14:18
     */
    BigDecimal getMchRechargedAmount(@Param("merchantId") Long merchantId);
}
