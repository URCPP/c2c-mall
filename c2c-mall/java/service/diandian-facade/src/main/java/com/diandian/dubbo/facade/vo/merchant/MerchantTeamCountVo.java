package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

@Data
public class MerchantTeamCountVo {
    /**
     * 直推人数
     */
    private Integer CountDirectTeamNumber;
    /**
     * 团队总人数
     */
    private Integer CountAllTeamNumber;
}
