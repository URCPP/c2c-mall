package com.diandian.dubbo.business.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.member.MemberInfoModel;
import com.diandian.dubbo.facade.vo.MemberInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 会员信息表
 *
 * @author wbc
 * @date 2019/02/13
 */
public interface MemberInfoMapper extends BaseMapper<MemberInfoModel> {


    IPage<MemberInfoVO> listPage(Page page, @Param("params") Map<String, Object> params);
}
