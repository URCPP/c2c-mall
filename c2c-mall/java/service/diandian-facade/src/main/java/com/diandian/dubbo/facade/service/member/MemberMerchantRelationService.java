package com.diandian.dubbo.facade.service.member;

import com.diandian.dubbo.facade.model.member.MemberMerchantRelationModel;

import java.util.List;
import java.util.Map;

/**
 * 商户下的会员帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface MemberMerchantRelationService {

    /**
     * 根据商户ID 及会员帐号 查会员帐户
     *
     * @param merchantId
     * @param memberAccount
     * @return
     */
    MemberMerchantRelationModel getMemberAccount(Long merchantId, String memberAccount);

    /**
     * 更新帐户
     *
     * @param memberAcc
     * @return
     */
    boolean updateAcc(MemberMerchantRelationModel memberAcc);

    /**
     * 商户会员列表
     *
     * @param params
     * @return
     */
    List<MemberMerchantRelationModel> listMemberInfo(Map<String, Object> params);

    /**
     * 会员ID 及  商户ID 获取会员帐户
     *
     * @param merchantId
     * @param memberId
     * @return
     */
    MemberMerchantRelationModel getMemberAccByMerIdAndMemId(Long merchantId, Long memberId);

    /**
     * 并发更新
     *
     * @param memberAcc
     * @param oldMemberAcc
     * @return
     */
    boolean updateAccByNum(MemberMerchantRelationModel memberAcc, MemberMerchantRelationModel oldMemberAcc);

    /**
     * 更新会员兑换券
     */
    void updateMemberAccount(String orderNo);

    /**
     * @Author wubc
     * @Description // 添加
     * @Date 18:04 2019/4/2
     * @Param [relationModel]
     * @return int
     **/
    int save(MemberMerchantRelationModel relationModel);
}
