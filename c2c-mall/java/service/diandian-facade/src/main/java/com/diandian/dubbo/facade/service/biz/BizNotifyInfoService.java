package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizNotifyInfoModel;

import java.util.List;
import java.util.Map;


/**
 * 系统公告表
 *
 * @author jbh
 * @date 2019/02/26
 */
public interface BizNotifyInfoService  {
    /**
     * 系统公告列表、分页
     * @param params
     * @return
     */
    PageResult listPage(Map<String,Object> params);

    /**
     * 添加
     * @param notifyInfoModel
     */
    void saveNotify(BizNotifyInfoModel notifyInfoModel);

    /**
     * 修改
     * @param notifyInfoModel
     */
    void updateNotify(BizNotifyInfoModel notifyInfoModel);

    /**
     * 删除
     * @param id
     */
    void deleteNotify(Long id);

    /**
     * 最新5条系统公告列表
     * @return
     */
    List<BizNotifyInfoModel> listNotifyByQuery();

    /**
     * 根据id查询公告
     * @param id
     */
    BizNotifyInfoModel getNotify(Long id);

    /**
     * 前后数据列表
     * @author: jbuhuan
     * @date: 2019/3/8 14:51
     * @param id
     * @return: R
     */
    List<BizNotifyInfoModel> listAround(Long id);
}
