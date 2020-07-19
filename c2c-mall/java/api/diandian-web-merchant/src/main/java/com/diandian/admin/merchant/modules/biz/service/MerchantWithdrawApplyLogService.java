package com.diandian.admin.merchant.modules.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.merchant.MerchantWithdrawApplyLogModel;

/**
 * 商家提现申请记录表
 *
 * @author jbh
 * @date 2019/02/26
 */
public interface MerchantWithdrawApplyLogService extends IService<MerchantWithdrawApplyLogModel> {
    /**
     * 添加提现申请记录
     * @param withdrawApplyLogModel
     */
    void saveWithdrawApplyLog(MerchantWithdrawApplyLogModel withdrawApplyLogModel);
}
