package com.diandian.dubbo.facade.vo.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/6/10 14:09
 */
@Getter
@Setter
@ToString
public class OrderDetailExpressInfoVO implements Serializable {

    private static final long serialVersionUID = 122877728701419575L;

    private String transportNo;

    private List<ExpressInfo> list;

    @Getter
    @Setter
    @ToString
    private static class ExpressInfo implements Serializable{
        private static final long serialVersionUID = 6807927156666358800L;
        private String time;
        private String context;
    }
}
