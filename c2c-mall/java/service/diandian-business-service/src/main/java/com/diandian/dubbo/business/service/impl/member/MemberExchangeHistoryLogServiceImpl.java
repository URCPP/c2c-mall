package com.diandian.dubbo.business.service.impl.member;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.member.MemberExchangeHistoryLogMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.member.MemberExchangeHistoryLogDTO;
import com.diandian.dubbo.facade.model.member.MemberExchangeHistoryLogModel;
import com.diandian.dubbo.facade.service.member.MemberExchangeHistoryLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商户会员兑换券变动台账表
 *
 * @author wbc
 * @date 2019/03/04
 */
@Service("memberExchangeHistoryLogService")
@Slf4j
public class MemberExchangeHistoryLogServiceImpl implements MemberExchangeHistoryLogService {

    @Autowired
    private MemberExchangeHistoryLogMapper memberExchangeHistoryLogMapper;


    @Override
    public boolean save(MemberExchangeHistoryLogModel exchangeHistoryLogModel) {
        int insert = memberExchangeHistoryLogMapper.insert(exchangeHistoryLogModel);
        return true;
    }

    @Override
    public MemberExchangeHistoryLogDTO getExchangeHistoryNum(Map<String, Object> params) {
        return memberExchangeHistoryLogMapper.getExchangeHistoryNum(params);
    }


    @Override
    public List<MemberExchangeHistoryLogModel> listMemberInfo(Map<String, Object> params) {
        return memberExchangeHistoryLogMapper.listMemberInfo(params);
    }

    @Override
    public PageResult listPage(Map<String, Object> params){
        Page<MemberExchangeHistoryLogModel> page = new PageWrapper<MemberExchangeHistoryLogModel>(params).getPage();
        QueryWrapper<MemberExchangeHistoryLogModel> qw = new QueryWrapper<>();
        Object merchantId = params.get("merchantId");
        Object memberId = params.get("memberId");
        if(ObjectUtil.isNotNull(merchantId)){
            qw.eq("merchant_id", Long.valueOf(merchantId.toString()));
        }
        if(ObjectUtil.isNotNull(memberId)){
            qw.eq("member_id", Long.valueOf(memberId.toString()));
        }
        qw.orderByDesc("id");
        IPage<MemberExchangeHistoryLogModel> iPage = memberExchangeHistoryLogMapper.selectPage(page, qw);
        return new PageResult(iPage);
    }
}
