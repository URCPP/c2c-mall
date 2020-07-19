package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizCategorySetModel;
import com.diandian.dubbo.facade.vo.BizCategorySetVO;
import com.diandian.dubbo.facade.vo.IntactTreeVO;

import java.util.List;
import java.util.Map;

/**
 * 行业类别设置表
 *
 * @author jbh
 * @date 2019/02/22
 */
public interface BizCategorySetService  {

    /**
     * 一级类目列表
     * @return
     */
    List<BizCategorySetVO> listOneCategory();

    /**
     * 二级类目列表
     * @return
     */
    List<BizCategorySetVO> listTwoCategory(Long id);

    /**
     * 行业类别列表、分页
     * @param params
     * @return
     */
    PageResult listPage(Map<String,Object> params);

    /**
     * 删除
     * @param id
     */
    void deleteCategory(Long id);

    /**
     * 修改
     * @param bizCategorySetModel
     */
    void updateCategory(BizCategorySetModel bizCategorySetModel);

    /**
     * 新增
     * @param bizCategorySetModel
     */
    void saveCategory(BizCategorySetModel bizCategorySetModel);

    /**
     *
     * 功能描述: 获取所有的行业类别
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/2/28 15:46
     */
    List<IntactTreeVO> getIntactTreeList();

}
