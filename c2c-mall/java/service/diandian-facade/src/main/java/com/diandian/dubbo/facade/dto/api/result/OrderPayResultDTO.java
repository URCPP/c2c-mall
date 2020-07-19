package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/15 13:50
 */
@Getter
@Setter
@ToString
public class OrderPayResultDTO implements Serializable {

    private String mchOrderNo;
    private String totalAmount;
}
