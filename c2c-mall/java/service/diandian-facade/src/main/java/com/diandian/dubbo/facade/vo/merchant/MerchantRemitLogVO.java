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
 * @date 2019/4/22 21:11
 */
@Getter
@Setter
@ToString
public class MerchantRemitLogVO implements Serializable {

    private static final long serialVersionUID = -6165434161565464564L;

    private Long id;
    private Long merchantId;
    private String merchantName;
    private String merchantCode;
    /**
     * 汇款金额
     */
    private BigDecimal amount;

    /**
     * 汇款凭证地址
     */
    private String proofUrl;

    /**
     * 审核状态(0 待审核；  1 审核通过; 2审核失败)
     */
    private Integer auditFlag;

    private String auditFailReason;

    private String auditor;

    private Date createTime;

    private Date auditTime;
}
