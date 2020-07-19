package com.diandian.admin.merchant.modules.biz.vo.merchant;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jbuhuan
 * @date 2019/2/26 9:56
 */
@Data
public class MerchantAccountInfoVO {
    private BigDecimal availableBalance;
    private String cardEndNumber;
    private String bankName;
}
