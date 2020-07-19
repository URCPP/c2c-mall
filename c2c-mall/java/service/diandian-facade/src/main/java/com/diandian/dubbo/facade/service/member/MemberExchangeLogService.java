package com.diandian.dubbo.facade.service.member;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.member.MemberExchangeDTO;

import java.util.Map;

/**
 * 会员卡券兑换记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface MemberExchangeLogService {

    /**
     * 会员兑换记录分页
     * @param params
     * @return
     */
    PageResult listPage(Map<String,Object> params);

    /**
     * 会员兑换
     * @param dto
     * @return
     */
    String exchange(MemberExchangeDTO dto);
}
