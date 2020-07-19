package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeStrategyModel;
import com.diandian.dubbo.facade.vo.SoftWareStrategyVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 软件类型策略
 *
 * @author zweize
 * @date 2019/02/19
 */
public interface BizSoftwareTypeStrategyMapper extends BaseMapper<BizSoftwareTypeStrategyModel> {

    Page<SoftWareStrategyVO> listBizSoftwareTypeStrategyPage(Page<SoftWareStrategyVO> page, @Param("params") Map<String, Object> params);

}
