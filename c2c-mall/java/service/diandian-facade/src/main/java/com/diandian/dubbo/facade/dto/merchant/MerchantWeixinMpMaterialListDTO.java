package com.diandian.dubbo.facade.dto.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商户公众号素材列表数据对象
 * @author cjunyuan
 * @date 2019/6/27 17:52
 */
@Getter
@Setter
@ToString
public class MerchantWeixinMpMaterialListDTO implements Serializable {

    private Long id;

    private Long parentId;

    private String mediaId;

    private Integer type;

    private String name;

    private String title;

    private String url;

    private Date updateTime;

    private List<MerchantWeixinMpMaterialListDTO> subList;
}
