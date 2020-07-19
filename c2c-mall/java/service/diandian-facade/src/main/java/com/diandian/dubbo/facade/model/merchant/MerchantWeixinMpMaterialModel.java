package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * <p>
 * 商户微信公众号素材表
 * </p>
 *
 * @author cjunyuan
 * @since 2019-06-26
 */
@Data
@TableName("merchant_weixin_mp_material")
public class MerchantWeixinMpMaterialModel extends BaseModel {

    private static final long serialVersionUID = -7976985440344389357L;
    /**
     * 上级素材ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 素材类型（1-图片，2-视频，3-音频，4图文）
     */
    private Integer type;

    /**
     * 微信素材唯一标识
     */
    @TableField("media_id")
    private String mediaId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 微信图文标题
     */
    private String title;

    /**
     * 微信图文子图文素材ID
     */
    @TableField("thumb_media_id")
    private String thumbMediaId;

    /**
     * 是否显示封面，（0-不显示，1-显示）
     */
    @TableField("show_cover_pic")
    private Integer showCoverPic;

    /**
     * 作者
     */
    private String author;

    /**
     * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
     */
    private String digest;

    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     */
    private String content;

    /**
     * 图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL
     */
    private String url;

    /**
     * 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    @TableField("content_source_url")
    private String contentSourceUrl;

    /**
     * 公众号appId
     */
    @TableField("mp_app_id")
    private String mpAppId;
}
