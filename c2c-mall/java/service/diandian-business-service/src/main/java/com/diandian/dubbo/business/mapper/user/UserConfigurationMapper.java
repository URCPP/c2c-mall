package com.diandian.dubbo.business.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallHelpModel;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalRuleModel;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserConfigurationMapper extends BaseMapper<UserConfiguration> {
    int deleteByPrimaryKey(Integer id);

    int insert(UserConfiguration record);

    int insertSelective(UserConfiguration record);

    UserConfiguration selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserConfiguration record);

    int updateByPrimaryKey(UserConfiguration record);

    Page<UserConfiguration> listHelpPage(Page<UserConfiguration> page, @Param("params")Map<String,Object> params);

    UserConfiguration findAll();
}