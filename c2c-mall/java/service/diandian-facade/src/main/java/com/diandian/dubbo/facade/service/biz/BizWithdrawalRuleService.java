package com.diandian.dubbo.facade.service.biz;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalRuleModel;

import java.util.Map;

/**
 * 提现规则表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizWithdrawalRuleService  {

    PageResult listPage(Map<String, Object> params);

    BizWithdrawalRuleModel getBizWithdrawalRuleModel();

    BizWithdrawalRuleModel getByName(String name);

    BizWithdrawalRuleModel getById(Long id);

    void update(BizWithdrawalRuleModel bizWithdrawalRuleModel, Wrapper wrapper);

    void updateById(BizWithdrawalRuleModel bizWithdrawalRuleModel);

    void save(BizWithdrawalRuleModel bizWithdrawalRuleModel);

    void removeById(Long id);
}
