package com.diandian.dubbo.facade.dto.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/3/7 17:14
 */
@Getter
@Setter
@ToString
public class OrgApplyQueryDTO implements Serializable {

    private static final long serialVersionUID = -2994498505864596437L;

    private String treeStr;

    private Integer auditState;

    private Integer applyType;

    private String startTime;

    private String endTime;
}
