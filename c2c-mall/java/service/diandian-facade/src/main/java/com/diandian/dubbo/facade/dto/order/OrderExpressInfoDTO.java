package com.diandian.dubbo.facade.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/15 18:36
 */
@Getter
@Setter
@ToString
public class OrderExpressInfoDTO implements Serializable {

    private static final long serialVersionUID = -93538697366721927L;

    private String context;
    private String time;
    private String ftime;
    private String status;
    private String areaCode;
    private String areaName;
}
