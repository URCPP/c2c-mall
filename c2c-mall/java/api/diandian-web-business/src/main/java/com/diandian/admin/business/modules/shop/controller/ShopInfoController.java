package com.diandian.admin.business.modules.shop.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.business.modules.shop.res.ShopInfoRes;
import com.diandian.admin.business.modules.shop.service.ShopTypeService;
import com.diandian.admin.business.modules.sys.service.SysUserRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.shop.ShopTypeModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.admin.model.sys.SysUserRoleModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.SMSUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizBusinessInformationModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import com.diandian.dubbo.facade.model.product.ProductSkuModel;
import com.diandian.dubbo.facade.model.product.ProductSkuPriceModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.model.user.UserAccountInfoModel;
import com.diandian.dubbo.facade.model.user.UserShopRoleModel;
import com.diandian.dubbo.facade.service.biz.BizBusinessInformationService;
import com.diandian.dubbo.facade.service.product.ProductInfoService;
import com.diandian.dubbo.facade.service.product.ProductSkuPriceService;
import com.diandian.dubbo.facade.service.product.ProductSkuService;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.user.UserAccountInfoService;
import com.diandian.dubbo.facade.service.user.UserShopRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author x
 * @date 2019-02-26
 */
@RequestMapping("/shopInfo")
@RestController
@Slf4j
public class ShopInfoController {

    @Autowired
    private ShopInfoService shopInfoService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private UserShopRoleService userShopRoleService;

    @Autowired
    private UserAccountInfoService userAccountInfoService;

    @Autowired
    private ProductSkuPriceService productSkuPriceService;

    @Autowired
    private ProductSkuService productSkuService;


    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${spring.profiles.active}")
    private String profilesActive;

    @Value("${role.merchant}")
    private Long roleId;

    @Value("${softwareTypeId}")
    private Long softwareTypeId;

    @Value("${pusherId}")
    private Long pusherId;

    @GetMapping("/list")
    public RespData list() {
        return RespData.ok(shopInfoService.listAll());
    }

    /**
    * @Description:  分页
    * @Param:  params
    * @return:  RespData
    * @Author: wsk
    * @Date: 2019/8/28
    */
    @GetMapping("listPage")
    public RespData listPage(@RequestParam Map<String, Object> params){
        PageResult page=shopInfoService.listPage(params);
        return RespData.ok(page);
    }

    /**
    * @Description: 更新
    * @Param:  shopInfoModel
    * @return:  RespData
    * @Author: wsk
    * @Date: 2019/8/28
    */
    @PostMapping("updateById")
    public RespData updateById(@RequestBody ShopInfoModel shopInfoModel){
        if (shopInfoModel.getAgentProfit()!=null){
            shopInfoModel.setAgentProfit(shopInfoModel.getAgentProfit().divide(BigDecimal.valueOf(100)));
        }
        if (shopInfoModel.getFloorPriceProportion()!=null){
            shopInfoModel.setFloorPriceProportion(shopInfoModel.getFloorPriceProportion().divide(BigDecimal.valueOf(100)));
        }
        if (shopInfoModel.getPlatformProfit()!=null){
            shopInfoModel.setPlatformProfit(shopInfoModel.getPlatformProfit().divide(BigDecimal.valueOf(100)));
        }
        List<ProductSkuModel> list=productSkuService.getByShopId(shopInfoModel.getId());
        for (ProductSkuModel productSkuModel:list){
            BigDecimal money=BigDecimal.ZERO;
            for (ProductSkuPriceModel productSkuPriceModel:productSkuModel.getPriceList()){
                if (softwareTypeId.equals(productSkuPriceModel.getSoftwareTypeId())){
                    money=productSkuPriceModel.getPrice();
                }
            }
            for (ProductSkuPriceModel productSkuPriceModel:productSkuModel.getPriceList()){
                if (!softwareTypeId.equals(productSkuPriceModel.getSoftwareTypeId())){
                    //productSkuPriceModel.setPrice(money.subtract(money.multiply(shopInfoModel.getAgentProfit().add(shopInfoModel.getFloorPriceProportion()).add(shopInfoModel.getPlatformProfit()))));
                   // BigDecimal mallProductShareBenefitPercentage=bizIdentityService.getById(pusherId).getMallProductShareBenefitPercentage();
                   // productSkuPriceModel.setPrice(money.subtract(money.multiply(shopInfoModel.getAgentProfit().add(shopInfoModel.getFloorPriceProportion()).add(shopInfoModel.getPlatformProfit())).multiply(mallProductShareBenefitPercentage)));
                    productSkuPriceService.updateById(productSkuPriceModel);
                }
                /*if (!softwareTypeId.equals(productSkuPriceModel.getSoftwareTypeId())){
                    productSkuPriceModel.setPrice(money.subtract(money.multiply(shopInfoModel.getFloorPriceProportion())));
                    productSkuPriceService.updateById(productSkuPriceModel);
                }*/
            }
        }
        shopInfoService.updateById(shopInfoModel);
        return RespData.ok();
    }

    /**
    * @Description: 逻辑删除
    * @Param:  shopInfoModel
    * @return:  RespData
    * @Author: wsk
    * @Date: 2019/8/28
    */
    @PostMapping("deleteById")
    public RespData deleteById(@RequestBody ShopInfoModel shopInfoModel){
        shopInfoModel.setDelFlag(BizConstant.STATE_DISNORMAL);
        shopInfoService.updateById(shopInfoModel);
        return RespData.ok();
    }


    /**
    * @Description:  根据商户id获取店铺
    * @Param:  merchantId
    * @return:  RespData
    * @Author: wsk
    * @Date: 2019/9/2
    */
    @GetMapping("getShopInfoByMerchantId/{merchantId}")
    public RespData getShopInfoByMerchantId(@PathVariable Long merchantId){
        return RespData.ok(shopInfoService.getShopInfoByMerchantId(merchantId));
    }

    /**
    * @Description: 店铺审核
    * @Param:  shopInfoModel
    * @return:  RespData
    * @Author: wsk
    * @Date: 2019/9/2
    */
    @PostMapping("shopExamine")
    public RespData shopExamine(@RequestBody ShopInfoModel shopInfoModel){
        if (shopInfoModel.getAgentProfit()!=null){
            shopInfoModel.setAgentProfit(shopInfoModel.getAgentProfit().divide(BigDecimal.valueOf(100)));
        }
        if (shopInfoModel.getFloorPriceProportion()!=null){
            shopInfoModel.setFloorPriceProportion(shopInfoModel.getFloorPriceProportion().divide(BigDecimal.valueOf(100)));
        }
        if (shopInfoModel.getPlatformProfit()!=null){
            shopInfoModel.setPlatformProfit(shopInfoModel.getPlatformProfit().divide(BigDecimal.valueOf(100)));
        }
        if(null!=shopInfoModel.getSingleQualityDeposit() && shopInfoModel.getSingleQualityDeposit().equals(0)){
            shopInfoModel.setPayState(2);
            shopInfoModel.setState(1);
        }
        shopInfoService.updateById(shopInfoModel);
        shopInfoModel=shopInfoService.getById(shopInfoModel.getId());
        SysUserModel sysUserModel=sysUserService.getOne(
                new QueryWrapper<SysUserModel>()
                        .eq("del_flag",0)
                        .eq("id",shopInfoModel.getUserId())
        );
        if(shopInfoModel.getState()==2&&sysUserModel!=null){
            sysUserService.delete(sysUserModel.getId());
        }
        if(shopInfoModel.getState()==1&&sysUserModel!=null){
            sysUserModel.setState(0);
            sysUserModel.setOrgTypeId(Long.valueOf(1));
            sysUserModel.setOrgId(Long.valueOf(1));
            sysUserService.updateById(sysUserModel);
            SysUserRoleModel sysUserRoleModel=new SysUserRoleModel();
            sysUserRoleModel.setUserId(sysUserModel.getId());
            sysUserRoleModel.setRoleId(roleId);
            sysUserRoleService.save(sysUserRoleModel);
            UserShopRoleModel userShopRoleModel=new UserShopRoleModel();
            userShopRoleModel.setShopId(shopInfoModel.getId());
            userShopRoleModel.setUserId(sysUserModel.getId());
            userShopRoleModel.setRoleId(roleId);
            userShopRoleService.insert(userShopRoleModel);
            UserAccountInfoModel userAccountInfoModel=new UserAccountInfoModel();
            userAccountInfoModel.setUserId(sysUserModel.getId());
            userAccountInfoModel.setAvailableBalance(BigDecimal.ZERO);
            userAccountInfoService.insert(userAccountInfoModel);
        }
        return RespData.ok();
    }

    @GetMapping("getById/{id}")
    public RespData getById(@PathVariable Long id){
        return RespData.ok(shopInfoService.getById(id));
    }

    /**
     * @Description: 开通店铺
     * @Param:  shopInfoModel
     * @return:  RespData
     * @Author: wsk
     * @Date: 2019/9/2
     */
    @PostMapping("openShop")
    public RespData openShop(@RequestBody BizBusinessInformationModel bizBusinessInformationModel){
        SysUserModel exists = sysUserService.getByUsername(bizBusinessInformationModel.getPhone());
        AssertUtil.isTrue(null == exists || SysConstant.IS_YES.equals(exists.getDelFlag()), "用户名已存在");
        Integer userState;
        Integer shopState;
        //如果是管理员开通,则通过审核
        if(bizBusinessInformationModel.getType()==1){
            //店铺正常,用户正常
            shopState=1;
            userState=0;
        }else{
            //店铺待审核,用户禁用
            shopState=0;
            userState=1;
        }
        ShopInfoModel shopInfoModel=shopInfoService.getShopInfoByName(bizBusinessInformationModel.getBusinessName());
        AssertUtil.isTrue(null==shopInfoModel,"店铺已存在或正在审核");
        SysUserModel sysUserModel=new SysUserModel();
        String salt = RandomStringUtils.randomAlphanumeric(19);
        sysUserModel.setUsername(bizBusinessInformationModel.getPhone());
        sysUserModel.setPassword(new Sha256Hash(bizBusinessInformationModel.getPassword(),salt).toHex());
        sysUserModel.setSalt(salt);
        sysUserModel.setType(3);
        sysUserModel.setPhone(bizBusinessInformationModel.getPhone());
        sysUserModel.setState(userState);
        sysUserService.saveSysUser(sysUserModel);
        shopInfoService.openShop(bizBusinessInformationModel,sysUserModel.getId(),shopState);
        return RespData.ok();
    }
}
