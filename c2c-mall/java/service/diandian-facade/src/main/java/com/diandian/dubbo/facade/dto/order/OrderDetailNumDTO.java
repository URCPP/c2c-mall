package com.diandian.dubbo.facade.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jbuhuan
 * @date 2019/3/6 11:46
 */
@Data
public class OrderDetailNumDTO implements Serializable {
    private Integer unSendOutNum;
    private Integer unReceivedNum;
}
