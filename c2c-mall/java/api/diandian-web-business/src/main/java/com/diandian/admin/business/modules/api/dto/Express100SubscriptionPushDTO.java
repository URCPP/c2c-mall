package com.diandian.admin.business.modules.api.dto;

import com.diandian.dubbo.facade.dto.order.OrderExpressInfoDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/5/15 17:23
 */
@Getter
@Setter
@ToString
public class Express100SubscriptionPushDTO implements Serializable {

    private static final long serialVersionUID = 5948755641792121071L;

    private String status;
    private String billStatus;
    private String message;
    private String autoCheck;
    private String comOld;
    private String comNew;
    private ExpressQueryResult lastResult;
    private ExpressQueryResult destResult;

    @Getter
    @Setter
    @ToString
    public static class ExpressQueryResult implements Serializable{

        private static final long serialVersionUID = 260914759497842783L;

        private String message;
        private String state;
        private String status;
        private String condition;
        private String isCheck;
        private String com;
        private String nu;
        private List<OrderExpressInfoDTO> data;
    }
}
