package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizCategorySetMapper;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.common.util.TreeUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizCategorySetModel;
import com.diandian.dubbo.facade.service.biz.BizCategorySetService;
import com.diandian.dubbo.facade.vo.BizCategorySetListVO;
import com.diandian.dubbo.facade.vo.BizCategorySetVO;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行业类别设置表
 *
 * @author jbh
 * @date 2019/02/22
 */
@Service("bizCategorySetService")
@Slf4j
public class BizCategorySetServiceImpl  implements BizCategorySetService {
    @Autowired
    private BizCategorySetMapper categorySetMapper;
    @Override
    public PageResult listPage(Map<String, Object> params) {
        Page<BizCategorySetListVO> page = new PageWrapper<BizCategorySetListVO>(params).getPage();
        IPage<BizCategorySetListVO> iPage = categorySetMapper.listPage(page,params);
        return new PageResult(iPage);
    }

    @Override
    public List<BizCategorySetVO> listOneCategory() {
        return categorySetMapper.listOneCategory();
    }

    @Override
    public List<BizCategorySetVO> listTwoCategory(Long id) {
        return categorySetMapper.listTwoCategory(id);
    }

    @Override
    public void deleteCategory(Long id) {
        categorySetMapper.deleteById(id);
    }

    @Override
    public void updateCategory(BizCategorySetModel bizCategorySetModel) {
        categorySetMapper.updateById(bizCategorySetModel);
    }

    @Override
    public void saveCategory(BizCategorySetModel bizCategorySetModel) {
        String categoryName = bizCategorySetModel.getCategoryName();
        AssertUtil.notBlank(categoryName,"类别名称不能为空" );
        QueryWrapper<BizCategorySetModel> wrapper = new QueryWrapper<>();
        wrapper.eq("category_name", categoryName);
        BizCategorySetModel categorySetModel = categorySetMapper.selectOne(wrapper);
        AssertUtil.isNull(categorySetModel, "该类别名称已存在");
        categorySetMapper.insert(bizCategorySetModel);
    }

    @Override
    public List<IntactTreeVO> getIntactTreeList() {
        List<IntactTreeVO> list = categorySetMapper.getIntactTreeList();
        if(list.isEmpty()){
            return list;
        }
        List<IntactTreeVO> resultList = new ArrayList<>();
        resultList.add(list.remove(0));
        return TreeUtil.buildIntactTree(resultList, list, false, list.size());
    }
}
