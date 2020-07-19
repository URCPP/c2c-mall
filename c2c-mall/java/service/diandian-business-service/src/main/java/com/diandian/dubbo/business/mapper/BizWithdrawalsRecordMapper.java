package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalsRecordModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-10 21:14
 */
public interface BizWithdrawalsRecordMapper extends BaseMapper<BizWithdrawalsRecordModel> {

    IPage<BizWithdrawalsRecordModel> listPage(Page page, @Param("params") Map<String, Object> params);
}
