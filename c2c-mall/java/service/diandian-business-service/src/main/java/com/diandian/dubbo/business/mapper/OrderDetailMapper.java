package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.merchant.MerchantOrderTradeDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailNumDTO;
import com.diandian.dubbo.facade.dto.order.OrderDetailQueryDTO;
import com.diandian.dubbo.facade.dto.order.OrderNumDTO;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.vo.ProductStateNumberVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: yqingyuan
 * @Date: 2019/2/28 14:33
 * @Version 1.0
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetailModel> {

    /**
     * @Description: 根据店铺id和日期查询当日收益
     * @Param:  shopId,date
     * @return:  Map
     * @Author: wsk
     * @Date: 2019/8/31
     */
    /*Map<String,String> getProfitByShopId(@Param("shopId")Long shopId,@Param("date")Date date);*/

}
