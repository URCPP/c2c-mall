package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.HotKeywordModel;

import java.util.Map;

/**
 * <p>
 * 商品热搜关键字 服务类
 * </p>
 *
 * @author zzj
 * @since 2019-03-15
 */
public interface HotKeywordService{
    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 新增保存
     *
     * @param hotKeywordModel
     */
    void saveHotKey(HotKeywordModel hotKeywordModel);

    /**
     * 通过ID更新
     *
     * @param hotKeywordModel
     */
    void updateHotKeyById(HotKeywordModel hotKeywordModel);

    /**
     * 通过ID逻辑删除
     *
     * @param id
     */
    void deleteHotKeyById(Long id);
}
