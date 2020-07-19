package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.biz.BizBusinessInformationModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-06 15:57
 */
public interface BizBusinessInformationMapper extends BaseMapper<BizBusinessInformationModel> {

    IPage<BizBusinessInformationModel> listPage(Page page, @Param("params") Map<String, Object> params);

    BizBusinessInformationModel getByShopId(@Param("shopId")Long shopId);
}
