package com.diandian.admin.merchant.modules.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.merchant.MerchantWithdrawApplyOptLogModel;
import com.diandian.dubbo.facade.dto.PageResult;

import java.util.Map;

/**
 * 商家提现申请操用记录表
 *
 * @author jbh
 * @date 2019/02/26
 */
public interface MerchantWithdrawApplyOptLogService extends IService<MerchantWithdrawApplyOptLogModel> {
    /**
     * 添加提现操作记录
     * @param optLogModel
     */
    void saveWithdrawOptLog(MerchantWithdrawApplyOptLogModel optLogModel);

    /**
     * 提现操作记录列表、分页
     * @param params
     * @return
     */
    PageResult ListPage(Map<String,Object> params);
}
