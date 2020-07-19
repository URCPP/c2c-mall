package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.api.query.OrderQueryDTO;
import com.diandian.dubbo.facade.dto.api.result.OrderPayResultDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;

import java.math.BigDecimal;

/**
 * 商户钱包帐户信息表
 *
 * @author jbh
 * @date 2019/02/21
 */
public interface MerchantWalletInfoService {

    /**
     * 添加钱包
     *
     * @param merchantWalletInfoModel
     * @return
     */
    boolean save(MerchantWalletInfoModel merchantWalletInfoModel);


    /**
     * 获取商户钱包帐户信息
     *
     * @param merchantId 商户ID
     * @return
     */
    MerchantWalletInfoModel getWalletInfo(Long merchantId);

    /**
     * 修改钱包
     *
     * @param walletInfo
     * @return
     */
    boolean updateById(MerchantWalletInfoModel walletInfo);


    /**
     * 更新钱包金额
     *
     * @param merchantId
     * @param oldAmount
     * @param newAmount
     * @return
     */
    boolean updateAmount(Long merchantId, BigDecimal oldAmount, BigDecimal newAmount);

    /**
     * 余额转保证金
     *
     * @param merchantId
     * @param oldAmount
     * @param newAmount
     * @param marginAmount
     * @return
     */
    boolean updateMarginAmount(Long merchantId, BigDecimal oldAmount, BigDecimal newAmount, BigDecimal marginAmount);

    /**
     * 修改
     *
     * @param walletInfo
     * @param oldWallet
     * @return
     */
    int updateByOld(MerchantWalletInfoModel walletInfo, MerchantWalletInfoModel oldWallet);

    /**
     * 更新商户钱包
     * @param orderNo
     */
    void updateMerchantWallet(String orderNo);

    /**
     *
     * 功能描述: api商户订单支付接口
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/15 13:52
     */
    OrderPayResultDTO apiPayOrder(OrderQueryDTO dto);
}
