package com.diandian.dubbo.facade.service.biz;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;

import java.util.Map;

/**
 * 机构开通明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizOpenDetailService {

    /**
     * 分页查询
     * @author: jbuhuan
     * @date: 2019/3/20 10:22
     * @param query
     * @return: R
     */
    PageResult listPage(OrgAccountQueryDTO query);
}
