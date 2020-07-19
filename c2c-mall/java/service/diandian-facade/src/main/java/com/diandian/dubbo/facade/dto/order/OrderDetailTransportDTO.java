package com.diandian.dubbo.facade.dto.order;

import com.diandian.dubbo.facade.model.order.OrderExpressInfoModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 订单快递信息
 * @author cjunyuan
 * @date 2019/6/4 15:07
 */
@Getter
@Setter
@ToString
public class OrderDetailTransportDTO implements Serializable {

    private static final long serialVersionUID = -80540431409136408L;

    /**
     * 运输单号
     */
    private String transportNo;

    /**
     * 运输公司编码
     */
    private String transportCode;

    List<OrderExpressInfoModel> expressList;
}
