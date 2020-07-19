package com.diandian.admin.merchant.modules.biz.vo.merchant;

import lombok.Data;

/**
 * @author jbuhuan
 * @date 2019/2/14 23:25
 */
@Data
public class MerchantDebitCardInfoVO {
    private String bankName;
    private String cardNumber;
    private String cardholderName;
    private String idCard;
    private String cardholderPhone;
    private Long merchantId;
    private String code;
    private Integer type;
}
