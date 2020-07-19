package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenFeeVO;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenCntVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 机构管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysOrgTypeMapper extends BaseMapper<SysOrgTypeModel> {

    /**
     *
     * 功能描述: 查询机构类型开通情况
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:32
     */
    List<StatisticsTypeOpenCntVO> getOrgTypeOpenOverview(@Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 统计每种机构类型总的开通费用
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/3/28 10:32
     */
    List<StatisticsTypeOpenFeeVO> statisticsOrgTypeOpenFee(@Param("params") Map<String, Object> params);
}
