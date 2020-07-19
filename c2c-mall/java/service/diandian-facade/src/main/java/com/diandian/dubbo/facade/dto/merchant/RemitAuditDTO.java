package com.diandian.dubbo.facade.dto.merchant;

import lombok.Data;

import java.io.Serializable;

@Data
public class RemitAuditDTO implements Serializable {
    /**
     * 商户商品ID
     */
    private Long id;

    private Integer auditFlag;

    private String auditFailReason;

    private Long merchantId;

    private String auditor;


}
