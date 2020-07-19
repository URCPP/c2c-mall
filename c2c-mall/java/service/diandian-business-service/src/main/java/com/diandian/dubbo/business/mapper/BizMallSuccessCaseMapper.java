package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.biz.BizMallHelpModel;
import com.diandian.dubbo.facade.model.biz.BizMallSuccessCaseModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 商城成功案例表
 *
 * @author zweize
 * @date 2019/03/06
 */
public interface BizMallSuccessCaseMapper extends BaseMapper<BizMallSuccessCaseModel> {

    Page<BizMallSuccessCaseModel> listCasePage(Page<BizMallSuccessCaseModel> page, @Param("params")Map<String,Object> params);


}
