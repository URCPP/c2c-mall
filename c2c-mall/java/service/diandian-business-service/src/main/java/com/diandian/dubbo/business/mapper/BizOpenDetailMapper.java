package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOpenDetailModel;
import com.diandian.dubbo.facade.vo.OrgOpenDetailVO;
import org.apache.ibatis.annotations.Param;

/**
 * 机构开通明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizOpenDetailMapper extends BaseMapper<BizOpenDetailModel> {

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
    Page<OrgOpenDetailVO> orgOpenDetailListPage(Page<OrgOpenDetailVO> page, @Param("query") OrgAccountQueryDTO query);
}
