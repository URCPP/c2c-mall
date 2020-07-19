package com.diandian.dubbo.facade.dto.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/2/21 18:59
 */
@Getter
@Setter
@ToString
public class OrgPrivilegeQueryDTO implements Serializable {

    private static final long serialVersionUID = -5077392737297496360L;

    private Long orgId;

    private Integer rewardTypeId;

    private Long rewardOrgTypeId;

    private Long rewardSoftwareTypeId;

    private Integer level;

}
