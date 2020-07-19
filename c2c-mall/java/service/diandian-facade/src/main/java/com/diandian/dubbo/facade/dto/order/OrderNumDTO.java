package com.diandian.dubbo.facade.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jbuhuan
 * @date 2019/3/6 11:46
 */
@Data
public class OrderNumDTO implements Serializable {
    private Integer exchangeNum;
    private Integer orderNum;
}
