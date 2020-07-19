package com.diandian.dubbo.business.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.member.MemberExchangeLogModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 会员兑换券充值记录表
 *
 * @author wbc
 * @date 2019/02/13
 */
public interface MemberExchangeLogMapper extends BaseMapper<MemberExchangeLogModel> {

    IPage<MemberExchangeLogModel> listPage(Page page, @Param("params") Map<String, Object> params);
}
