package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-08-28 11:26
 */
public interface ShopInfoMapper extends BaseMapper<ShopInfoModel> {

    /**
     * 查询所有
     *
     * @return
     */
    List<ShopInfoModel> listAll();

    IPage<ShopInfoModel> listPage(IPage page, @Param("params") Map<String, Object> params);

    ShopInfoModel getShopInfoBymerId(@Param("merchantInfoId") Long merchantInfoId);
}
