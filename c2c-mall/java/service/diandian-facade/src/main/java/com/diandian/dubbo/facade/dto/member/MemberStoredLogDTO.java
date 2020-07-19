package com.diandian.dubbo.facade.dto.member;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jbuhuan
 * @date 2019/2/18 19:06
 */
@Data
public class MemberStoredLogDTO implements Serializable {


    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 会员帐号
     */
    private String memberAccount;

    /**
     * 储值金额
     */
    private BigDecimal storedAmount;

    /**
     * 实际到帐金额
     */
    private BigDecimal realAmount;

    /**
     * 赠送的卡券数量
     */
    private Integer exchangeCouponNum;

    /**
     * 赠送的购物券金额
     */
    private BigDecimal shoppingCouponAmount;


}
