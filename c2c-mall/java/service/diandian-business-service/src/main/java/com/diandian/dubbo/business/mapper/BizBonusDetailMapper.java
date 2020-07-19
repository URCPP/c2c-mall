package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizBonusDetailModel;
import com.diandian.dubbo.facade.vo.OrgAccountDetailVO;
import com.diandian.dubbo.facade.vo.OrgAccountStatementVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 奖金明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizBonusDetailMapper extends BaseMapper<BizBonusDetailModel> {

    /**
     *
     * 功能描述: 统计奖金流水
     *
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 11:23
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
    Page<OrgAccountDetailVO> orgBonusDetailListPage(Page<OrgAccountDetailVO> page, @Param("query") OrgAccountQueryDTO query);
}
