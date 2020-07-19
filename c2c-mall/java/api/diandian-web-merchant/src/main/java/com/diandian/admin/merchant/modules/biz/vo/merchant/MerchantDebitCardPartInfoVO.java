package com.diandian.admin.merchant.modules.biz.vo.merchant;

import lombok.Data;

/**
 * @author jbuhuan
 * @date 2019/2/15 14:08
 */
@Data
public class MerchantDebitCardPartInfoVO {
    private  Long id;
    private String bankName;
    private String cardEndNumber;
    private String cardholderName;
    private String cardholderPhone;
    private Integer defaultFlag;
}
