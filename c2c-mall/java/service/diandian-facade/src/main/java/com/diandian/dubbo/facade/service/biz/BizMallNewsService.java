package com.diandian.dubbo.facade.service.biz;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallNewsModel;

import java.util.Map;


/**
 * 商城新闻表
 *
 * @author zweize
 * @date 2019/02/28
 */
public interface BizMallNewsService {

    PageResult listPage(Map<String,Object> params);

    void save(BizMallNewsModel bizMallNewsModel);

    void updateById(BizMallNewsModel bizMallNewsModel);

    void deleteById(Long id);

    BizMallNewsModel getById(Long id);
}
