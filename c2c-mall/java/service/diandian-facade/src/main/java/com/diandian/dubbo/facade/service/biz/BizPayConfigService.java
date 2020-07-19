package com.diandian.dubbo.facade.service.biz;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizPayConfigModel;

import java.util.List;
import java.util.Map;

/**
 * 支付管理表
 *
 * @author zweize
 * @date 2019/03/04
 */
public interface BizPayConfigService {

    PageResult listPage(Map<String,Object> params);

    BizPayConfigModel getById(Long id);

    void updateById(BizPayConfigModel bizPayConfigModel);

    void save(BizPayConfigModel bizPayConfigModel);

    void updateStateByIdBatch(List<Long> ids,Integer state);

    List<BizPayConfigModel> list();
}
