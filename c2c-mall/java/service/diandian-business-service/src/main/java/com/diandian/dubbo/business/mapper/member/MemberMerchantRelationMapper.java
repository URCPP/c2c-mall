package com.diandian.dubbo.business.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.member.MemberMerchantRelationModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 商户下的会员帐户信息表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface MemberMerchantRelationMapper extends BaseMapper<MemberMerchantRelationModel> {

    /**
     * 商户会员列表
     *
     * @param params
     * @return
     */
    List<MemberMerchantRelationModel> listMemberInfo(Map<String, Object> params);

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
    Integer getMemberExchange(@Param("mchId") Long merchantId, @Param("mbId") Long memberId);
}
