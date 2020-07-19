package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;

import java.util.Map;

/**
 * 商城目录配置
 *
 * @author zweize
 * @date 2019/02/27
 */
public interface BizMallConfigService {

    PageResult listPage(Map<String, Object> params);

    void save(BizMallConfigModel bizMallConfigModel);

    void updateById(BizMallConfigModel bizMallConfigModel);

    void deleteById(Long id);

    BizMallConfigModel getById(Long id);

    /**
     * 根据英文名获最参数
     *
     * @param appLoadUrlEngName
     * @return
     */
    BizMallConfigModel getByEngName(String appLoadUrlEngName);
}