package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/5/14 17:00
 */
@Getter
@Setter
@ToString
public class UnifiedOrderResultDTO implements Serializable {

    private static final long serialVersionUID = 6340188741776233352L;

    private String orderNo;
    private String totalAmount;
    private List<ApiOrderProductResult> orderProducts;

    public void addOrderProduct(ApiOrderProductResult item){
        if(null == orderProducts){
            orderProducts = new ArrayList<>();
        }
        orderProducts.add(item);
    }


    @Getter
    @Setter
    @ToString
    public class ApiOrderProductResult implements Serializable{
        private Long productId;
        private Integer num;
        private String price;
        private String serviceFee;
        private String freightFee;
    }
}
