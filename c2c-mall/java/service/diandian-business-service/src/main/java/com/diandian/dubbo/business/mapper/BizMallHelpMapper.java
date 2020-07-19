package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.biz.BizMallHelpModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 商城图文配置表
 *
 * @author zweize
 * @date 2019/02/27
 */
public interface BizMallHelpMapper extends BaseMapper<BizMallHelpModel> {

    Page<BizMallHelpModel> listHelpPage(Page<BizMallHelpModel> page, @Param("params")Map<String,Object> params);


}
