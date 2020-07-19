package com.diandian.dubbo.facade.service.user;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.user.UserConfiguration;

import java.util.Map;

public interface UserConfigurationService {


    int deleteByPrimaryKey(Integer id);

    int insert(UserConfiguration record);

    int insertSelective(UserConfiguration record);

    UserConfiguration selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserConfiguration record);

    int updateByPrimaryKey(UserConfiguration record);

    PageResult listPage(Map<String, Object> params);

    UserConfiguration findAll();
}
