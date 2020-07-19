package com.diandian.dubbo.facade.service.member;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.member.MemberExchangeHistoryLogDTO;
import com.diandian.dubbo.facade.model.member.MemberExchangeHistoryLogModel;

import java.util.Map;

import java.util.List;

/**
 * 商户会员兑换券变动台账表
 *
 * @author wbc
 * @date 2019/03/04
 */
public interface MemberExchangeHistoryLogService {

    /**
     * 保存
     *
     * @param exchangeHistoryLogModel
     * @return
     */
    boolean save(MemberExchangeHistoryLogModel exchangeHistoryLogModel);

    /**
     * 兑换券总数量
     * @param params
     * @return
     */
    MemberExchangeHistoryLogDTO getExchangeHistoryNum(Map<String,Object> params );

    /**
     * 活跃会员数
     *
     * @param params
     * @return
     */
    List<MemberExchangeHistoryLogModel> listMemberInfo(Map<String, Object> params);

    /**
     *
     * 功能描述: 明细
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/5/7 14:25
     */
    PageResult listPage(Map<String, Object> params);
}
