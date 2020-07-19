package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 统计机构类型的免费开通和付费开通数量
 * @author cjunyuan
 * @date 2019/3/7 21:37
 */
@Getter
@Setter
@ToString
public class StatisticsTypeOpenCntVO implements Serializable {

    private static final long serialVersionUID = -3087091267765819844L;

    private Long typeId;

    private Integer type;

    private Integer level;

    private String typeName;

    private Integer payCnt;

    private Integer freeCnt;
}
