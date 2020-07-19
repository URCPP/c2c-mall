package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.model.merchant.MerchantRecipientsSetModel;

import java.util.List;
import java.util.Map;

/**
 * 商户收货地址设置表
 *
 * @author jbh
 * @date 2019/02/27
 */
public interface MerchantRecipientsSetService {

    /**
     * 添加
     *
     * @param model
     * @return
     */
    boolean save(MerchantRecipientsSetModel model);

    /**
     * 修改
     *
     * @param model
     * @return
     */
    boolean update(MerchantRecipientsSetModel model);

    /**
     * 收货地址列表
     *
     * @param params
     * @return
     */
    List<MerchantRecipientsSetModel> list(Map<String, Object> params);

    /**
     * 设置默认收货地址
     *
     * @param id
     * @return
     */
    boolean setDefault(Long id);

    /**
     *
     * 功能描述: 获取商户默认的收货地址
     *
     * @param mchId
     * @return
     * @author cjunyuan
     * @date 2019/3/31 16:25
     */
    MerchantRecipientsSetModel getMchDefaultShippingAddress(Long mchId);

    void delete(Long id);

    MerchantRecipientsSetModel selectOneById(Long id);
}
