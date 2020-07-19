package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.biz.BizFinancialRecordsDetailModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-03 15:37
 */
public interface BizFinancialRecordsDetailMapper extends BaseMapper<BizFinancialRecordsDetailModel> {

    /**
     * @Description: 根据店铺id和日期查询当日收益
     * @Param:  shopId,date
     * @return:  Map
     * @Author: wsk
     * @Date: 2019/8/31
     */
    Map<String,Object> getByShopId(@Param("shopId")Long shopId, @Param("date") Date date,@Param("type")Integer type);

    /**
    * @Description: 获取七天前的数据
    * @Param:
    * @return:
    * @Author: wsk
    * @Date: 2019/9/5
    */
    List<BizFinancialRecordsDetailModel> getUnissuedByCreateTime();
}
