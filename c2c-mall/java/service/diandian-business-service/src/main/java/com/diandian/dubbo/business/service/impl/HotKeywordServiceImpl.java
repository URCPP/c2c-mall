package com.diandian.dubbo.business.service.impl;



import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.dubbo.business.mapper.HotKeywordMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.HotKeywordModel;
import com.diandian.dubbo.facade.service.biz.HotKeywordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 商品热搜关键字 服务实现类
 * </p>
 *
 * @author zzj
 * @since 2019-03-15
 */
@Service("hotKeywordService")
public class HotKeywordServiceImpl implements HotKeywordService {
    @Autowired
    HotKeywordMapper hotKeywordMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Integer state=(Integer) params.get("state");
        String keyword=(String) params.get("keyword");
        IPage<HotKeywordModel> hotKeywordModelIPage = hotKeywordMapper.selectPage(
                new PageWrapper<HotKeywordModel>(params).getPage(),
                new QueryWrapper<HotKeywordModel>()
                        .like(StringUtils.isNotEmpty(keyword),"keyword", (String) params.get("keyword"))
                        .eq(ObjectUtil.isNotNull(state),"state", state)
                        .orderByDesc("sort")
        );
        return new PageResult(hotKeywordModelIPage);
    }

    @Override
    public void saveHotKey(HotKeywordModel hotKeywordModel) {
        hotKeywordMapper.insert(hotKeywordModel);
    }

    @Override
    public void updateHotKeyById(HotKeywordModel hotKeywordModel) {
        hotKeywordMapper.updateById(hotKeywordModel);
    }

    @Override
    public void deleteHotKeyById(Long id) {
        hotKeywordMapper.deleteById(id);
    }
}
