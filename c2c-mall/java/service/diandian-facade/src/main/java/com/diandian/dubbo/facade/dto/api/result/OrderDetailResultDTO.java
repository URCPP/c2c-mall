package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/5/15 15:22
 */
@Getter
@Setter
@ToString
public class OrderDetailResultDTO implements Serializable {

    private static final long serialVersionUID = 7226205598602072504L;

    private String orderNo;
    private String mchOrderNo;
    private String createTime;
    private List<ApiOrderProductDetailResult> orderProducts;

    @Getter
    @Setter
    @ToString
    static class ApiOrderProductDetailResult implements Serializable{
        private Long productId;
        private Integer orderStatus;
        private String transportCompany;
        private String transportName;
        private String transportNo;
        private String deliveryTime;
    }
}
