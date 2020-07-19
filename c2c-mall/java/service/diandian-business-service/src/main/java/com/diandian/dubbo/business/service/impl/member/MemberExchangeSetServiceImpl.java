package com.diandian.dubbo.business.service.impl.member;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.member.MemberExchangeSetMapper;
import com.diandian.dubbo.facade.model.member.MemberExchangeSetModel;
import com.diandian.dubbo.facade.service.member.MemberExchangeSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员兑换券充值设置表
 *
 * @author wbc
 * @date 2019/02/15
 */
@Service("memberExchangeSetService")
@Slf4j
public class MemberExchangeSetServiceImpl implements MemberExchangeSetService {

    @Autowired
    private MemberExchangeSetMapper memberExchangeSetMapper;


    @Override
    public boolean updateSet(MemberExchangeSetModel memberExchangeSetModel) {
        Long id = memberExchangeSetModel.getId();
        if (ObjectUtil.isNotNull(id)) {
            memberExchangeSetMapper.updateById(memberExchangeSetModel);
        } else {
            memberExchangeSetMapper.insert(memberExchangeSetModel);
        }
        return true;
    }

    @Override
    public MemberExchangeSetModel getSet() {
        QueryWrapper<MemberExchangeSetModel> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        qw.last("limit 1");
        return memberExchangeSetMapper.selectOne(qw);
    }

    @Override
    public MemberExchangeSetModel getSetByMerchantId(Long merchantId) {
        QueryWrapper<MemberExchangeSetModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        qw.orderByDesc("id");
        qw.last("limit 1");
        return memberExchangeSetMapper.selectOne(qw);
    }
}
