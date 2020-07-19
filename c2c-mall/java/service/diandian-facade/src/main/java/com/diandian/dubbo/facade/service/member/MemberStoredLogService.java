package com.diandian.dubbo.facade.service.member;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.member.MemberStoredLogDTO;
import com.diandian.dubbo.facade.model.member.MemberStoredLogModel;

import java.util.Map;

/**
 * 会员储值记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface MemberStoredLogService  {
    /**
     * 会员储值记录分页
     * @param params
     * @return
     */
    PageResult listPage(Map<String,Object> params);

    /**
     * 储值充值
     */
    void insertMemberStoredLog(MemberStoredLogModel storedLogModel);


}
