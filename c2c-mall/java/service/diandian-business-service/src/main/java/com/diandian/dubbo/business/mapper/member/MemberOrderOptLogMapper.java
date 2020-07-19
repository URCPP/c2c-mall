package com.diandian.dubbo.business.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 会员订单操作记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface MemberOrderOptLogMapper extends BaseMapper<MemberOrderOptLogModel> {

    IPage<MemberOrderOptLogModel> listPage(Page page, @Param("params") Map<String, Object> params);

}
