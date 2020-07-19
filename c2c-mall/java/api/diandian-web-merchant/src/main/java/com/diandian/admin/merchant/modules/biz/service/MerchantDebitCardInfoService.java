package com.diandian.admin.merchant.modules.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantDebitCardInfoVO;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantDebitCardPartInfoVO;
import com.diandian.admin.model.merchant.MerchantDebitCardInfoModel;

import java.util.List;

/**
 * 商户银行卡信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
public interface MerchantDebitCardInfoService extends IService<MerchantDebitCardInfoModel> {
    /**
     * 添加银行卡信息
     *
     * @param merchantDebitCardInfoVO
     */
    void insertCardInfo(MerchantDebitCardInfoVO merchantDebitCardInfoVO);

    /**
     * 获取当前用户的银行卡信息列表
     *
     * @return
     */
    List<MerchantDebitCardPartInfoVO> listCardInfo(Long id);

    /**
     * 删除银行卡信息
     *
     * @return
     */
    void deleteCardInfo(Long id);

    /**
     * 修改银行卡为默认卡
     *
     * @param cardId
     * @param merchantId
     */
    void updateCardInfo(Long cardId, Long merchantId);

    /**
     * 根据银行卡卡号查询记录
     *
     * @param cardNumber
     * @return
     */
    MerchantDebitCardInfoModel getDebitCardInfo(String cardNumber);

    /**
     * 获取默认银行卡
     *
     * @param merchantId
     * @param defaultFlag
     * @return
     */
    MerchantDebitCardInfoModel getDefaultCardInfo(Long merchantId, Integer defaultFlag);
}
