package com.diandian.dubbo.business.service.impl.member;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.member.MemberRecipientsSetMapper;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.model.member.MemberRecipientsSetModel;
import com.diandian.dubbo.facade.service.member.MemberRecipientsSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员收货地址设置表
 *
 * @author wbc
 * @date 2019/03/13
 */
@Service("memberRecipientsSetService")
@Slf4j
public class MemberRecipientsSetServiceImpl implements MemberRecipientsSetService {

    @Autowired
    private MemberRecipientsSetMapper memberRecipientsSetMapper;


    @Override
    public MemberRecipientsSetModel getOne(Long memberId) {
        QueryWrapper<MemberRecipientsSetModel> qw = new QueryWrapper<>();
        qw.eq("state", MemberConstant.NORMAL);
        qw.eq("member_id", memberId);
        return memberRecipientsSetMapper.selectOne(qw);
    }

    @Override
    public Integer save(MemberRecipientsSetModel model) {
        return memberRecipientsSetMapper.insert(model);
    }

    @Override
    public MemberRecipientsSetModel getById(Long id) {
        return memberRecipientsSetMapper.selectById(id);
    }

    @Override
    public Integer update(MemberRecipientsSetModel old) {
        return memberRecipientsSetMapper.updateById(old);
    }
}
