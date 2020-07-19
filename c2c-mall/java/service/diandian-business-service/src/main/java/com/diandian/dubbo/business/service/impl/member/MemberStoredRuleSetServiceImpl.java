package com.diandian.dubbo.business.service.impl.member;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.member.MemberStoredRuleSetMapper;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.model.member.MemberStoredRuleSetModel;
import com.diandian.dubbo.facade.service.member.MemberStoredRuleSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 会员储值设置表
 *
 * @author wbc
 * @date 2019/02/15
 */
@Service("memberStoredRuleSetService")
@Slf4j
public class MemberStoredRuleSetServiceImpl implements MemberStoredRuleSetService {

    @Autowired
    private MemberStoredRuleSetMapper memberStoredRuleSetMapper;


    @Override
    public boolean insertSet(MemberStoredRuleSetModel memberStoredRuleSetModel) {
        BigDecimal storedAmount = memberStoredRuleSetModel.getStoredAmount();
        AssertUtil.notNull(storedAmount, "储值金额不能为空");
        QueryWrapper<MemberStoredRuleSetModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", memberStoredRuleSetModel.getMerchantId());
        List<MemberStoredRuleSetModel> list = memberStoredRuleSetMapper.selectList(wrapper);
        wrapper.eq("stored_amount", storedAmount);
        MemberStoredRuleSetModel setModel = memberStoredRuleSetMapper.selectOne(wrapper);
        AssertUtil.isNull(setModel, "该储值金额比例已存在");
        if (list == null || (list != null && list.size() < 9)) {
            memberStoredRuleSetMapper.insert(memberStoredRuleSetModel);
        }
        return true;
    }

    @Override
    public boolean updateSet(MemberStoredRuleSetModel memberStoredRuleSetModel) {
        memberStoredRuleSetMapper.updateById(memberStoredRuleSetModel);
        return true;
    }

    @Override
    public List<MemberStoredRuleSetModel> listSets(Map<String, Object> params, Long merchantInfoId) {
        params.put("merchant_id", merchantInfoId);
        return memberStoredRuleSetMapper.selectByMap(params);
    }

    @Override
    public MemberStoredRuleSetModel getSetById(Long id) {
        return memberStoredRuleSetMapper.selectById(id);
    }
}
