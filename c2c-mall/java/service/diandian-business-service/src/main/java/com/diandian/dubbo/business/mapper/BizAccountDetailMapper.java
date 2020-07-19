package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.biz.BizAccountDetailModel;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.vo.OrgAccountDetailVO;
import com.diandian.dubbo.facade.vo.OrgAccountStatementVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 机构账号明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizAccountDetailMapper extends BaseMapper<BizAccountDetailModel> {

    /**
     *
     * 功能描述: 账户每天统计每天的流水
     *
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 10:38
     */
    List<OrgAccountStatementVO> statisticsEveryDay(@Param("query") OrgAccountQueryDTO query);

    /**
     *
     * 功能描述: 自定义对象分页查询
     *
     * @param page
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 19:44
     */
    Page<OrgAccountDetailVO> orgAccountDetailListPage(Page<OrgAccountDetailVO> page, @Param("query") OrgAccountQueryDTO query);
}
