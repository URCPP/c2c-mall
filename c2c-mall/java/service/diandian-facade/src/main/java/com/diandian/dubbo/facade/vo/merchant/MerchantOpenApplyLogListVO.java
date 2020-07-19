package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cjunyuan
 * @date 2019/2/21 17:17
 */
@Data
public class MerchantOpenApplyLogListVO implements Serializable {

    private Long id;

    private String billNo;

    private String merchantName;

    private String softTypeName;

    private Integer applyStateFlag;

    private String leader;

    private String phone;

    private String loginName;

    private Integer typeFlag;

    private Integer applyType;
    /**
     * 创建时间
     */
    private Date createTime;

    private String auditFailReason;

    private String parentName;

    private String parentPhone;

    private String parentContactName;

    private String parentTypeName;
}
