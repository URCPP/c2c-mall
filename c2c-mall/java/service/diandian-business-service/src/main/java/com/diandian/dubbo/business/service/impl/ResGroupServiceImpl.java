package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.ResGroupMapper;
import com.diandian.dubbo.business.mapper.ResOssMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.res.ResGroupModel;
import com.diandian.dubbo.facade.model.res.ResOssModel;
import com.diandian.dubbo.facade.service.res.ResGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 资源分组
 *
 * @author zzhihong
 * @date 2019/02/19
 */
@Service("resGroupService")
@Slf4j
public class ResGroupServiceImpl implements ResGroupService {

    @Autowired
    private ResGroupMapper resGroupMapper;
    @Autowired
    private ResOssMapper resOssMapper;

    @Override
    public List<ResGroupModel> list(Map<String, Object> params) {
        String resType = (String) params.get("resType");
        return resGroupMapper.selectList(new QueryWrapper<ResGroupModel>().eq("res_type",resType));
    }

    @Override
    public ResGroupModel getById(Long id) {
        return resGroupMapper.selectById(id);
    }

    @Override
    public void save(ResGroupModel resGroupModel) {
        resGroupMapper.insert(resGroupModel);
    }

    @Override
    public void updateById(ResGroupModel resGroupModel) {
        resGroupMapper.updateById(resGroupModel);
    }

    @Override
    public void deleteById(Long id) {
        resGroupMapper.deleteById(id);
        ResOssModel resOssModel = new ResOssModel();
        resOssModel.setGroupId(0L);
        resOssMapper.update(resOssModel,new QueryWrapper<ResOssModel>().eq("group_id",id));
    }
}
