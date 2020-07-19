package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizCategorySetModel;
import com.diandian.dubbo.facade.vo.BizCategorySetListVO;
import com.diandian.dubbo.facade.vo.BizCategorySetVO;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 行业类别设置表
 *
 * @author jbh
 * @date 2019/02/22
 */
public interface BizCategorySetMapper extends BaseMapper<BizCategorySetModel> {

    /**
     * 获取一级分类列表
     * @return
     */
    List<BizCategorySetVO> listOneCategory();

    /**
     * 获取二级分类列表
     * @return
     */
    List<BizCategorySetVO> listTwoCategory(@Param("parentCategoryId") Long parentCategoryId);

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

    /**
     * 获取类目列表、分页
     * @author: jbuhuan
     * @date: 2019/3/11 22:03
     * @param params
     * @return: R
     */
    IPage<BizCategorySetListVO> listPage(Page page,@Param("params")Map<String,Object> params);
}
