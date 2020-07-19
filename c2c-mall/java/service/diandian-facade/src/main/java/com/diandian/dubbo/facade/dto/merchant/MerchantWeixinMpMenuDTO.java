package com.diandian.dubbo.facade.dto.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信公众号菜单批量添加数据传输对象DTO
 * @author cjunyuan
 * @date 2019/4/18 10:02
 */
@Getter
@Setter
@ToString
public class MerchantWeixinMpMenuDTO implements Serializable {

    private static final long serialVersionUID = 252966295661734039L;

    private Long id;

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
    private String mediaId;

    /**
     * 菜单类型为miniprogram时不为空
     */
    private String appId;

    /**
     * 菜单类型为miniprogram时不为空
     */
    private String pagePath;

    /**
     * 公众号appId
     */
    private String mpAppId;

    /**
     * 菜单禁用状态（0-正常，1-禁用）
     */
    private Integer disabledState;

    /**
     * 子菜单
     */
    private List<MerchantWeixinMpMenuDTO> children;



    public void add(MerchantWeixinMpMenuDTO child) {
        if(children == null){
            children = new ArrayList<>();
        }
        children.add(child);
    }
}
