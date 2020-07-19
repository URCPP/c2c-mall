package com.diandian.dubbo.business.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.member.MemberExchangeHistoryLogDTO;
import com.diandian.dubbo.facade.model.member.MemberExchangeHistoryLogModel;

import java.util.List;
import java.util.Map;


/**
 * 商户会员兑换券变动台账表
 *
 * @author wbc
 * @date 2019/03/04
 */
public interface MemberExchangeHistoryLogMapper extends BaseMapper<MemberExchangeHistoryLogModel> {

    /**
     * 兑换券总数量
     *
     * @param params
     * @return
     */
    MemberExchangeHistoryLogDTO getExchangeHistoryNum(Map<String, Object> params);

    /**
     * 活跃会员数
     *
     * @param params
     * @return
     */
    List<MemberExchangeHistoryLogModel> listMemberInfo(Map<String, Object> params);
}