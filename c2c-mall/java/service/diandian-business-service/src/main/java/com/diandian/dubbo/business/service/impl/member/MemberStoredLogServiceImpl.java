package com.diandian.dubbo.business.service.impl.member;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.member.MemberStoredLogMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.member.MemberStoredLogModel;
import com.diandian.dubbo.facade.service.member.MemberStoredLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 会员储值记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Service("memberStoredLogService")
@Slf4j
public class MemberStoredLogServiceImpl implements MemberStoredLogService {
    @Autowired
    private MemberStoredLogMapper memberStoredLogMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Page<MemberStoredLogModel> page = new PageWrapper<MemberStoredLogModel>(params).getPage();
        IPage<MemberStoredLogModel> iPage = memberStoredLogMapper.listPage(page, params);
        return new PageResult(iPage);
    }

    @Override
    public void insertMemberStoredLog(MemberStoredLogModel storedLogModel) {
        memberStoredLogMapper.insert(storedLogModel);
    }
}
