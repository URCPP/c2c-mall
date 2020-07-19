package com.diandian.dubbo.facade.dto.biz;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 审核结果DTO对象
 * @author cjunyuan
 * @date 2019/2/20 10:39
 */
@Getter
@Setter
@ToString
public class ApplyCheckResultDTO implements Serializable {

    private static final long serialVersionUID = -7248164411009810783L;

    private Long id;

    /**
     * 审核状态（0 待审核，1审核通过 ，2审核失败）
     */
    private Integer auditState;

    /**
     * 审核人ID
     */
    private Long auditUserId;

    /**
     * 审核失败原因
     */
    private String auditFailReason;
}
