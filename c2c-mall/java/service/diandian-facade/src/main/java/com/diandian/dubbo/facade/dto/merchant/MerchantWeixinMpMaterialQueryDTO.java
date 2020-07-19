package com.diandian.dubbo.facade.dto.merchant;

import com.diandian.dubbo.facade.dto.PageQueryDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公众号素材查询类
 * @author cjunyuan
 * @date 2019/6/28 9:34
 */
@Getter
@Setter
@ToString
public class MerchantWeixinMpMaterialQueryDTO extends PageQueryDTO {

    private static final long serialVersionUID = -3013116726833585414L;

    private String appId;

    private Integer type;

    private String keyword;
}
