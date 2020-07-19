package com.diandian.dubbo.facade.vo.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author cjunyuan
 * @date 2019/4/22 16:40
 */
@Getter
@Setter
@ToString
public class MerchantPayfeeRecordVO implements Serializable {

    private static final long serialVersionUID = 3227723366090201008L;

    /**
     * ID
     */
    private Long id;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户编码
     */
    private String merchantCode;

    /**
     * 支付订单号
     */
    private String payNo;

    /**
     * 商户支付类型 0充值 1续费 2开通商城
     */
    private Integer payType;

    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;

    /**
     * 交易方式
     */
    private String tradeWay;

    /**
     * 交易状态(0待支付 1交易成功 2交易失败)
     */
    private Integer state;

    /**
     * 交易成功时间
     */
    private Date tradeTime;

    /**
     * 创建时间
     */
    private Date createTime;
}
