package com.diandian.dubbo.business.service.impl.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.user.UserConfigurationMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.user.UserConfiguration;
import com.diandian.dubbo.facade.service.user.UserConfigurationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
@Service("userConfigurationService")
public class UserConfigurationServiceImpl implements UserConfigurationService {

    @Resource
    private UserConfigurationMapper userConfigurationMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userConfigurationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserConfiguration record) {
        return userConfigurationMapper.insert(record);
    }

    @Override
    public int insertSelective(UserConfiguration record) {
        return userConfigurationMapper.insertSelective(record);
    }

    @Override
    public UserConfiguration selectByPrimaryKey(Integer id) {
        return userConfigurationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserConfiguration record) {
        return userConfigurationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserConfiguration record) {
        return userConfigurationMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Page<UserConfiguration> page = new PageWrapper<UserConfiguration>(params).getPage();
        Page resultPage = userConfigurationMapper.listHelpPage(page, params);
        return new PageResult(resultPage);
    }

    @Override
    public UserConfiguration findAll() {
        return userConfigurationMapper.findAll();
    }


}
