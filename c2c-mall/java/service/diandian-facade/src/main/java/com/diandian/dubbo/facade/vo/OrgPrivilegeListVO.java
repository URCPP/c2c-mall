package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author cjunyuan
 * @date 2019/3/6 14:48
 */
@Getter
@Setter
@ToString
public class OrgPrivilegeListVO implements Serializable {

    private static final long serialVersionUID = 8979177563183473330L;

    private Integer rewardTypeId;
    private String rewardTypeName;
    private Long rewardOrgTypeId;
    private Integer rewardOrgLevel;
    private String rewardOrgTypeName;
    private Long rewardSoftwareTypeId;
    private String rewardSoftwareTypeName;
    private BigDecimal rewardValue;
}
