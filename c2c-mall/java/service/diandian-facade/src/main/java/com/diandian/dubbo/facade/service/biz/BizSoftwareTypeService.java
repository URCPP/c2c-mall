package com.diandian.dubbo.facade.service.biz;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenCntVO;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenFeeVO;

import java.util.List;
import java.util.Map;

/**
 * 软件类型
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizSoftwareTypeService {

    /**
     * 软件类型列表分页
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 软件类型列表
     *
     * @return
     */
    List<BizSoftwareTypeModel> listSoftType();

    /**
     * 根据ID 查询软件类型
     *
     * @param softTypeId
     * @return
     */
    BizSoftwareTypeModel getSoftTypeById(Long softTypeId);

    /**
     * 修改软件类型
     *
     * @param bizSoftwareTypeModel
     */
    void updateById(BizSoftwareTypeModel bizSoftwareTypeModel);

    /**
     * 保存软件类型
     *
     * @param bizSoftwareTypeModel
     */
    void save(BizSoftwareTypeModel bizSoftwareTypeModel);

    /**
     * 删除软件类型
     *
     * @param id
     */
    void removeById(Long id);

    /**
     *
     * 功能描述: 查询软件类型开通情况
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:32
     */
    List<StatisticsTypeOpenCntVO> getSoftwareOpenOverview(Map<String, Object> params);

    /**
     *
     * 功能描述: 统计每种软件类型总的开通费用
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/3/28 10:32
     */
    List<StatisticsTypeOpenFeeVO> statisticsSoftwareTypeOpenFee(Map<String, Object> params);

    List<BizSoftwareTypeModel> getOrderBySoftware();
}
