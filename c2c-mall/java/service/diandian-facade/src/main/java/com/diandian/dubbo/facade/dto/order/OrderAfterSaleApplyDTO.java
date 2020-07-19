package com.diandian.dubbo.facade.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jbuhuan
 * @date 2019/3/5 15:02
 */
@Data
public class OrderAfterSaleApplyDTO  implements Serializable {
    private String orderNo;
    private String imageUrls;
    private Integer afterSaleType;
    private Integer state;
    private Long id;
    private Long skuId;
    private String skuName;
    private Long repositoryId;
    private String repositoryName;
    private BigDecimal price;
    private Integer num;
    private BigDecimal serviceFee;
    private String recvName;
    private String recvPhone;
    private String recvAddress;
    private BigDecimal transportFee;
    private String transportFeeIntroduce;
    private String transportName;
    private Long transportCompanyId;
    private Integer afterSaleFlag;
}
