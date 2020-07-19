package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.biz.BizOrgApplyModel;
import com.diandian.dubbo.facade.vo.OrgApplyInfoDetailVO;
import com.diandian.dubbo.facade.vo.OrgApplyInfoListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 机构申请表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizOrgApplyMapper extends BaseMapper<BizOrgApplyModel> {

    /**
     *
     * 功能描述: 机构申请信息分页查询
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/20 9:49
     */
    Page<OrgApplyInfoListVO> orgApplyInfoListPage(Page<OrgApplyInfoListVO> page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 获取申请信息详情
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/26 17:47
     */
    OrgApplyInfoDetailVO getOrgApplyInfoDetail(long id);
}
