package com.diandian.dubbo.facade.service.sys;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.sys.OrgTypeDTO;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenFeeVO;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenCntVO;

import java.util.List;
import java.util.Map;

/**
 * 机构管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysOrgTypeService {

    /**
     *
     * 功能描述: 根据条件查询列表
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:59
     */
    List<SysOrgTypeModel> list(OrgTypeDTO dto);

    /**
     *
     * 功能描述: 分页查询
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/15 16:27
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 添加机构类型信息
     * @param orgType
     * @return
     */
    Boolean insertOrgType(SysOrgTypeModel orgType);

    /**
     * 删除机构类型
     * @param id 机构 ID
     * @return 成功、失败
     */
    Boolean deleteOrgTypeById(Long id);

    /**
     * 更新机构类型
     * @param orgType 机构类型信息
     * @return 成功、失败
     */
    Boolean updateOrgType(SysOrgTypeModel orgType);

    /**
     *
     * 功能描述: 根据ID查询
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:56
     */
    SysOrgTypeModel getById(Long id);

    /**
     *
     * 功能描述: 查询机构类型开通情况
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:32
     */
    List<StatisticsTypeOpenCntVO> getOrgTypeOpenOverview(Map<String, Object> params);

    /**
     *
     * 功能描述: 统计每种机构类型总的开通费用
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/3/28 10:32
     */
    List<StatisticsTypeOpenFeeVO> statisticsOrgTypeOpenFee(Map<String, Object> params);
}

