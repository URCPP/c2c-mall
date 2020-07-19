package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizNotifyInfoMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizNotifyInfoModel;
import com.diandian.dubbo.facade.service.biz.BizNotifyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统公告表
 *
 * @author jbh
 * @date 2019/02/26
 */
@Service("bizNotifyInfoService")
@Slf4j
public class BizNotifyInfoServiceImpl  implements BizNotifyInfoService {
    @Autowired
    private BizNotifyInfoMapper notifyInfoMapper;
    @Override
    public PageResult listPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        String stateFlag = (String) params.get("stateFlag");
        String typeFlag = (String) params.get("typeFlag");
        Page<BizNotifyInfoModel> page = new PageWrapper<BizNotifyInfoModel>(params).getPage();
        QueryWrapper<BizNotifyInfoModel> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(title),"title", title);
        if (StringUtils.isNotBlank(stateFlag)) {
            wrapper.eq("state_flag", Integer.valueOf(stateFlag));
        }
        if (StringUtils.isNotBlank(typeFlag)) {
            wrapper.eq("type_flag", Integer.valueOf(typeFlag));
        }
        wrapper.orderByDesc("id");
        IPage<BizNotifyInfoModel> iPage = notifyInfoMapper.selectPage(page, wrapper);
        return new PageResult(iPage);
    }

    @Override
    public void saveNotify(BizNotifyInfoModel notifyInfoModel) {
        Date date = new Date();
        notifyInfoModel.setUpdateTime(date);
        notifyInfoModel.setCreateTime(date);
        notifyInfoMapper.insert(notifyInfoModel);
    }

    @Override
    public void updateNotify(BizNotifyInfoModel notifyInfoModel) {
        notifyInfoModel.setUpdateTime(new Date());
        notifyInfoMapper.updateById(notifyInfoModel);
    }

    @Override
    public void deleteNotify(Long id) {
        notifyInfoMapper.deleteById(id);
    }

    @Override
    public List<BizNotifyInfoModel> listNotifyByQuery() {
        QueryWrapper<BizNotifyInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("state_flag", 0)
                .eq("new_flag", 1)
                .orderByDesc("id");
        List<BizNotifyInfoModel> list = notifyInfoMapper.selectList(wrapper);
        List<BizNotifyInfoModel> newList = new ArrayList<>();
        if (list!=null && !list.isEmpty()) {
            if (list.size()<=5) {
                for (BizNotifyInfoModel model : list) {
                    newList.add(model);
                }
            } else {
                for (int i = 0; i <5; i++) {
                    newList.add(list.get(i));
                }
            }
        }
        return newList;
    }

    @Override
    public BizNotifyInfoModel getNotify(Long id) {
        return notifyInfoMapper.selectById(id);
    }

    @Override
    public List<BizNotifyInfoModel> listAround(Long id) {
        return notifyInfoMapper.listAround(id);
    }
}
