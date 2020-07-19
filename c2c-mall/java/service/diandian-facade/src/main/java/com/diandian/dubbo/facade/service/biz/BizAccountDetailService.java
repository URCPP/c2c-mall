package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizAccountDetailModel;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.vo.OrgAccountStatementVO;

import java.util.List;
import java.util.Map;

/**
 * 机构账号明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizAccountDetailService {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     *
     * 功能描述: 新增账户流水明细
     *
     * @param accountDetail
     * @return
     * @author cjunyuan
     * @date 2019/2/22 14:05
     */
    boolean save(BizAccountDetailModel accountDetail);

    /**
     *
     * 功能描述: 账户每天统计每天的流水
     *
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 10:38
     */
    List<OrgAccountStatementVO> statisticsEveryDay(OrgAccountQueryDTO query);

    /**
     *
     * 功能描述: 自定义对象分页查询
     *
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 19:37
     */
    PageResult listPage(OrgAccountQueryDTO query);

}
