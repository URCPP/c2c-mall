package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizBusinessInformationMapper;
import com.diandian.dubbo.business.mapper.ShopInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizBusinessInformationModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-08-28 11:02
 */
@Service("shopInfoService")
@Slf4j
public class ShopInfoServiceImpl implements ShopInfoService {

    @Autowired
    private ShopInfoMapper shopInfoMapper;
    @Autowired
    private BizBusinessInformationMapper bizBusinessInformationMapper;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;

    @Override
    public List<ShopInfoModel> getShopInfoByParam(String column, String value) {
        return shopInfoMapper.selectList(new QueryWrapper<ShopInfoModel>().eq(column,value));
    }

    @Override
    public List<ShopInfoModel> getShopInfoByMerchantId(Long userId) {
        return shopInfoMapper.selectList(new QueryWrapper<ShopInfoModel>().eq("merchant_id",userId));
    }

    @Override
    public List<ShopInfoModel> listAll() {
        return shopInfoMapper.listAll();
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        IPage<ShopInfoModel> page=shopInfoMapper.listPage(new PageWrapper<ShopInfoModel>(params).getPage(),params);
        return new PageResult(page);
        /*String shopTypeId = null == params.get("shopTypeId")?"":params.get("shopTypeId").toString();
        String shopName = null == params.get("shopName")?"":params.get("shopName").toString();
        String contactName = null == params.get("contactName")?"":params.get("contactName").toString();
        IPage<ShopInfoModel> page=shopInfoMapper.selectPage(
                new PageWrapper<ShopInfoModel>(params).getPage(),
                new QueryWrapper<ShopInfoModel>().eq(StrUtil.isNotBlank(shopTypeId),"shop_type_id",shopTypeId)
                        .eq(StrUtil.isNotBlank(shopName),"shop_name",shopName)
                        .eq(StrUtil.isNotBlank(contactName),"contact_name",contactName)
        );
        return new PageResult(page);*/
    }

    @Override
    public void insert(ShopInfoModel shopInfoModel) {
        shopInfoMapper.insert(shopInfoModel);
    }

    @Override
    public void updateById(ShopInfoModel shopInfoModel) {
        shopInfoModel.setUpdateTime(new Date());
        shopInfoMapper.updateById(shopInfoModel);
    }

    @Override
    public void deleteById(ShopInfoModel shopInfoModel) {
        shopInfoMapper.deleteById(shopInfoModel);
    }

    @Override
    public ShopInfoModel getById(Long id) {
        return shopInfoMapper.selectById(id);
    }

    @Override
    public ShopInfoModel getByUserId(Long id) {
        return shopInfoMapper.selectOne(
                new QueryWrapper<ShopInfoModel>()
                        .eq("user_id",id)
                        .eq("del_flag", BizConstant.STATE_NORMAL)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void openShop(BizBusinessInformationModel bizBusinessInformationModel, Long userId, Integer shopState) {
        ShopInfoModel shopInfoModel=new ShopInfoModel();
        shopInfoModel.setContactName(bizBusinessInformationModel.getContactsName());
        shopInfoModel.setContactPhone(bizBusinessInformationModel.getContactsPhone());
        shopInfoModel.setContactEmail(bizBusinessInformationModel.getContactEmail());
        shopInfoModel.setContactQQ(bizBusinessInformationModel.getContactQQ());
        shopInfoModel.setShopName(bizBusinessInformationModel.getShopName());
        shopInfoModel.setMerchantId(bizBusinessInformationModel.getMerchantId());
        shopInfoModel.setSingleQualityDeposit(bizBusinessInformationModel.getSingleQualityDeposit());
        shopInfoModel.setState(shopState);
        shopInfoModel.setUserId(userId);
        shopInfoModel.setShopTypeId(bizBusinessInformationModel.getShopTypeId());
        shopInfoModel.setShopTypeName(bizBusinessInformationModel.getShopTypeName());
        shopInfoModel.setClassifyId1(bizBusinessInformationModel.getClassifyId1());
        shopInfoModel.setClassifyId2(bizBusinessInformationModel.getClassifyId2());
        shopInfoModel.setReferralCode(bizBusinessInformationModel.getReferralCode());
        shopInfoModel.setAgentProfit(bizBusinessInformationModel.getAgentProfit());
        shopInfoModel.setPlatformProfit(bizBusinessInformationModel.getPlatformProfit());
        shopInfoModel.setFloorPriceProportion(bizBusinessInformationModel.getFloorPriceProportion());
        shopInfoMapper.insert(shopInfoModel);
        bizBusinessInformationModel.setShopId(shopInfoModel.getId());
        bizBusinessInformationMapper.insert(bizBusinessInformationModel);
    }

    @Override
    public ShopInfoModel getShopInfoByName(String shopName) {
        return shopInfoMapper.selectOne(
                new QueryWrapper<ShopInfoModel>()
                        .eq("shop_name",shopName)
        );
    }

    @Override
    public PageResult getShopByRecommendMemberId(Map<String, Object> params) {
        IPage<ShopInfoModel> page=shopInfoMapper.selectPage(
                new PageWrapper<ShopInfoModel>(params).getPage(),
                new QueryWrapper<ShopInfoModel>()
                        .eq("del_flag",BizConstant.STATE_NORMAL)
                        .eq("state",1)
                        .eq("referral_code",params.get("referralCode"))
        );
        Map map;
        for (ShopInfoModel shopInfoModel:page.getRecords()){
            map=new HashMap();
            map.put("shopId",shopInfoModel.getId());
            map.put("exclusiveMemberPhone","0");
            shopInfoModel.setProductNum(productInfoService.getByShopId(map).size());
        }
        return new PageResult(page);
    }

    @Override
    public ShopInfoModel getShopInfoBymerId(Long merchantInfoId) {
        return shopInfoMapper.getShopInfoBymerId(merchantInfoId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upgrade(ShopInfoModel shopInfo, MerchantInfoModel dto, Integer level,Integer type) {
        if (type==1) {
            shopInfoService.updateById(shopInfo);
        }else {
            shopInfoService.insert(shopInfo);
        }
        dto.setLevel(level);
        merchantInfoService.updateById(dto);
        merchantAccountInfoService.openingAward(dto.getId(),level);
    }

}