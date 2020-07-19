package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenCntVO;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenFeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 软件类型
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizSoftwareTypeMapper extends BaseMapper<BizSoftwareTypeModel> {

    /**
     *
     * 功能描述: 查询软件类型开通情况
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:32
     */
    List<StatisticsTypeOpenCntVO> getSoftwareOpenOverview(@Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 统计每种软件类型总的开通费用
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/3/28 10:32
     */
    List<StatisticsTypeOpenFeeVO> statisticsSoftwareTypeOpenFee(@Param("params") Map<String, Object> params);
}
