package com.diandian.dubbo.facade.dto.member;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jbuhuan
 * @date 2019/3/4 18:16
 */
@Data
public class MemberExchangeHistoryLogDTO implements Serializable {
    private Long giveOutTotalSum;
    private Long consumeTotalSum;
}
