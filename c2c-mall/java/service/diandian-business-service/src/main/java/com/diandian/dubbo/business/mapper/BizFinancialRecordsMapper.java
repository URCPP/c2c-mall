package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-08-31 16:18
 */
public interface BizFinancialRecordsMapper extends BaseMapper<BizFinancialRecordsModel> {

    IPage<BizFinancialRecordsModel> getByMonth(Page page, @Param("params") Map<String,Object> params);
}
