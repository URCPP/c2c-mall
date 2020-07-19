package com.diandian.admin.merchant.modules.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;

import java.util.List;

/**
 * 店铺信息
 *
 * @author zzhihong
 * @date 2019/02/26
 */
public interface ShopInfoMapper extends BaseMapper<ShopInfoModel> {

    /**
     * 查询所有
     *
     * @return
     */
    List<ShopInfoModel> listAll();

}
