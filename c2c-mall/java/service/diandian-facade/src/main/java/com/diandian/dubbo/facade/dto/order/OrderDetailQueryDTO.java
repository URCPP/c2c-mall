package com.diandian.dubbo.facade.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 订单明细查询对象
 * @author cjunyuan
 * @date 2019/6/5 15:51
 */
@Getter
@Setter
@ToString
public class OrderDetailQueryDTO implements Serializable {

    private Integer state;

    private Integer shareFlag;

    private Integer afterSaleFlag;

    private String startTime;

    private String endTime;
}
