package com.diandian.dubbo.facade.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 订单查询条件对象
 * @author cjunyuan
 * @date 2019/4/15 18:21
 */
@Getter
@Setter
@ToString
public class OrderInfoQueryDTO implements Serializable {

    private static final long serialVersionUID = -2347551454494538947L;
    private String startTime;
    private String endTime;
}
