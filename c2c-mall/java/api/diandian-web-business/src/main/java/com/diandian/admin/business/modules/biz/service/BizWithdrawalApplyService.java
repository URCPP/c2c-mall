package com.diandian.admin.business.modules.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.biz.BizWithdrawalApplyModel;
import com.diandian.dubbo.facade.dto.PageResult;

import java.util.List;
import java.util.Map;


/**
 * 提现申请表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizWithdrawalApplyService extends IService<BizWithdrawalApplyModel> {

    PageResult listPage(Map<String, Object> params);

    /**
     * 统计机构账号当日提现次数
     * @param orgId
     * @return
     */
   Integer countByOrgId(Long orgId);

    /**
     * 保存提现记录
     * @param bizWithdrawalApplyModel
     */
   void saveBizWithdrawalApply(BizWithdrawalApplyModel bizWithdrawalApplyModel);

    /**
     * 提现审核
     * @param bizWithdrawalApplyModel
     */
   void auditBizWithdrawalApply(BizWithdrawalApplyModel bizWithdrawalApplyModel);

    /**
     * 提现付款
     * @param bizWithdrawalApplyModel
     */
   void paymentBizWithdrawalApply(BizWithdrawalApplyModel bizWithdrawalApplyModel,String pic);
}
