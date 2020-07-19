package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeStrategyModel;

import java.util.Map;


/**
 * 软件类型策略
 *
 * @author zweize
 * @date 2019/02/19
 */
public interface BizSoftwareTypeStrategyService {

    PageResult listPage(Map<String, Object> params);

    BizSoftwareTypeStrategyModel getById(Long id);

    void save(BizSoftwareTypeStrategyModel bizSoftwareTypeStrategyModel);

    void updateById(BizSoftwareTypeStrategyModel bizSoftwareTypeStrategyModel);

    void removeById(Long id);

    BizSoftwareTypeStrategyModel getByRewardType(Integer strategyType,Integer rewardType);
}
