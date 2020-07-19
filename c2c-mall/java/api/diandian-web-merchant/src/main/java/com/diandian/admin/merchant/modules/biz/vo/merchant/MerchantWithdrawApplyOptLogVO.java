package com.diandian.admin.merchant.modules.biz.vo.merchant;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jbuhuan
 * @date 2019/2/26 13:36
 */
@Data
public class MerchantWithdrawApplyOptLogVO {
    private String bankName;
    private BigDecimal withdrawFee;
    private String withdrawPassword;
}
