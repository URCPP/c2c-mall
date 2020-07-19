package com.diandian.dubbo.facade.service.shop;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizBusinessInformationModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-08-28 11:01
 */
public interface ShopInfoService {

    List<ShopInfoModel> getShopInfoByParam(String column, String value);

    List<ShopInfoModel> getShopInfoByMerchantId(Long userId);

    /**
     * 查询所有
     *
     * @return
     */
    List<ShopInfoModel> listAll();

    PageResult listPage(Map<String, Object> params);

    void insert(ShopInfoModel shopInfoModel);

    void updateById(ShopInfoModel shopInfoModel);

    void deleteById(ShopInfoModel shopInfoModel);

    ShopInfoModel getById(Long id);

    ShopInfoModel getByUserId(Long id);

    void openShop(BizBusinessInformationModel bizBusinessInformationModel,Long userId,Integer shopState);

    ShopInfoModel getShopInfoByName(String shopName);

    PageResult getShopByRecommendMemberId(Map<String, Object> params);

    ShopInfoModel getShopInfoBymerId(Long merchantInfoId);

    void upgrade(ShopInfoModel shopInfo, MerchantInfoModel dto, Integer level,Integer Type);

}
