package com.diandian.dubbo.facade.dto.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/3/4 11:56
 */
@Getter
@Setter
@ToString
public class MerchantInfoQueryDTO implements Serializable {
    private static final long serialVersionUID = 4975225856418713117L;

    private Long merchantId;

    private String name;

    private String treeStr;

    private Integer approveFlag;

    private Integer curPage;

    private Integer pageSize;

    private Integer openType;
}
