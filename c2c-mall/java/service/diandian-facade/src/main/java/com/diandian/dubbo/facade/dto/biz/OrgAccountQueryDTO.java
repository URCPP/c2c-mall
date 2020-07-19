package com.diandian.dubbo.facade.dto.biz;

import com.diandian.dubbo.facade.common.constant.BizConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 账户统计查询对象
 * @author cjunyuan
 * @date 2019/3/19 10:37
 */
@Getter
@Setter
@ToString
public class OrgAccountQueryDTO implements Serializable {

    private static final long serialVersionUID = 8058727746689155097L;

    private Long orgId;

    private Integer accountType;

    private BizConstant.StatisticalUnit unit;

    private String startTime;

    private String endTime;

    private Integer tradeType;

    private Integer busType;

    private Long typeId;

    private Integer pageSize;

    private Integer curPage;
}
