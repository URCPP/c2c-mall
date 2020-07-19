package com.diandian.admin.business.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.sys.mapper.SysAreaMapper;
import com.diandian.admin.business.modules.sys.service.SysAreaService;
import com.diandian.admin.model.sys.SysAreaModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 地区码表
 *
 * @author x
 * @date 2018/09/26
 */
@Service("sysAreaService")
@Slf4j
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysAreaModel> implements SysAreaService {

	@Autowired
	private SysAreaMapper sysAreaMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        IPage<SysAreaModel> page = this.page(new PageWrapper<SysAreaModel>(params).getPage(), null);
        return new PageResult(page);
    }

    @Override
    public List<SysAreaModel> listByParentId(Long parentId) {
        QueryWrapper<SysAreaModel> wrapper = new QueryWrapper<>();
        wrapper.eq("parentId", parentId);
        return this.list(wrapper);
    }

    @Override
    public List<SysAreaModel> list() {
        return baseMapper.selectList(null);
    }

}
