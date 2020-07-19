package com.diandian.dubbo.facade.service.biz;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizMallSuccessCaseModel;

import java.util.List;
import java.util.Map;

/**
 * 商城成功案例表
 *
 * @author zweize
 * @date 2019/03/06
 */
public interface BizMallSuccessCaseService {

    PageResult listPage(Map<String,Object> params);

    void save(BizMallSuccessCaseModel bizMallSuccessCaseModel);

    void updateById(BizMallSuccessCaseModel bizMallSuccessCaseModel);

    void deleteById(Long id);

    BizMallSuccessCaseModel getById(Long id);

    /**
     * 获取根目录
     * @return
     */
    List<BizMallSuccessCaseModel> listCataLog();

}
