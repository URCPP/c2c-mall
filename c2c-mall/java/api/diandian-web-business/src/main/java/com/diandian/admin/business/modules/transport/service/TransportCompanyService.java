package com.diandian.admin.business.modules.transport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.transport.TransportCompanyModel;
import com.diandian.dubbo.facade.dto.PageResult;

import java.util.Map;

/**
 * 运输公司
 *
 * @author zzhihong
 * @date 2019/02/28
 */
public interface TransportCompanyService extends IService<TransportCompanyModel> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 通过ID逻辑删除
     *
     * @param id
     */
    void logicDeleteById(Long id);

}
