package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信公众号菜单表
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-18
 */
@Data
@TableName("merchant_weixin_mp_menu")
public class MerchantWeixinMpMenuModel extends BaseModel {

    private static final long serialVersionUID = 8912093285412568997L;
    /**
     * 菜单上级（0-顶级）
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单类型（view-网页类型，click-点击类型，miniprogram-小程序类型，media_id-响应媒体文件，view_limited-响应图文链接）
     */
    private String type;

    /**
     * 菜单类型为click时不为空，且唯一
     */
    private String key;

    /**
     * 菜单类型为view时不为空
     */
    private String url;

    /**
     * 菜单类型为media_id或view_limited时不能为空
     */
    @TableField("media_id")
    private String mediaId;

    /**
     * 菜单类型为miniprogram时不为空
     */
    @TableField("app_id")
    private String appId;

    /**
     * 菜单类型为miniprogram时不为空
     */
    @TableField("page_path")
    private String pagePath;

    /**
     * 公众号appId
     */
    @TableField("mp_app_id")
    private String mpAppId;

    /**
     * 菜单禁用状态（0-正常，1-禁用）
     */
    @TableField("disabled_state")
    private Integer disabledState;

}
