package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.biz.BizNotifyInfoModel;

import java.util.List;


/**
 * 系统公告表
 *
 * @author jbh
 * @date 2019/02/26
 */
public interface BizNotifyInfoMapper extends BaseMapper<BizNotifyInfoModel> {

    /**
     *  根据id获取前后数据列表
     * @author: jbuhuan
     * @date: 2019/3/8 14:45
     * @param id
     * @return: R
     */
    List<BizNotifyInfoModel> listAround(Long id);
}
