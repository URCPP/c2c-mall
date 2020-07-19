package com.diandian.dubbo.facade.service.biz;


import com.diandian.dubbo.facade.model.biz.BizOrgInfoModel;

/**
 * 机构表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizOrgInfoService {

    /**
     * 获取机构对象
     * @param orgId
     * @return
     */
    BizOrgInfoModel getBizOrgInfoById(Long orgId);

}
