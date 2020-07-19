package com.diandian.dubbo.facade.service.biz;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallHelpModel;

import java.util.List;
import java.util.Map;

/**
 * 商城帮助配置表
 *
 * @author zweize
 * @date 2019/02/27
 */
public interface BizMallHelpService {

    PageResult listPage(Map<String,Object> params);

    void save(BizMallHelpModel bizMallHelpModel);

    void updateById(BizMallHelpModel bizMallHelpModel);

    void deleteById(Long id);

    BizMallHelpModel getById(Long id);

    /**
     * 获取根目录
     * @return
     */
    List<BizMallHelpModel> listCataLog();

    /**
     * @Author wubc
     * @Description // 内容列表
     * @Date 20:57 2019/3/13
     * @Param []
     * @return java.util.List<com.diandian.dubbo.facade.model.biz.BizMallHelpModel>
     **/
    List<BizMallHelpModel> listContent();
}
