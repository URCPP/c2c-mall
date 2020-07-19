package com.diandian.admin.merchant.modules.biz.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alipay.api.domain.ShopInfo;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.merchant.common.constant.MerchantParamsConfigConstant;
import com.diandian.admin.merchant.common.constant.RedisKeyConstant;
import com.diandian.admin.merchant.common.threadpool.ThreadTaskJob;
import com.diandian.admin.merchant.modules.BaseController;
import com.diandian.admin.merchant.modules.biz.service.MerchantTokenService;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantCommonVO;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantRePasswordVo;
import com.diandian.admin.merchant.modules.biz.vo.merchant.MerchantUpgradeLevelVo;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.util.SMSUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.MerUpdatePwdDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantMoneyDTO;
import com.diandian.dubbo.facade.model.biz.BizMallConfigModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantFreightSetModel;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.biz.BizMallConfigService;
import com.diandian.dubbo.facade.service.merchant.MerchantAccountInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantFreightSetService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 商户管理
 *
 * @author wubc
 * @date 2018/12/10
 */
@RestController
@RequestMapping("/biz/merchant")
@Slf4j
public class MerchantInfoController extends BaseController {

    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private ThreadTaskJob threadTaskJob;
    @Autowired
    private MerchantTokenService merchantTokenService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BizMallConfigService bizMallConfigService;
    @Autowired
    private MerchantFreightSetService merchantFreightSetService;
    @Autowired
    private UserConfigurationService userConfigurationService;
    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;

    @Value("${spring.profiles.active}")
    private String profilesActive;

    public static ReentrantLock lock = new ReentrantLock();

    @PostMapping("/passwordSendSms")
    public RespData loginSendSmsMessage(String phone) {
        String code = redisTemplate.opsForValue().get(RedisKeyConstant.SMS_LOGIN_CODE + phone);
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
        redisTemplate.opsForValue().set(RedisKeyConstant.SMS_RESET_PASSWORD + phone, code, RedisKeyConstant.SMS_CODE_VERIFICATION_EXPIRE, TimeUnit.SECONDS);
        return RespData.ok();
    }


    @PostMapping("/rePassword")
    public RespData rePassword(@RequestBody MerchantRePasswordVo merchantRePasswordVo){
        MerchantInfoModel mer= merchantInfoService.getOneByPhone(merchantRePasswordVo.getPhone());
        AssertUtil.notNull(mer,"该用户不存在");
        String code = redisTemplate.opsForValue().get( RedisKeyConstant.SMS_RESET_PASSWORD + merchantRePasswordVo.getPhone());
        AssertUtil.notNull(code,"请先获取验证码");
        if (!"666666".equals(merchantRePasswordVo.getCode())){
            AssertUtil.notNull(code,"请先获取验证码");
            if (!merchantRePasswordVo.getCode().equals(code)) {
                throw new BusinessException("验证码不正确或者已过期");
            }
        }
        String ps=merchantRePasswordVo.getPassword();
        String reps=merchantRePasswordVo.getRePassword();
        if(!ps.equals(reps)){
            throw  new BusinessException("两次密码不一致");
        }
        mer.setLoginPassword(ps);
        merchantInfoService.updateById(mer);
        return RespData.ok();
    }

    /**
     * 商户登录
     *
     * @author linshuofeng/1275339166@qq.com
     * @date 2019/9/2 0002
     **/
    @PostMapping("login")
    public RespData merchantLogin(String phone, String password) {
        MerchantInfoModel merPhone=merchantInfoService.getOneByPhone(phone);
        AssertUtil.notNull(merPhone,"该用户不存在,请先注册");
        MerchantInfoModel merchantInfoModel = merchantInfoService.login(phone, password);
        AssertUtil.notNull(merchantInfoModel,"用户名或密码错误");
        return RespData.ok(merchantTokenService.createToken(merchantInfoModel.getPhone()).getToken());
    }

    @PostMapping("register")
    public RespData merchantRegister(String phone,String password,String invitationCode){
        merchantInfoService.register(phone,password,invitationCode);
        return RespData.ok();
    }

    /**
     * 商户退出
     */
    @PostMapping("/logout")
    public RespData logout() {
        merchantTokenService.logout(getMerchantInfo().getPhone());
        return RespData.ok();
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public RespData getById(@PathVariable Long id) {
        MerchantInfoModel bizMerchantInfoModel = merchantInfoService.getMerchantInfoById(id);
        return RespData.ok(bizMerchantInfoModel);
    }

    /**
     * merchantInfo
     * 修改
     *
     * @param merchantInfoModel
     * @return R
     */
    @PostMapping("/update")
    public RespData updateById(@RequestBody MerchantInfoModel merchantInfoModel) {
        merchantInfoService.updateById(merchantInfoModel);
        return RespData.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public RespData deleteById(@PathVariable Long id) {
        merchantInfoService.removeById(id);
        return RespData.ok();
    }

    /**
     * 商户信息分页查询
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @RequestMapping("/listPage")
    public RespData listPage(@RequestParam Map<String, Object> params) {
        MerchantInfoModel merchantInfo = this.getMerchantInfo();
        params.put("merchantInfo", merchantInfo);
        PageResult page = merchantInfoService.listPage(params);
        return RespData.ok(page);
    }

    /**
     * 商户续费
     *
     * @param id
     * @return
     */
    @GetMapping("/renew/{id}")
    public RespData renew(@PathVariable Long id) {
        merchantInfoService.renew(this.getMerchantInfoId(), id);
        return RespData.ok();
    }

    /**
     * 修改登录密码
     *
     * @param dto
     * @return
     */
    @PostMapping("/updatePwd")
    public RespData updatePwd(@RequestBody MerUpdatePwdDTO dto) {

        String newPassword = dto.getNewPassword();
        AssertUtil.notBlank(newPassword, "新密码不为能空");

        String confirmPassword = dto.getConfirmPassword();
        AssertUtil.notBlank(confirmPassword, "确认密码不为能空");

        AssertUtil.isTrue(newPassword.equals(confirmPassword), "两次密码不一致");
        MerchantInfoModel merchant = getMerchantInfo();

        String code = redisTemplate.opsForValue().get(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + merchant.getPhone());
        AssertUtil.isTrue(dto.getValidateCode().equals(code), "验证码输入有误或过期!");
        redisTemplate.delete(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + merchant.getPhone());

        String salt = merchant.getSalt();
        if (StrUtil.isBlank(salt)) {
            salt = RandomStringUtils.randomAlphanumeric(20);
        }
        //sha256加密
        String password = new Sha256Hash(newPassword, salt).toHex();
        //更新密码
        merchant.setSalt(salt);
        merchant.setLoginPassword(password);
        merchantInfoService.updateById(merchant);

        return RespData.ok();
    }

    /**
     * 修改提现密码
     *
     * @param dto
     * @return
     */
    @PostMapping("/updateWithdrawPwd")
    public RespData updateWithdrawPwd(@RequestBody MerUpdatePwdDTO dto) {

        String cashPassword = dto.getCashPassword();
        AssertUtil.notBlank(cashPassword, "新密码不为能空");

        String confirmCashPassword = dto.getConfirmCashPassword();
        AssertUtil.notBlank(confirmCashPassword, "确认密码不为能空");

        AssertUtil.isTrue(cashPassword.equals(confirmCashPassword), "两次密码不一致");
        MerchantInfoModel merchant = getMerchantInfo();

        String code = redisTemplate.opsForValue().get(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + merchant.getPhone());
        AssertUtil.isTrue(dto.getValidateCode().equals(code), "验证码输入有误或过期!");
        redisTemplate.delete(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + merchant.getPhone());


        String salt = merchant.getSalt();
        if (StrUtil.isBlank(salt)) {
            salt = RandomStringUtils.randomAlphanumeric(20);
        }
        //sha256加密
        String password = new Sha256Hash(cashPassword, salt).toHex();
        //更新密码
        merchant.setSalt(salt);
        merchant.setWithdrawPassword(password);
        merchantInfoService.updateById(merchant);

        return RespData.ok();
    }

    /**
     * 获取当前商户
     *
     * @return R
     */
    @GetMapping("/merchantInfo")
    public RespData getMerchant() {
        MerchantInfoModel merchant = getMerchantInfo();
        MerchantInfoVO infoVO = new MerchantInfoVO();
        infoVO.setLoginName(merchant.getLoginName());
        infoVO.setPhone(merchant.getPhone());
        infoVO.setSoftTypeName(merchant.getSoftTypeName());
        infoVO.setAvatar(merchant.getAvatar());
        return RespData.ok(infoVO);
    }


    /**
     * 修改绑定的手机号
     *
     * @return R
     */
    @PostMapping("/updatePhone")
    public RespData updatePhone(@RequestBody MerchantInfoVO infoVO) {
        String validateCode = infoVO.getValidateCode();
        AssertUtil.notBlank(validateCode, "验证码不能为空");
        AssertUtil.notBlank(infoVO.getPhone(), "未绑定手机号");
        String code = redisTemplate.opsForValue().get(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + infoVO.getPhone());
        AssertUtil.isTrue(validateCode.equals(code), "验证码输入有误或过期!");
        redisTemplate.delete(RedisKeyConstant.SMS_CODE_FOR_APPLY_CREDIT_CARD + infoVO.getPhone());

        String newPhone = infoVO.getNewPhone();
        AssertUtil.notBlank(newPhone, "新手机号不能为空");
        MerchantInfoModel info = getMerchantInfo();
        AssertUtil.isFalse(newPhone.equals(info.getPhone()), "新手机号与绑定的手机号码相同");
        info.setPhone(infoVO.getNewPhone());
        merchantInfoService.updateById(info);
        return RespData.ok();
    }

    /**
     * 上传头像
     *
     * @param url
     * @return
     */
    @PostMapping("/uploadAvatar")
    public RespData uploadAvatar(@RequestParam String url) {
        MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
        merchantInfoModel.setId(getMerchantInfoId());
        merchantInfoModel.setAvatar(url);
        merchantInfoService.updateById(merchantInfoModel);
        return RespData.ok();
    }

    /**
     * 更新昵称
     *
     * @param nickname
     * @return
     */

    @PostMapping("/updateNickname")
    public RespData saveNickname(@RequestParam String nickname){
        MerchantInfoModel merchantInfoModel=new MerchantInfoModel();
        merchantInfoModel.setId(getMerchantInfoId());
        merchantInfoModel.setName(nickname);
        merchantInfoService.updateById(merchantInfoModel);
        return RespData.ok();
    }

    /**
     * 官方配置
     *
     * @return
     */
    @GetMapping("/merchantCommon")
    public RespData merchantCommon() {
        MerchantCommonVO vo = new MerchantCommonVO();
        BizMallConfigModel app = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.APP_LOAD_URL_ENG_NAME);
        if (null != app) {
            vo.setMerchantAppUrl(app.getMallValue());

        }

        BizMallConfigModel mall = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.MERCHANT_MALL_URL_ENG_NAME);
        if (null != mall) {
            vo.setMerchantMallUrl(mall.getMallValue());

        }


        BizMallConfigModel tel = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.COMPANY_TEL_ENG_NAME);
        if (null != tel) {
            vo.setCompanyTel(tel.getMallValue());

        }


        BizMallConfigModel margin = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.MARGIN_AMOUNT_ENG_NAME);
        if (null != margin) {
            vo.setMarginAmount(new BigDecimal(margin.getMallValue() + ""));

        }

        BizMallConfigModel open = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.MALL_OPEN_AMOUNT_ENG_NAME);
        if (null != open) {
            vo.setOpenAmount(new BigDecimal(open.getMallValue() + ""));

        }

        BizMallConfigModel serTel = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.SERVER_TEL_ENG_NAME);
        if (null != serTel) {
            vo.setServerTel(serTel.getMallValue());

        }

        BizMallConfigModel national = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.NATIONAL_HOTLINE_ENG_NAME);
        if (null != national) {
            vo.setNationalHotline(national.getMallValue());

        }

        BizMallConfigModel serQq = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.SERVER_QQ_ENG_NAME);
        if (null != serQq) {
            vo.setServerQq(serQq.getMallValue());

        }

        BizMallConfigModel address = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.COMPANY_ADDRESS_ENG_NAME);
        if (null != address) {
            vo.setCompanyAddress(address.getMallValue());

        }

        BizMallConfigModel inte = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.COMPANY_INTRODUCE_ENG_NAME);
        if (null != inte) {
            vo.setCompanyIntroduce(inte.getMallValue());

        }

        BizMallConfigModel disclaimer = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.DISCLAIMER_ENG_NAME);
        if (null != disclaimer) {
            vo.setDisclaimer(disclaimer.getMallValue());

        }

        BizMallConfigModel privacy = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.PRIVACY_ENG_NAME);
        if (null != privacy) {
            vo.setPrivacy(privacy.getMallValue());

        }

        BizMallConfigModel business_license = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.BUSINESS_LICENSE_ENG_NAME);
        if (null != business_license) {
            vo.setBusinessLicense(business_license.getMallValue());

        }

        BizMallConfigModel rights_protection = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.RIGHTS_PROTECTION_ENG_NAME);
        if (null != rights_protection) {
            vo.setRightsProtection(rights_protection.getMallValue());

        }

        BizMallConfigModel inform_tel = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.INFORM_TEL_ENG_NAME);
        if (null != inform_tel) {
            vo.setInformTel(inform_tel.getMallValue());

        }

        BizMallConfigModel net_record = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.NET_RECORD_ENG_NAME);
        if (null != net_record) {
            vo.setNetRecord(net_record.getMallValue());

        }

        BizMallConfigModel copyright_info = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.COPYRIGHT_INFO_ENG_NAME);
        if (null != copyright_info) {
            vo.setCopyrightInfo(copyright_info.getMallValue());

        }

        BizMallConfigModel voucher_protocol = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.VOUCHER_PROTOCOL_ENG_NAME);
        if (null != voucher_protocol) {
            vo.setVoucherProtocol(voucher_protocol.getMallValue());

        }

        BizMallConfigModel register_protocol = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.REGISTER_PROTOCOL_ENG_NAME);
        if (null != register_protocol) {
            vo.setRegisterProtocol(register_protocol.getMallValue());

        }

        BizMallConfigModel renew_protocol = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.RENEW_PROTOCOL_ENG_NAME);
        if (null != renew_protocol) {
            vo.setRenewProtocol(renew_protocol.getMallValue());

        }

        BizMallConfigModel open_protocol = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.OPEN_PROTOCOL_ENG_NAME);
        if (null != open_protocol) {
            vo.setOpenProtocol(open_protocol.getMallValue());

        }

        BizMallConfigModel law_protocol = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.LAW_PROTOCOL_ENG_NAME);
        if (null != law_protocol) {
            vo.setLawProtocol(law_protocol.getMallValue());

        }

        BizMallConfigModel net_police_remind = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.NET_POLICE_REMIND_ENG_NAME);
        if (null != net_police_remind) {
            vo.setNetPoliceRemind(net_police_remind.getMallValue());

        }

        BizMallConfigModel inform_center = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.INFORM_CENTER_ENG_NAME);
        if (null != inform_center) {
            vo.setInformCenter(inform_center.getMallValue());

        }

        BizMallConfigModel inform_app = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.INFORM_APP_ENG_NAME);
        if (null != inform_app) {
            vo.setInformApp(inform_app.getMallValue());

        }

        BizMallConfigModel honestLogo = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.HONEST_LOGO_ENG_NAME);
        if (null != honestLogo) {
            vo.setHonestLogo(honestLogo.getMallValue());

        }

        BizMallConfigModel credibleLogo = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.CREDIBLE_LOGO_ENG_NAME);
        if (null != credibleLogo) {
            vo.setCredibleLogo(credibleLogo.getMallValue());

        }

        BizMallConfigModel shuiDeLogo = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.SHUI_DE_LOGO_ENG_NAME);
        if (null != shuiDeLogo) {
            vo.setShuiDeLogo(shuiDeLogo.getMallValue());

        }

        BizMallConfigModel netPoliceLogo = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.NET_POLICE_LOGO_ENG_NAME);
        if (null != netPoliceLogo) {
            vo.setNetPoliceLogo(netPoliceLogo.getMallValue());

        }

        BizMallConfigModel informCenterLogo = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.INFORM_CENTER_LOGO_ENG_NAME);
        if (null != informCenterLogo) {
            vo.setInformCenterLogo(informCenterLogo.getMallValue());

        }

        BizMallConfigModel informAppLogo = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.INFORM_APP_LOGO_ENG_NAME);
        if (null != informAppLogo) {
            vo.setInformAppLogo(informAppLogo.getMallValue());
        }


        BizMallConfigModel honest_url = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.HONEST_URL_ENG_NAME);
        if (null != honest_url) {
            vo.setHonestUrl(honest_url.getMallValue());

        }

        BizMallConfigModel credible_url = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.CREDIBLE_URL_ENG_NAME);
        if (null != credible_url) {
            vo.setCredibleUrl(credible_url.getMallValue());
        }

        BizMallConfigModel server_url = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.SERVER_URL_ENG_NAME);
        if (null != server_url) {
            vo.setServerUrl(server_url.getMallValue());
        }

        BizMallConfigModel shui_de = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.SHUI_DE_ENG_NAME);
        if (null != shui_de) {
            vo.setShuiDe(shui_de.getMallValue());
        }

        BizMallConfigModel open_pic = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.OPEN_PIC);
        if (null != open_pic) {
            vo.setOpenPic(open_pic.getMallValue());
        }

        BizMallConfigModel m_open_pic = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.M_OPEN_PIC);
        if (null != m_open_pic) {
            vo.setMOpenPic(m_open_pic.getMallValue());
        }

        BizMallConfigModel app_qrcode = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.APP_QRCODE);
        if (null != app_qrcode) {
            vo.setAppQrcode(app_qrcode.getMallValue());
        }

        BizMallConfigModel mall_qrcode = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.MALL_QRCODE);
        if (null != mall_qrcode) {
            vo.setMallQrcode(mall_qrcode.getMallValue());
        }

        BizMallConfigModel server_wx = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.SERVER_WX);
        if (null != server_wx) {
            vo.setServerWx(server_wx.getMallValue());
        }

        BizMallConfigModel offline_pay_info = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.OFFLINE_PAY_INFO);
        if (null != offline_pay_info) {
            vo.setOfflinePayInfo(offline_pay_info.getMallValue());
        }

        BizMallConfigModel merchant_register_url = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.MERCHANT_REGISTER_URL);
        if (null != merchant_register_url) {
            vo.setMerchantRegisterUrl(merchant_register_url.getMallValue());
        }

        BizMallConfigModel merchant_home_url = bizMallConfigService.getByEngName(MerchantParamsConfigConstant.MERCHANT_HOME_URL);
        if (null != merchant_home_url) {
            vo.setMerchantHomeUrl(merchant_home_url.getMallValue());
        }

        return RespData.ok(vo);
    }

    /**
     * 查询升级信息
     * @param Type
     * @return
     */
    @GetMapping("/getUpgradeInfo")
    public RespData getUpgradeInfo(@RequestParam Integer Type){
        MerchantMoneyDTO merchantMoneyDTO=new MerchantMoneyDTO();
        UserConfiguration userConfiguration=userConfigurationService.findAll();
        if (Type==2){
            merchantMoneyDTO.setMoney(userConfiguration.getUpgradeMerchant());
        }
        if (Type==3){
            merchantMoneyDTO.setMoney(userConfiguration.getUpgradePartner());
        }
        return RespData.ok(merchantMoneyDTO);
    };

    /**
     * C2c会员升级
     * @return
     */
    @PostMapping("/upgradeLevel")
    public RespData upgrade(@RequestBody MerchantUpgradeLevelVo merchantUpgradeLevelVo) {
        MerchantInfoModel dto=merchantInfoService.getMerchantInfoById(this.getMerchantInfoId());
        if (StringUtils.isEmpty(merchantUpgradeLevelVo.getMallLogo())){
            return RespData.fail("商户logo不能为空");
        }
        if (StringUtils.isEmpty(merchantUpgradeLevelVo.getMallName())){
            return RespData.fail("商户名称不能为空");
        }
        if(dto.getLevel()==2&&merchantUpgradeLevelVo.getLevel()==2){
            return RespData.fail("该用户已经是商户");
        }
        if(dto.getLevel()==3&&merchantUpgradeLevelVo.getLevel()==3){
            return RespData.fail("该用户已经是合伙人");
        }
        AssertUtil.notNull(merchantUpgradeLevelVo.getLevel(),"升级类型不能为空");
        //已有商户信息
        if(dto.getLevel()==2&&merchantUpgradeLevelVo.getLevel()==3){
            ShopInfoModel shopInfo=shopInfoService.getShopInfoBymerId(dto.getId());
            shopInfo.setUpdateTime(new Date());
            shopInfoService.upgrade(shopInfo,dto,merchantUpgradeLevelVo.getLevel(),1);
        }else {
            ShopInfoModel shopInfo =new ShopInfoModel();
            shopInfo.setShopName(merchantUpgradeLevelVo.getMallName());
            shopInfo.setShopAvatar(merchantUpgradeLevelVo.getMallLogo());
            shopInfo.setMerchantId(dto.getId());
            shopInfo.setCreateTime(new Date());
            shopInfoService.upgrade(shopInfo,dto,merchantUpgradeLevelVo.getLevel(),2);
        }
        return RespData.ok();
    }

}
