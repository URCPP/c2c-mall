package com.diandian.admin.merchant.modules.shop.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.common.constant.RedisKeyConstant;
import com.diandian.admin.merchant.modules.shop.service.ShopTypeService;
import com.diandian.admin.merchant.modules.sys.service.SysUserService;
import com.diandian.admin.model.shop.ShopTypeModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.admin.common.oss.AliyunStorageUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.SMSUtil;
import com.diandian.dubbo.facade.model.biz.BizBusinessInformationModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.user.UserAccountInfoService;
import com.diandian.dubbo.facade.service.user.UserShopRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: wsk
 * @create: 2019-08-28 10:46
 */
@RestController
@RequestMapping("/shopInfo")
@Slf4j
public class ShopInfoController {

    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ShopTypeService shopTypeService;
    @Value("${spring.profiles.active}")
    private String profilesActive;


    @GetMapping("getShopInfoByMerchantId/{merchantId}")
    public RespData getShopInfoByMerchantId(@PathVariable Long merchantId) {
        return RespData.ok(shopInfoService.getShopInfoByMerchantId(merchantId));
    }

    /**
     * 上传图片
     *
     * @param params
     * @return
     */
    @PostMapping("/upload")
    public RespData upload(@RequestBody Map<String, String> params) {
        return RespData.ok(AliyunStorageUtil.uploadBase64Img("diandian-business", params.get("file")));
    }

    @GetMapping("/getShopType")
    public RespData getShopType() {
        return RespData.ok(shopTypeService.list(new QueryWrapper<ShopTypeModel>().eq("del_flag", BizConstant.STATE_NORMAL)));
    }

    /**
     * @Description: 开通店铺
     * @Param: shopInfoModel
     * @return: RespData
     * @Author: wsk
     * @Date: 2019/9/2
     */
    @PostMapping("/openShop")
    public RespData openShop(@RequestBody BizBusinessInformationModel bizBusinessInformationModel) {
        ShopInfoModel shopInfoModel = shopInfoService.getShopInfoByName(bizBusinessInformationModel.getShopName());
        //SysUserModel exists = sysUserService.getByUsername(bizBusinessInformationModel.getPhone());
        if (null != shopInfoModel) {
            log.info("shopInfo ->{}", shopInfoModel.toString());
        }
        /*if (null != exists){
            log.info("exists ->{}",exists.toString());
        }*/
        if (shopInfoModel != null) {
            if (shopInfoModel.getState()==0){
                return RespData.fail("店铺正在审核中");
            }else{
                return RespData.fail("店铺已存在");
            }

        }
//        if (ObjectUtil.isNotNull(bizBusinessInformationModel.getReferralCode()) && !bizBusinessInformationModel.getReferralCode().equals("")) {
//            BizMemberModel bizMemberModel = bizMemberService.getByInvitationCode(bizBusinessInformationModel.getReferralCode());
//            if (null == bizMemberModel) {
//                return RespData.fail("推荐码不正确");
//            }
//        }
        Integer userState;
        Integer shopState;
        //如果是管理员开通,则通过审核
        if (bizBusinessInformationModel.getType() == 1) {
            //店铺正常,用户正常
            shopState = 1;
            userState = 0;
        } else {
            //店铺待审核,用户禁用
            shopState = 0;
            userState = 1;
        }
        SysUserModel sysUserModel = new SysUserModel();
        String salt = RandomStringUtils.randomAlphanumeric(19);
        SysUserModel byUsername = sysUserService.getByUsername(bizBusinessInformationModel.getPhone());
        if (null != byUsername){
            throw new DubboException("账号已存在");
        }
        sysUserModel.setUsername(bizBusinessInformationModel.getPhone());
        sysUserModel.setPassword(new Sha256Hash(bizBusinessInformationModel.getPassword(), salt).toHex());
        sysUserModel.setSalt(salt);
        sysUserModel.setType(3);
        sysUserModel.setPhone(bizBusinessInformationModel.getPhone());
        sysUserModel.setState(userState);
        sysUserService.save(sysUserModel);
        shopInfoService.openShop(bizBusinessInformationModel, sysUserModel.getId(), shopState);
        return RespData.ok();
    }

    @PostMapping("/SendSms")
    public RespData SendSms(String phone) {
        String code = redisTemplate.opsForValue().get(RedisKeyConstant.SMS_OPENSHOP_CODE + phone);
        AssertUtil.isNull(code, "验证码已发送五分钟内有效,请勿频繁发送验证码");
        code = RandomUtil.randomNumbers(6);
        while ("0".equals(code.subSequence(0, 1))) {
            code = RandomUtil.randomNumbers(6);
        }
        //开发环境发送验证码
        if (SysConstant.SYS_PROFILE.equals(profilesActive)) {
            boolean sendResult = SMSUtil.sendMsgValidateCode(code, phone);
            com.diandian.dubbo.facade.common.util.AssertUtil.isFalse(!sendResult, "验证码发送失败");
        } else {
            code = "666666";
        }
        redisTemplate.opsForValue().set(RedisKeyConstant.SMS_OPENSHOP_CODE + phone, code, RedisKeyConstant.SMS_CODE_VERIFICATION_EXPIRE, TimeUnit.SECONDS);
        return RespData.ok(code);
    }
}