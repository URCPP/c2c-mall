package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * <p>
 * 商户微信公众号信息表
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-11
 */
@Data
@TableName("merchant_weixin_mp")
public class MerchantWeixinMpModel extends BaseModel {

    private static final long serialVersionUID = 7561399759587940587L;
    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 公众号appId
     */
    @TableField("app_id")
    private String appId;

    /**
     * 公众号昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 公众号头像
     */
    @TableField("head_img")
    private String headImg;

    /**
     * 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
     */
    @TableField("service_type_info")
    private Integer serviceTypeInfo;

    /**
     * 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
     */
    @TableField("verify_type_info")
    private Integer verifyTypeInfo;

    /**
     * 授权方公众号的原始ID
     */
    @TableField("user_name")
    private String userName;

    /**
     * 公众号的主体名称
     */
    @TableField("principal_name")
    private String principalName;

    /**
     * 授权方公众号所设置的微信号，可能为空
     */
    private String alias;

    /**
     * 是否开通微信门店功能（0-否，1-是）
     */
    @TableField("open_store")
    private Integer openStore;

    /**
     * 是否开通微信扫商品功能（0-否，1-是）
     */
    @TableField("open_scan")
    private Integer openScan;

    /**
     * 是否开通微信支付功能（0-否，1-是）
     */
    @TableField("open_pay")
    private Integer openPay;

    /**
     * 是否开通微信卡券功能（0-否，1-是）
     */
    @TableField("open_card")
    private Integer openCard;

    /**
     * 是否开通微信摇一摇功能（0-否，1-是）
     */
    @TableField("open_shake")
    private Integer openShake;

    /**
     * 二维码图片的URL，开发者最好自行也进行保存
     */
    @TableField("qrcode_url")
    private String qrcodeUrl;

    /**
     * 公众号授权给开发者的权限集列表，ID为1到15时分别代表： 1.消息管理权限 2.用户管理权限 3.帐号服务权限 4.网页服务权限 5.微信小店权限 6.微信多客服权限 7.群发与通知权限 8.微信卡券权限 9.微信扫一扫权限 10.微信连WIFI权限 11.素材管理权限 12.微信摇周边权限 13.微信门店权限 14.微信支付权限 15.自定义菜单权限 请注意： 1）该字段的返回不会考虑公众号是否具备该权限集的权限（因为可能部分具备），请根据公众号的帐号类型和认证情况，来判断公众号的接口权限。
     */
    @TableField("func_info")
    private String funcInfo;

    /**
     * 用于刷新微信公众号调用凭据的token
     */
    @TableField("authorizer_refresh_token")
    private String authorizerRefreshToken;

}
