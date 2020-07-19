package com.diandian.admin.merchant.modules.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.model.biz.BizConfigModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.biz.BizConfigService;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 *
 * @author x
 * @date 2018/11/08
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Autowired
    private BizConfigService bizConfigService;
    @Autowired
    private UserConfigurationService userConfigurationService;

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public RespData info() {
        MerchantInfoModel merchantInfo = getMerchantInfo();
        MerchantInfoVO merVO = new MerchantInfoVO();
        BeanUtil.copyProperties(merchantInfo, merVO);
        merVO.setIsExpireFlag(MerchantConstant.IsExpireFlag.NORMAL.value());
        //前段要求在登陆信息中加入冻结金额
        UserConfiguration userConfiguration=userConfigurationService.findAll();
        merVO.setFreezeCommission(userConfiguration.getFreezeCommission());
        /*Date merchantExpireTime = merchantInfo.getMerchantExpireTime();
        if (null != merchantExpireTime) {
            Date date = new Date();
            if (date.getTime() > merchantExpireTime.getTime()) {
                merVO.setIsExpireFlag(MerchantConstant.IsExpireFlag.EXPIRE.value());
            } else {
                merVO.setIsExpireFlag(MerchantConstant.IsExpireFlag.NORMAL.value());
            }
        }*/
//        BizMemberModel oneByPhone = bizMemberService.getOneByPhone(getMerchantInfo().getPhone());
//        if (!oneByPhone.getCurrentIdentityId().equals(1149565059472769025L)&&
//            !oneByPhone.getCurrentIdentityId().equals(1149565059409854465L)){
//            merVO.setInvitationCode(oneByPhone.getInvitationCode());
//        }

        Map<String,Object> map = new HashMap<>(1);
        map.put("MerVO",merVO);
       // map.put("agentUserInfo",bizMemberService.getShowInfoByPhone(oneByPhone.getPhone()));
        return RespData.ok(map);
    }

    /**
     * @return com.diandian.admin.common.util.RespData
     * @Author wubc
     * @Description // 总配置信息
     * @Date 10:54 2019/3/14
     * @Param []
     **/
    @GetMapping("/getConfigInfo")
    public RespData getConfigInfo() {
        BizConfigModel config = bizConfigService.getOne();
        return RespData.ok(config);
    }

}
