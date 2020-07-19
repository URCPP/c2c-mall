package com.diandian.admin.merchant.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.admin.model.sys.SysAreaModel;
import com.diandian.dubbo.facade.dto.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 地区码表
 *
 * @author x
 * @date 2018/09/26
 */
public interface SysAreaService extends IService<SysAreaModel> {

    /**
     * 分页查询
     *
     * @return page
     */
    PageResult listPage(Map<String, Object> params);

    List<SysAreaModel> list();

    List<SysAreaModel> listByParentId(Long parentId);
}
