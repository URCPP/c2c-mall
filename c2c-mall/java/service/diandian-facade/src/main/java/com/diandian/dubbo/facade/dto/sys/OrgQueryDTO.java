package com.diandian.dubbo.facade.dto.sys;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/2/22 15:39
 */
@Getter
@Setter
@ToString
public class OrgQueryDTO implements Serializable {

    private static final long serialVersionUID = 2155801395771349569L;

    private Long id;

    private Long parentId;

    private String orgName;

    private Long applyId;

    private Integer state;

    private String treeStr;

    private Integer openType;
}
