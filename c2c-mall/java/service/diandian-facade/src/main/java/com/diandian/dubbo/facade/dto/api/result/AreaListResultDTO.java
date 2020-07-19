package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 18:29
 */
@Getter
@Setter
@ToString
public class AreaListResultDTO implements Serializable {

    private static final long serialVersionUID = -3000400078181062185L;

    private Integer regionCode;
    private Integer parentRegionCode;
    private String regionName;
    private Integer regionType;
}
