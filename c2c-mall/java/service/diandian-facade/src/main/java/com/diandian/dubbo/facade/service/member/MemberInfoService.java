package com.diandian.dubbo.facade.service.member;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.member.MemberInfoDTO;
import com.diandian.dubbo.facade.model.member.MemberInfoModel;

import java.util.Map;

/**
 * 会员信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface MemberInfoService {
    /**
     * id查询会员
     * @param id
     * @return
     */
    MemberInfoModel getMemberById(Long id);

    /**
     * 根据会员账号查询会员信息
     * @param memberAccount
     * @return
     */
    MemberInfoModel getByMemberAccount(String memberAccount);

    /**
     * 会员列表分页
     * @param params
     * @return
     */
    PageResult listPage(Map<String,Object> params);

    /**
     * 会员注册
     * @param dto
     */
    boolean memberRegister(MemberInfoDTO dto);

    /**
     * 重置密码
     * @param dto
     */
    void resetPassword(MemberInfoDTO dto);

    /**
     *
     * 功能描述: 获取会员在某商户下的兑换券数量
     *
     * @param merchantId
     * @param memberId
     * @return
     * @author cjunyuan
     * @date 2019/4/22 13:47
     */
    Integer getMemberExchange(Long merchantId, Long memberId);

    /**
     *
     * 功能描述: 积分订单支付运费成功后回调
     *
     * @param orderNo
     * @param tradeNo
     * @param tradeWay
     * @return
     * @author cjunyuan
     * @date 2019/4/28 10:54
     */
    void exchangePaySuccess(String orderNo, String tradeNo, String tradeWay);

    /**
     * 根据会员手机号查询会员信息
     */
    MemberInfoModel getByPhone(String phone);
}
