package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 物流数据对象
 * @author cjunyuan
 * @date 2019/5/16 9:07
 */
@Getter
@Setter
@ToString
public class ExpressInfoListResultDTO implements Serializable {

    private static final long serialVersionUID = 6895571035084480084L;
    private String orderNo;
    private Integer orderStatus;
    private String transportCompany;
    private String transportName;
    private String transportNo;
    private List<ExpressInfo> list;

    @Getter
    @Setter
    @ToString
    public static class ExpressInfo implements Serializable{

        private static final long serialVersionUID = -8296863821886813417L;

        private Integer state;
        private String context;
        private String time;
    }
}
