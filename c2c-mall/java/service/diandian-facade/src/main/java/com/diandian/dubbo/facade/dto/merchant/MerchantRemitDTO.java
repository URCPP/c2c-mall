package com.diandian.dubbo.facade.dto.merchant;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MerchantRemitDTO implements Serializable {

    /**
     * 商户ID
     */
    private Long merchantId;
    private String merchantName;


    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 凭证地址
     */
    private String proofUrl;


    private String orderNo;



}
