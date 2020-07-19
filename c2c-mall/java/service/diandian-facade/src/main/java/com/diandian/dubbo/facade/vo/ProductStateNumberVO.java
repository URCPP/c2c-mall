package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 订单中商品状态的数量
 * @author cjunyuan
 * @date 2019/4/28 10:06
 */
@Getter
@Setter
@ToString
public class ProductStateNumberVO implements Serializable {

    /**
     * 总数量
     */
    private Integer total;

    /**
     * 上架数量
     */
    private Integer normalCnt;
}
