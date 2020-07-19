package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.merchant.MerchantSoftInfoDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantSoftInfoModel;
import com.diandian.dubbo.facade.vo.merchant.MerchantSoftInfoVO;

import java.util.List;

/**
 * 商户软件信息表
 *
 * @author wbc
 * @date 2019/02/19
 */
public interface MerchantSoftInfoService {

    /**
     * 获取商户软件帐户信息
     *
     * @param merchantId 商户ID
     * @param softId     软件ID
     * @return
     */
    MerchantSoftInfoModel getSoftAcc(Long merchantId, Long softId);

    /**
     * 保存商户软件帐户
     *
     * @param mSoftAcc
     * @return
     */
    boolean saveSoftAcc(MerchantSoftInfoModel mSoftAcc);

    /**
     * 修改商户软件帐户
     *
     * @param softAccCur
     * @return
     */
    boolean updateSoftAccById(MerchantSoftInfoModel softAccCur);


    /**
     * 根据商户的id查询商户的软件信息
     *
     * @param merchantId
     * @return
     */
    List<MerchantSoftInfoVO> listByMerchantId(Long merchantId);

    /**
     *
     * 功能描述: 批量保存商户的软件信息
     *
     * @param merchantId
     * @param list
     * @return
     * @author cjunyuan
     * @date 2019/3/6 16:10
     */
    void batchSaveMerchantSoftInfo(Long merchantId, List<MerchantSoftInfoDTO> list);

    /**
     * 根据商户的Id和软件Id查询记录
     *
     * @param softTypeId
     * @param merchantId
     * @return
     */
    MerchantSoftInfoModel getSoftTypeByIdAndMerchantId(Long softTypeId, Long merchantId);

    /**
     *
     * 功能描述: 回收软件权益
     *
     * @param mchId
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/29 10:31
     */
    void recycleSoftStrategy(Long mchId, MerchantSoftInfoDTO dto);

    /**
     *
     * 功能描述: 赠送软件权益
     *
     * @param mchId
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/29 10:31
     */
    void giftSoftStrategy(Long mchId, MerchantSoftInfoDTO dto);

}
